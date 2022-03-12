import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TOPSIS {
    public static void start(double[][] matrix, String[] keys, double[]grade){
        ArrayList<Double> crit = new ArrayList<>();
        double a = 0;
        for(int i = 0; i<matrix.length;i++){
            for(int j = 0;j<matrix[0].length;j++){
                a+=Math.pow(matrix[i][j], 2);
            }
            crit.add(Math.sqrt(a));
            a = 0;
        }
        for(int i = 0; i<matrix.length;i++){
            for(int j = 0;j<matrix[0].length;j++){
                matrix[i][j] = grade[i] * matrix[i][j]/crit.get(i);
            }
        }

        ArrayList<Double> max = new ArrayList<>(); //массивы для хранения идеальных решений
        ArrayList<Double> min = new ArrayList<>();
        double mx = 0;
        double mn = 1000000;
        for (int i = 0; i<matrix.length;i++){
            for(int j = 0;j<matrix[0].length;j++){  //заполнение
                if (matrix[i][j]>mx){
                    mx = matrix[i][j];
                }
                if(matrix[i][j]<mn){
                    mn = matrix[i][j];
                }
            }
            max.add(mx);
            mx = 0;
            min.add(mn);
            mn = 1000000;
        }

        ArrayList<Double> Smax = new ArrayList<>(); //массивы для хранения расстояний
        ArrayList<Double> Smin = new ArrayList<>();
        double Smx = 0;
        double Smn = 0;
        for(int i = 0; i<matrix[0].length;i++){     //считаем относительную близость
            for(int j = 0;j<matrix.length;j++) {
                Smx += Math.pow(matrix[j][i] - max.get(j), 2);
                Smn += Math.pow(matrix[j][i] - min.get(j), 2);
            }
            Smax.add(Math.sqrt(Smx));
            Smin.add(Math.sqrt(Smn));
            Smx = 0;
            Smn = 0;
        }

        HashMap<String, Double> result = new HashMap<>(); //хештаблица для хранения результатов
        for (int i = 0; i<Smax.size();i++){
            result.put(keys[i], Smin.get(i)/(Smax.get(i)+Smin.get(i)));
        }

        System.out.println("Рейтинг ПО:"); //выводим, отсортировав
        result.entrySet().stream().sorted(Map.Entry.<String, Double> comparingByValue().reversed()).forEach(System.out::println);
    }
}
