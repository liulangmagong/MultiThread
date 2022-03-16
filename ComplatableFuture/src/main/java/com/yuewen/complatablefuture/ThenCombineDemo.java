package com.yuewen.complatablefuture;

import java.util.concurrent.*;

/**
 * @author wangshiyang
 * @since 2022/3/16
 **/
public class ThenCombineDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        // 异步任务一
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {

            try {
                System.out.println("future1(异步任务一)使用的线程：" + Thread.currentThread().getName());
                System.out.println("等待任务一完成。。。");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello future1";
        }, threadPool);

        // 异步任务二
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future2(异步任务二)使用的线程：" + Thread.currentThread().getName());
            return "hello future2";
        }, threadPool);

        // 当两个异步任务【全部】执行完毕后，触发后续的任务处理
        // 方法 thenCombineAsync() 的参数 other 就是要连接的任务
        // 这里的 f1和f2表示的就是异步任务一二的返回数据

        CompletableFuture<String> future = future1.thenCombineAsync(future2, (f1, f2) -> {
            System.out.println(Thread.currentThread().getName());
            return f1 + " " + f2;
        }, threadPool);

        System.out.println(future.get());

        threadPool.shutdown();


    }
}
