import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ELECTRE {
    public static void start(double[][] matrix, String[] keys, double[]grade) {
        char[][][] exc = new char[keys.length][grade.length][keys.length-1];

        int indx = 0;
        int j2 = 0;
        boolean f = false;
        for(int k = 0;k<exc.length;k++){
            for(int i = 0; i < exc[0].length;i++){
                for(int j = 0; j < exc[0][0].length;j++){   //строим матрицы превосходства
                                                            //относительно каждого ПО
                    if(indx == j){
                        f = true;
                    }
                    if(f){
                        j2 = j+1;
                    }else j2 = j;
                    if(matrix[i][indx] > matrix[i][j2]){
                        exc[k][i][j] = '+';
                    }
                    else if (matrix[i][indx]<matrix[i][j2]){
                        exc[k][i][j] = '-';
                    }
                    else if (matrix[i][indx] == matrix[i][j2]){
                        exc[k][i][j] = '=';
                    }
                }
                f = false;
            }
            indx++;
        }

        double[][][] crit = new double[3][keys.length][keys.length];
        double sum = 0;
        indx = 0;
        int i1 = 0;
        for(int k = 0;k<crit.length;k++){
            for(int i = 0; i < keys.length;i++){
                for(int j = 0; j <keys.length;j++){ //определяем суммы весов критериев
                                                    //для множеств +,=,-
                    if(indx == j){
                        f = true;
                    }
                    if(f){
                        j2 = j+1;
                    }else j2 = j;

                    if(j<exc[0][0].length){
                        for(int j1 = 0; j1 < exc[0].length;j1++){
                            if(k == 0 && exc[i][j1][i1] == '+'){
                                sum+=grade[j1];
                            }else if(k == 2 && exc[i][j1][i1]=='-'){
                                sum+=grade[j1];
                            }else if(k==1 && exc[i][j1][i1] == '='){
                                sum+=grade[j1];
                            }
                        }
                        crit[k][i][j2] = sum;
                        sum = 0;
                        i1++;
                    }
                }
                f = false;
                i1=0;
                indx++;
            }
            indx = 0;
        }

        double[][] agree = new double[keys.length][keys.length];
        for(int i = 0; i< agree.length;i++){
            for(int j = 0; j< agree[0].length;j++){             //матрица индексов согласия
                agree[i][j]+=(crit[0][i][j] + crit[1][i][j])/1;
            }
        }

        double[][][] deskeys = new double[keys.length][grade.length][keys.length-1];
        indx = 0;
        j2 = 0;
        f = false;
        for(int k = 0;k<deskeys.length;k++){
            for(int i = 0; i < deskeys[0].length;i++){
                for(int j = 0; j < deskeys[0][0].length;j++){   //находим индексы несогласия для каждого ПО
                    if(indx == j){
                        f = true;
                    }
                    if(f){
                        j2 = j+1;
                    }else j2 = j;
                    if(matrix[i][indx] > matrix[i][j2]  || matrix[i][indx] == matrix[i][j2]){
                        deskeys[k][i][j] = 0;
                    }
                    else deskeys[k][i][j] = (matrix[i][j2] - matrix[i][indx])/10;
                }
                f = false;
            }
            indx++;
        }

        double[][] desagree = new double[keys.length][keys.length];
        double max = 0;
        indx = 0;
        int indx2 = 0;
        for(int i = 0; i< agree.length;i++){
            for(int j = 0; j< agree[0].length;j++){     //матрица несогласия
                if(indx == j){
                    f = true;
                }
                if(f){
                    j2 = j+1;
                }else j2 = j;
                if(j<deskeys[0][0].length){
                    for(int k = 0; k < grade.length;k++){
                        if(deskeys[indx2][k][j]>max){
                            max = deskeys[indx2][k][j];
                        }
                    }
                    desagree[i][j2] = max;
                    max = 0;
                }
            }
            indx++;
            indx2++;
            f = false;
        }

        double p = 0.5;
        char[][] resultmatrix = new char[keys.length][keys.length];
        for(int i = 0; i< resultmatrix.length;i++){         //матрица решений
            for(int j = 0; j< resultmatrix[0].length;j++){
                if((agree[i][j] > p || agree[i][j] == p) && (desagree[i][j] < p || desagree[i][j] == p) ){
                    resultmatrix[i][j] = '+';
                }else resultmatrix[i][j] = '-';
            }
        }

        HashMap<String, Integer> result = new HashMap<String,Integer>();
        int s = 0;
        for(int i = 0; i<resultmatrix.length;i++){
            for(int j = 0;j<resultmatrix[0].length;j++){
                if(resultmatrix[i][j] == '+'){
                    s++;
                }
            }                                    //создаём массив ключ-значение для хранения итоговых результатов
            result.put(keys[i], s);
            s = 0;
        }
        System.out.println("Рейтинг ПО:"); //выводим, отсортировав
        result.entrySet().stream().sorted(Map.Entry.<String, Integer> comparingByValue().reversed()).forEach(System.out::println);
    }
}
