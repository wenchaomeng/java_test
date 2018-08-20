package spring.boot.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenchao.meng
 *         <p>
 *         Mar 26, 2018
 */
@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class Simple {

    @RequestMapping(path = "health")
    public String health() {
        return "healthy";
    }


    @RequestMapping(path = "hello")
    public String hello() {
        return "hello";
    }

    public static void main(String[] argc) {

        System.setProperty("server.port", "8080");
        SpringApplication.run(Simple.class);

    }
}
