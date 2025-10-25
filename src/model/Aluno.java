package model;

public class Aluno {

    private String nome;
    private int idade;
    private String matricula;
    private String codigoCurso;

    public Aluno(String nome, int idade, String matricula, String codigoCurso) {
        this.nome = nome;
        this.idade = idade;
        this.matricula = matricula;
        this.codigoCurso = codigoCurso;
    }
    public Aluno() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }
}
