package com.kuyun.shared;

import java.util.Map;

/**
 * Created by xuwuqiang on 2017/6/22.
 */
public final class Settings {

    public static final class Agent {

        public static String agentMethod;
        public static String agentClass;

        public static void fromArgsMap(Map<String, String> argsMap) {
            Agent.agentClass = argsMap.get("agentClass");
            Agent.agentMethod = argsMap.get("agentMethod");
        }
    }
}
