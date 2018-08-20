package test.basic.mysql;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 11, 2018
 */
public class TimerThreadLease extends AbstractMysqlTest {

    @Test
    public void testTimer() throws InterruptedException {

        Timer timer = new Timer();

        logger.info("[begin][schedule]");
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                logger.info("[begin][run]");
//            }
//        }, 1000);
//

        TimeUnit.SECONDS.sleep(1000);

    }

    @Test
    public void testSelectConnection() throws SQLException, ClassNotFoundException, InterruptedException {

        Connection conn = getConnection();

        logger.info("{}", conn.getClass());

        TimeUnit.SECONDS.sleep(1000);

    }

    @Test
    public void testSelect() throws SQLException, ClassNotFoundException, InterruptedException {

        Connection conn = getConnection();

        Statement stmt = conn.createStatement();
        stmt.setQueryTimeout(5);
        ResultSet resultSet = stmt.executeQuery("select id,name from test");

        while (resultSet.next()) {

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            logger.info("{}, {}", id, name);
        }

        while (true) {
            boolean valid = conn.isValid(1);
            if (!valid) {
                logger.info("not valid");
                break;
            } else {
                logger.info("valid");
                TimeUnit.SECONDS.sleep(1);
            }
        }

        logger.info("close connection");
        conn.close();

        TimeUnit.SECONDS.sleep(1000);
    }

    @Override
    protected String getUrl() {
        return "jdbc:mysql://10.2.58.244:3306/test";
    }
}
