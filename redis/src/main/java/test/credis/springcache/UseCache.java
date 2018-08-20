package test.credis.springcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Jun 14, 2018
 */
@Component
public class UseCache {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(4);

    @Autowired
    private CityService cityService;

    @PostConstruct
    public void postConstruct() {

        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                try {
                    CityInfo city = cityService.getCity(1, "1");
                    logger.info("{}", city);
                }catch (Throwable th){
                    logger.error("[get cache error]", th);
                }
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);



    }
}
