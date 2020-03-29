package app;

import object.Aluno;
import util.FileManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // TODO alterar dadosTeste para dadosCompleto quando terminar e testar
        String textFilePath = "LabComp3_Exerc1_dadosTeste.txt";
        String binaryFilePath = "BinaryOutputFile.txt";


        try {
            Scanner userInput = new Scanner(System.in);
            System.out.print("Digite o indice do aluno que deseja encontrar: ");
            int indice = userInput.nextInt();
            FileManager.txtToBinary(textFilePath, binaryFilePath);
            System.out.println(
            FileManager.binaryToAlunoByIndice(indice, binaryFilePath) //Imprime um objeto Aluno instanciado do arquivo binario pego pelo indice
            );
        }catch (Exception error) {
            error.printStackTrace();
        }
    }
}
