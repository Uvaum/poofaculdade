package view;

import model.Curso;
import model.Disciplina;
import dao.DisciplinaDAO;

import java.util.List;
import java.util.Scanner;

public class CursoView {

    public static void Criar(Curso curso) {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n=== CADASTRAR CURSO ===");
        System.out.print("Código: ");
        curso.setCodigo(scan.nextLine());

        System.out.print("Nome: ");
        curso.setNome(scan.nextLine());

        System.out.print("Duração (em semestres): ");
        curso.setDuracao(scan.nextInt());
        scan.nextLine();

        String turno;
        boolean turnoValido = false;
        do {
            System.out.println("\nTurnos disponíveis:");
            System.out.println("1 - Manhã");
            System.out.println("2 - Tarde");
            System.out.println("3 - Noite");
            System.out.print("Escolha o turno (1-3): ");

            int opcaoTurno = scan.nextInt();
            scan.nextLine(); // Limpar buffer

            switch (opcaoTurno) {
                case 1 -> {
                    curso.setTurno("Manhã");
                    turnoValido = true;
                }
                case 2 -> {
                    curso.setTurno("Tarde");
                    turnoValido = true;
                }
                case 3 -> {
                    curso.setTurno("Noite");
                    turnoValido = true;
                }
                default -> System.out.println("ERRO: Opção inválida! Tente novamente.");
            }
        } while (!turnoValido);


    }

    public static void GerenciarDisciplinas(String codigoCurso) {
        Scanner scan = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== GERENCIAR DISCIPLINAS DO CURSO ===");
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
                            DisciplinaDAO.AddDisciplinaToCurso(codigo, codigoCurso);
                        } else {
                            System.out.println("Disciplina inválida!");
                        }
                    }
                }
                case 2 -> {
                    List<Disciplina> disciplinasCurso = DisciplinaDAO.GetByCurso(codigoCurso);
                    if (disciplinasCurso.isEmpty()) {
                        System.out.println("Curso não possui disciplinas.");
                    } else {
                        System.out.println("\n=== DISCIPLINAS DO CURSO ===");
                        for (Disciplina d : disciplinasCurso) {
                            System.out.println(d.getCodigo() + " - " + d.getNome());
                        }
                        System.out.print("\nCódigo da disciplina a remover: ");
                        String codigo = scan.nextLine();
                        DisciplinaDAO.RemoveDisciplinaFromCurso(codigo, codigoCurso);
                    }
                }
                case 3 -> continuar = false;
            }
        }
    }

    public static void Atualizar(Curso curso) {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n=== ATUALIZAR CURSO ===");
        System.out.println("(Pressione ENTER para manter o valor atual)");

        System.out.print("Nome (" + curso.getNome() + "): ");
        String nome = scan.nextLine();
        if (!nome.isEmpty()) {
            curso.setNome(nome);
        }

        System.out.print("Duração (" + curso.getDuracao() + " semestres): ");
        String duracaoStr = scan.nextLine();
        if (!duracaoStr.isEmpty()) {
            try {
                curso.setDuracao(Integer.parseInt(duracaoStr));
            } catch (NumberFormatException e) {
                System.out.println("Duração inválida, mantendo valor atual.");
            }
        }

        System.out.print("Deseja alterar o turno? (S/N): ");
        String alterarTurno = scan.nextLine();
        if (alterarTurno.equalsIgnoreCase("S")) {
            String turno;
            boolean turnoValido = false;
            do {
                System.out.println("\nTurnos disponíveis:");
                System.out.println("1 - Manhã");
                System.out.println("2 - Tarde");
                System.out.println("3 - Noite");
                System.out.print("Escolha o turno (1-3, atual: " + curso.getTurno() + "): ");

                int opcaoTurno = scan.nextInt();
                scan.nextLine();

                switch (opcaoTurno) {
                    case 1 -> {
                        curso.setTurno("Manhã");
                        turnoValido = true;
                    }
                    case 2 -> {
                        curso.setTurno("Tarde");
                        turnoValido = true;
                    }
                    case 3 -> {
                        curso.setTurno("Noite");
                        turnoValido = true;
                    }
                    default -> System.out.println("ERRO: Opção inválida! Tente novamente.");
                }
            } while (!turnoValido);
        }

        System.out.print("\nDeseja gerenciar as disciplinas? (S/N): ");
        String gerenciarDisc = scan.nextLine();
        if (gerenciarDisc.equalsIgnoreCase("S")) {
            GerenciarDisciplinas(curso.getCodigo());
        }


    }

    public static void Listar(List<Curso> cursos) {
        System.out.println("\n=== LISTA DE CURSOS ===");
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
        } else {
            for (Curso c : cursos) {
                Consultar(c);
                System.out.println("-------------------------");
            }
        }
    }

    public static void Consultar(Curso curso) {
        System.out.println("\n=== DADOS DO CURSO ===");
        System.out.println("Código: " + curso.getCodigo());
        System.out.println("Nome: " + curso.getNome());
        System.out.println("Duração: " + curso.getDuracao() + " semestres");
        System.out.println("Turno: " + curso.getTurno());

        List<Disciplina> disciplinas = DisciplinaDAO.GetByCurso(curso.getCodigo());
        if (disciplinas.isEmpty()) {
            System.out.println("\nDisciplinas: Nenhuma disciplina vinculada");
        } else {
            System.out.println("\nDisciplinas do curso:");
            int totalCargaHoraria = 0;
            for (Disciplina d : disciplinas) {
                System.out.println("  • " + d.getNome() + " (" + d.getCodigo() + ") - " + d.getCargaHoraria() + "h");
                totalCargaHoraria += d.getCargaHoraria();
            }
            System.out.println("Total de carga horária: " + totalCargaHoraria + "h");
        }
    }

    public static String GetCodigo() {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nInforme o código do curso: ");
        return scan.nextLine();
    }

}