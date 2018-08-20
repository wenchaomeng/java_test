package javabasic.basic.client;

import java.io.IOException;
import java.net.Socket;

/**
 * @author wenchao.meng
 *         <p>
 *         May 13, 2018
 */
public class ConnectAndSendMessage extends AbstractClientTest {


    public static void main(String[] argc) throws IOException, InterruptedException {

        new ConnectAndSendMessage().start();

    }

    @Override
    public void start() throws IOException, InterruptedException {


        for (int i = 0; i < 100; i++) {

            executors.execute(new Runnable() {

                @Override
                public void run() {

                    Socket socket = null;
                    try {
                        socket = new Socket(ip, port);
                        logger.info("{}", socket);

                        socket.getOutputStream().write("quit\r\n".getBytes());
                        logger.info("{} message written", socket);

                        sleep(100000000);
                    } catch (IOException e) {
                        logger.error(String.format("%s", socket), e);
                    }

                }
            });
        }

    }
}
