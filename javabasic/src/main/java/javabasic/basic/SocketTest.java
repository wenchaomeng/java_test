package javabasic.basic;

import javabasic.AbstractTest;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wenchao.meng
 *         <p>
 *         May 10, 2018
 */
public class SocketTest extends AbstractTest {

    @Test
    public void test() {
        List<String> lists = new LinkedList<>();

    }

    @Test
    public void testWrite() throws IOException {

        Socket socket = new Socket("10.2.38.45", 9999);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            logger.info("[begin]");
            socket.getOutputStream().write("0123456789".getBytes());
            logger.info("[end]");
            sleep(1);
        }

    }

    @Test
    public void testSocket() {

        for (int i = 0; i < 1; i++) {

            try {
                logger.info("{}", i);
                Socket s = new Socket("localhost", 8888);
                s.getOutputStream().write("12345".getBytes());

                System.out.println("read:");
                s.getInputStream().read(new byte[4], 0, 4);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        sleep(1000000);
    }
}
