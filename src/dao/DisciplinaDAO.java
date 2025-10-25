package dao;

import database.DatabaseConnection;
import model.Disciplina;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    // LISTA para armazenar disciplinas em memória
    private static List<Disciplina> listaDisciplinas = new ArrayList<>();

    public static void Add(Disciplina disciplina) {
        // Adicionar na LISTA
        listaDisciplinas.add(disciplina);
        
        // Também salvar no banco de dados
        String sql = "INSERT INTO disciplinas (codigo, nome, carga_horaria, codigo_curso) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, disciplina.getCodigo());
            stmt.setString(2, disciplina.getNome());
            stmt.setInt(3, disciplina.getCargaHoraria());
            stmt.setString(4, disciplina.getCodigoCurso());

            stmt.executeUpdate();
            System.out.println("Disciplina cadastrada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar disciplina: " + e.getMessage());
            listaDisciplinas.remove(disciplina); // Remove da lista se falhar no banco
        }
    }

    public static Disciplina Get(String codigo) {
        // Buscar primeiro na LISTA
        for (Disciplina d : listaDisciplinas) {
            if (d.getCodigo().equals(codigo)) {
                return d;
            }
        }

        // Se não encontrar na lista, busca no banco
        String sql = "SELECT * FROM disciplinas WHERE codigo = ?";
        Disciplina disciplina = null;

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    disciplina = new Disciplina(
                            rs.getString("codigo"),
                            rs.getString("nome"),
                            rs.getInt("carga_horaria"),
                            rs.getString("codigo_curso")
                    );
                    // Adiciona na lista para cache
                    listaDisciplinas.add(disciplina);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar disciplina: " + e.getMessage());
        }


        return disciplina;
    }

    public static List<Disciplina> GetAll() {
        // Se a lista estiver vazia, carrega do banco
        if (listaDisciplinas.isEmpty()) {
            CarregarDoBanco();
        }
        return new ArrayList<>(listaDisciplinas); // Retorna cópia da lista
    }

    public static List<Disciplina> GetByCurso(String codigoCurso) {
        List<Disciplina> disciplinasCurso = new ArrayList<>();
        
        for (Disciplina d : listaDisciplinas) {
            if (d.getCodigoCurso().equals(codigoCurso)) {
                disciplinasCurso.add(d);
            }
        }
        
        return disciplinasCurso;
    }

    private static void CarregarDoBanco() {
        String sql = "SELECT * FROM disciplinas";

        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Disciplina disciplina = new Disciplina(
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getInt("carga_horaria"),
                        rs.getString("codigo_curso")
                );
                listaDisciplinas.add(disciplina);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar disciplinas: " + e.getMessage());
        }
    }

    public static void Update(Disciplina disciplina) {
        // Atualizar na LISTA
        for (int i = 0; i < listaDisciplinas.size(); i++) {
            if (listaDisciplinas.get(i).getCodigo().equals(disciplina.getCodigo())) {
                listaDisciplinas.set(i, disciplina);
                break;
            }
        }

        // Atualizar no banco
        String sql = "UPDATE disciplinas SET nome = ?, carga_horaria = ?, codigo_curso = ? WHERE codigo = ?";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, disciplina.getNome());
            stmt.setInt(2, disciplina.getCargaHoraria());
            stmt.setString(3, disciplina.getCodigoCurso());
            stmt.setString(4, disciplina.getCodigo());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Disciplina atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma disciplina encontrada com esse código.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar disciplina: " + e.getMessage());
        }
    }

    public static void Delete(String codigo) {
        // Remover da LISTA
        listaDisciplinas.removeIf(d -> d.getCodigo().equals(codigo));

        // Remover do banco
        String sql = "DELETE FROM disciplinas WHERE codigo = ?";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, codigo);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Disciplina removida com sucesso!");
            } else {
                System.out.println("Nenhuma disciplina encontrada com esse código.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover disciplina: " + e.getMessage());
        }
    }

    public static void Criar() {
        String sqlCreateTable = """
            CREATE TABLE IF NOT EXISTS disciplinas (
                codigo VARCHAR(10) PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                carga_horaria INT NOT NULL,
                codigo_curso VARCHAR(10) NOT NULL,
                FOREIGN KEY (codigo_curso) REFERENCES cursos(codigo)
            );
        """;

        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {

            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Tabela 'disciplinas' criada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }
}