package javabasic.basic.client;

import java.io.IOException;
import java.net.Socket;

/**
 * @author wenchao.meng
 *         <p>
 *         May 13, 2018
 */
public class WriteAndClose extends AbstractClientTest {


    public static void main(String[] argc) throws IOException, InterruptedException {

        new WriteAndClose().start();

    }

    @Override
    public void start() throws IOException, InterruptedException {

        Socket socket = new Socket(ip, port);
        socket.getOutputStream().write("quit\r\n".getBytes());
        sleep(100);
        System.out.println("close");
        socket.close();
//        socket.shutdownOutput();
        logger.info("closed...");

        sleep(100000000);
    }
}
