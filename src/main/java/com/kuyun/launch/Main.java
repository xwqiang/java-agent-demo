package com.kuyun.launch;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import java.io.IOException;

/**
 * Created by xuwuqiang on 2017/6/19.
 */
public class Main {

    public static void main(String[] args) {
        try {
            run(args);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AttachNotSupportedException e) {
            e.printStackTrace();
        } catch (AgentLoadException e) {
            e.printStackTrace();
        } catch (AgentInitializationException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void run(String[] args)
        throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        if (args == null || args.length <= 0) {
            System.out.println("usage: ./runsh.sh pid");
            System.exit(0);
        }
        String pid = args[0];

        VirtualMachine vm = VirtualMachine.attach(pid);
        String agentJar = System.getProperty("agent.Jar");
        if (agentJar == null) {
            System.out.println("please use -Dagent.Jar=[your agent jar path]");
            System.exit(0);
        }
        System.out.printf("load agent.Jar path = '%s'\n", agentJar);
        vm.loadAgent(agentJar);
    }

}
