package javat.agent.transformer;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 19, 2018
 */
public class AbstractMain {

    protected static ClassDefinition createDefinition() {

        byte[] aByte = getByte("String.class");
        System.out.println("new String class byte: " + aByte.length);
        ClassDefinition classDefinition = new ClassDefinition(String.class, aByte);
        return classDefinition;
    }

    protected static byte[] getByte(String file) {
        try {
            return Transformer.getBytesFromFile(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

}
