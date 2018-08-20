package test.dal.version;

import com.ctrip.platform.dal.dao.DalClient;
import com.ctrip.platform.dal.dao.DalClientFactory;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalTableDao;
import com.ctrip.platform.dal.dao.helper.DalDefaultJpaParser;
import com.ctrip.platform.dal.dao.sqlbuilder.SelectSqlBuilder;
import test.dal.mysql.pojo.ClusterTbl;

import java.util.List;

/**
 * @author wenchao.meng
 *         <p>
 *         Jun 21, 2018
 */
public class VersionTest {

    public static void main(String[] argc) throws Exception {

        new VersionTest().start();

    }

    private void start() throws Exception {

        DalClientFactory.initClientFactory(); // load from class-path Dal.config
        DalClientFactory.warmUpConnections();
        DalTableDao<ClusterTbl> tableDao = new DalTableDao<>(new DalDefaultJpaParser<ClusterTbl>(ClusterTbl.class));

        SelectSqlBuilder selectSqlBuilder = new SelectSqlBuilder();
        System.out.println("begin query");
        List<ClusterTbl> query = tableDao.query(selectSqlBuilder, new DalHints());
        System.out.println(" end  query");

    }
}
