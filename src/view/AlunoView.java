package view;

import model.Aluno;

import java.util.List;
import java.util.Scanner;

public class AlunoView {

    public static void Criar(Aluno aluno) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Nome: ");
        aluno.setNome(scan.next());

        System.out.print("Idade: ");
        aluno.setIdade(scan.nextInt());

        System.out.print("Matricula: ");
        aluno.setMatricula(scan.next());

    }
    public static void Atualizar(Aluno aluno) {
        Scanner scan = new Scanner(System.in);

        System.out.print("("+ aluno.getNome() + ") - Nome: ");
        String nome = scan.next();
        if (!nome.isEmpty()) { aluno.setNome(nome); }

        System.out.print("("+ aluno.getIdade() + ") - Idade: ");
        String idade = scan.nextLine();
        if (!idade.isEmpty()) { aluno.setIdade(Integer.parseInt(idade)); }

    }

    public static void Listar(List<Aluno> alunos) {
        for(Aluno a : alunos) {
            Consultar(a);
        }
    }

    public static void Consultar(Aluno aluno) {
        System.out.println("Matricula: " + aluno.getMatricula());
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("Idade: " + aluno.getIdade());
        System.out.println();
    }

    public static String GetMatricula() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Informe sua matricula: ");
        String matricula = scan.next();
        return matricula;
    }

}
