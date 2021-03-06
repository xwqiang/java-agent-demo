package com.kuyun.agent.duration.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by xuwuqiang on 2017/6/19.
 */
public class TimeClassVisitor extends ClassVisitor {

    private String className;


    public TimeClassVisitor(ClassVisitor cv, String className) {
        super(Opcodes.ASM5, cv);
        this.className = className;
    }


    //扫描到每个方法都会进入
    @Override
    public MethodVisitor visitMethod(int access, final String name, final String desc, String signature,
        String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        final String key = className + name + desc;
        //过来待修改类的构造函数
        if (!name.equals("<init>") && mv != null) {
            mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {

                //方法进入时获取开始时间
                @Override
                public void onMethodEnter() {
                    this.visitLdcInsn(key);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/kuyun/agent/duration/visitor/TimeUtil",
                        "setStartTime",
                        "(Ljava/lang/String;)V", false);
                }

                //方法退出时获取结束时间并计算执行时间
                @Override
                public void onMethodExit(int opcode) {
                    this.visitLdcInsn(key);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/kuyun/agent/duration/visitor/TimeUtil",
                        "setEndTime",
                        "(Ljava/lang/String;)V", false);
                    //向栈中压入类名称
                    this.visitLdcInsn(className);
                    //向栈中压入方法名
                    this.visitLdcInsn(name);
                    //向栈中压入方法描述
                    this.visitLdcInsn(desc);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "com/kuyun/agent/duration/visitor/TimeUtil",
                        "getExclusiveTime",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J", false);
                }
            };
        }
        return mv;
    }
}
