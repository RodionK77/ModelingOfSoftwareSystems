import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SAW {
    public static void start(double[][] matrix, String[] keys, double[]grade) {
        ArrayList<Double> max = new ArrayList<>(); //массивы для хранения максимальных и минимальных знаечний характеристик
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


        for (int i = 0; i<matrix.length;i++){
            for(int j = 0;j<matrix[0].length;j++){
                matrix[i][j] = matrix[i][j]/max.get(i) * grade[i]; //преобразование с помощью формулы x[i][j]/max[i]
            }                                                     //и умножение на вес
        }

        HashMap<String, Double> result = new HashMap<String,Double>();
        int indx = 0;
        double sum = 0;
        for(int i = 0; i<matrix[0].length;i++){
            for(int j = 0;j<matrix.length;j++){
                sum+=matrix[j][i];
            }                                    //создаём массив ключ-значение для хранения итоговых результатов
            result.put(keys[indx], sum);
            sum = 0;
            indx++;
        }

        System.out.println("Рейтинг ПО:"); //выводим, отсортировав
        result.entrySet().stream().sorted(Map.Entry.<String, Double> comparingByValue().reversed()).forEach(System.out::println);
    }
}
