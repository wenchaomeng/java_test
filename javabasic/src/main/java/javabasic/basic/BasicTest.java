package javabasic.basic;

import javabasic.AbstractTest;
import org.junit.Test;

/**
 * @author wenchao.meng
 *         <p>
 *         Apr 25, 2018
 */
public class BasicTest extends AbstractTest {

    @Test
    public void testMod() {

        System.out.println(-1 % 5);
        System.out.println(-6 % 5);

        System.out.println(-1 % -5);
        System.out.println(-6 % -5);

        System.out.println(1 % -5);
        System.out.println(6 % -5);

    }

    @Test
    public void test() {

        System.out.println(~(-1));
        System.out.println(~(-2));
        System.out.println(~(-3));
        System.out.println(~(-4));


    }
}
