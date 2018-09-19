package javat.agent.transformer;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 11, 2018
 */
public class Agentmain extends AbstractMain {

    public static void agentmain(String args, Instrumentation inst) throws UnmodifiableClassException, ClassNotFoundException {

        System.err.println("传进来的参数为" + args);
        System.out.println("begin agentmain");
        inst.redefineClasses(createDefinition());
        System.out.println(" end  agentmain");

    }

}
