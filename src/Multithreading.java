import java.util.concurrent.CountDownLatch;

class Multithreading extends Thread {
    private static double[][] leftMatrix;
    private static double[][] rightMatrix;
    private static double[][] outputMatrix;
    private static int threadCount;
    private int start;
    private static CountDownLatch latch;

    public Multithreading (int start, int threadCount, Matrix leftMatrix, Matrix rightMatrix, double[][] outputMatrix, CountDownLatch latch) {
        Multithreading.leftMatrix = leftMatrix.getData();
        Multithreading.rightMatrix = rightMatrix.getData();
        Multithreading.outputMatrix = outputMatrix;
        this.start = start;
        Multithreading.threadCount = threadCount;
        Multithreading.latch = latch;
    }

    private void threadingMultiply () {
        int leftRowCount = leftMatrix.length;
        int leftColumnCount = leftMatrix[0].length;
        int rightColumnCount = rightMatrix[0].length;
        for (; start < leftRowCount; start += threadCount) {
            threadingMultiply2(start, leftColumnCount, rightColumnCount);
        }
    }

    private void threadingMultiply2 (int i, int leftColumnCount, int rightColumnCount) {
        for (int j = 0; j < leftColumnCount; j++) {
            threadingMultiply3(i, j,rightColumnCount);
        }
    }

    private void threadingMultiply3 (int i, int j, int rightColumnCount) {
        for (int z = 0; z < rightColumnCount; z++) {
            outputMatrix[i][z] += leftMatrix[i][j] * rightMatrix[j][z];
        }
    }

    @Override
    public void run() {
        threadingMultiply();
        latch.countDown();
    }
}
