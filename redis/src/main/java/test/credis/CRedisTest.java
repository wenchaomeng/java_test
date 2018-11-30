package test.credis;

import credis.java.client.CacheProvider;
import credis.java.client.util.CacheFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         May 29, 2018
 */
public class CRedisTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private ExecutorService executors = Executors.newCachedThreadPool();

    private static CacheProvider provider = CacheFactory.GetProvider(System.getProperty("cluster", "HotelProduct"));

    @Test
    public void test() {

        String data = provider.get("GeoPlace_Data_1_EN_73");
        System.out.println(data);
    }


    @Test
    public void redisSetGet() throws Exception {

        String key = "TestKey";
        provider.del(key);
        provider.set(key, "TestValue");
        String value = provider.get(key);
        logger.info("{}", value);

        TimeUnit.SECONDS.sleep(600);
    }

    public static void main(String[] argc) {

        String value = provider.get("123");
        System.out.println("result:" + value);

    }
}
