package test.dal.mysql.unittest;


import org.junit.*;

import static org.junit.Assert.*;

import com.ctrip.platform.dal.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.dal.mysql.pojo.ClusterTbl;
import test.dal.mysql.pojo.ClusterTblDao;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * JUnit test of ClusterTblDao class.
 * Before run the unit test, you should initiate the test data and change all the asserts correspond to you case.
 **/
public class DalUnitTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DATA_BASE = "fxxpipeDB_W";

    private static DalClient client = null;
    private static ClusterTblDao dao = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        DalClientFactory.initClientFactory(); // load from class-path Dal.config
        DalClientFactory.warmUpConnections();
        client = DalClientFactory.getClient(DATA_BASE);
        dao = new ClusterTblDao();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
    }

    private ClusterTbl createPojo(int index) {
        ClusterTbl daoPojo = new ClusterTbl();

        //daoPojo.setId(index);
        //daoPojo set not null field

        return daoPojo;
    }

    private void changePojo(ClusterTbl daoPojo) {
        // Change a field to make pojo different with original one
    }

    private void changePojos(List<ClusterTbl> daoPojos) {
        for (ClusterTbl daoPojo : daoPojos)
            changePojo(daoPojo);
    }

    private void verifyPojo(ClusterTbl daoPojo) {
        //assert changed value
    }

    private void verifyPojos(List<ClusterTbl> daoPojos) {
        for (ClusterTbl daoPojo : daoPojos)
            verifyPojo(daoPojo);
    }

    @After
    public void tearDown() throws Exception {
//		To clean up all test data
//		dao.delete(null, dao.queryAll(null));
    }


    @Test
    public void testCountSimple() throws Exception {

        int affected = dao.count(new DalHints());
        logger.info("total count: {}", affected);

    }

    @Test
    public void testCount() throws Exception {

        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(4);
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    int affected = dao.count(new DalHints());
                    logger.info("count: {}", affected);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);


        TimeUnit.SECONDS.sleep(10000);
    }

    @Test
    public void testDelete1() throws Exception {
        DalHints hints = new DalHints();
        ClusterTbl daoPojo = createPojo(1);
        /**
         * WARNING !!!
         * To test delete, please make sure you can easily restore all the data. otherwise data will not be revovered.
         */
//		int affected = dao.delete(hints, daoPojo);
//		assertEquals(1, affected);
    }

    @Test
    public void testDelete2() throws Exception {
        DalHints hints = new DalHints();
//		List<ClusterTbl> daoPojos = dao.queryAll(null);
        /**
         * WARNING !!!
         * To test delete, please make sure you can easily restore all the data. otherwise data will not be revovered.
         */

//		int[] affected = dao.delete(hints, daoPojos);
//		assertArrayEquals(new int[]{1,1,1,1,1,1,1,1,1,1},  affected);
    }

    @Test
    public void testBatchDelete() throws Exception {
        DalHints hints = new DalHints();
//		List<ClusterTbl> daoPojos = dao.queryAll(null);
        /**
         * WARNING !!!
         * To test batchDelete, please make sure you can easily restore all the data. otherwise data will not be revovered.
         */
//		int[] affected = dao.batchDelete(hints, daoPojos);
//		assertArrayEquals(new int[]{1,1,1,1,1,1,1,1,1,1},  affected);
    }

    @Test
    public void testQueryAll() throws Exception {
        List<ClusterTbl> list = dao.queryAll(new DalHints().allowPartial());
        logger.info("{}", list);

//		assertEquals(10, list.size());
    }

    @Test
    public void testInsert1() throws Exception {
        DalHints hints = new DalHints();
        ClusterTbl daoPojo = createPojo(1);
        int affected = dao.insert(hints, daoPojo);
        assertEquals(1, affected);
    }

    @Test
    public void testInsert2() throws Exception {
        DalHints hints = new DalHints();
        List<ClusterTbl> daoPojos = dao.queryAll(new DalHints());
        int[] affected = dao.insert(hints, daoPojos);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, affected);
    }

    @Test
    public void testInsert3() throws Exception {
        DalHints hints = new DalHints();
        KeyHolder keyHolder = new KeyHolder();
        ClusterTbl daoPojo = createPojo(1);
        int affected = dao.insert(hints, keyHolder, daoPojo);
        assertEquals(1, affected);
        assertEquals(1, keyHolder.size());
    }

    @Test
    public void testInsert4() throws Exception {
        DalHints hints = new DalHints();
        KeyHolder keyHolder = new KeyHolder();
        List<ClusterTbl> daoPojos = dao.queryAll(new DalHints());
        int[] affected = dao.insert(hints, keyHolder, daoPojos);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, affected);
        assertEquals(10, keyHolder.size());
    }

    @Test
    public void testInsert5() throws Exception {
        DalHints hints = new DalHints();
        List<ClusterTbl> daoPojos = dao.queryAll(new DalHints());
        int[] affected = dao.batchInsert(hints, daoPojos);
    }

    @Test
    public void testCombinedInsert1() throws Exception {
        DalHints hints = new DalHints();
        List<ClusterTbl> daoPojos = dao.queryAll(new DalHints());
        int affected = dao.combinedInsert(hints, daoPojos);
        assertEquals(10, affected);
    }

    @Test
    public void testCombinedInsert2() throws Exception {
        DalHints hints = new DalHints();
        KeyHolder keyHolder = new KeyHolder();
        List<ClusterTbl> daoPojos = dao.queryAll(new DalHints());
        int affected = dao.combinedInsert(hints, keyHolder, daoPojos);
        assertEquals(10, keyHolder.size());
    }

    @Test
    public void testQueryAllByPage() throws Exception {
        DalHints hints = new DalHints();
        int pageSize = 100;
        int pageNo = 1;
        List<ClusterTbl> list = dao.queryAllByPage(pageNo, pageSize, hints);
        assertEquals(10, list.size());
    }

    @Test
    public void testQueryByPk1() throws Exception {
        Number id = 1;
        DalHints hints = new DalHints();
        ClusterTbl affected = dao.queryByPk(id, hints);
        assertNotNull(affected);
    }

    @Test
    public void testQueryByPk2() throws Exception {
        ClusterTbl pk = createPojo(1);
        DalHints hints = new DalHints();
        ClusterTbl affected = dao.queryByPk(pk, hints);
        assertNotNull(affected);
    }

    @Test
    public void testUpdate1() throws Exception {
        DalHints hints = new DalHints();
        ClusterTbl daoPojo = dao.queryByPk(createPojo(1), hints);
        changePojo(daoPojo);
        int affected = dao.update(hints, daoPojo);
        assertEquals(1, affected);
        daoPojo = dao.queryByPk(createPojo(1), null);
        verifyPojo(daoPojo);
    }

    @Test
    public void testUpdate2() throws Exception {
        DalHints hints = new DalHints();
        List<ClusterTbl> daoPojos = dao.queryAll(new DalHints());
        changePojos(daoPojos);
        int[] affected = dao.update(hints, daoPojos);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, affected);
        verifyPojos(dao.queryAll(new DalHints()));
    }

    @Test
    public void testBatchUpdate() throws Exception {
        DalHints hints = new DalHints();
        List<ClusterTbl> daoPojos = dao.queryAll(new DalHints());
        changePojos(daoPojos);
        int[] affected = dao.batchUpdate(hints, daoPojos);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, affected);
        verifyPojos(dao.queryAll(new DalHints()));
    }

    @Test
    public void testtestquery() throws Exception {
        //List<ClusterTbl> ret = dao.testquery(new DalHints());
    }

    @Test
    public void testtest() throws Exception {
        //BigInteger param1 = null;// Test value here
        //List<ClusterTbl> ret = dao.test(param1, new DalHints());
    }

}
