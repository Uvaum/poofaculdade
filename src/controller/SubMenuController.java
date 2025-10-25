package controller;

import view.SubMenuView;

import java.util.Scanner;

public class SubMenuController {

    public static void show(String value) {
        Scanner scan = new Scanner(System.in);
        int opcao = -1;

        do {
            try {
                SubMenuView.print(value);
                opcao = scan.nextInt();
                scan.nextLine(); // Limpar buffer

                if (value.equals("Aluno")) {
                    switch (opcao) {
                        case 1 -> AlunoController.Criar();
                        case 2 -> AlunoController.Consultar();
                        case 3 -> AlunoController.Deletar();
                        case 4 -> AlunoController.Atualizar();
                        case 5 -> AlunoController.Listar();
                        case 6 -> System.out.println("Voltando ao menu principal...");
                        default -> System.out.println("Opção inválida! Tente novamente.");
                    }
                }
                if (value.equals("Disciplinas")) {
                    switch (opcao) {
                        case 1 -> DisciplinaController.Criar();
                        case 2 -> DisciplinaController.Consultar();
                        case 3 -> DisciplinaController.Deletar();
                        case 4 -> DisciplinaController.Atualizar();
                        case 5 -> DisciplinaController.Listar();
                        case 6 -> System.out.println("Voltando ao menu principal...");
                        default -> System.out.println("Opção inválida! Tente novamente.");
                    }
                }
                if (value.equals("Curso")) {
                    switch (opcao) {
                        case 1 -> CursoController.Criar();
                        case 2 -> CursoController.Consultar();
                        case 3 -> CursoController.Deletar();
                        case 4 -> CursoController.Atualizar();
                        case 5 -> CursoController.Listar();
                        case 6 -> System.out.println("Voltando ao menu principal...");
                        default -> System.out.println("Opção inválida! Tente novamente.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida! Digite um número.");
                scan.nextLine(); // Limpar buffer em caso de erro
                opcao = -1; // Força continuar no loop
            }

        } while (opcao != 6);
    }

}