import java.util.concurrent.CountDownLatch;

class Multithreading extends Thread {
    private static double[][] leftMatrix;
    private static double[][] rightMatrix;
    private static double[][] outputMatrix;
    private static int threadCount;
    private int i;
    private static CountDownLatch latch;

    public Multithreading (int i, int threadCount, Matrix leftMatrix, Matrix rightMatrix, double[][] outputMatrix, CountDownLatch latch) {
        Multithreading.leftMatrix = leftMatrix.getData();
        Multithreading.rightMatrix = rightMatrix.getData();
        Multithreading.outputMatrix = outputMatrix;
        this.i = i;
        Multithreading.threadCount = threadCount;
        Multithreading.latch = latch;
    }

    private void threadingMultiply () {
        int leftRowCount = leftMatrix.length;
        int leftColumnCount = leftMatrix[0].length;
        int rightColumnCount = rightMatrix[0].length;
        while (i < leftRowCount) {
            for (int j = 0; j < leftColumnCount; j++) {
                for (int z = 0; z < rightColumnCount; z++) {
                    outputMatrix[i][z] += leftMatrix[i][j] * rightMatrix[j][z];
                }
            }
            i += threadCount;
        }
    }

    @Override
    public void run() {
        threadingMultiply();
        latch.countDown();
    }
}
