package javat.agent.transformer;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 11, 2018
 */
public class Main {


    public static void main(String[] argc) {

        System.out.println("path :" + TransClass.class.getClassLoader().getResource("javat/agent/transformer/Main.class"));
        System.out.println("path :" + TransClass.class.getResource("Main.class"));
        System.out.println(new TransClass().getNumber());
    }
}
