package com.kuyun.test;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuwuqiang on 2017/6/20.
 */
public class Target {

    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        ClassLoader cl = Target.class.getClassLoader();
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }

        URL blPathURL = cl.getResource(Target.class.getName().replace('.', '/') + ".class");
        System.out.println(blPathURL);
        String blPath = blPathURL.toString().replace("jar:file:", "");
        System.out.println(blPath);
        blPath = blPath.substring(0, blPath.indexOf(".jar!") + 4).replace("btrace-agent", "btrace-boot"); // NOI18N
        System.out.println(blPath);
        System.out.println(Boolean.getBoolean("net.java.btrace.debug"));
    }

    public static void f() {
        System.out.printf("%s:test\n", ++i);
    }
}
