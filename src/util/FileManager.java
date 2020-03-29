package util;

import object.Aluno;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class FileManager {

    //Tamanho do objeto aluno em bytes (int size + float size + string size * 2)
    public static final int ALUNO_SIZE = (4 + 4 + 4 + (Aluno.getMAX_NAME_SIZE() * 2)); //Cara caractere ocupa 2 bytes, por isso foi multiplicado a string por 2

    // Converte um arquivo com objetos aluno em formato texto para arquivo binario
    public static void txtToBinary (String inputTextFilePath, String outputTextFilePath) throws IOException {
        RandomAccessFile inputFile = new RandomAccessFile(inputTextFilePath, "r");
        RandomAccessFile outputFile = new RandomAccessFile(outputTextFilePath, "rw");

        inputFile.seek(0);
        String line;
        while ((line = inputFile.readLine()) != null) { // A conversao é feita linha por linha até chegar ao final do arquivo
            String[] data = new String[3];
            data = line.split(";");
            int matricula = Integer.parseInt(data[0]);
            String nome = data[1];
            float nota = Float.parseFloat(data[2].replace(",","."));
            Aluno aluno = new Aluno(matricula, nome, nota); // O objeto aluno é instanciado a partir do formato da linha (matricula;nome;nota)
            aluno.writeToBinary(outputFile);
        }
        inputFile.close();
        outputFile.close();
    }

    // Le cada aluno dentro do arquivo binario pelo seu tamanho em bytes e retorna um objeto Aluno ao chegar a posicao passada
    public static Aluno binaryToAlunoByPos(int pos, String inputBinaryFilePath) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(inputBinaryFilePath, "r");

        raf.seek(0); // Coloca o cursor do arquivo em seu inicio

        raf.seek(ALUNO_SIZE * pos); //Aqui o cursor é movido para a posicao desejada
        int indice = raf.readInt();
        int matricula = raf.readInt();
        char stringChars[] = new char[Aluno.getMAX_NAME_SIZE()];
        for(int i=0; i<stringChars.length; i++){ //O tamanho da string nao esta na biblioteca do RandomAcessFile, lemos char por char e criamos a string
            stringChars[i] = raf.readChar();
        }
        String nome = new String(stringChars);
        float nota = raf.readFloat();

        raf.close();
        return new Aluno(matricula, nome, nota);
    }

    public static ArrayList<Aluno> orderByMatricula(String inputBinaryFilePath) throws IOException {
        ArrayList<Aluno> alunosOrdenados = new ArrayList<>();
        RandomAccessFile raf = new RandomAccessFile(inputBinaryFilePath, "r");
        try{
            // Le o arquivo e adiona todos os Alunos a memoria (dentro de um array list)
            while(true){
                int matricula = raf.readInt();
                char stringChars[] = new char[Aluno.getMAX_NAME_SIZE()];
                for(int i=0; i<stringChars.length; i++){ //O tamanho da string nao esta na biblioteca do RandomAcessFile, lemos char por char e criamos a string
                    stringChars[i] = raf.readChar();
                }
                String nome = new String(stringChars);
                float nota = raf.readFloat();
                alunosOrdenados.add(new Aluno(matricula, nome, nota));
            }
        } catch(EOFException e){
        } catch (IOException e) {
        }
        Collections.sort(alunosOrdenados ,new AlunoComparator());

        raf.close();
        return alunosOrdenados;
    }

    public static void addIndexAndWrite(String inputBinaryFilePath, String outputBinaryFilePath) throws IOException {
        RandomAccessFile outputFile = new RandomAccessFile(outputBinaryFilePath, "rw");
        ArrayList<Aluno> alunosOrdenados = orderByMatricula(inputBinaryFilePath);
        AtomicInteger i = new AtomicInteger(0);
        alunosOrdenados.stream().forEach(aluno -> { //Reescrevemos o conteudo do Arraylist ordenado adicionando um indice a cada entrada
            try {
                outputFile.writeInt(i.get());
                i.getAndIncrement();
                outputFile.writeInt(aluno.getMatricula());
                outputFile.writeChars(aluno.getNome());
                outputFile.writeFloat(aluno.getNota());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static Aluno searchByIndex(int indice, String inputBinaryFilePath) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(inputBinaryFilePath, "r");

        raf.seek(0); // Coloca o cursor do arquivo em seu inicio
        // Nota-se que a cada leitura abaixo o cursos se move para o final do dado lido, foi usada essa logica para percorrer por cada Aluno
        int matricula = -1;
        float nota = -1;
        char stringChars[] = new char[Aluno.getMAX_NAME_SIZE()];
        String nome = null;
        int auxIndice = -1;
        while(auxIndice != indice) {
            auxIndice = raf.readInt();
            matricula = raf.readInt();
            for (int i = 0; i < stringChars.length; i++) { //O tamanho da string nao esta na biblioteca do RandomAcessFile, lemos char por char e criamos a string
                stringChars[i] = raf.readChar();
            }
            nome = new String(stringChars);
            nota = raf.readFloat();
        }
        return new Aluno(matricula, nome, nota);
    }

    public static Aluno searchByMatricula(int matricula, String inputBinaryFilePath) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(inputBinaryFilePath, "r");

        raf.seek(0); // Coloca o cursor do arquivo em seu inicio
        // Nota-se que a cada leitura abaixo o cursos se move para o final do dado lido, foi usada essa logica para percorrer por cada Aluno
        int indice = -1;
        float nota = -1;
        char stringChars[] = new char[Aluno.getMAX_NAME_SIZE()];
        String nome = null;
        int auxMatricula = -1;
        while(auxMatricula != matricula) {
            indice = raf.readInt();
            auxMatricula = raf.readInt();
            for (int i = 0; i < stringChars.length; i++) { //O tamanho da string nao esta na biblioteca do RandomAcessFile, lemos char por char e criamos a string
                stringChars[i] = raf.readChar();
            }
            nome = new String(stringChars);
            nota = raf.readFloat();
        }
        return new Aluno(auxMatricula, nome, nota);
    }

}
