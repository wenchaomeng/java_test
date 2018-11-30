package test.basic.mysql;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Dec 27, 2017
 */
public class PoolMysql extends AbstractMysqlTest {

    private PoolProperties p;
    private int maxActive = 5;
    private int minIdle = 3;


    @Before
    public void beforePoolMysql() {

        p = new PoolProperties();
        p.setUrl(getUrl());
        p.setDriverClassName(getDriver());
        p.setUsername(getUser());
        p.setPassword(getPassword());
        setProperties(p);
    }

    @Override
    protected boolean createInitConnection() {
        return false;
    }

    @Test
    public void testPoolMaxRows() throws SQLException {

        p.setInitialSize(1);
        p.setConnectionProperties("maxRows=1");

        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);

        for (int i = 0; i < 2; i++) {

            try (Connection con = datasource.getConnection()) {
                PreparedStatement statement = con.prepareStatement("select * from test");
                ResultSet resultSet = statement.executeQuery();
                printResultSet(resultSet);
            }
        }


    }


    @Test
    public void testPoolFull() throws SQLException {

        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);
        for (int i = 0; i < maxActive + 1; i++) {
            Connection con = null;
            con = datasource.getConnection();
        }
    }

    @Test
    public void testRemoveAbandoned() throws SQLException, InterruptedException {

        int removeAbandonedTimeout = 5;

        p.setInitialSize(0);
        p.setRemoveAbandoned(true);
        p.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        p.setLogAbandoned(true);
        p.setTimeBetweenEvictionRunsMillis(removeAbandonedTimeout / 2);

        DataSource datasource = new DataSource(p);

        try {

            logger.info("[begin]");
            Connection connection = datasource.getConnection();
            PreparedStatement statement = connection.prepareStatement(String.format("select sleep(%d)", removeAbandonedTimeout * 12));
            statement.execute();
            connection.close();
        } catch (Exception e) {
            logger.error("error", e);
        }


        TimeUnit.SECONDS.sleep(3000);
    }

    @Test
    public void testGetMax() throws SQLException, InterruptedException {

        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);

        for (int i = 0; i < 10; i++) {

            Connection connection = datasource.getConnection();
            logger.info("{}", connection);
            connection.close();
            TimeUnit.SECONDS.sleep(1);
        }


    }


    @Test
    public void testGetProperties() throws SQLException {

        p.setConnectionProperties("useLocalSessionState=true");
        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);

        Connection con = null;
        try {
            con = datasource.getConnection();
            for (int i = 0; i < 3; i++) {
                con.getTransactionIsolation();
            }

        } finally {
            con.close();
        }
    }

    @Test
    public void testMysql() throws InterruptedException {

        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);

        Connection con = null;
        try {
            con = datasource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from test");

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                logger.info("{}, {}", id, name);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }

    }

    @Test
    public void testPurge() throws SQLException {

        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);

        printInfo(datasource);

        Connection connection = datasource.getConnection();

        logger.info("[get one connection]");

        printInfo(datasource);

        datasource.purge();
        logger.info("[purge]");

        printInfo(datasource);
    }

    @Test
    public void testClosePool() throws SQLException {

        final DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);

        printInfo(datasource);

        executors.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                logger.info("[begin close datasource]");
                datasource.close(true);
                logger.info("[end close datasource]");
            }
        });

        Connection connection = datasource.getConnection();
        logger.info("[begin query]");
        ResultSet resultSet = connection.createStatement().executeQuery("select id,name from test where sleep(20) = 0");
        logger.info("[end query]");

        logger.info("[get one connection]");

        printInfo(datasource);

    }

    private void printInfo(DataSource datasource) {

        logger.info("idle:{}", datasource.getIdle());
        logger.info("active:{}", datasource.getActive());

    }

    @Test
    public void testAfterReturn() {

        DataSource datasource = new DataSource();
        p.setInitialSize(1);
        datasource.setPoolProperties(p);

        Connection con = null;
        try {
            con = datasource.getConnection();
            logger.info("{}", con);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void setProperties(PoolProperties p) {

        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(maxActive);
        p.setInitialSize(minIdle);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(minIdle);
        p.setMaxIdle(minIdle);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
//        p.setJdbcInterceptors(
//                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
//                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
    }


}
