package dao;

import database.DatabaseConnection;
import model.Disciplina;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisciplinaDAO {

    // LISTA para armazenar disciplinas em memória
    private static List<Disciplina> listaDisciplinas = new ArrayList<>();

    // MAP para armazenar o relacionamento disciplina-curso
    private static Map<String, List<String>> disciplinaCursos = new HashMap<>();

    public static void Add(Disciplina disciplina) {
        // Adicionar na LISTA
        listaDisciplinas.add(disciplina);
        
        // Também salvar no banco de dados
        String sql = "INSERT INTO disciplinas (codigo, nome, carga_horaria) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, disciplina.getCodigo());
            stmt.setString(2, disciplina.getNome());
            stmt.setInt(3, disciplina.getCargaHoraria());

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
                            rs.getInt("carga_horaria")
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

        String sql = "SELECT d.* FROM disciplinas d " +
                     "INNER JOIN curso_disciplina cd ON d.codigo = cd.codigo_disciplina " +
                     "WHERE cd.codigo_curso = ?";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, codigoCurso);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Disciplina disciplina = new Disciplina(
                            rs.getString("codigo"),
                            rs.getString("nome"),
                            rs.getInt("carga_horaria")
                    );
                    disciplinasCurso.add(disciplina);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar disciplinas do curso: " + e.getMessage());
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
                        rs.getInt("carga_horaria")
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
        String sql = "UPDATE disciplinas SET nome = ?, carga_horaria = ? WHERE codigo = ?";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, disciplina.getNome());
            stmt.setInt(2, disciplina.getCargaHoraria());
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

    public static void AddDisciplinaToCurso(String codigoDisciplina, String codigoCurso) {
        disciplinaCursos.computeIfAbsent(codigoDisciplina, k -> new ArrayList<>()).add(codigoCurso);

        String sql = "INSERT INTO curso_disciplina (codigo_curso, codigo_disciplina) VALUES (?, ?)";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, codigoCurso);
            stmt.setString(2, codigoDisciplina);

            stmt.executeUpdate();
            System.out.println("Disciplina vinculada ao curso com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao vincular disciplina ao curso: " + e.getMessage());
        }
    }

    public static void RemoveDisciplinaFromCurso(String codigoDisciplina, String codigoCurso) {
        if (disciplinaCursos.containsKey(codigoDisciplina)) {
            disciplinaCursos.get(codigoDisciplina).remove(codigoCurso);
        }

        String sql = "DELETE FROM curso_disciplina WHERE codigo_curso = ? AND codigo_disciplina = ?";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, codigoCurso);
            stmt.setString(2, codigoDisciplina);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Disciplina removida do curso com sucesso!");
            } else {
                System.out.println("Vínculo não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover disciplina do curso: " + e.getMessage());
        }
    }

    public static List<String> GetCursosDaDisciplina(String codigoDisciplina) {
        String sql = "SELECT codigo_curso FROM curso_disciplina WHERE codigo_disciplina = ?";
        List<String> codigos = new ArrayList<>();

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, codigoDisciplina);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    codigos.add(rs.getString("codigo_curso"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar cursos da disciplina: " + e.getMessage());
        }

        return codigos;
    }

    public static void Criar() {
        String sqlCreateTableDisciplinas = """
            CREATE TABLE IF NOT EXISTS disciplinas (
                codigo VARCHAR(10) PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                carga_horaria INT NOT NULL
            );
        """;

        String sqlCreateTableCursoDisciplina = """
            CREATE TABLE IF NOT EXISTS curso_disciplina (
                codigo_curso VARCHAR(10),
                codigo_disciplina VARCHAR(10),
                PRIMARY KEY (codigo_curso, codigo_disciplina),
                FOREIGN KEY (codigo_curso) REFERENCES cursos(codigo) ON DELETE CASCADE,
                FOREIGN KEY (codigo_disciplina) REFERENCES disciplinas(codigo) ON DELETE CASCADE
            );
        """;

        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {

            stmt.executeUpdate(sqlCreateTableDisciplinas);
            System.out.println("Tabela 'disciplinas' criada com sucesso!");

            stmt.executeUpdate(sqlCreateTableCursoDisciplina);
            System.out.println("Tabela 'curso_disciplina' criada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar as tabelas: " + e.getMessage());
        }
    }
}