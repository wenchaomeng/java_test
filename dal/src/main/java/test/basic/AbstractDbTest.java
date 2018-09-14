package test.basic;

import org.junit.After;
import org.junit.Before;
import test.AbstractTest;

import java.sql.*;
import java.util.Properties;

/**
 * @author wenchao.meng
 *         <p>
 *         Dec 26, 2017
 */
public abstract class AbstractDbTest extends AbstractTest {

    protected Connection connection;


    @Before
    public void beforeAbstractDbTest() throws SQLException, ClassNotFoundException {
        try {
            connection = getConnection();
        } catch (Exception e) {
            logger.error("[error get connection]" + getUrl(), e);
        }
    }

    @After
    public void afterAbstractDbTest() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


    protected abstract String getUrl();

    protected abstract String getUser();

    protected abstract String getPassword();

    protected Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName(getDriver());// 动态加载mysql驱动
        Properties properties = new Properties();
        properties.put("user", getUser());
        properties.put("password", getPassword());

        addProperties(properties);

        Connection connection = DriverManager.getConnection(getUrl(), properties);

        return connection;

    }

    protected void addProperties(Properties properties) {

    }

    public static void printResultSet(ResultSet resultSet) throws SQLException {

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        StringBuilder sb = new StringBuilder();
        sb.append("Table:");
        for (int i = 1; i <= columnCount; i++) {
            sb.append(metaData.getTableName(i));
            sb.append(",");
        }

        logger.info("{}", sb);

        while (resultSet.next()) {
            sb = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                sb.append(
                        resultSet.getMetaData().getColumnName(i)
                                + "[" + simpleClassName(resultSet.getMetaData().getColumnClassName(i)) + "]"
                                + ":" + resultSet.getObject(i));

                sb.append(",");
            }
            logger.info("{}", sb);
        }
    }

    private static String simpleClassName(String columnClassName) {

        if (columnClassName == null) {
            return "--null--";
        }

        int index = columnClassName.lastIndexOf(".");
        return columnClassName.substring(index + 1);
    }


    protected abstract String getDriver();
}
