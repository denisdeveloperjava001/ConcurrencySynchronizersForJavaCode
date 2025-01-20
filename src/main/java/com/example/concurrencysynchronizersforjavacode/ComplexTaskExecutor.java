package com.example.concurrencysynchronizersforjavacode;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ComplexTaskExecutor {
    private final int numberOfTasks;
    private final CyclicBarrier barrier;
    private ExecutorService executorService;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
        this.barrier = new CyclicBarrier(numberOfTasks, this::combineResults);
    }

    public void executeTasks(int numberOfTasks) {
        this.executorService = Executors.newFixedThreadPool(numberOfTasks);
        for (int i = 0; i < numberOfTasks; i++) {
            final int taskId = i;
            executorService.submit(() -> {
                try {
                    ComplexTask task = new ComplexTask(taskId);
                    task.execute();
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    private void combineResults() {
        //method for combining results

    }

}
