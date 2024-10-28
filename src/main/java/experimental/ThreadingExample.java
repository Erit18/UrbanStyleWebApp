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
    
    public void complexThreadOperations() {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 10, 1, TimeUnit.MINUTES, workQueue
        );
        
        CompletableFuture<?>[] futures = new CompletableFuture[50];
        for (int i = 0; i < 50; i++) {
            final int taskId = i;
            futures[i] = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(100);
                    return "Task " + taskId + " completed";
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return "Task " + taskId + " interrupted";
                }
            }, executor);
        }
    }
    
    public void producerConsumerExample() {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(20);
        
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    queue.put(i);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    Integer value = queue.take();
                    Thread.sleep(20);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        producer.start();
        consumer.start();
    }
    
    public void advancedThreadPatterns() {
        // Barrier Pattern
        CyclicBarrier barrier = new CyclicBarrier(5, () -> 
            System.out.println("Barrier action executed"));

        // Semaphore Pattern
        Semaphore semaphore = new Semaphore(3);
        
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executorService.submit(() -> {
                try {
                    semaphore.acquire();
                    Thread.sleep(1000);
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    public void forkJoinExample() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        
        RecursiveTask<Long> task = new RecursiveTask<Long>() {
            protected Long compute() {
                long sum = 0;
                for (int i = 0; i < 10000; i++) {
                    sum += i;
                }
                return sum;
            }
        };

        forkJoinPool.execute(task);
    }

    public void phaser() {
        Phaser phaser = new Phaser(1);
        for (int i = 0; i < 3; i++) {
            phaser.register();
            final int threadId = i;
            executorService.submit(() -> {
                try {
                    Thread.sleep(threadId * 100);
                    phaser.arriveAndAwaitAdvance();
                    Thread.sleep(threadId * 200);
                    phaser.arriveAndDeregister();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
}
