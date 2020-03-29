package app;

import object.Aluno;
import util.FileManager;

public class Main {

    public static void main(String[] args) {

        // TODO alterar dadosTeste para dadosCompleto quando terminar e testar
        String textFilePath = "LabComp3_Exerc1_dadosTeste.txt";
        String binaryFilePath = "BinaryOutputFile.txt";

        try {
            FileManager.txtToBinary(textFilePath, binaryFilePath);
            System.out.println(
            FileManager.binaryToAlunoByIndice(0, binaryFilePath) //Imprime um objeto Aluno instanciado do arquivo binario pego pelo indice
            );
        }catch (Exception error) {
            error.printStackTrace();
        }
    }
}
