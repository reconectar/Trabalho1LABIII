package util;

import object.Aluno;

import java.util.Comparator;

public class AlunoComparator implements Comparator <Aluno> {

    //Compara pelo valor da matricula
    @Override
    public int compare(Aluno aluno1, Aluno aluno2){
        return aluno1.getMatricula() - aluno2.getMatricula();
    }
}
