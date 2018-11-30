package test.basic.sqlserver;

import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import org.junit.Test;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Mar 20, 2018
 */
public class TestTvp extends AbstractSqlserverTest {

//    protected String getUrl() {
//        return "jdbc:sqlserver://10.2.24.217:2761;databaseName=testdb";
//    }

    @Test
    public void testDefault() throws SQLException, ClassNotFoundException, InterruptedException {

        Connection conn = getConnection();

        doCallSp(conn);

        conn.close();

    }

    @Test
    public void testUseLongSql() throws SQLException, ClassNotFoundException, InterruptedException {

        Connection conn = getConnection();

        String sql = "DECLARE @LocationTVP AS tvptest;\n" +
                "INSERT INTO @LocationTVP (time, name) values (?, ?)\n" +
                "EXEC tvp_insert_test @LocationTVP;\n";


        PreparedStatement statement = conn.prepareStatement(sql);

        int count = 5;
        for (int i = 0; i < count; i++) {

            statement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            statement.setString(2, getTestName() + ":" + i);
            statement.addBatch();
            TimeUnit.SECONDS.sleep(1);
        }

        logger.info("execute");
        statement.executeBatch();

        conn.close();

    }

    private void doCallSp(Connection conn) throws SQLException {
        doCallSp(conn, "tvp_insert_test");
    }

    private void doCallSp(Connection conn, String sp_name) throws SQLException {


        SQLServerCallableStatement statement = (SQLServerCallableStatement) conn.prepareCall(String.format("exec %s ?", sp_name));

        SQLServerDataTable tvp = new SQLServerDataTable();

        tvp.addColumnMetadata("name", JDBCType.VARCHAR.getVendorTypeNumber());
        tvp.addColumnMetadata("time", JDBCType.DATE.getVendorTypeNumber());
        tvp.addRow("tvp from java", new java.sql.Date(System.currentTimeMillis()));

        tvp.addColumnMetadata("time", JDBCType.DATE.getVendorTypeNumber());
        tvp.addColumnMetadata("name", JDBCType.VARCHAR.getVendorTypeNumber());
        tvp.addRow(new java.sql.Date(System.currentTimeMillis()), "tvp from java");

        statement.setStructured(1, "tvptest", tvp);

        boolean execute = statement.execute();

        System.out.println(execute);


    }
}


/**
 * CREATE TYPE tvptest AS TABLE
 * ( name VARCHAR(100)
 * , time datetime);
 * <p>
 * GO
 * <p>
 * CREATE PROCEDURE tvp_insert_test
 *
 * @TVP tvptest READONLY
 * AS
 * <p>
 * INSERT INTO test
 * (name
 * ,time)
 * SELECT *
 * FROM  @TVP;
 * GO
 * <p>
 * DECLARE @LocationTVP AS tvptest;
 * INSERT INTO @LocationTVP (name, time) values ('hellotcp', '2018-09-30')
 * EXEC tvp_insert_test @LocationTVP;
 * <p>
 * GO
 */
