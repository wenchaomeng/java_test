package test.dal.mybatis.spring;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.AbstractTest;
import test.dal.mybatis.TestPojo;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 22, 2018
 */
@RestController
public class ControllerApi extends AbstractTest{

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @RequestMapping(path = "/get")
    public TestPojo get(){

        SqlSession sqlSession = sqlSessionFactory.openSession();
        TestPojo testPojo = sqlSession.selectOne("select", 1027);

        logger.info("{}", testPojo);

        return testPojo;

    }

}
