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
public class BrokenPipeTest extends AbstractTest {

    @Test
    public void test() throws IOException {

        Socket socket = new Socket("10.2.58.242", 8081);

        while (true) {

            sleep(1000);
            logger.info("[begin write]");
            socket.getOutputStream().write("123".getBytes());
        }


    }
}
