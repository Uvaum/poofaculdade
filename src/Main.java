import controller.MenuController;
import dao.AlunoDAO;
import dao.DisciplinaDAO;
import dao.CursoDAO;
import database.DatabaseConnection;


public class Main {
    public static void main(String[] args) {
    //Rhuan Oliveira e Vitória Campos.
        DatabaseConnection.getConnection();

        CursoDAO.Criar();
        DisciplinaDAO.Criar();
        AlunoDAO.Criar();

        MenuController.show();
        DatabaseConnection.closeConnection();

    }
}