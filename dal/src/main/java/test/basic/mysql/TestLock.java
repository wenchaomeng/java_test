package test.basic.mysql;


import org.junit.Test;

import java.sql.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Dec 26, 2017
 */
public class TestLock extends AbstractMysqlTest {

    @Test
    public void testDeadLock() throws SQLException, InterruptedException {

        final Long id1 = insertLine(getTestName());
        final Long id2 = insertLine(getTestName());

        logger.info("{}, {}", id1, id2);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try (Connection connection = getConnection()) {

                    connection.setAutoCommit(false);

                    logger.info("[createStatement]");
                    Statement statement = connection.createStatement();
                    statement.execute(String.format("update test set name = 'new1%s' where id = %d", getTestName(), id1));

                    logger.info("[sleep]");
                    TimeUnit.SECONDS.sleep(1);
                    logger.info("[sleep finished]");

                    statement.execute(String.format("update test set name = 'new1%s' where id = %d", getTestName(), id2));

                    logger.info("[begin commit]");
                    connection.commit();
                    logger.info("[end  commit]");
                } catch (Exception e) {
                    logger.error("[error]", e);
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try (Connection connection = getConnection()) {

                    connection.setAutoCommit(false);

                    Statement statement = connection.createStatement();
                    statement.execute(String.format("update test set name = 'new2%s' where id = %d", getTestName(), id2));

                    logger.info("[sleep]");
                    TimeUnit.SECONDS.sleep(1);
                    logger.info("[sleep finished]");

                    statement.execute(String.format("update test set name = 'new2%s' where id = %d", getTestName(), id1));
                    logger.info("[begin commit]");
                    connection.commit();
                    logger.info("[ end  commit]");
                } catch (Exception e) {
                    logger.error("[error]", e);
                }
            }
        }).start();


        TimeUnit.SECONDS.sleep(60);
    }

    private Long insertLine(String testName) throws SQLException {

        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into test(name) values('"+testName+"')", Statement.RETURN_GENERATED_KEYS);
        ResultSet generatedKeys = statement.getGeneratedKeys();
        Long result = null;
        while (generatedKeys.next()) {
            result = generatedKeys.getLong(1);
        }

        return result;
    }


    @Override
    protected void addProperties(Properties properties) {
        super.addProperties(properties);

    }

    @Test
    public void testTransactionWaitTimeout() throws SQLException, ClassNotFoundException, InterruptedException {

        Long id1 = insertLine(getTestName());
        Long id2 = insertLine(getTestName());

        logger.info("{}, {}", id1, id2);

        final int lockWaitTimeout = 5;

        for (int i = 0; i < 2; i++) {

            int finalI = i;
            executors.execute(new Runnable() {
                @Override
                public void run() {

                    try (Connection connection = getConnection()) {

                        executeSql(connection, "set session innodb_lock_wait_timeout = " + lockWaitTimeout);

                        if (finalI > 0) {
                            TimeUnit.SECONDS.sleep(1);
                        }

                        connection.setAutoCommit(false);
                        logger.info("{}", connection.getAutoCommit());
                        try {
                            logger.info("[begin prepareStatement]");

                            if (finalI > 0) {
                                logger.info("[set id2]");
                                PreparedStatement statement1 = connection.prepareStatement(
                                        String.format("update test set name = 'nihao!!!!new value' where id=%d", id2)
                                );
                                statement1.execute();
                            }

                            PreparedStatement statement1 = connection.prepareStatement(
                                    String.format("update test set name = 'nihao' where id = %d", id1));

                            logger.info("[begin execute]");
                            statement1.execute();
                            logger.info("[ end  execute]");

                            if (finalI == 0) {
                                logger.info("[begin sleep]");
                                TimeUnit.SECONDS.sleep(lockWaitTimeout + 2);
                                logger.info("[ end  sleep]");
                            }
                            logger.info("[begin commit]");
                            connection.commit();
                            logger.info("[end commit]");
                        } catch (Exception e) {
//                            connection.rollback();
                            logger.error("[error]" + finalI, e);
                            connection.commit();
                        }
                    } catch (Exception e) {
                        logger.error("[error]", e);
                    }

                }
            });
        }

        TimeUnit.SECONDS.sleep(60);
    }

    private void executeSql(Connection connection, String sql) throws SQLException {

        Statement statement = connection.createStatement();
        statement.execute(sql);

    }

}
