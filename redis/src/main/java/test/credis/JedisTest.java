package test.credis;

import credis.java.client.setting.RAppSetting;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Aug 15, 2018
 */
public class JedisTest {

    private String host = "127.0.0.1";
    private int port = 6379;
    private ExecutorService executors = Executors.newCachedThreadPool();


    @Test
    public void testPool() throws InterruptedException {

        final JedisPool jedisPool = new JedisPool(newPoolConfig(), this.host, this.port,
                RAppSetting.getTimeout(), null, 0);

        Jedis resource = jedisPool.getResource();

        String value = resource.get("a");

        resource.close();

        TimeUnit.SECONDS.sleep(10000);

    }


    @Test
    public void testJedis() throws InterruptedException {

        final JedisPool jedisPool = new JedisPool(newPoolConfig(), this.host, this.port,
                RAppSetting.getTimeout(), null, 0);

        for (int i = 0; i < 10; i++) {

            executors.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Jedis resource = jedisPool.getResource();
                        System.out.println(resource);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        TimeUnit.SECONDS.sleep(1000);
    }

    private GenericObjectPoolConfig newPoolConfig() {

        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(3);
        genericObjectPoolConfig.setMaxWaitMillis(5000);

        return genericObjectPoolConfig;
    }
}
