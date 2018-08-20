package javabasic.hash;

import javabasic.AbstractTest;
import org.junit.Test;

/**
 * @author wenchao.meng
 *         <p>
 *         May 17, 2018
 */
public class HashTest extends AbstractTest {


    @Test
    public void test() {

        int size = 4;
        int[] count = new int[size];

        for (int i = 0; i < (1 << 20); i++) {
            String s = randomString(10);
            count[Math.abs(s.hashCode() % 4)]++;
        }


        for (int c : count) {
            System.out.println(c);
        }
    }


}
