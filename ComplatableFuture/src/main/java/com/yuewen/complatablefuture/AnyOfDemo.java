package com.yuewen.complatablefuture;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangshiyang
 * @since 2022/3/16
 **/
public class AnyOfDemo {
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

        // 异步任务三
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            // 随机获取一个100以内的随机值返回
            int f3 = new Random().nextInt(100);
            System.out.println("f3 value:" + f3);
            return f3;
        }, threadPool);

        // 从源码可知，allOf() 传入的是一个CompletableFuture数组，首先创建一个list集合
        ArrayList<CompletableFuture<Integer>> allFuture = new ArrayList<>();
        allFuture.add(future1);
        allFuture.add(future2);
        allFuture.add(future3);

        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(allFuture.toArray(new CompletableFuture[]{}));

        // 对上边几个异步任务的结果进行处理
        anyFuture.thenRunAsync(() -> {
            try {
                System.out.println("最快的线程任务结果：" + anyFuture.get());
                threadPool.shutdown();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, threadPool);
    }
}
