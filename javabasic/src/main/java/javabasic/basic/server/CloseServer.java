package javabasic.basic.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author wenchao.meng
 *         <p>
 *         May 13, 2018
 */
public class CloseServer extends AbstractServerTest {

    public static void main(String[] argc) throws IOException, InterruptedException {

        new CloseServer().start();

    }


    @Override
    protected void doWith(Socket socket) throws IOException {

        logger.info("close socket {}", socket);

    }
}
