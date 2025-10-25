package controller;

import dao.CursoDAO;
import model.Curso;
import view.CursoView;

public class CursoController {

    public static void Criar() {
        Curso curso = new Curso();
        CursoView.Criar(curso);
        if (curso != null && curso.getCodigo() != null) {
            CursoDAO.Add(curso);
        } else {
            System.out.println("Erro: Dados do curso inválidos.");
        }
    }

    public static void Consultar() {
        String codigo = CursoView.GetCodigo();
        Curso curso = CursoDAO.Get(codigo);
        if (curso != null) {
            CursoView.Consultar(curso);
        } else {
            System.out.println("Curso não encontrado!");
        }
    }

    public static void Listar() {
        CursoView.Listar(CursoDAO.GetAll());
    }

    public static void Atualizar() {
        String codigo = CursoView.GetCodigo();
        Curso curso = CursoDAO.Get(codigo);
        if (curso != null) {
            CursoView.Atualizar(curso);
            CursoDAO.Update(curso);
        } else {
            System.out.println("Curso não encontrado!");
        }
    }

    public static void Deletar() {
        String codigo = CursoView.GetCodigo();
        CursoDAO.Delete(codigo);
    }

}