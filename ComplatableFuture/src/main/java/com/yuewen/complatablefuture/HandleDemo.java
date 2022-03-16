package com.yuewen.complatablefuture;

import java.util.concurrent.*;

/**
 * @author wangshiyang
 * @since 2022/3/16
 **/
public class HandleDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("异步任务执行使用的线程：" + Thread.currentThread().getName());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 345;
        }, threadPool).handleAsync((value, throwable) -> {
            int result = 1;
            if (throwable == null){
                // 表示前置任务执行没有出现异常
                result = value * 10;
                System.out.println("后置任务使用的线程："+ Thread.currentThread().getName());
            }else {
                System.out.println(throwable.getMessage());
            }
            return result;
        }, threadPool);

        // 主任务
        System.out.println("main end");

        // 获取子任务结果
        try {
            Integer value = future.get();
            System.out.println(value);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        threadPool.shutdown();
    }
}
