package model;

public class Curso {

    private String codigo;
    private String nome;
    private int duracao; // em semestres
    private String turno;

    public Curso(String codigo, String nome, int duracao, String turno) {
        this.codigo = codigo;
        this.nome = nome;
        this.duracao = duracao;
        this.turno = turno;

    }

    public Curso() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getTurno() {return turno;}

    public void setTurno(String turno) {this.turno = turno;}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setDisciplinas(Disciplina[] disciplinas){

    }

}
