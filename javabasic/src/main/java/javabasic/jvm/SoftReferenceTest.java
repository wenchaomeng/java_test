package javabasic.jvm;

import javabasic.AbstractTest;
import org.junit.Test;

import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author wenchao.meng
 *         <p>
 *         Apr 17, 2018
 */
public class SoftReferenceTest extends AbstractTest {


    @Test
    public void testSoftReference() {


        SoftReference<ReferenceTest> reference = new SoftReference<ReferenceTest>(new ReferenceTest(1, "hello"));

        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                logger.info("{}", reference.get());

            }
        }, 0, 5, TimeUnit.SECONDS);


        List<byte[]> all = new LinkedList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            all.add(new byte[1<<10]);
            sleep(1);

        }

        sleep(1000000000);

    }



}
