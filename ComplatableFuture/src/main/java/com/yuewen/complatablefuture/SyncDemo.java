package com.yuewen.complatablefuture;

import java.util.Random;

/**
 * @author wangshiyang
 * @since 2022/3/15
 * 同步Demo
 **/
public class SyncDemo {

    static Random random = new Random();

    // 接收文章名称，计算出文章分数
    public static int getArticleScore(String articleName) {
        int a = calculateArticleScore(articleName);
        int b = calculateArticleScore(articleName);
        int c = calculateArticleScore(articleName);

        // 其他业务处理
        doSomething();

        // 返回文章分数
        return a + b + c;
    }

    private static void doSomething() {
        System.out.println("处理其他业务");
    }

    private static int calculateArticleScore(String articleName) {
        // 模拟调用第三方服务的延迟
        otherService();
        return random.nextInt();
    }

    // 模拟服务调用延迟
    private static void otherService() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        System.out.println(getArticleScore("demo"));
    }
}
