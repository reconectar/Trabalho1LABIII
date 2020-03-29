package object;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Aluno {

    private static final int MAX_NAME_SIZE = 30; // Usado depois para forçar o tamanho da string quando for convertido para bytes
    //FIXME remove ids if needed
//    private static int cont = 0;
//    private int id;
    private int matricula;
    private String nome;
    private float nota;

    public Aluno() {}

    public Aluno(int matricula, String nome, float nota) {
//        this.id = cont;
//        cont++;
        this.matricula = matricula;
        this.nome = nome;
        this.setNota(nota); // Setter usado devido a regra de negocio necessaria para nota (!<0 and !>100)
    }

    public void writeToBinary(RandomAccessFile outputFile) throws IOException {
        StringBuilder nomeFinal = new StringBuilder(this.nome);
        nomeFinal.setLength(MAX_NAME_SIZE); //Setamos um tamanho estatico para facilitar navegação do arquivo depois
        outputFile.writeInt(matricula);
        outputFile.writeChars(nomeFinal.toString());
        outputFile.writeFloat(nota);
    }

    public static int getMAX_NAME_SIZE() {
        return MAX_NAME_SIZE;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        if (nota < 0 || nota > 100)
            throw new IllegalArgumentException("Nota deve ser maior ou igual a 0 e menor ou igual a 100");
        else
            this.nota = nota;
    }

    // Metodo de impressao do objeto aluno
    @Override
    public String toString(){
        return
                //FIXME remove ids if needed
//                "Id: " + this.id +
                                System.lineSeparator() + "Nome: " + this.nome +
                                System.lineSeparator() + "Matricula: " + this.matricula +
                                System.lineSeparator() + "Nota: " + this.nota;
    }

}
