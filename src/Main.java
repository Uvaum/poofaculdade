import controller.MenuController;
import dao.AlunoDAO;
import dao.DisciplinaDAO;
import dao.CursoDAO;
import database.DatabaseConnection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        DatabaseConnection.getConnection();

        DisciplinaDAO.Criar();
        CursoDAO.Criar();
        AlunoDAO.Criar();

        MenuController.show();
        DatabaseConnection.closeConnection();

    }
}