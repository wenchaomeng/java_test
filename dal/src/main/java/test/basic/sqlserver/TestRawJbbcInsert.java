package test.basic.sqlserver;

import org.junit.Test;
import test.basic.AbstractDbTest;
import test.basic.CommonTask.BatchTask;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Dec 26, 2017
 */
public class TestRawJbbcInsert extends AbstractSqlserverTest {


//    protected String getUrl() {
//        return "jdbc:sqlserver://10.2.38.45:9999;databaseName=testdb";
//    }

    @Test
    public void testBatch() {

        new BatchTask(connection).run();

    }

    @Test
    public void testCombined() throws SQLException, ClassNotFoundException {

        PreparedStatement statement1 = connection.prepareStatement(
                " insert into test(name)  OUTPUT INSERTED.id values('testCombined:1'), ('testCombined:2')", Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = statement1.executeQuery();

        printResultSet(resultSet);

        ResultSet generatedKeys = statement1.getGeneratedKeys();
        AbstractDbTest.printResultSet(generatedKeys);

    }


    @Test
    public void testTimeout() throws SQLException, ClassNotFoundException {

        logger.info("[testTimeout][getConnection]");
        try {
            Connection conn = getConnection();
        } catch (Exception e) {
            logger.error("[testTimeout]", e);
        }
    }

    @Test
    public void testBatchSp() throws SQLException, ClassNotFoundException {

        CallableStatement statement = connection.prepareCall("exec insert_test ?,?");

        for (int i = 0; i < 3; i++) {

            statement.setString(1, "name" + i);
            statement.registerOutParameter(2, Types.BIGINT);
            statement.addBatch();
        }

        statement.executeBatch();


    }

    @Test
    public void testSpNameCall1() throws SQLException, ClassNotFoundException {

        CallableStatement statement = connection.prepareCall("exec insert_test @name=?, @id=?");
        statement.setString(1, "name");
        statement.registerOutParameter(2, Types.BIGINT);
        long id = statement.getLong(1);
        logger.info("inserted:{}", id);


        //inverse arguments!!
        statement = connection.prepareCall("exec insert_test @id=?, @name=?");
        statement.setString(2, "name");
        statement.registerOutParameter(1, Types.BIGINT);
        statement.execute();
        id = statement.getLong(1);
        logger.info("inserted:{}", id);


    }

    @Test
    public void testSpNameCall2() throws SQLException, ClassNotFoundException, InterruptedException {

        CallableStatement statement = connection.prepareCall("exec insert_test @name=?, @id=?  ");
        statement.setString("name", "name" + getTestName());
        statement.registerOutParameter("id", Types.BIGINT);
        statement.execute();
        long id = statement.getLong("id");
        logger.info("inserted:{}", id);

        TimeUnit.SECONDS.sleep(1);

        //inverse arguments
        statement = connection.prepareCall("exec insert_test @id=?, @name=? ");
        statement.setString("name", "name" + getTestName());
        statement.registerOutParameter("id", Types.BIGINT);
        statement.execute();
        id = statement.getLong("id");
        logger.info("inserted:{}", id);

    }

    @Test
    public void testSpNameCallTwoParam() throws SQLException, ClassNotFoundException {

//        CallableStatement statement = connection.prepareCall("exec insert_test @id=?, @name=? ");
//        CallableStatement statement = connection.prepareCall("exec insert_test @name=?,@id=? ");
        CallableStatement statement = connection.prepareCall("exec insert_test1 @time=?, @name=?  ");
        statement.setString(2, getTestName());
        statement.setDate(1, new Date(System.currentTimeMillis()));
        statement.execute();
        System.out.println("finished....");

    }

    @Test
    public void testSpOut() throws SQLException, ClassNotFoundException {

        CallableStatement statement = connection.prepareCall("exec insert_test ?,?");

        for (int i = 0; i < 3; i++) {

            statement.setString(1, "name");
            statement.registerOutParameter(2, Types.BIGINT);
            boolean execute = statement.execute();
            long aLong = statement.getLong(2);
            logger.info("{}", aLong);
        }
    }


    @Test
    public void testSelect() throws SQLException, ClassNotFoundException {

        Statement stmt = connection.createStatement();
        String sql = "select id,name from test";
        ResultSet resultSet = stmt.executeQuery(sql);


        while (resultSet.next()) {

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            logger.info("{}, {}", id, name);
        }

    }

    @Test
    public void testSetting800() {

        TimeZone componentTimeZone = TimeZone.getTimeZone("GMT+8:00");
        GregorianCalendar cal = new GregorianCalendar(componentTimeZone, Locale.US);

        cal.set(1900, Calendar.JANUARY, 1 + 0, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, (int) 32400000);

        long timeInMillis = cal.getTimeInMillis();

        logger.info("{}", timeInMillis);
        Date date = new Date(timeInMillis);
        logger.info("{}, {}", date);

    }

    @Test
    public void tesgtDate() {

        TimeZone componentTimeZone = TimeZone.getDefault();
        GregorianCalendar cal = new GregorianCalendar(componentTimeZone, Locale.US);

        cal.set(1900, Calendar.JANUARY, 1 + 0, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, (int) 32400000);

        long timeInMillis = cal.getTimeInMillis();

        logger.info("{}", timeInMillis);
        Date date = new Date(timeInMillis);

        logger.info("{}, {}", date);

        SimpleDateFormat format = getGmt8format();
        System.out.println(format.format(date));

        long time = date.getTime();

    }

    @Test
    public void testTimezone() {

        logger.info("{}", TimeZone.getDefault());
    }

    @Test
    public void testStatement() throws SQLException, ClassNotFoundException {

        CallableStatement statement = connection.prepareCall("select id,name,time from test where id >= ?");


    }

    @Test
    public void testSelectStatement() throws SQLException, ClassNotFoundException {


        CallableStatement statement = connection.prepareCall("select id,name,time from test where id >= ?");
        statement.setInt(1, 1);

        ResultSet resultSet = statement.executeQuery();

        SimpleDateFormat format = getGmt8format();

        while (resultSet.next()) {

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            long num = 0;
            Timestamp timestamp = resultSet.getTimestamp(3, Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")));
            logger.info("{}, {}, {}, {}", id, name, num, timestamp);
            if (timestamp != null) {
                logger.info("{}", format.format(timestamp));
            }

        }
    }


    @Test
    public void testSelectStatementName() throws SQLException, ClassNotFoundException {

    }

}
