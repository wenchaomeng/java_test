package javabasic.basic;

import org.junit.Test;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 04, 2018
 */
public class DoubleCmp {

    @Test
    public void test() {


        float f1 = 0.15f;
        float f2 = 0.45f/3;

        System.out.println(Float.toHexString(0.5F));
        System.out.println(Float.toHexString(f1));
        System.out.println(Float.toHexString(f2));

        System.out.println(f1);
        System.out.println(f2);

        System.out.println(f1 == f2);

    }

}
