package controller;

import dao.DisciplinaDAO;
import model.Disciplina;
import view.DisciplinaView;

public class DisciplinaController {

    public static void Criar() {
        Disciplina disciplina = new Disciplina();
        DisciplinaView.Criar(disciplina);

        // Validação: verifica se código foi preenchido
        if (disciplina != null && disciplina.getCodigo() != null) {
            DisciplinaDAO.Add(disciplina);
        } else {
            System.out.println("Erro: Dados da disciplina inválidos.");
        }
    }

    public static void Consultar() {
        String codigo = DisciplinaView.GetCodigo();
        Disciplina disciplina = DisciplinaDAO.Get(codigo);
        if (disciplina != null) {
            DisciplinaView.Consultar(disciplina);
        } else {
            System.out.println("Disciplina não encontrada!");
        }
    }

    public static void Listar() {
        DisciplinaView.Listar(DisciplinaDAO.GetAll());
    }

    public static void Atualizar() {
        String codigo = DisciplinaView.GetCodigo();
        Disciplina disciplina = DisciplinaDAO.Get(codigo);
        if (disciplina != null) {
            DisciplinaView.Atualizar(disciplina);
            DisciplinaDAO.Update(disciplina);
        } else {
            System.out.println("Disciplina não encontrada!");
        }
    }

    public static void Deletar() {
        String codigo = DisciplinaView.GetCodigo();
        DisciplinaDAO.Delete(codigo);
    }

}


