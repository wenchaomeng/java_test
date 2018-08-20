package test.credis.springcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Jun 13, 2018
 */
@Component
@CacheConfig(cacheNames="CityService")
public class CityService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Cacheable
    public CityInfo getCity(int id, String city) {

        logger.info("id: {}, city: {}", id, city);
        return new CityInfo(id, city);
    }
}