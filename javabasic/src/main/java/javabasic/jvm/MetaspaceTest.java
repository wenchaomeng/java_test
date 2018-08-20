package javabasic.jvm;

import javabasic.AbstractTest;
import org.junit.Test;

import java.io.ObjectStreamClass;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Apr 17, 2018
 */
public class MetaspaceTest extends AbstractTest {

    @Test
    public void test() {

        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                try {
                    ObjectStreamClass lookup = ObjectStreamClass.lookup(ReferenceTest.class);
                    logger.info("{}", lookup, new ReferenceTest(1, "hello"));
                } catch (Throwable th) {
                    logger.error("", th);
                }
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);

        sleep(100000000);

    }


    @Test
    public void testRandomString() {

        List<String> ls = new LinkedList<>();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {

            ls.add(randomString(1 << 20));
            if (i > 30){
                ls.remove(0);
            }
            sleep(50);
        }


    }

}
