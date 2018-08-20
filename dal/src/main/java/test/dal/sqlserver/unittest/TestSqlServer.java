package test.dal.sqlserver.unittest;

import com.ctrip.platform.dal.dao.DalClientFactory;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalTableDao;
import com.ctrip.platform.dal.dao.helper.DalDefaultJpaParser;
import com.ctrip.platform.dal.dao.sqlbuilder.SelectSqlBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import test.dal.sqlserver.pojo.TestTbl;

import java.sql.SQLException;

/**
 * @author wenchao.meng
 *         <p>
 *         Dec 26, 2017
 */
public class TestSqlServer {

    private static final String DATA_BASE = "testSqlServer";

    private static DalTableDao<TestTbl> clusterTblDalTableDao;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        DalClientFactory.initClientFactory(); // load from class-path Dal.config
        DalClientFactory.warmUpConnections();

        clusterTblDalTableDao = new DalTableDao<TestTbl>(
                new DalDefaultJpaParser<TestTbl>(TestTbl.class));
    }

    @Test
    public void insert() throws SQLException {

        TestTbl testTbl = new TestTbl();
        testTbl.setName("name1");

        DalHints hints = DalHints.createIfAbsent(null);
        SelectSqlBuilder builder = new SelectSqlBuilder().selectCount();
        clusterTblDalTableDao.insert(hints, testTbl);

    }

    @Test
    public void testCount() throws SQLException {

        DalHints hints = DalHints.createIfAbsent(null);
        SelectSqlBuilder builder = new SelectSqlBuilder().selectCount();
        int count = clusterTblDalTableDao.count(builder, hints).intValue();

        System.out.println(count);
    }



}
