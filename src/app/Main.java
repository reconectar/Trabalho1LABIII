package app;

import util.FileManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    // TODO alterar dadosTeste para dadosCompleto quando terminar e testar
    public static String textFilePath = "LabComp3_Exerc1_dadosTeste.txt";
    public static String binaryFilePath = "BinaryOutputFile.txt";

    public static void menu() throws IOException {
        System.out.println("--------------------------------");
        System.out.println("1. Procurar por posicao");
        System.out.println("2. Procurar por indice");
        System.out.println("3. Sair");
        System.out.println("--------------------------------");
        System.out.print("Digite a opcao desejada: ");
        Scanner userInput = new Scanner(System.in);
        int opcao = userInput.nextInt();
        switch (opcao){
            case 1:
                searchByPos();
                break;
            case 2:
                System.out.println("work in progress");
                break;
        }
    }

    public static void searchByPos() throws IOException {
        System.out.println("--------------------------------");
        Scanner userInput = new Scanner(System.in);
        System.out.print("Digite a pos do aluno que deseja encontrar: ");
        int pos = userInput.nextInt();
        System.out.println(
                FileManager.binaryToAlunoByPos(pos, binaryFilePath) //Imprime um objeto Aluno instanciado do arquivo binario pego pela pos
        );
    }

    public static void main(String[] args) {

        try {
            System.out.println("Convertendo txt para arquivo binario...");
            FileManager.txtToBinary(textFilePath, binaryFilePath);
            menu();
            System.out.println("--------------FIM---------------");
        }catch (Exception error) {
            error.printStackTrace();
        }
    }
}
