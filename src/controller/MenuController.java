package controller;

import view.MenuView;

import java.util.Scanner;

public class MenuController {

    public static void show() {
        Scanner scan = new Scanner(System.in);
        int opcao = -1;

        do {
            try {
                MenuView.print();
                opcao = scan.nextInt();
                scan.nextLine(); // Limpar buffer

                switch (opcao) {
                    case 1 -> SubMenuController.show("Aluno");
                    case 2 -> SubMenuController.show("Disciplinas");
                    case 3 -> SubMenuController.show("Curso");
                    case 4 -> System.out.println("\nEncerrando o sistema... Até logo!");
                    default -> {
                        if (opcao != 4) {
                            System.out.println("Opção inválida! Tente novamente.");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida! Digite um número.");
                scan.nextLine(); // Limpar buffer em caso de erro
                opcao = -1; // Força continuar no loop
            }

        } while (opcao != 4);

    }

}