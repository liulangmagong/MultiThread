package com.yuewen.complatablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wangshiyang
 * @since 2022/3/15
 **/
public class RunAsync2ParamDemo {
    public static void main(String[] args) {
        // 创建一个指定的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        // 这里使用的是传入两个参数的 runAsync() 方法
        // 使用 lambda 表达式创建一个异步任务作为第一个参数传入方法中
        // 指定线程池作为第二个参数传入

        CompletableFuture.runAsync(()->{
            // 子任务
            try {
                // 这里的子任务的执行使用的是自己传入的线程池
                TimeUnit.SECONDS.sleep(3);
                // 输出子线程的名称
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, threadPool);

        // 主任务
        System.out.println("main end");
        // 关闭线程池
        threadPool.shutdown();

    }
}
