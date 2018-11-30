package test.basic.mysql;

import com.mysql.jdbc.MySQLConnection;
import org.junit.Test;
import test.basic.AbstractDbTest;
import test.basic.CommonTask.BatchTask;

import java.sql.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Dec 26, 2017
 */
public class TestRawJbbcInsert extends AbstractMysqlTest {


    //    protected String getUrl() {
////        return "jdbc:mysql://10.2.38.45:9999/test";
//        return "jdbc:mysql://127.0.0.1:8888/test";
//    }

    @Override
    protected void addProperties(Properties properties) {
        //properties.put("useLocalSessionState", "true");
        properties.setProperty("connectTimeout", "5000");
        properties.setProperty("socketTimeout", "5000");
        properties.setProperty("tinyInt1isBit", "false");
        properties.setProperty("maxRows", "1");

        //
        properties.setProperty("allowMultiQueries", "true");
    }

    @Test
    public void testGetConnectionTimeout() throws SQLException, ClassNotFoundException {

        logger.error("[testGetConnectionTimeout][begin]");
        try {
            Connection connection = getConnection();
            logger.info("[testGetConnectionTimeout][ end ]");
        } catch (Exception e) {
            logger.error("[testGetConnectionTimeout][ end ]", e);
        }
    }

    @Test
    public void testTinyInt() throws SQLException {

        PreparedStatement statement = connection.prepareStatement("select * from test");
        ResultSet resultSet = statement.executeQuery();
        printResultSet(resultSet);

        logger.info("-------query");
        statement = connection.prepareStatement("select * from test");
        resultSet = statement.executeQuery();
        printResultSet(resultSet);

        logger.info("--------update");
        statement = connection.prepareStatement("update test set name = 'hello' where id = 1");
        int i = statement.executeUpdate();


        logger.info("-------query");
        statement = connection.prepareStatement("select * from test");
        resultSet = statement.executeQuery();
        printResultSet(resultSet);

    }

    @Test
    public void testPingWhileQuery() throws SQLException, InterruptedException {

        executors.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    TimeUnit.SECONDS.sleep(1);
                    logger.info("[begin ping]");
                    ((MySQLConnection) connection).pingInternal(true, 5000);
                    logger.info("[ edn  ping]");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        logger.info("[begin preparestatement]");
        PreparedStatement statement1 = connection.prepareStatement("select sleep(3)");
        ResultSet resultSet = statement1.executeQuery();
        printResultSet(resultSet);
        logger.info("[ end  execute]");

        TimeUnit.SECONDS.sleep(3);
    }


    @Test
    public void testBatchOrCombined() {

        new BatchTask(connection).run();
    }

    @Test
    public void testDuplicateId() throws SQLException, ClassNotFoundException {

        PreparedStatement statement1 = connection.prepareStatement(
                " insert into test(id, name) values(?, ?)", Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < 2; i++) {
            statement1.setLong(1, 1);
            statement1.setString(2, "name:" + i);
            statement1.addBatch();
        }

        statement1.executeBatch();
        ResultSet generatedKeys = statement1.getGeneratedKeys();
        printResultSet(generatedKeys);

    }


    @Test
    public void testBatch() throws SQLException, ClassNotFoundException {

        PreparedStatement statement1 = connection.prepareStatement(
                " insert into test(name) values(?)", Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < 3; i++) {
//            statement1.setString(1, "name:" + i);
            statement1.addBatch(String.format("insert into test(name) values('%s')", "testBatch:" + i));
        }

        statement1.addBatch(String.format("insert into test(name) values('%s')", randomString(200)));

        statement1.executeBatch();
        ResultSet generatedKeys = statement1.getGeneratedKeys();
        printResultSet(generatedKeys);

    }


    @Test
    public void testCombined() throws SQLException, ClassNotFoundException {


        PreparedStatement statement = connection.prepareStatement("delete from test where id =1 or id = 2 or id = 3");
        statement.executeUpdate();

        PreparedStatement statement1 = connection.prepareStatement(
                " insert into test(id, name)  values(2, 'testCombined:1'), (5, 'testCombined:2')", Statement.RETURN_GENERATED_KEYS);

//        PreparedStatement statement1 = connection.prepareStatement(
//                " insert into test(id, name)  values(1, 'testCombined:1')",  Statement.RETURN_GENERATED_KEYS);

        statement1.execute();
        ResultSet generatedKeys = statement1.getGeneratedKeys();
        printResultSet(generatedKeys);
    }


    @Test
    public void testEmoji() throws SQLException, ClassNotFoundException {

        logger.info("{}", connection.getAutoCommit());

        PreparedStatement statement1 = connection.prepareStatement("insert into test(name) values('hello1😀')");
        statement1.execute();

        showCharacters(connection);
    }


    @Test
    public void testTransaction() throws SQLException, ClassNotFoundException {

        connection.setAutoCommit(false);
        logger.info("{}", connection.getAutoCommit());

        try {
            PreparedStatement statement1 = connection.prepareStatement("insert into test(name) values('hello1😀')");
            statement1.execute();

            PreparedStatement statement2 = connection.prepareStatement("insert into test1(name) values('hello2')");
            statement2.execute();

            connection.commit();
        } catch (Exception e) {

            connection.rollback();
            e.printStackTrace();
        }

    }


    @Test
    public void testReadTimeout() throws SQLException, ClassNotFoundException {

        try {
            logger.info("begin");
            Connection connection = getConnection();
            logger.info("{}", connection);
        } catch (Exception e) {
            logger.error("excepton", e);
        }

    }

    @Test
    public void testSelect() throws SQLException, ClassNotFoundException {

        Statement stmt = connection.createStatement();

        ResultSet resultSet = stmt.executeQuery("select count(*) from test");

        printResultSet(resultSet);

    }


    @Test
    public void testGetSomeParameter() throws SQLException, ClassNotFoundException, InterruptedException {

        Connection conn = getConnection();

        int transactionIsolation = 0;

        for (int i = 0; i < 3; i++) {
            transactionIsolation = conn.getTransactionIsolation();
            logger.info("transactionIsolation :{}", transactionIsolation);
        }

        TimeUnit.SECONDS.sleep(1000);
    }

    @Test
    public void testSelectSleep() throws SQLException, ClassNotFoundException {

        Statement stmt = connection.createStatement();

        executors.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    logger.info("[begin close]");
                    connection.close();
                    logger.info("[end close]");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        logger.info("[begin query]");
        ResultSet resultSet = stmt.executeQuery("select id,name from test where sleep(20) = 0");
        logger.info("[end query]");

        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            logger.info("{}, {}", id, name);
        }
    }

    @Test
    public void testSelectStatement() throws SQLException, ClassNotFoundException, InterruptedException {


        PreparedStatement statement = connection.prepareStatement(" select id,name from test where id > ? and name = ?");

        statement.setInt(1, 1);
        statement.setString(2, "nihaoma");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            logger.info("{}, {}", id, name);
        }

        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void testSelectStatement1() throws SQLException, ClassNotFoundException {


        for (int i = 0; i < 100; i++) {

            PreparedStatement statement = connection.prepareStatement(" select id,name from test where id > 10 and name = 'nihamma'");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                logger.info("{}, {}", id, name);
            }
        }
    }

    @Test
    public void testSelectStatementName() throws SQLException, ClassNotFoundException {

    }

}
