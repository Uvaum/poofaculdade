package view;


import dao.AlunoDAO;
import dao.CursoDAO;
import dao.DisciplinaDAO;
import model.Aluno;
import model.Curso;
import model.Disciplina;

import java.util.List;
import java.util.Scanner;

public class AlunoView {

    public static void Criar(Aluno aluno) {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n=== CADASTRAR ALUNO ===");
        System.out.print("Nome: ");
        aluno.setNome(scan.nextLine());

        System.out.print("Idade: ");
        aluno.setIdade(scan.nextInt());
        scan.nextLine(); // Limpar buffer

        System.out.print("Matrícula: ");
        aluno.setMatricula(scan.nextLine());

        // Exibir cursos disponíveis
        List<Curso> cursos = CursoDAO.GetAll();
        if (cursos.isEmpty()) {
            System.out.println("\nERRO: Nenhum curso cadastrado no sistema!");
            System.out.println("É necessário cadastrar ao menos um curso antes de cadastrar alunos.");
            aluno.setMatricula(null); // Invalida o aluno
            return;
        }

        System.out.println("\n=== CURSOS DISPONÍVEIS ===");
        for (Curso c : cursos) {
            System.out.println(c.getCodigo() + " - " + c.getNome());
        }

        String codigoCurso;
        boolean cursoValido = false;
        do {
            System.out.print("\nInforme o código do curso: ");
            codigoCurso = scan.nextLine();
        
            // Validar se o curso existe
            Curso cursoSelecionado = CursoDAO.Get(codigoCurso);
            if (cursoSelecionado != null) {
                aluno.setCodigoCurso(codigoCurso);
                cursoValido = true;
                System.out.println("Curso selecionado: " + cursoSelecionado.getNome());
            } else {
                System.out.println("ERRO: Código de curso inválido! Tente novamente.");
            }
        } while (!cursoValido);

        // REMOVER esta linha - as disciplinas serão adicionadas depois no Controller
        // AdicionarDisciplinas(aluno.getMatricula());
    }

    public static void AdicionarDisciplinas(String matriculaAluno) {
        Scanner scan = new Scanner(System.in);
        
        List<Disciplina> disciplinas = DisciplinaDAO.GetAll();
        if (disciplinas.isEmpty()) {
            System.out.println("\nNenhuma disciplina cadastrada no sistema.");
            return;
        }

        System.out.println("\n=== ADICIONAR DISCIPLINAS AO ALUNO ===");
        System.out.println("Disciplinas disponíveis:");
        for (Disciplina d : disciplinas) {
            System.out.println(d.getCodigo() + " - " + d.getNome() + " (" + d.getCargaHoraria() + "h)");
        }

        boolean continuar = true;
        while (continuar) {
            System.out.print("\nInforme o código da disciplina (ou 'S' para sair): ");
            String codigo = scan.nextLine();

            if (codigo.equalsIgnoreCase("S")) {
                continuar = false;
            } else {
                Disciplina disciplina = DisciplinaDAO.Get(codigo);
                if (disciplina != null) {
                    AlunoDAO.AddDisciplina(matriculaAluno, codigo);
                } else {
                    System.out.println("ERRO: Código de disciplina inválido!");
                }
            }
        }
    }

