package app;

import object.Aluno;
import util.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // TODO alterar dadosTeste para dadosCompleto quando terminar e testar
    public static String textFilePath = "LabComp3_Exerc1_dadosTeste.txt";
    public static String binaryFilePath = "BinaryOutputFile.txt";
    public static String orderedBinaryFilePath = "orderedBinaryOutputFile.txt";
    public static Scanner userInput = new Scanner(System.in);

    public static void menu() throws IOException {
        System.out.println("--------------------------------");
        System.out.println("1. Procurar por posicao");
        System.out.println("2. Procurar por matricula");
        System.out.println("3. Paginacao em ordem de matricula");
        System.out.println("4. Sair");
        System.out.println("--------------------------------");
        System.out.print("Digite a opcao desejada: ");
        Scanner userInput = new Scanner(System.in);
        int opcao = userInput.nextInt();
        switch (opcao){
            case 1:
                searchByPos();
                break;
            case 2:
                searchByMatricula();
                break;
            case 3:
                pagination();
                break;
        }
    }

    public static void searchByPos() throws IOException {
        System.out.println("--------------------------------");
        System.out.print("Digite a pos do aluno que deseja encontrar: ");
        int pos = userInput.nextInt();
        System.out.println(
                FileManager.binaryToAlunoByPos(pos, orderedBinaryFilePath) //Imprime um objeto Aluno instanciado do arquivo binario pego pela pos
        );
    }

    public static void searchByMatricula() throws IOException {
        System.out.println("--------------------------------");
        System.out.print("Digite a matricula do aluno que deseja encontrar: ");
        int matricula = userInput.nextInt();
        System.out.println("Procurando matricula no arquivo...");
        System.out.println(
            FileManager.searchByMatricula(matricula, orderedBinaryFilePath)
        );
    }

    public static void pagination() throws IOException {
        System.out.println("Criando ArrayList...");
        ArrayList<Aluno> alunosOrdenados = FileManager.orderByMatricula(binaryFilePath);

        System.out.println("--------------------------------");
        System.out.println("1. Iniciar");
        System.out.println("2. Passar indice");
        System.out.print("Digite a opcao desejada: ");
        int n = userInput.nextInt();
        if(n==2){
            System.out.print(System.lineSeparator() + "Digite o valor do Indice: ");
            int indice = userInput.nextInt();
            for (int i=0; i< (indice - 1); i++){
                alunosOrdenados.remove(0);
            }
        }

        n=-1;
        while(n!=2){
            for(int i=0; i<20; i++){
                try{
                    System.out.println(alunosOrdenados.get(0));
                    alunosOrdenados.remove(0);
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Fim de arquivo!");
                    break;
                }
            }
            System.out.print("Pressione 1 para exibir mais ou 2 para encerrar: ");
            n = userInput.nextInt();
        }
    }

    public static void main(String[] args) {

        try {
            System.out.println("Convertendo txt para arquivo binario...");
            FileManager.txtToBinary(textFilePath, binaryFilePath);
            System.out.println("Ordenando e adicionando indice ao arquivo binario...");
            FileManager.addIndexAndWrite(binaryFilePath ,orderedBinaryFilePath);
            menu();
            System.out.println("--------------FIM---------------");
        }catch (Exception error) {
            error.printStackTrace();
        }
    }
}
