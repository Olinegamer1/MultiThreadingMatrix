import java.util.Arrays;
import java.util.Random;

public class Test {
    public static boolean equals(double[][] mas, double[][] mas1) {
        try {
            for (int i = 0; i < mas.length; i++) {
                for (int j = 0; j < mas[i].length; j++) {
                    if (mas[i][j] != mas1[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Разные размеры массивов");
        }
        return false;
    }

    public static void print (double[][] mas) {
        System.out.println(Arrays.deepToString(mas));
    }

    public static double[][] getRandomData (int size) {
        double[][] outputData = new double[size][size];

        for (int i = 0; i < outputData.length; i++) {
            for (int j = 0; j < outputData[i].length; j++) {
                outputData[i][j] = new Random().nextInt(1000);
            }
        }
        return outputData;
    }

}
