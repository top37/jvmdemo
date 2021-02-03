package demo;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 重写方法快捷键：Ctrl O ， 注意：非cmd O；
 */
public class MyClsVisitor0 extends ClassVisitor {
    public MyClsVisitor0(ClassVisitor classVisitor) {
        //api -> 版本
        super(Opcodes.ASM7, classVisitor);
    }



}
