package view;

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

        System.out.print("Professor: ");
        disciplina.setProfessor(scan.nextLine());
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

        System.out.print("Professor (" + disciplina.getProfessor() + "): ");
        String professor = scan.nextLine();
        if (!professor.isEmpty()) {
            disciplina.setProfessor(professor);
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
        System.out.println("Professor: " + disciplina.getProfessor());
    }

    public static String GetCodigo() {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nInforme o código da disciplina: ");
        return scan.nextLine();
    }

}
