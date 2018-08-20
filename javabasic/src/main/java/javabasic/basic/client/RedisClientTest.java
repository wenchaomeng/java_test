package javabasic.basic.client;

import javabasic.AbstractTest;
import org.junit.Before;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author wenchao.meng
 *         <p>
 *         Aug 06, 2018
 */
public class RedisClientTest extends AbstractTest {

    private Socket socket;

    @Before
    public void beforeRedisClientTest() throws IOException {
        socket = new Socket("127.0.0.1", 6379);

    }

    @Test
    public void test() throws IOException {

        socket.setSoTimeout(2000);
        socket.getOutputStream().write("set a b\r\n".getBytes());

        String s = new DataInputStream(socket.getInputStream()).readLine();

        logger.info("{}", s);

    }
}
