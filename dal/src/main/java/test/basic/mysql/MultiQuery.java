package test.basic.mysql;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Aug 20, 2018
 */
public class MultiQuery extends AbstractMysqlTest {


    @Override
    protected void addProperties(Properties properties) {
        //properties.put("useLocalSessionState", "true");
        properties.setProperty("connectTimeout", "5000");
        properties.setProperty("socketTimeout", "5000");
        properties.setProperty("tinyInt1isBit", "false");

        //
        properties.setProperty("allowMultiQueries", "true");
    }

    @Test
    public void testMultiQueries() throws SQLException {

//        String sql = "insert into test(name) values('hello1');SELECT LAST_INSERT_ID();";
        String sql = "insert into test(name) values('hello1');SELECT LAST_INSERT_ID();";
//        String sql = "select min(id) min from test; select max(id) max from test;select count(*) count from test;";

        multiQuery(connection, sql);
    }

    private void multiQuery(Connection connection, String sql) throws SQLException {
        Statement statement = connection.createStatement();
        boolean execute = statement.execute(sql);

        do {
            logger.info("{}", "------------------");
            ResultSet resultSet = statement.getResultSet();
            if (resultSet != null) {
                printResultSet(resultSet);
            } else {
                int updateCount = statement.getUpdateCount();
                logger.info("updateCount:{}", updateCount);
            }
        } while (statement.getMoreResults());

    }

    @Test
    public void testMultiQueriesConcurrent() throws SQLException, InterruptedException {

        String sql = "insert into test(name) values('hello1');SELECT LAST_INSERT_ID();";

        for (int i = 0; i < 10; i++) {

            executors.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        try (Connection connection = getConnection()) {
                            multiQuery(connection, sql);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        TimeUnit.SECONDS.sleep(10);
    }
}
