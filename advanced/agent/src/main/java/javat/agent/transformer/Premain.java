package javat.agent.transformer;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 11, 2018
 */
public class Premain extends AbstractMain {

    public static void premain(String agentArgs, Instrumentation inst)
            throws ClassNotFoundException, UnmodifiableClassException {

        System.out.println("begin premain");
        inst.redefineClasses(createDefinition());
        //inst.addTransformer(new Transformer());
        System.out.println(" end  premain");
    }

}
