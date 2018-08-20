package javabasic.basic;

import javabasic.AbstractTest;
import org.junit.Test;

/**
 * @author wenchao.meng
 *         <p>
 *         Apr 18, 2018
 */
public class TestUncaughtExceptionHandler extends AbstractTest {


    @Test
    public void test() {

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t);
                e.printStackTrace();
                throw  new IllegalArgumentException("throw from uncaught");
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                throw new IllegalStateException();
            }
        }).start();


        sleep(10000000);
    }


}
