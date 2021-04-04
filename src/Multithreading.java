import java.util.concurrent.CountDownLatch;

class Multithreading extends Thread {
    private static double[][] rightMatrix;
    private static double[][] leftMatrix;
    private static double[][] outputMatrix;
    private int start;
    private final int end;
    private static CountDownLatch latch;

    public Multithreading (int start, int end, Matrix leftMatrix, Matrix rightMatrix, double[][] outputMatrix, CountDownLatch latch) {
        Multithreading.rightMatrix = leftMatrix.getData();
        Multithreading.leftMatrix = rightMatrix.getData();
        Multithreading.outputMatrix = outputMatrix;
        this.start = start;
        this.end = end;
        Multithreading.latch = latch;
    }

    private void threadingMultiply () {
        int leftRowCount = rightMatrix.length;
        int leftColumnCount = rightMatrix[0].length;
        int rightColumnCount = leftMatrix[0].length;
        for (; (start < end) & (start < leftRowCount); start++) {
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
            outputMatrix[i][z] += rightMatrix[i][j] * leftMatrix[j][z];
        }
    }

    @Override
    public void run() {
        threadingMultiply();
        latch.countDown();
    }
}
