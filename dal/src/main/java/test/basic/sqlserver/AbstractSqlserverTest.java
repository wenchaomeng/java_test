package test.basic.sqlserver;

import test.basic.AbstractDbTest;

/**
 * @author wenchao.meng
 *         <p>
 *         Dec 27, 2017
 */
public class AbstractSqlserverTest extends AbstractDbTest{

    protected String getUrl() {
        return "jdbc:sqlserver://10.32.21.17:2761;databaseName=testdb";
    }

    protected String getUser() {
        return "sa";
    }

    protected String getPassword() {
        return "Ctrip1234";
    }

    protected String getDriver() {
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }
}
