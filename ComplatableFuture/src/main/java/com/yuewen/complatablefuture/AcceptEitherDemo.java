package com.yuewen.complatablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wangshiyang
 * @since 2022/3/16
 **/
public class AcceptEitherDemo {
    public static void main(String[] args) {
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

        // 这里的value就是先执行完的任务返回的结果   acceptEitherAsync没有返回值
        future1.acceptEitherAsync(future2, (value) -> {
            System.out.println(value);
            threadPool.shutdown();
        }, threadPool);
    }
}
