package com.yuewen.complatablefuture;

import java.util.concurrent.*;

/**
 * @author wangshiyang
 * @since 2022/3/16
 **/
public class ApplyToEitherDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future1: " + Thread.currentThread().getName());
            return "hello";
        }, threadPool);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {

                // 执行时间小于任务1，故先执行完
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future1: " + Thread.currentThread().getName());
            return "hello";
        }, threadPool);

        // 这里的 value 就是先执行完的任务返回的结果
        CompletableFuture<String> future = future1.applyToEitherAsync(future2, (value) -> {
            System.out.println("result:" + Thread.currentThread().getName());
            return value;
        }, threadPool);

        System.out.println(future.get());
        threadPool.shutdown();
    }
}
