package com.kuyun.util;

/**
 * Created by xuwuqiang on 2017/6/24.
 */

import sun.misc.Signal;
import sun.misc.SignalHandler;


/**
 * kill 程序时做的一些事情
 *
 * HUP     1    终端断线
 * INT     2    中断（同 Ctrl + C）
 * QUIT    3    退出（同 Ctrl + \）
 * TERM    15   终止
 * KILL    9    强制终止
 * CONT    18   继续（与STOP相反， fg/bg命令）
 * STOP    19   暂停（同 Ctrl + Z）
 */
public class KillSignalHandler implements SignalHandler {


    private SignalHandler oldHandler;

    public static SignalHandler install(String signalName) {
        Signal diagSignal = new Signal(signalName);
        KillSignalHandler instance = new KillSignalHandler();
        instance.oldHandler = Signal.handle(diagSignal, instance);
        return instance;
    }

    public static void main(String[] args) {

        //kill命令
        KillSignalHandler.install("TERM");
        //ctrl+c命令
        KillSignalHandler.install("INT");

        System.out.println("Signal handling example.");
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }

    }

    @Override
    public void handle(Signal signal) {
        System.out.println("Signal handler called for signal " + signal);

        try {

            signalAction(signal);

            // Chain back to previous handler, if one exists
            if (oldHandler != SIG_DFL && oldHandler != SIG_IGN) {
                oldHandler.handle(signal);
            }

        } catch (Exception e) {
            System.out.println("handle|Signal handler"
                + "failed, reason " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void signalAction(Signal signal) {

        System.out.println("Handling " + signal.getName());
        System.out.println("Just sleep for 5 seconds.");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
    }

}
