package view;

import model.Curso;

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
        scan.nextLine(); // Limpar buffer

        System.out.print("Coordenador: ");
        curso.setCoordenador(scan.nextLine());
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

        System.out.print("Coordenador (" + curso.getCoordenador() + "): ");
        String coordenador = scan.nextLine();
        if (!coordenador.isEmpty()) {
            curso.setCoordenador(coordenador);
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
        System.out.println("Coordenador: " + curso.getCoordenador());
    }

    public static String GetCodigo() {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nInforme o código do curso: ");
        return scan.nextLine();
    }

}