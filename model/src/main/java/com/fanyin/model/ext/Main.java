package com.fanyin.model.ext;

/**
 * @author 二哥很猛
 * @date 2019/12/4 15:10
 */
public class Main {

    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        FilePath a = FilePath.builder().address("test").build();
        B b = new B(a);
        a = null;
        System.out.println(b.getFilePath());
    }
}
