package com.yuewen.complatablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wangshiyang
 * @since 2022/3/16
 **/
public class RunAfterBothDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        // 异步任务一
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            // 随机获取一个100以内的随机值返回
            int f1 = new Random().nextInt(100);
            System.out.println("f1 value:" + f1);
            return f1;
        }, threadPool);

        // 异步任务二
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            // 随机获取一个100以内的随机值返回
            int f2 = new Random().nextInt(100);
            try {
                System.out.println("等待任务二结果。。。");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("f2 value:" + f2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return f2;
        }, threadPool);

        // runAfterBothAsync的执行，不需要前边两个任务的结果，但是需要前边两个任务执行完成
        future1.runAfterBothAsync(future2, ()->{
            System.out.println("前边两个任务已经执行完成");
            threadPool.shutdown();
        }, threadPool);
    }
}
