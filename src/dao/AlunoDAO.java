package dao;

import database.DatabaseConnection;
import model.Aluno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoDAO {

    public static void Add(Aluno aluno) {
        String sql = "INSERT INTO alunos (matricula, nome, idade) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setInt(3, aluno.getIdade());

            stmt.executeUpdate();
            System.out.println("Aluno cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    public static Aluno Get(String matricula) {
        String sql = "SELECT * FROM alunos WHERE matricula = ?";
        Aluno aluno = null;

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    aluno = new Aluno(
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("matricula")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar aluno: " + e.getMessage());
        }

        return aluno;
    }

    public static List<Aluno> GetAll() {
        String sql = "SELECT * FROM alunos";
        List<Aluno> alunos = new ArrayList<>();

        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("matricula")
                );
                alunos.add(aluno);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
        }

        return alunos;
    }

    public static void Update(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, idade = ? WHERE matricula = ?";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getIdade());
            stmt.setString(3, aluno.getMatricula());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Aluno atualizado com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com essa matrícula.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    public static void Delete(String matricula) {
        String sql = "DELETE FROM alunos WHERE matricula = ?";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, matricula);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Aluno removido com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com essa matrícula.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover aluno: " + e.getMessage());
        }
    }

    public static void Criar() {
        String sqlCreateTable = """
            CREATE TABLE IF NOT EXISTS alunos (
                matricula VARCHAR(10) PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                idade INT NOT NULL
            );
        """;

        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {

            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Tabela 'alunos' criada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }
}