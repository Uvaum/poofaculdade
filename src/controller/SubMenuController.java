package controller;

import view.MenuView;
import view.SubMenuView;

import java.util.Scanner;

public class SubMenuController {

    public static void show(String value) {
        Scanner scan = new Scanner(System.in);
        int opcao = -1;

        do {
            SubMenuView.print(value);
            opcao = scan.nextInt();
            if (value.equals("Aluno")) {
                switch (opcao) {
                    case 1 -> AlunoController.Criar();
                    case 2 -> AlunoController.Consultar();
                    case 3 -> AlunoController.Listar();
                    case 4 -> AlunoController.Atualizar();
                    case 5 -> AlunoController.Deletar();
                }
            }
            if (value.equals("Curso")) {
                switch (opcao) {
//                    case 1 -> CursoController.Criar();
//                    case 2 -> CursoController.Consultar();
//                    case 3 -> CursoController.Atualizar();
//                    case 4 -> CursoController.Deletar();
                }
            }
            if (value.equals("Disciplina")) {
                switch (opcao) {
//                    case 1 -> DisciplinaController.Criar();
//                    case 2 -> DisciplinaController.Consultar();
//                    case 3 -> DisciplinaController.Atualizar();
//                    case 4 -> DisciplinaController.Deletar();
                }
            }

        } while (opcao < 5);
    }

}
