package view;

import dao.CursoDAO;
import dao.DisciplinaDAO;
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

        System.out.println("\nDisciplina cadastrada! Agora você pode vinculá-la a um ou mais cursos.");
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

        // Buscar e exibir cursos aos quais a disciplina pertence
        List<String> codigosCursos = DisciplinaDAO.GetCursosDaDisciplina(disciplina.getCodigo());
        if (codigosCursos.isEmpty()) {
            System.out.println("Cursos: Nenhum curso vinculado");
        } else {
            System.out.println("\nCursos que oferecem esta disciplina:");
            for (String codigo : codigosCursos) {
                Curso curso = CursoDAO.Get(codigo);
                if (curso != null) {
                    System.out.println("  • " + curso.getNome() + " (" + curso.getCodigo() + ") - " + curso.getTurno());
                }
            }
        }
    }

    public static String GetCodigo() {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nInforme o código da disciplina: ");
        return scan.nextLine();
    }

}
