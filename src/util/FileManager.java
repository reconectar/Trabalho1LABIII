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
            Aluno aluno = new Aluno(nome, matricula, nota); // O objeto aluno é instanciado a partir do formato da linha (matricula;nome;nota)
            aluno.writeToBinary(outputFile);
        }
    }

    // Le cada aluno dentro do arquivo binario pelo seu tamanho em bytes e retorna ao chegar ao indice passado
    public static Aluno binaryToAlunoByIndice(int indice, String inputBinaryFilePath) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(inputBinaryFilePath, "r");

        raf.seek(0); // Coloca o cursor do arquivo em seu inicio

        // Variaveis usadas para instanciar um Aluno quando o indice for encontrado
        int matricula = 0;
        String nome = null;
        float nota = 0;

        // Nota-se que a cada leitura abaixo o cursor se move para o final do dado lido, foi usada essa logica para percorrer por cada Aluno
        for(int i=0; i<indice; i++){
            //TODO mudar para raf.skipBytes
            matricula = raf.readInt();
//            byte[] stringSize = new byte[Aluno.getMAX_NAME_SIZE()*2]; //Cada char possui 2 bytes, entao multiplicamos o tamanho da string por 2
            // FIXME instanciar nome com o numero de bytes correto da string
//            nome = raf.readUTF();
            nota = raf.readFloat();
        }
        return new Aluno(nome, matricula, nota);
//        return new Aluno(); // Just for testing
    }

}
