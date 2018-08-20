package javabasic;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 04, 2018
 */
public class AbstractTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected ExecutorService executors = Executors.newCachedThreadPool();;

    protected ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(4);

    @Rule
    public TestName name = new TestName();

    @Before
    public void beforeAbstractTest() {

    }

    protected String randomString(int length) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + (int) (26 * Math.random())));
        }

        return sb.toString();
    }

    protected void sleep(int milli) {

        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            //ignore
        }
    }

    protected void sleep() {
        try {
            TimeUnit.DAYS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
