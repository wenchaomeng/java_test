package test.credis.springboot;

import credis.java.client.CacheProvider;
import credis.java.client.util.CacheFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.credis.CRedisTest;

import java.sql.SQLException;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 14, 2018
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@RestController
public class Boot {

    private static CacheProvider provider = CacheFactory.GetProvider(System.getProperty("cluster", "FlightExchangeIntlClass"));

    public Boot() {

    }

    @RequestMapping(path = "get")
    public String read() throws SQLException {

        return provider.get("nihao");

    }


    public static void main(String[] argc) {

        SpringApplication.run(Boot.class);

    }

}
