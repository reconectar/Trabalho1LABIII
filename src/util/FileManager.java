package util;

import object.Aluno;

import java.io.*;

public class FileManager {

    // Converte um arquivo com objetos aluno em formato texto para arquivo binario
    public static void txtToBinary (String inputTextFilePath, String outputTextFilePath) throws IOException {
        RandomAccessFile inputFile = new RandomAccessFile(inputTextFilePath, "r");
        RandomAccessFile outputFile = new RandomAccessFile(outputTextFilePath, "rw");

        inputFile.seek(0);
        String[] data = new String[3];
        String line;
        while ((line = inputFile.readLine()) != null) { // A conversao é feita linha por linha até chegar ao final do arquivo
            data = line.split(";");
            int matricula = Integer.parseInt(data[0]);
            String nome = data[1];
            float nota = Float.parseFloat(data[2].replace(",","."));
            Aluno aluno = new Aluno(matricula, nome, nota); // O objeto aluno é instanciado a partir do formato da linha (matricula;nome;nota)
            aluno.writeToBinary(outputFile);
        }
    }

    // Le cada aluno dentro do arquivo binario pelo seu tamanho em bytes e retorna ao chegar a pos passado
    public static Aluno binaryToAlunoByPos(int pos, String inputBinaryFilePath) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(inputBinaryFilePath, "r");
        raf.seek(0); // Coloca o cursor do arquivo em seu inicio

                                //Tamanho do objeto aluno em bytes (int size + float size + string size * 2)
        final int ALUNO_SIZE = (4 + 4 + (Aluno.getMAX_NAME_SIZE() * 2)); //Cara caractere ocupa 2 bytes, por isso foi multiplicado a string por 2
        raf.seek(ALUNO_SIZE * pos); //Aqui o cursor é movido para a posicao desejada

        int matricula = raf.readInt();
        char stringChars[] = new char[Aluno.getMAX_NAME_SIZE()];
        for(int i=0; i<stringChars.length; i++){ //O tamanho da string nao esta na biblioteca do RandomAcessFile, lemos char por char e criamos a string
            stringChars[i] = raf.readChar();
        }
        String nome = new String(stringChars);
        float nota = raf.readFloat();

        return new Aluno(matricula, nome, nota);
    }

}
