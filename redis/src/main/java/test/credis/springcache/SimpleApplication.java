package test.credis.springcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wenchao.meng
 *         <p>
 *         Jun 13, 2018
 */
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class SimpleApplication {

    public static void main(String[] args) {

        System.out.println("hello");
        SpringApplication.run(SimpleApplication.class);

    }
}

