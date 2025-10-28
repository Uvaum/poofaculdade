package dao;

import database.DatabaseConnection;
import model.Aluno;
import view.AlunoView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlunoDAO {

    // LISTA para armazenar alunos em memória
    private static List<Aluno> listaAlunos = new ArrayList<>();

    // MAP para armazenar disciplinas dos alunos em memória
    private static Map<String, List<String>> alunoDisciplinas = new HashMap<>();

    public static void Add(Aluno aluno) {
        // Adicionar na LISTA
        listaAlunos.add(aluno);

        String sql = "INSERT INTO alunos (matricula, nome, idade, codigo_curso) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setInt(3, aluno.getIdade());
            stmt.setString(4, aluno.getCodigoCurso());

            stmt.executeUpdate();
            System.out.println("Aluno cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
            listaAlunos.remove(aluno); // Remove da lista se falhar no banco
        }
    }

    public static Aluno Get(String matricula) {
        // Buscar primeiro na LISTA
        for (Aluno a : listaAlunos) {
            if (a.getMatricula().equals(matricula)) {
                return a;
            }
        }

        // Se não encontrar na lista, busca no banco
        String sql = "SELECT * FROM alunos WHERE matricula = ?";
        Aluno aluno = null;

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    aluno = new Aluno(
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("matricula"),
                            rs.getString("codigo_curso")
                    );
                    listaAlunos.add(aluno);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar aluno: " + e.getMessage());
        }

        return aluno;
    }

    public static List<Aluno> GetAll() {
        // Se a lista estiver vazia, carrega do banco
        if (listaAlunos.isEmpty()) {
            CarregarDoBanco();
        }
        return new ArrayList<>(listaAlunos); // Retorna cópia da lista
    }

    private static void CarregarDoBanco() {
        String sql = "SELECT * FROM alunos";

        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("matricula"),
                        rs.getString("codigo_curso")
                );
                listaAlunos.add(aluno);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
        }
    }

    public static void Update(Aluno aluno) {
        // Atualizar na LISTA
        for (int i = 0; i < listaAlunos.size(); i++) {
            if (listaAlunos.get(i).getMatricula().equals(aluno.getMatricula())) {
                listaAlunos.set(i, aluno);
                break;
            }
        }

        String sql = "UPDATE alunos SET nome = ?, idade = ?, codigo_curso = ? WHERE matricula = ?";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getIdade());
            stmt.setString(3, aluno.getCodigoCurso());
            stmt.setString(4, aluno.getMatricula());

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
        // Remover da LISTA
        listaAlunos.removeIf(a -> a.getMatricula().equals(matricula));

        // Remover disciplinas associadas
        alunoDisciplinas.remove(matricula);

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

    // Métodos para gerenciar disciplinas do aluno usando LISTA
    public static void AddDisciplina(String matriculaAluno, String codigoDisciplina) {
        // Adicionar na LISTA em memória
        alunoDisciplinas.computeIfAbsent(matriculaAluno, k -> new ArrayList<>()).add(codigoDisciplina);

        String sql = "INSERT INTO aluno_disciplina (matricula_aluno, codigo_disciplina) VALUES (?, ?)";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, matriculaAluno);
            stmt.setString(2, codigoDisciplina);

            stmt.executeUpdate();
            System.out.println("Disciplina adicionada ao aluno com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar disciplina ao aluno: " + e.getMessage());
        }
    }

    public static void RemoveDisciplina(String matriculaAluno, String codigoDisciplina) {
        // Remover da LISTA em memória
        if (alunoDisciplinas.containsKey(matriculaAluno)) {
            alunoDisciplinas.get(matriculaAluno).remove(codigoDisciplina);
        }

        String sql = "DELETE FROM aluno_disciplina WHERE matricula_aluno = ? AND codigo_disciplina = ?";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, matriculaAluno);
            stmt.setString(2, codigoDisciplina);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Disciplina removida do aluno com sucesso!");
            } else {
                System.out.println("Disciplina não encontrada para este aluno.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover disciplina do aluno: " + e.getMessage());
        }
    }

    public static List<String> GetDisciplinasCodigos(String matriculaAluno) {
        // Verificar primeiro na LISTA em memória
        if (alunoDisciplinas.containsKey(matriculaAluno) && !alunoDisciplinas.get(matriculaAluno).isEmpty()) {
            return new ArrayList<>(alunoDisciplinas.get(matriculaAluno));
        }

        // Se não estiver em memória, busca no banco
        String sql = "SELECT codigo_disciplina FROM aluno_disciplina WHERE matricula_aluno = ?";
        List<String> codigos = new ArrayList<>();

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

            stmt.setString(1, matriculaAluno);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    codigos.add(rs.getString("codigo_disciplina"));
                }
            }

            // Armazena em memória para próximas consultas
            alunoDisciplinas.put(matriculaAluno, codigos);

        } catch (SQLException e) {
            System.out.println("Erro ao buscar disciplinas do aluno: " + e.getMessage());
        }

        return codigos;
    }


    public static void Criar() {
        // Criar tabela de alunos
        String sqlCreateTableAlunos = """
            CREATE TABLE IF NOT EXISTS alunos (
                matricula VARCHAR(10) PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                idade INT NOT NULL,
                codigo_curso VARCHAR(10) NOT NULL,
                FOREIGN KEY (codigo_curso) REFERENCES cursos(codigo)
            );
        """;

        // Criar tabela de relacionamento aluno-disciplina
        String sqlCreateTableAlunoDisciplina = """
            CREATE TABLE IF NOT EXISTS aluno_disciplina (
                matricula_aluno VARCHAR(10),
                codigo_disciplina VARCHAR(10),
                PRIMARY KEY (matricula_aluno, codigo_disciplina),
                FOREIGN KEY (matricula_aluno) REFERENCES alunos(matricula) ON DELETE CASCADE,
                FOREIGN KEY (codigo_disciplina) REFERENCES disciplinas(codigo) ON DELETE CASCADE
            );
        """;

        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {

            stmt.executeUpdate(sqlCreateTableAlunos);
            System.out.println("Tabela 'alunos' criada com sucesso!");

            stmt.executeUpdate(sqlCreateTableAlunoDisciplina);
            System.out.println("Tabela 'aluno_disciplina' criada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar as tabelas: " + e.getMessage());
        }
    }
}