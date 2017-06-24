package com.kuyun.test;


import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * Created by xuwuqiang on 2017/6/20.
 */
public class Target {

    private static int i = 0;


    public static void main(String[] args) throws InterruptedException {
        TestUtil t = new TestUtil();
        while (true) {
            f();
            t.testFn();
            Thread.sleep(2000L);
        }
    }

    public static void f() {
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(pid);

        System.out.printf("%s:test\n", ++i);
    }


}
