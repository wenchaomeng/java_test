package javabasic.log4j;

import javabasic.AbstractTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 04, 2018
 */
public class Log4jTest extends AbstractTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testLog4j() throws InterruptedException {

        final String random = randomString(1 << 12);

        scheduled.scheduleAtFixedRate(new Runnable() {

            public void run() {

                logger.info("\n{}", random);

            }
        }, 0, 30, TimeUnit.MILLISECONDS);


        TimeUnit.DAYS.sleep(1);
    }

    @Test
    public void testAsyncLogger() {

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            logger.info("{}", i);
        }

    }
}
