package com.yuewen.complatablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author wangshiyang
 * @since 2022/3/15
 **/
public class WhenCompleteDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                int a = 1/0;
                System.out.println("异步任务执行使用的线程：" + Thread.currentThread().getName());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 345;
        }, threadPool);

        // 主任务
        System.out.println("main end");

        // 根据异步任务的结果，触发后续业务逻辑的处理
        // 这里需要传入一个 BiConsumer, 于是乎就new一个BiConsumer, 并重写accept
        future.whenCompleteAsync(new BiConsumer<Integer, Throwable>() {
            @Override
            public void accept(Integer integer, Throwable throwable) {
                // 这里的integer 就是当前异步任务的返回值
                // 这里的throwable的作用就是当执行的异步任务中出现异常的时候，进一步进行处理
                System.out.println("异步任务后续处理线程：" + Thread.currentThread().getName());
                System.out.println(throwable.getMessage());
                System.out.println(integer + 123);
                threadPool.shutdown();
            }
        }, threadPool);

        // 使用lambda表达式的写法
        future.whenCompleteAsync(((integer, throwable) -> {
            System.out.println("异步任务后续处理线程：" + Thread.currentThread().getName());
            System.out.println(throwable.getMessage());
            System.out.println(integer + 124);
            threadPool.shutdown();
        }), threadPool);


    }

}
