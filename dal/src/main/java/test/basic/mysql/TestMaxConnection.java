package test.basic.mysql;

import org.junit.Test;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Dec 26, 2017
 */
public class TestMaxConnection extends AbstractMysqlTest {

    @Test
    public void testSelect() throws SQLException, ClassNotFoundException, InterruptedException {

        int counts = 200;
        List<Connection> connections = new LinkedList<>();

        try {
            for (int i = 0; i < counts; i++) {

                Connection conn = getConnection();
                connections.add(conn);
            }
        } catch (Exception e) {
            logger.error("error create connections", e);
        }

    }

}
