package com.kuyun.loaded;

import com.kuyun.loaded.transformer.PrintTimeTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * Created by xuwuqiang on 2017/6/19.
 */
public class LoadedAgent {


    /**
     * JVM hook to dynamically load javaagent at runtime.
     * <p/>
     * The agent class may have an agentmain method for use when the agent is
     * started after VM startup.
     */
    public static void agentmain(String args, Instrumentation inst) throws Exception {
        inst.addTransformer(new PrintTimeTransformer(), true);
        printLoaded(inst);
    }

    private static void printLoaded(Instrumentation inst) throws UnmodifiableClassException {
        for (Class klass : inst.getAllLoadedClasses()) {
            if (inst.isModifiableClass(klass)) {
                inst.retransformClasses(klass);
//                System.out.println(klass.getName());
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
