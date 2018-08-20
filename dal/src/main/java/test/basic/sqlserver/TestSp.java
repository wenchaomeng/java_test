package test.basic.sqlserver;

import org.junit.Test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Mar 20, 2018
 */
public class TestSp extends AbstractSqlserverTest {


    @Test
    public void test() throws SQLException, ClassNotFoundException, InterruptedException {

        Connection conn = getConnection();
        conn.setAutoCommit(false);


        doCallSp(conn);

        logger.info("[test][begin sleep]");
        TimeUnit.SECONDS.sleep(10);
        logger.info("[test][ end  sleep]");
//        conn.commit();
        logger.info("[test][ end  commit]");
        conn.close();

    }

    private void doCallSp(Connection conn) throws SQLException {
        doCallSp(conn, "insert_test1");
    }

    private void doCallSp(Connection conn, String sp_name) throws SQLException {

        CallableStatement statement = conn.prepareCall(String.format("exec %s ?", sp_name));
        statement.setString(1, genValue());
        boolean execute = statement.execute();
    }

    @Test
    public void testMultiTransaction() throws SQLException, ClassNotFoundException, InterruptedException {

        executors.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = getConnection();
                    conn.setAutoCommit(false);
//                    doInert(conn);
                    doCallSp(conn);
                    logger.info("[wait for commit]");
                    TimeUnit.SECONDS.sleep(5);
                    conn.commit();
                    logger.info("[commited ... commit]");
                } catch (Exception e) {
                    logger.error("[run]", e);
                }
            }
        });


//        executors.execute(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                    Connection conn = getConnection();
//                    conn.setAutoCommit(false);
//                    doInert(conn);
//                    logger.info("[wait for commit]");
//                    conn.commit();
//                    logger.info("[commited...]");
//                } catch (Exception e) {
//                    logger.error("[run]", e);
//                }
//            }
//        });

        TimeUnit.SECONDS.sleep(10000);
    }

    private void doInert(Connection conn) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("insert into test(name) values(?)");
        String value = genValue();
        statement.setString(1, value);
        statement.execute();
    }

    @Test
    public void testLock() throws SQLException, ClassNotFoundException, InterruptedException {


        int task = 2;

        for (int i = 0; i < task; i++) {

            executors.execute(new Runnable() {
                @Override
                public void run() {
                    Connection conn = null;

                    try {
                        conn = getConnection();
                        conn.setAutoCommit(false);
                        //SET LOCK_TIMEOUT 1800;
                        CallableStatement statement = conn.prepareCall("SET LOCK_TIMEOUT 30000; exec update_test ?");
//                        statement.setQueryTimeout(2);
                        statement.setString(1, randomString(10));
                        logger.info("[begin]");
                        boolean execute = statement.execute();
                        logger.info("[end]");
                        conn.commit();
                        logger.info("[commit]");
                    } catch (ClassNotFoundException e) {
                        logger.error("[run]", e);
                    } catch (SQLException e) {
                        logger.error("[run]", e);
                    }
                }
            });
        }

        TimeUnit.SECONDS.sleep(1000);

    }
}
