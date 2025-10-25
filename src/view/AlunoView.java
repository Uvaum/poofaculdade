package view;

import model.Aluno;

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
    }

    public static String GetMatricula() {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nInforme a matrícula: ");
        return scan.nextLine();
    }

}