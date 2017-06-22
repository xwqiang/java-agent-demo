package com.kuyun.loaded;

import com.kuyun.loaded.transformer.PrintTimeTransformer;
import com.kuyun.shared.Settings.Agent;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
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

        setupBootstrap(args);
        inst.addTransformer(new PrintTimeTransformer(), true);
//        printLoaded(inst);
    }

    private static void setupBootstrap(String args) {
        argsMap = mapArgs(args);
        Agent.agentClass = argsMap.get("agentClass");
        Agent.agentMethod = argsMap.get("agentMethod");
        System.out.println("agentClass:" + Agent.agentClass);
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

    private static void printLoaded(Instrumentation inst) throws UnmodifiableClassException {
        for (Class klass : inst.getAllLoadedClasses()) {
            if (inst.isModifiableClass(klass)) {
                inst.retransformClasses(klass);
            }
        }
    }


    /**
     * JVM hook to statically load the javaagent at startup.
     * <p/>
     * After the Java Virtual Machine (JVM) has initialized, the premain method
     * will be called. Then the real application main method will be called.
     */
    public static void premain(String args, Instrumentation inst) throws Exception {
        inst.addTransformer(new PrintTimeTransformer());
    }
}
