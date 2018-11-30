package javabasic.pool;

import javabasic.AbstractTest;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Test;

/**
 * @author wenchao.meng
 *         <p>
 *         Sep 27, 2018
 */
public class GenericObjectPoolTest extends AbstractTest {

    @Test
    public void test() throws Exception {

        GenericObjectPool<String> pool = new GenericObjectPool(new PooledObjectFactory() {

            private int count = 0;

            @Override
            public PooledObject makeObject() throws Exception {

                logger.info("makeObject");
                return new DefaultPooledObject<String>("String:" + count++);
            }

            @Override
            public void destroyObject(PooledObject p) throws Exception {

                logger.info("DestroyObject{}", p);
            }

            @Override
            public boolean validateObject(PooledObject p) {
                logger.info("validateObject {}", p);
                return true;
            }

            @Override
            public void activateObject(PooledObject p) throws Exception {
                logger.info("activateObject{}", p);
            }

            @Override
            public void passivateObject(PooledObject p) throws Exception {
                logger.info("passivateObject {}", p);
            }
        });


        String s1 = pool.borrowObject();
        String s2 = pool.borrowObject();
        System.out.println(s1);
        System.out.println(s2);

        pool.returnObject(s1);

        pool.close();

    }
}
