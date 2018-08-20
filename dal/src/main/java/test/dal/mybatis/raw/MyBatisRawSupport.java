package test.dal.mybatis.raw;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import test.AbstractTest;
import test.dal.mybatis.TestPojo;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 22, 2018
 */
public class MyBatisRawSupport extends AbstractTest {

    @Test
    public void test() throws IOException {

        String resource = "test/dal/mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        TestPojo testPojo = sqlSession.selectOne("select", 1027);

        logger.info("{}", testPojo);

    }


}
