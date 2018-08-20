package spring.boot.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * @author wenchao.meng
 *         <p>
 *         Jun 06, 2018
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@RestController
public class TransactionMain {

    @Autowired
    private TestTransacation testTransacation;

    @RequestMapping(path = "write")
    public void transactionTest() throws SQLException {

        testTransacation.testTransaction();

    }

    @RequestMapping(path = "get")
    public void read() throws SQLException {

        testTransacation.read();

    }


    public static void main(String[] argc) {

        SpringApplication.run(TransactionMain.class);

    }

}
