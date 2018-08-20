package javabasic.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wenchao.meng
 *         <p>
 *         Feb 27, 2018
 */
public class TestMain {

    private static final Logger LOGGER = LoggerFactory.getLogger("aaa");
    private Object obj;
//  private Class<?> obj;

    public TestMain() throws ClassNotFoundException {
//        obj = new Object();
//        System.identityHashCode(obj);
        obj = Class.forName("java.lang.String");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        long start = System.currentTimeMillis();
        TestMain t = new TestMain();
        long sum = 0;
        for (int i = 0; i < 100000000; i++) {
            sum = t.a(sum, i);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    long a(long sum, int i) {
        synchronized (obj) {
            return sum + i;
        }
    }
}