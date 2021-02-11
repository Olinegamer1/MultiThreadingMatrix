import org.apache.commons.math3.exception.NullArgumentException;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Matrix {
    private static final String NO_ONE_COLUMN = "Matrix hasn't column";
    private static final String INCORRECT_MATRIX = "Incorrect matrix";
    private static final String DIFFERENT_DIMENSION  = "Column and Row has different dimensions";
    private static final String INCORRECT_COUNT_THREADS = "Count of threads can't be below zero";
    private static CountDownLatch latch;
    private double[][] data;
    private double[][] outputData;

    public Matrix(double[][] data) {
        copy(data);
    }

    public Matrix multiply (Matrix matrix, int threadCount) throws InterruptedException{
        checkMultiplication(this, matrix);
        outputData = getOutputData(matrix);

        Thread[] threads = createThreading(threadCount, matrix);
        launchThreading(threads);
        latch.await();
        return new Matrix(outputData);
    }

    private double[][] getOutputData(Matrix matrix) {
        int rows = this.getRowDimension();
        int columns = matrix.getColumnDimension();
        return new double[rows][columns];
    }

    private void launchThreading (Thread[] threads) {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    private Thread[] createThreading(int threadCount, Matrix matrix) {
        if (threadCount <= 0) {
            throw new IllegalArgumentException(INCORRECT_COUNT_THREADS);
        }
        int maxNumOfCPUThreads = Runtime.getRuntime().availableProcessors();
        threadCount = Math.min(threadCount, maxNumOfCPUThreads);
        latch = new CountDownLatch(threadCount);

        Thread[] threads = new Thread[threadCount];

        int step = getStep(threadCount);
        int start = 0;
        int end = step;

        for (int i = 0; i < threadCount; i++) {
            if (i + 1 >= threadCount) {
                end = this.getRowDimension();
            }
            threads[i] = new Multithreading(start, end, this, matrix, outputData, latch);
            start += step;
            end += step;
        }

        return threads;

    }

    private int getStep(int threadCount) {
        int step = this.getRowDimension() / threadCount;
        if (step == 0) {
            return  this.getRowDimension();
        } else {
            return step;
        }
    }

    private void checkMultiplication(Matrix matrixLeft, Matrix matrixRight) {
        if (matrixLeft.getColumnDimension() != matrixRight.getRowDimension()) {
            throw new IllegalArgumentException(DIFFERENT_DIMENSION);
        }
    }

    private int getRowDimension () {
        return this.data != null ? this.data.length : 0;
    }

    private int getColumnDimension () {
        return this.data != null && this.data[0] != null ? this.data[0].length : 0;
    }

    public double[][] getData() {
        return data;
    }

    private void copy (double[][] data) {
        if (data == null) {
            throw new NullArgumentException();
        }

        int columns = data[0].length;
        if (columns == 0) {
            throw new IllegalArgumentException(NO_ONE_COLUMN);
        }

        int rows = data.length;

        this.data = new double[rows][columns];

        for (int i = 0; i < this.data.length; ++i) {
            if (data[i].length != columns) {
                throw new IllegalArgumentException(INCORRECT_MATRIX);
            }

            System.arraycopy(data[i], 0, this.data[i], 0, columns);
        }

    }

    @Override
    public String toString() {
        return Arrays.deepToString(data);
    }
}

