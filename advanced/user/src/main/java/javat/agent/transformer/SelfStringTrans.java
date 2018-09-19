package javat.agent.transformer;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 19, 2018
 */
public class SelfStringTrans {

    private String path = "advanced/replace/target/classes/java/lang/String.class";

    public void startTrans() throws UnmodifiableClassException, ClassNotFoundException {

        Instrumentation instrumentation = ByteBuddyAgent.install();
        instrumentation.redefineClasses(createDefinition());
    }


    protected ClassDefinition createDefinition() {

        byte[] aByte = getByte(path);
        System.out.println("new String class byte: " + aByte.length);
        ClassDefinition classDefinition = new ClassDefinition(String.class, aByte);
        return classDefinition;
    }

    protected static byte[] getByte(String file) {
        try {
            return getBytesFromFile(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }


    public static byte[] getBytesFromFile(InputStream ins) throws IOException {
        try {
            // precondition
            ByteArrayOutputStream bous = new ByteArrayOutputStream();
            int data = 0;
            while ((data = ins.read()) >= 0) {
                bous.write(data);
            }
            return bous.toByteArray();
        } catch (Exception e) {
            System.out.println("error occurs in _ClassTransformer!"
                    + e.getClass().getName());
            return null;
        } finally {
            ins.close();
        }
    }
}
