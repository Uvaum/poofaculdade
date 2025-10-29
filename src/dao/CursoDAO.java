package dao;

import database.DatabaseConnection;
import model.Curso;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    // LISTA para armazenar cursos em memória
    private static List<Curso> listaCursos = new ArrayList<>();

    public static void Add(Curso curso) {
        // Adicionar na LISTA
        listaCursos.add(curso);

        String sql = "INSERT INTO cursos (codigo, nome, duracao, turno) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, curso.getCodigo());
            stmt.setString(2, curso.getNome());
            stmt.setInt(3, curso.getDuracao());
            stmt.setString(4, curso.getTurno());

            stmt.executeUpdate();
            System.out.println("Curso cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar curso: " + e.getMessage());
            listaCursos.remove(curso);
        }
    }

    public static Curso Get(String codigo) {
        // Buscar primeiro na LISTA
        for (Curso c : listaCursos) {
            if (c.getCodigo().equals(codigo)) {
                return c;
            }
        }

        // Se não encontrar na lista, busca no banco
        String sql = "SELECT * FROM cursos WHERE codigo = ?";
        Curso curso = null;

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    curso = new Curso(
                            rs.getString("codigo"),
                            rs.getString("nome"),
                            rs.getInt("duracao"),
                            rs.getString("turno")
                    );
                    listaCursos.add(curso);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar curso: " + e.getMessage());
        }

        return curso;
    }

    public static List<Curso> GetAll() {
        // Se a lista estiver vazia, carrega do banco
        if (listaCursos.isEmpty()) {
            CarregarDoBanco();
        }
        return new ArrayList<>(listaCursos); // Retorna cópia da lista
    }

    private static void CarregarDoBanco() {
        String sql = "SELECT * FROM cursos";

        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getInt("duracao"),
                        rs.getString("turno")
                );
                listaCursos.add(curso);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar cursos: " + e.getMessage());
        }
    }

    public static void Update(Curso curso) {
        // Atualizar na LISTA
        for (int i = 0; i < listaCursos.size(); i++) {
            if (listaCursos.get(i).getCodigo().equals(curso.getCodigo())) {
                listaCursos.set(i, curso);
                break;
            }
        }

        String sql = "UPDATE cursos SET nome = ?, duracao = ?, turno = ? WHERE codigo = ?";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getDuracao());
            stmt.setString(3, curso.getCodigo());
            stmt.setString(4, curso.getCodigo());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Curso atualizado com sucesso!");
            } else {
                System.out.println("Nenhum curso encontrado com esse código.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar curso: " + e.getMessage());
        }
    }

    public static void Delete(String codigo) {
        // Remover da LISTA
        listaCursos.removeIf(c -> c.getCodigo().equals(codigo));

        String sql = "DELETE FROM cursos WHERE codigo = ?";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, codigo);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Curso removido com sucesso!");
            } else {
                System.out.println("Nenhum curso encontrado com esse código.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover curso: " + e.getMessage());
        }
    }

    public static void Criar() {
        String sqlCreateTable = """
            CREATE TABLE IF NOT EXISTS cursos (
                codigo VARCHAR(10) PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                turno VARCHAR(10) NOT NULL,
                duracao INT NOT NULL
            );
        """;

        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {

            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Tabela 'cursos' criada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }
}