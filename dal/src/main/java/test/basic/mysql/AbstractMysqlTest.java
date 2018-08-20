package test.basic.mysql;

import test.basic.AbstractDbTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wenchao.meng
 *         <p>
 *         Dec 26, 2017
 */
public abstract class AbstractMysqlTest extends AbstractDbTest {

    @Override
    protected String getUrl() {
        return "jdbc:mysql://localhost:3306/test";
    }


    @Override
    protected String getUser() {
        return "root";
    }

    @Override
    protected String getPassword() {
        return "root";
    }

    @Override
    protected String getDriver() {
        return "com.mysql.jdbc.Driver";
    }

    protected void showCharacters(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("show variables like '%character%'");

        while (resultSet.next()) {
            logger.info("{}, {}", resultSet.getObject(1), resultSet.getObject(2));
        }
    }

    protected void setNames(Connection connection, String character) throws SQLException {

        logger.info("setnames {}", character);
        connection.createStatement().executeQuery(String.format("set names %s", character));
    }


}
