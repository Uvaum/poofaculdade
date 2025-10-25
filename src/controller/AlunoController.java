package controller;

import dao.AlunoDAO;
import model.Aluno;
import view.AlunoView;

public class AlunoController {

    public static void Criar() {
        Aluno aluno = new Aluno();
        AlunoView.Criar(aluno);
        
        // Validação: verifica se matrícula e curso foram preenchidos
        if (aluno != null && aluno.getMatricula() != null && aluno.getCodigoCurso() != null) {
            AlunoDAO.Add(aluno);
            // As disciplinas já foram adicionadas na view
        } else {
            System.out.println("Erro: Dados do aluno inválidos ou curso não selecionado.");
        }
    }

    public static void Consultar() {
        String matricula = AlunoView.GetMatricula();
        Aluno aluno = AlunoDAO.Get(matricula);
        if (aluno != null) {
            AlunoView.Consultar(aluno);
        } else {
            System.out.println("Aluno não encontrado!");
        }
    }

    public static void Listar() {
        AlunoView.Listar(AlunoDAO.GetAll());
    }

    public static void Atualizar() {
        String matricula = AlunoView.GetMatricula();
        Aluno aluno = AlunoDAO.Get(matricula);
        if (aluno != null) {
            AlunoView.Atualizar(aluno);
            AlunoDAO.Update(aluno);
        } else {
            System.out.println("Aluno não encontrado!");
        }
    }

    public static void Deletar() {
        String matricula = AlunoView.GetMatricula();
        AlunoDAO.Delete(matricula);
    }

}