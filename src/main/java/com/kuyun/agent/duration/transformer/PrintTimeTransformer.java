package com.kuyun.agent.duration.transformer;


import com.kuyun.agent.TransferContext;
import com.kuyun.agent.duration.visitor.TimeClassVisitor;
import com.kuyun.shared.Settings.Agent;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * Created by xuwuqiang on 2017/6/19.
 */
public class PrintTimeTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
        ProtectionDomain protectionDomain, byte[] classfileBuffer)
        throws IllegalClassFormatException {
        String agentClass = Agent.agentClass;
        if (className.matches(agentClass)) {
            System.out.printf("transfer class '%s' with pattern '%s' by PrintTimeTransformer\n", className, agentClass);
            TransferContext.transferedClass.add(agentClass);
            ClassReader reader = new ClassReader(classfileBuffer);
            //创建操作字节流值对象，ClassWriter.COMPUTE_MAXS:表示自动计算栈大小
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
            //接受一个ClassVisitor子类进行字节码修改
            reader.accept(new TimeClassVisitor(writer, className), 8);
            return writer.toByteArray();
        }
        return classfileBuffer;
    }
}