    public static void GerenciarDisciplinas(String matriculaAluno) {
        Scanner scan = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== GERENCIAR DISCIPLINAS DO ALUNO ===");
            System.out.println("1 - Adicionar disciplina");
            System.out.println("2 - Remover disciplina");
            System.out.println("3 - Voltar");
            System.out.print("Opção: ");
            
            int opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao) {
                case 1 -> {
                    List<Disciplina> disciplinas = DisciplinaDAO.GetAll();
                    if (disciplinas.isEmpty()) {
                        System.out.println("Nenhuma disciplina disponível.");
                    } else {
                        System.out.println("\n=== DISCIPLINAS DISPONÍVEIS ===");
                        for (Disciplina d : disciplinas) {
                            System.out.println(d.getCodigo() + " - " + d.getNome());
                        }
                        System.out.print("\nCódigo da disciplina: ");
                        String codigo = scan.nextLine();
                        if (DisciplinaDAO.Get(codigo) != null) {
                            AlunoDAO.AddDisciplina(matriculaAluno, codigo);
                        } else {
                            System.out.println("Disciplina inválida!");
                        }
                    }
                }
                case 2 -> {
                    List<String> codigosDisciplinas = AlunoDAO.GetDisciplinasCodigos(matriculaAluno);
                    if (codigosDisciplinas.isEmpty()) {
                        System.out.println("Aluno não possui disciplinas.");
                    } else {
                        System.out.println("\n=== DISCIPLINAS DO ALUNO ===");
                        for (String cod : codigosDisciplinas) {
                            Disciplina d = DisciplinaDAO.Get(cod);
                            if (d != null) {
                                System.out.println(d.getCodigo() + " - " + d.getNome());
                            }
                        }
                        System.out.print("\nCódigo da disciplina a remover: ");
                        String codigo = scan.nextLine();
                        AlunoDAO.RemoveDisciplina(matriculaAluno, codigo);
                    }
                }
                case 3 -> continuar = false;
            }
        }
    }

    public static void Atualizar(Aluno aluno) {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n=== ATUALIZAR ALUNO ===");
        System.out.println("(Pressione ENTER para manter o valor atual)");

        System.out.print("Nome (" + aluno.getNome() + "): ");
        String nome = scan.nextLine();
        if (!nome.isEmpty()) {
            aluno.setNome(nome);
        }

        System.out.print("Idade (" + aluno.getIdade() + "): ");
        String idadeStr = scan.nextLine();
        if (!idadeStr.isEmpty()) {
            try {
                aluno.setIdade(Integer.parseInt(idadeStr));
            } catch (NumberFormatException e) {
                System.out.println("Idade inválida, mantendo valor atual.");
            }
        }

        // Permitir alteração do curso
        System.out.print("Deseja alterar o curso? (S/N): ");
        String alterarCurso = scan.nextLine();
        if (alterarCurso.equalsIgnoreCase("S")) {
            List<Curso> cursos = CursoDAO.GetAll();
            System.out.println("\n=== CURSOS DISPONÍVEIS ===");
            for (Curso c : cursos) {
                System.out.println(c.getCodigo() + " - " + c.getNome());
            }

            String codigoCurso;
            boolean cursoValido = false;
            do {
                System.out.print("\nInforme o novo código do curso (atual: " + aluno.getCodigoCurso() + "): ");
                codigoCurso = scan.nextLine();
                
                if (!codigoCurso.isEmpty()) {
                    Curso cursoSelecionado = CursoDAO.Get(codigoCurso);
                    if (cursoSelecionado != null) {
                        aluno.setCodigoCurso(codigoCurso);
                        cursoValido = true;
                        System.out.println("Curso alterado para: " + cursoSelecionado.getNome());
                    } else {
                        System.out.println("ERRO: Código de curso inválido! Tente novamente.");
                    }
                } else {
                    cursoValido = true; // Mantém o curso atual
                }
            } while (!cursoValido);
        }

        // Gerenciar disciplinas
        System.out.print("\nDeseja gerenciar as disciplinas? (S/N): ");
        String gerenciarDisc = scan.nextLine();
        if (gerenciarDisc.equalsIgnoreCase("S")) {
            GerenciarDisciplinas(aluno.getMatricula());
        }
    }

    public static void Listar(List<Aluno> alunos) {
        System.out.println("\n=== LISTA DE ALUNOS ===");
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            for (Aluno a : alunos) {
                Consultar(a);
                System.out.println("-------------------------");
            }
        }
    }

    public static void Consultar(Aluno aluno) {
        System.out.println("\n=== DADOS DO ALUNO ===");
        System.out.println("Matrícula: " + aluno.getMatricula());
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("Idade: " + aluno.getIdade());
        
        // Buscar e exibir nome do curso
        Curso curso = CursoDAO.Get(aluno.getCodigoCurso());
        if (curso != null) {
            System.out.println("Curso: " + curso.getNome() + " (" + curso.getCodigo() + ")");
            System.out.println("Duração: " + curso.getDuracao() + " semestres");
        } else {
            System.out.println("Curso: Não encontrado");
        }

        // Buscar e exibir disciplinas do aluno
        List<String> codigosDisciplinas = AlunoDAO.GetDisciplinasCodigos(aluno.getMatricula());;
        if (codigosDisciplinas.isEmpty()) {
            System.out.println("\nDisciplinas: Nenhuma disciplina matriculada");
        } else {
            System.out.println("\nDisciplinas matriculadas:");
            int totalCargaHoraria = 0;
            for (String codigo : codigosDisciplinas) {
                Disciplina disciplina = DisciplinaDAO.Get(codigo);
                if (disciplina != null) {
                    System.out.println("  • " + disciplina.getNome() + " (" + disciplina.getCodigo() + ") - " + disciplina.getCargaHoraria() + "h");
                    totalCargaHoraria += disciplina.getCargaHoraria();
                }
            }
            System.out.println("Total de carga horária: " + totalCargaHoraria + "h");
        }
    }

    public static String GetMatricula() {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nInforme a matrícula: ");
        return scan.nextLine();
    }

}