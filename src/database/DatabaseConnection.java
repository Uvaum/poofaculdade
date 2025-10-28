package database;

import java.sql.*;

public class DatabaseConnection {

    private static Connection connection = null;
    private static final String HOST = "jdbc:mysql://localhost/";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static final String DATABASE = "faculdade";

    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DatabaseConnection.class) {
                if (connection == null) {
                    String user = DB_USER;
                    String pass = DB_PASS;
                    if (user == null || pass == null) {
                        throw new RuntimeException("Usuário ou senha não definidos");
                    }

                    try {
                        System.out.println("Conectando…");
                        // Criar a conexão com o banco de dados
                        connection = DriverManager.getConnection(
                                HOST + DATABASE, user, pass
                        );
                        

                        connection.setAutoCommit(true);
                        
                        System.out.println("Conexão bem-sucedida!");
                    } catch (SQLException e) {
                        System.out.println("Erro ao conectar: " + e.getMessage());
                        throw new RuntimeException("Falha ao conectar ao banco de dados", e);
                    }
                }
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexão fechada.");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
