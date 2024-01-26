package com.applicationsec;


import com.applicationsec.rce.ProcessImplMethodVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class RASPClassTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            if (!className.equals("java/lang/ProcessImpl")) {
                return null;
            }
            System.out.println("Class is being loaded by the JVM, it will transformed to include RASP probes : " + className);
            ClassReader reader = new ClassReader(classfileBuffer);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            reader.accept(new ProcessImplMethodVisitor(writer), ClassReader.EXPAND_FRAMES);
            System.out.println("Probes inserted for " + className);
            return writer.toByteArray();
        }

}
