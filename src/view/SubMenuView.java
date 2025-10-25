package view;

public class SubMenuView {

    public static void print(String value) {
        System.out.println("Submenu " + value);
        System.out.println("-------------------------");
        System.out.println("1 - Cadastrar " + value);
        System.out.println("2 - Consultar " + value);
        System.out.println("3 - Remover " + value);
        System.out.println("4 - Atualizar " + value);
        System.out.println("5 - Listar " + value);
        System.out.println("6 - Voltar ao Menu inicial");
        System.out.println("-------------------------");
        System.out.print("Opção: ");
    }

}
