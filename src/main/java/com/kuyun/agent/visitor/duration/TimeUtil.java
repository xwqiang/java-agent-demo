package com.kuyun.agent.visitor.duration;

import java.util.HashMap;
import java.util.Map;

/**
 * All methods and fileds can not be changed for reason that it was referenced by
 * <code>TimeClassVisitor.java</code>
 *
 * Created by xuwuqiang on 2017/6/19.
 */
public class TimeUtil {

    private static Map<String, Long> startTimes = new HashMap<String, Long>();
    private static Map<String, Long> endTimes = new HashMap<String, Long>();

    private TimeUtil() {
    }

    public static long getStartTime(String key) {
        return startTimes.get(key);
    }

    public static void setStartTime(String key) {
        startTimes.put(key, System.currentTimeMillis());
    }

    public static long getEndTime(String key) {
        return endTimes.get(key);
    }

    public static void setEndTime(String key) {
        endTimes.put(key, System.currentTimeMillis());
    }

    public static long getExclusiveTime(String className, String methodName, String methodDesc) {
        String key = className + methodName + methodDesc;
        long exclusive = getEndTime(key) - getStartTime(key);
        System.out.println(className.replace("/", ".") + "." + methodName + " exclusive:" + exclusive);
        return exclusive;
    }
}
