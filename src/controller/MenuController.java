package controller;

import view.MenuView;

import java.util.Scanner;

public class MenuController {

    public static void show() {
        Scanner scan = new Scanner(System.in);
        int opcao = -1;

        do {
            MenuView.print();
            opcao = scan.nextInt();
            switch (opcao) {
                case 1 -> SubMenuController.show("Aluno");
                case 2 -> SubMenuController.show("Curso");
                case 3 -> SubMenuController.show("Disciplina");
            }

        } while (opcao < 4);

    }

}
