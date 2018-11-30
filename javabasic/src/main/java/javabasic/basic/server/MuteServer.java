package javabasic.basic.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         May 13, 2018
 */
public class MuteServer extends AbstractServerTest {

    public static void main(String[] argc) throws IOException, InterruptedException {

        new MuteServer().start();

    }


    @Override
    protected void doWith(Socket socket) throws IOException {

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        logger.info("doWith sleep begin");
        try {
            TimeUnit.DAYS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("doWith sleep end");


    }
}
