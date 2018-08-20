package javabasic.basic;

import javabasic.AbstractTest;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wenchao.meng
 *         <p>
 *         Apr 19, 2018
 */
public class OutOfMemory extends AbstractTest {


    private List<byte[]> lists = new LinkedList<>();

    @Test
    public void test() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int begin = 2 * (1 << 20);
                while (true) {
                    try {
                        lists.add(new byte[begin]);
                    } catch (Throwable th) {
                        System.out.println(begin);
                        System.err.println(th.getMessage());
                        sleep(1000);
                        if (begin > (1 << 5)) {
                            begin /= 2;
                            System.out.println(begin);
                        }
                    }
                }
            }
        }, "mythread").start();


        sleep(1000000);
    }
}
