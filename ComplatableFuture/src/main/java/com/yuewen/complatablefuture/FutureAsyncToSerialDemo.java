package com.yuewen.complatablefuture;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author wangshiyang
 * @since 2022/3/15
 **/
public class FutureAsyncToSerialDemo {
    static Random random = new Random();

    // 这里尽量不要使用自带的线程池实现
    static ExecutorService executor= Executors.newCachedThreadPool();

    // 接收文章名称，计算出文章分数
    public static int getArticleScore(String articleName) {
        Future<Integer> futureA = executor.submit(new CalculateArticleScoreB());
        Future<Integer> futureB = executor.submit(new CalculateArticleScoreB());
        Future<Integer> futureC = executor.submit(new CalculateArticleScoreB());

        // 其他业务处理
        doSomething();

        Integer a = null;
        try {
            a = futureA.get();
        } catch (InterruptedException | ExecutionException e) {
            futureA.cancel(true);
            e.printStackTrace();
        }

        Integer b = null;
        try {
            b = futureB.get();
        } catch (InterruptedException | ExecutionException e) {
            futureB.cancel(true);
            e.printStackTrace();
        }

        Integer c = null;
        try {
            c = futureC.get();
        } catch (InterruptedException | ExecutionException e) {
            futureC.cancel(true);
            e.printStackTrace();
        }

        executor.shutdown();
        // 返回文章分数
        return a + b + c;
    }

    private static void doSomething() {
        System.out.println("处理其他业务");
    }
    

    public static void main(String[] args) {
        System.out.println(getArticleScore("demo"));
    }
}

class CalculateArticleScoreA implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        // 业务代码
        Random random = new Random();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(Thread.currentThread().getName());
        return random.nextInt(100);
    }
}