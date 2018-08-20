package test.basic.mysql;

import org.junit.Test;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 12, 2018
 */
public class DatetimeTest extends AbstractMysqlTest {

    @Test
    public void testInsert() throws SQLException, ClassNotFoundException {

        Connection conn = getConnection();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));

        PreparedStatement statement = conn.prepareStatement("insert into test(name, time) values (?, ?)");

        statement.setString(1, getTestName() + "-" + randomString(3));
//        statement.setTime(2, new Date(System.currentTimeMillis()), Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")));
        statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()), cal);
        statement.execute();

        conn.close();

    }

    @Test
    public void testSelectStatement() throws SQLException, ClassNotFoundException {

        Connection conn = getConnection();

        CallableStatement statement = conn.prepareCall("select id,name,time from test where id >= ?");
        statement.setInt(1, 1);

        ResultSet resultSet = statement.executeQuery();

        SimpleDateFormat format = getGmt8format();

        while (resultSet.next()) {

            logger.info("-----------------------------");

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            Timestamp timeoffset = resultSet.getTimestamp(3);
            Timestamp timeoffset8 = resultSet.getTimestamp(3, Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")));
            Timestamp timeoffset7 = resultSet.getTimestamp(3, Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00")));

            logger.info("{}, {}, {}, {}, ", id, name, timeoffset);
            logger.info("offset:{}, offset8:{}, offset7:{}", timeoffset, timeoffset8, timeoffset7);
        }
    }

}
