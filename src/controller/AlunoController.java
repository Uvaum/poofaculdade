package controller;

import dao.AlunoDAO;
import model.Aluno;
import view.AlunoView;

public class AlunoController {

    public static void Criar() {
        Aluno aluno = new Aluno();
        AlunoView.Criar(aluno);
        if (aluno != null) {
            AlunoDAO.Add(aluno);
        }
    }

    public static void Consultar() {
        String matricula = AlunoView.GetMatricula();
        Aluno aluno = AlunoDAO.Get(matricula);
        AlunoView.Consultar(aluno);
    }

    public static void Listar() {
        AlunoView.Listar(AlunoDAO.GetAll());
    }

    public static void Atualizar() {
        String matricula = AlunoView.GetMatricula();
        Aluno aluno = AlunoDAO.Get(matricula);
        AlunoView.Atualizar(aluno);
    }

    public static void Deletar() {
        String matricula = AlunoView.GetMatricula();
        AlunoDAO.Delete(matricula);
    }

}
