package experimental;

import java.util.concurrent.*;

public class ThreadingExample {
    private ExecutorService executorService;
    private CountDownLatch latch;
    
    public ThreadingExample() {
        executorService = Executors.newFixedThreadPool(10);
        latch = new CountDownLatch(100);
    }
    
    public void executeParallelTasks() {
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                    latch.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
}
