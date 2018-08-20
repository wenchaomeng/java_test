package test.basic.CommonTask;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.basic.AbstractDbTest;

import java.sql.*;

/**
 * @author wenchao.meng
 *         <p>
 *         Jul 19, 2018
 */
public class BatchTask extends AbstractTask {


    private Connection connection;

    public BatchTask(Connection connection) {
        this.connection = connection;

    }

    @Override
    public void doRun() {

        try {

            logger.info("testBatch");
            executeCatException(() -> testBatch());

            logger.info("testBatch1");
            executeCatException(() -> testBatch1());

            logger.info("testCombined");
            executeCatException(() -> testCombined());

        } catch (Exception e) {
            logger.error("run", e);
        }
    }


    @Test
    public void testBatch1() throws SQLException, ClassNotFoundException {

        PreparedStatement statement1 = connection.prepareStatement(
                " insert into test(name) values(?)", Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < 3; i++) {
//            statement1.setString(1, "name:" + i);
            statement1.addBatch(String.format("insert into test(name) values('%s')", "testBatch1" + i));
        }
        statement1.executeBatch();
        ResultSet generatedKeys = statement1.getGeneratedKeys();
        AbstractDbTest.printResultSet(generatedKeys);

    }

    @Test
    public void testBatch() throws SQLException, ClassNotFoundException {

        PreparedStatement statement1 = connection.prepareStatement(
                " insert into test(name) values(?)", Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < 3; i++) {
            statement1.setString(1, "testBatch:" + i);
            statement1.addBatch();
        }
        statement1.executeBatch();
        ResultSet generatedKeys = statement1.getGeneratedKeys();
        AbstractDbTest.printResultSet(generatedKeys);
    }

    @Test
    public void testCombined() throws SQLException, ClassNotFoundException {

        PreparedStatement statement1 = connection.prepareStatement(
                " insert into test(name)  values('testCombined:1'), ('testCombined:2')", Statement.RETURN_GENERATED_KEYS);

        statement1.execute();
        ResultSet generatedKeys = statement1.getGeneratedKeys();
        AbstractDbTest.printResultSet(generatedKeys);
    }


}
