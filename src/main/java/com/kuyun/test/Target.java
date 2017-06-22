package com.kuyun.test;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuwuqiang on 2017/6/20.
 */
public class Target {

    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            f();
            Thread.sleep(2000L);
        }
    }

    public static void f() {
        System.out.printf("%s:test\n", ++i);
    }
}
