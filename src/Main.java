import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Main {
    private static final int COUNT_THREADS = 12;
    private static final double[][] matrixA = TestUtils.getRandomData(1000);
    private static final double[][] matrixB = TestUtils.getRandomData(1000);

    public static void main(String[] args) throws InterruptedException {
        Matrix leftMatrix = new Matrix(matrixA);
        Matrix rightMatrix = new Matrix(matrixB);

        RealMatrix realMatrix = new Array2DRowRealMatrix(matrixA);
        RealMatrix realMatrix1 = new Array2DRowRealMatrix(matrixB);

        long l = System.currentTimeMillis();
        double[][] result = leftMatrix.multiply(rightMatrix, COUNT_THREADS).getData();
        System.out.println(System.currentTimeMillis() - l);
        double[][] result1 = realMatrix.multiply(realMatrix1).getData();

        System.out.println(TestUtils.equals(result, result1));
    }
}


