package com.yuewen.complatablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wangshiyang
 * @since 2022/3/16
 **/
public class ThenAcceptDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        CompletableFuture.supplyAsync(()->{
            //子任务
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return 123;
        }, threadPool).thenAccept(value -> {
            System.out.println("连接任务执行，前置任务的结果为：" + value);
            threadPool.shutdown();
        });

        // 主任务
        System.out.println("main end");
    }
}
