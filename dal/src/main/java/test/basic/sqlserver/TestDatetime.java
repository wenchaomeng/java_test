package test.basic.sqlserver;

import microsoft.sql.DateTimeOffset;
import org.junit.Test;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author wenchao.meng
 *         <p>
 *         Dec 26, 2017
 */
public class TestDatetime extends AbstractSqlserverTest {

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
    public void testInsert() throws SQLException, ClassNotFoundException {

        Connection conn = getConnection();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));

        PreparedStatement statement = conn.prepareStatement("insert into test(name, timeoffset) values (?, ?)");

        statement.setString(1, getTestName() + "-" + randomString(3));
//        statement.setTime(2, new Date(System.currentTimeMillis()), Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")));
//        statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        statement.setObject(2, DateTimeOffset.valueOf(timestamp, cal));
        statement.execute();

        conn.close();

    }

    @Test
    public void testSelectStatement() throws SQLException, ClassNotFoundException {

        Connection conn = getConnection();

        CallableStatement statement = conn.prepareCall("select id,name,time,timeoffset from test where id >= ?");
        statement.setInt(1, 1);

        ResultSet resultSet = statement.executeQuery();

        SimpleDateFormat format = getGmt8format();

        while (resultSet.next()) {

            logger.info("-----------------------------");

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            Timestamp timestamp = resultSet.getTimestamp(3, Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")));

            Timestamp timeoffset = resultSet.getTimestamp(4);
            Timestamp timeoffset8 = resultSet.getTimestamp(4, Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")));
            Timestamp timeoffset7 = resultSet.getTimestamp(4, Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00")));

            logger.info("{}, {}, {}, {}, ", id, name, timestamp, timeoffset);
            logger.info("offset:{}, offset8:{}, offset7:{}", timeoffset, timeoffset8, timeoffset7);
            if (timestamp != null) {
                logger.info("{}", format.format(timestamp));
            }

        }
    }

    protected SimpleDateFormat getGmt8format() {

        String formatStr = "yyyy-MM-dd'T'HH:mm:ss.SSS ZZZZ";
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        format.setCalendar(Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")));

        return format;
    }


    @Test
    public void testSelectStatementName() throws SQLException, ClassNotFoundException {

    }

}
