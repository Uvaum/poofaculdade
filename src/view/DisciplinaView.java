package view;

import dao.CursoDAO;
import model.Curso;
import model.Disciplina;

import java.util.List;
import java.util.Scanner;

public class DisciplinaView {

    public static void Criar(Disciplina disciplina) {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n=== CADASTRAR DISCIPLINA ===");
        System.out.print("Código: ");
        disciplina.setCodigo(scan.nextLine());

        System.out.print("Nome: ");
        disciplina.setNome(scan.nextLine());

        System.out.print("Carga Horária: ");
        disciplina.setCargaHoraria(scan.nextInt());
        scan.nextLine(); // Limpar buffer

        // Exibir cursos disponíveis
        List<Curso> cursos = CursoDAO.GetAll();
        if (cursos.isEmpty()) {
            System.out.println("\nERRO: Nenhum curso cadastrado no sistema!");
            System.out.println("É necessário cadastrar ao menos um curso antes de cadastrar disciplinas.");
            disciplina.setCodigo(null); // Invalida a disciplina
            return;
        }

        System.out.println("\n=== CURSOS DISPONÍVEIS ===");
        for (Curso c : cursos) {
            System.out.println(c.getCodigo() + " - " + c.getNome());
        }

        String codigoCurso;
        boolean cursoValido = false;
        do {
            System.out.print("\nInforme o código do curso ao qual a disciplina pertence: ");
            codigoCurso = scan.nextLine();
            
            // Validar se o curso existe
            Curso cursoSelecionado = CursoDAO.Get(codigoCurso);
            if (cursoSelecionado != null) {
                disciplina.setCodigoCurso(codigoCurso);
                cursoValido = true;
                System.out.println("Disciplina será vinculada ao curso: " + cursoSelecionado.getNome());
            } else {
                System.out.println("ERRO: Código de curso inválido! Tente novamente.");
            }
        } while (!cursoValido);
    }

    public static void Atualizar(Disciplina disciplina) {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n=== ATUALIZAR DISCIPLINA ===");
        System.out.println("(Pressione ENTER para manter o valor atual)");

        System.out.print("Nome (" + disciplina.getNome() + "): ");
        String nome = scan.nextLine();
        if (!nome.isEmpty()) {
            disciplina.setNome(nome);
        }

        System.out.print("Carga Horária (" + disciplina.getCargaHoraria() + "): ");
        String cargaStr = scan.nextLine();
        if (!cargaStr.isEmpty()) {
            try {
                disciplina.setCargaHoraria(Integer.parseInt(cargaStr));
            } catch (NumberFormatException e) {
                System.out.println("Carga horária inválida, mantendo valor atual.");
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
                System.out.print("\nInforme o novo código do curso (atual: " + disciplina.getCodigoCurso() + "): ");
                codigoCurso = scan.nextLine();
                
                if (!codigoCurso.isEmpty()) {
                    Curso cursoSelecionado = CursoDAO.Get(codigoCurso);
                    if (cursoSelecionado != null) {
                        disciplina.setCodigoCurso(codigoCurso);
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
    }

    public static void Listar(List<Disciplina> disciplinas) {
        System.out.println("\n=== LISTA DE DISCIPLINAS ===");
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
        } else {
            for (Disciplina d : disciplinas) {
                Consultar(d);
                System.out.println("-------------------------");
            }
        }
    }

    public static void Consultar(Disciplina disciplina) {
        System.out.println("\n=== DADOS DA DISCIPLINA ===");
        System.out.println("Código: " + disciplina.getCodigo());
        System.out.println("Nome: " + disciplina.getNome());
        System.out.println("Carga Horária: " + disciplina.getCargaHoraria() + "h");
        
        // Buscar e exibir nome do curso
        Curso curso = CursoDAO.Get(disciplina.getCodigoCurso());
        if (curso != null) {
            System.out.println("Curso: " + curso.getNome() + " (" + curso.getCodigo() + ")");
        } else {
            System.out.println("Curso: Não encontrado");
        }
    }

    public static String GetCodigo() {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nInforme o código da disciplina: ");
        return scan.nextLine();
    }

}
