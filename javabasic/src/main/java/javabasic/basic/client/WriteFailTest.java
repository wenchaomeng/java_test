package javabasic.basic.client;

import javabasic.AbstractTest;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

/**
 * @author wenchao.meng
 *         <p>
 *         Jul 20, 2018
 */
public class WriteFailTest extends AbstractTest {

    @Test
    public void test() throws IOException {

        Socket socket = new Socket("127.0.0.1", 8888);

        while (true) {
            logger.info("[begin write]");
            socket.getOutputStream().write(randomString(1024).getBytes());
            logger.info("[ end  write]");
            //java write fail hang!!!
        }


    }
}
