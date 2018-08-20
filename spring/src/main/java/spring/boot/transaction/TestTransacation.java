package spring.boot.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;

/**
 * @author wenchao.meng
 *         <p>
 *         Jun 06, 2018
 */
@Component
public class TestTransacation {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "datasource1")
    private DataSource dataSource1;

    @Resource(name = "datasource2")
    private DataSource dataSource2;

    @Transactional(transactionManager = "tx1")
    public void testTransaction() throws SQLException {

        updateSomething(dataSource1);
        updateSomething(dataSource2);

        throw new RuntimeException();

    }

    private void updateSomething(DataSource dataSource) throws SQLException {

        Connection connection = DataSourceUtils.getConnection(dataSource);
        logger.info("{}, {}", connection.getAutoCommit(), connection);
        PreparedStatement statement1 = connection.prepareStatement("insert into test(name) values('hello1')");
        statement1.execute();

    }

    public void read() throws SQLException {

        readAll(dataSource1);
        readAll(dataSource2);
    }

    private void readAll(DataSource dataSource) throws SQLException {

        Connection connection = DataSourceUtils.getConnection(dataSource);

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("select * from test");
        logger.info("[end query]");

        while (resultSet.next()) {

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);

            logger.info("{}, {}", id, name);
        }

    }

}
