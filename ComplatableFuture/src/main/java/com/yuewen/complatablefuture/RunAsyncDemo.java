package com.yuewen.complatablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author wangshiyang
 * @since 2022/3/15
 **/
public class RunAsyncDemo {
    public static void main(String[] args) {
        // runAsync() 方法的返回值类型为 void
        // 这里先使用一个参数的runAsync() 方法，传入一个异步任务
        // 通过lambda的形式创建一个异步任务传入方法中
        CompletableFuture.runAsync(()->{
            // 异步子任务要处理的任务
            try {
                // 这里休眠3秒，表示子任务执行过程
                // 子线程执行速度远慢于主线程，由于是使用的守护线程，故不会执行
                TimeUnit.SECONDS.sleep(3);
                // 输出子线程名称
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("main end");
    }
}
