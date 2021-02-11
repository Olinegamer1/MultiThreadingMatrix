import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        double[][] matrixA = Test.getRandomData(10);
        double[][] matrixB = Test.getRandomData(10);

        Matrix leftMatrix = new Matrix(matrixA);
        Matrix rightMatrix = new Matrix(matrixB);

        RealMatrix realMatrix = new Array2DRowRealMatrix(matrixA);
        RealMatrix realMatrix1 = new Array2DRowRealMatrix(matrixB);

        double[][] result = leftMatrix.multiply(rightMatrix, 12).getData();
        double[][] result1 = realMatrix.multiply(realMatrix1).getData();

        System.out.println(Test.equals(result, result1));
    }
}

