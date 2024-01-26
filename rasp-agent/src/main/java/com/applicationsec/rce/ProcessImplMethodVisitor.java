package com.applicationsec.rce;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

public class ProcessImplMethodVisitor extends ClassVisitor {

    public ProcessImplMethodVisitor(ClassVisitor visitor){
        super(Opcodes.ASM7, visitor);
    }

    @Override
    public MethodVisitor visitMethod(int methodAccess, String methodName, String methodDesc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(methodAccess, methodName, methodDesc, signature, exceptions);
        if ("start".equals(methodName) && "([Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;[Ljava/lang/ProcessBuilder$Redirect;Z)Ljava/lang/Process;".equals(methodDesc)) {
            return new ProcessImplStartMethodVisitor(Opcodes.ASM5, methodVisitor, methodAccess, methodName, methodDesc);
        }
        return methodVisitor;
    }

    public static class ProcessImplStartMethodVisitor extends AdviceAdapter {

        protected ProcessImplStartMethodVisitor(int api, MethodVisitor methodVisitor, int methodAccess, String methodName, String methodDesc) {
            super(api, methodVisitor, methodAccess, methodName, methodDesc);
        }

        @Override
        protected void onMethodEnter() {
            Method mtd = new Method("check", "([Ljava/lang/String;)V");
            Type tp = Type.getType(ProcessImplStartCheck.class);
            loadArg(0);
            invokeStatic(tp, mtd);
        }
    }

}
