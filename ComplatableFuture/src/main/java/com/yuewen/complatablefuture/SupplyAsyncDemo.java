package com.yuewen.complatablefuture;

import java.util.concurrent.*;

/**
 * @author wangshiyang
 * @since 2022/3/15
 **/
public class SupplyAsyncDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // 子任务
            try {
                TimeUnit.SECONDS.sleep(3);
                // 输出子线程名称
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return 123;
        }, threadPool);

        // 主任务
        System.out.println("main end");

        // supplyAsync() 可以进行结果返回  通过get() 获取子任务结果
        try {
            Integer value = future.get();
            System.out.println(value);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // 关闭线程池
        threadPool.shutdown();

    }
}
