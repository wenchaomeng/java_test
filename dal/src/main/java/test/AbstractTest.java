package test;

import org.junit.Rule;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author wenchao.meng
 *         <p>
 *         Dec 26, 2017
 */
public abstract class AbstractTest {

    protected static Logger logger = LoggerFactory.getLogger(AbstractTest.class);

    protected ExecutorService executors = Executors.newCachedThreadPool();

    protected ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(4);

    @Rule
    public TestName name = new TestName();

    public static String randomString(int length) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + (int) (26 * Math.random())));
        }

        return sb.toString();
    }

    protected String getTestName() {
        return name.getMethodName();
    }

    protected SimpleDateFormat getGmt8format() {

        String formatStr = "yyyy-MM-dd'T'HH:mm:ss.SSS ZZZZ";
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        format.setCalendar(Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")));

        return format;
    }


    protected String genValue() {
        return String.format("%s-%s", getTestName(), randomString(10));
    }


}
