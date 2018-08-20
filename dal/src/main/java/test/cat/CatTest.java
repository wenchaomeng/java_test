package test.cat;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.junit.Test;
import test.AbstractTest;

import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 04, 2018
 */
public class CatTest extends AbstractTest {

    @Test
    public void testCat() throws InterruptedException {

        Cat.logEvent("type1", "name1");

        TimeUnit.SECONDS.sleep(200);

    }

    @Test
    public void test() {

        logger.info("{}", Cat.getCurrentMessageId());

        Transaction transaction = Cat.newTransaction("test", "name");
        logger.info("{}", Cat.getCurrentMessageId());
        transaction.setStatus(Transaction.SUCCESS);
        logger.info("{}", Cat.getCurrentMessageId());
        transaction.complete();
        logger.info("{}", Cat.getCurrentMessageId());

    }
}
