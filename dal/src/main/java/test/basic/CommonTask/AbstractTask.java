package test.basic.CommonTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wenchao.meng
 *         <p>
 *         Jul 19, 2018
 */
public abstract class AbstractTask implements Runnable {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run() {

        try {
            doRun();
        } catch (Exception e) {
            logger.error("[run]", e);
        }
    }

    protected abstract void doRun();

    void executeCatException(Task runnable) {

        try {
            runnable.run();
        } catch (Exception e) {
            logger.error("[run]", e);
        }
    }
}
