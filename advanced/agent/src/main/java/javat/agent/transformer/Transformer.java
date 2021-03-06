package javat.agent.transformer;

/**
 * @author wenchao.meng
 * <p>
 * Sep 11, 2018
 */

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.net.URL;
import java.security.ProtectionDomain;

class Transformer implements ClassFileTransformer {

    public static final String classNumberReturns2 = "TransClass2.class";

    public static byte[] getBytesFromFile(InputStream ins) throws IOException {
        try {
            // precondition

            ByteArrayOutputStream bous = new ByteArrayOutputStream();

            int data = 0;
            while ( (data = ins.read()) >= 0){

                bous.write(data);
            }
            return bous.toByteArray();
        } catch (Exception e) {
            System.out.println("error occurs in _ClassTransformer!"
                    + e.getClass().getName());
            return null;
        }finally {
            ins.close();
        }
    }

    public byte[] transform(ClassLoader l, String className, Class<?> c,
                            ProtectionDomain pd, byte[] b) throws IllegalClassFormatException {


        System.out.println(className + ", clazz:" + c);
        if (!className.endsWith("TransClass")) {
            return null;
        }

        try {
            InputStream ins = new FileInputStream(new File("TransClass.class"));
            return getBytesFromFile(ins);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}