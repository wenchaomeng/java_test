package test.credis;

import credis.java.client.CacheProvider;
import credis.java.client.util.CacheFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wenchao.meng
 *         <p>
 *         May 29, 2018
 */
public class CRedisTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private ExecutorService executors = Executors.newCachedThreadPool();

    private static CacheProvider provider = CacheFactory.GetProvider(System.getProperty("cluster", "FlightExchangeIntlClass"));

    @Test
    public void redisSetGet() throws Exception {

        String key = "TestKey";
        provider.del(key);
        provider.set(key, "TestValue");
        String value = provider.get(key);
        logger.info("{}", value);
    }

    public static void main(String[] argc) {

        String value = provider.get("123");
        System.out.println("result:" + value);

    }
}
