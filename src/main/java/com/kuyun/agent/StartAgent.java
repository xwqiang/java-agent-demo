package com.kuyun.agent;

import com.kuyun.agent.duration.transformer.PrintTimeTransformer;
import com.kuyun.shared.Settings.Agent;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuwuqiang on 2017/6/19.
 */
public class StartAgent {

    private static Map<String, String> argsMap;

    /**
     * JVM hook to dynamically load javaagent at runtime.
     * <p/>
     * The agent class may have an agentmain method for use when the agent is
     * started after VM startup.
     */
    public static void agentmain(String args, Instrumentation inst) throws Exception {

        printVmDetail();

        printArguments(args);

        domain(args, inst);

//        retransformClasses(inst);
    }


    /**
     * JVM hook to statically load the javaagent at startup.
     * <p/>
     * After the Java Virtual Machine (JVM) has initialized, the premain method
     * will be called. Then the real application main method will be called.
     */
    public static void premain(String args, Instrumentation inst) throws Exception {

        domain(args, inst);

    }


    private static void retransformClasses(Instrumentation inst) throws UnmodifiableClassException {

        System.out.printf("retransfered classes = %s ,size = %s\n", AgentContext.transferedClass,
            AgentContext.transferedClass.size());

        for (Class klass : inst.getAllLoadedClasses()) {

            if (AgentContext.transferedClass.contains(klass.getName())) {

                System.out.printf("retransfer class: '%s'\n", klass.getName());

                inst.retransformClasses(klass);

            }
        }
    }

    private static void domain(String args, Instrumentation inst) {

        setupBootstrap(args);

        inst.addTransformer(new PrintTimeTransformer(), true);

    }


    private static void setupBootstrap(String args) {

        argsMap = mapArgs(args);

        Agent.fromArgsMap(argsMap);
    }


    /**
     * 打印jvm的详细信息
     */
    private static void printVmDetail() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        System.out.println("进程PID: \t" + runtime.getName().split("@")[0]);
        System.out.println("jvm规范名称: \t" + runtime.getSpecName());
        System.out.println("jvm规范运营商: \t" + runtime.getSpecVendor());
        System.out.println("jvm规范版本: \t" + runtime.getSpecVersion());
        System.out.println("jvm运营商: \t" + runtime.getVmVendor());
        System.out.println("jvm实现版本: \t" + runtime.getVmVersion());
        System.out.println("类路径: \t" + runtime.getClassPath());
        System.out.println("引导类路径: \t" + runtime.getBootClassPath());
        System.out.println("库路径: \t" + runtime.getLibraryPath());
        System.out.println();

    }

    /**
     * @param args : agentmain 传过来的参数
     */
    private static Map<String, String> mapArgs(String args) {
        if (args == null) {
            args = "";
        }
        String[] pairs = args.split(",");

        Map<String, String> argMap = new HashMap<String, String>();
        for (String s : pairs) {
            int i = s.indexOf('=');
            String key, value = "";
            if (i != -1) {
                key = s.substring(0, i).trim();
                if (i + 1 < s.length()) {
                    value = s.substring(i + 1).trim();
                }
            } else {
                key = s;
            }
            argMap.put(key, value);
        }
        return argMap;
    }

    private static void printArguments(String args) {

        System.out.printf("arguments : '%s'\n", args);

    }
}
