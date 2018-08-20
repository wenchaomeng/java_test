package test.qconfig;

import org.junit.Test;
import qunar.tc.qconfig.client.MapConfig;
import test.AbstractTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Feb 24, 2018
 */
public class QConfigTest extends AbstractTest {

    @Test
    public void testSubEnv() {

        MapConfig mapConfig = MapConfig.get("test.properties");

        Map<String, String> map = mapConfig.asMap();
        System.out.println(map);

    }

    @Test
    public void testDomain() throws InterruptedException {


        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                try {
                    InetAddress[] allByName = InetAddress.getAllByName("test.domain");
                    logger.info("{}", allByName);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        }, 0, 5, TimeUnit.SECONDS);

        TimeUnit.DAYS.sleep(1);
    }
}
