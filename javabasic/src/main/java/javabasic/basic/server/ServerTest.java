package javabasic.basic.server;

import java.io.IOException;
import java.net.Socket;

/**
 * @author wenchao.meng
 *         <p>
 *         May 11, 2018
 */
public class ServerTest extends AbstractServerTest {


    public static void main(String[] argc) throws IOException, InterruptedException {

        new ServerTest().start();

    }

    @Override
    protected void doWith(Socket socket) {
        System.out.println("do nothing:" + socket);

    }
}
