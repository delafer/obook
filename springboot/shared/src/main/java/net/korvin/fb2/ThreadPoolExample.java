package net.korvin.fb2;

import java.util.Random;
import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String args[]) throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(3, 3,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor){
                        try {
                            System.out.println("REJECTED");
                            // this needs to be put(...) and not add(...)
                            executor.getQueue().put(runnable);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
        for (int i =1; i<=8; i++){
            service.submit(new Task(i)); //submit that to be done
            //service.execute(new Task(i));
            System.out.println("::::"+i);
        }
        service.shutdown();
        if (!service.awaitTermination(30, TimeUnit.SECONDS))
            System.err.println("Threads didn't finish in 30 seconds!");
    }
}

final class Task implements Runnable {
    private int taskId;
    public Task(int id){
        this.taskId = id;
    }

    @Override
    public void run() {
        System.out.println("Task ID : " + this.taskId +" performed by "
                + Thread.currentThread().getName()+" STARTED");
        try {
            int randomNum = (new Random()).nextInt(10) + 3;
            Thread.sleep(randomNum * 5000);
        } catch (InterruptedException e) {e.printStackTrace();}

        System.out.println("Task ID : " + this.taskId +" performed by "
                + Thread.currentThread().getName()+" FINISHED");
    }
}
