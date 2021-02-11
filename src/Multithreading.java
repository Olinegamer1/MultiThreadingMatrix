import java.util.concurrent.CountDownLatch;

class Multithreading extends Thread {
    private static double[][] data;
    private static double[][] tempData;
    private static double[][] outputData;
    private int start;
    private final int end;
    private static CountDownLatch latch;

    public Multithreading (int start, int end, Matrix leftMatrix, Matrix rightMatrix, double[][] outputData, CountDownLatch latch) {
        Multithreading.data = leftMatrix.getData();
        Multithreading.tempData = rightMatrix.getData();
        Multithreading.outputData = outputData;
        this.start = start;
        this.end = end;
        Multithreading.latch = latch;
    }

    private void threadingMultiply () {
        int leftRowCount = data.length;
        int leftColumnCount = data[0].length;
        int rightColumnCount = tempData[0].length;
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
            outputData[i][z] += data[i][j] * tempData[j][z];
        }
    }

    @Override
    public void run() {
        threadingMultiply();
        latch.countDown();
    }
}
