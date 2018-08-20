package test.basic.sqlserver;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 12, 2018
 */
public class LoadTest extends AbstractSqlserverTest {

    private int count = 50000;

    private int eachSize = 1 << 5;

    private int concurrentCount = 100;


    @Test
    public void testInsert() throws SQLException, ClassNotFoundException, InterruptedException {

        for (int i = 0; i < concurrentCount; i++) {

            executors.execute(new Runnable() {
                @Override
                public void run() {
                    Connection connection = null;
                    try {
                        connection = getConnection();
                        PreparedStatement statement = connection.prepareStatement("insert into loadtest2(name) values (?)");
                        for (int i = 0; i < count; i++) {
                            if (i % 1000 == 0) {
                                logger.info("{}", i);
                            }
                            statement.setString(1, randomString(eachSize));
                            statement.execute();
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        TimeUnit.DAYS.sleep(1);
    }


}
