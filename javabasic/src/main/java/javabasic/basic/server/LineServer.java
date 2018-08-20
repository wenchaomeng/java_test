package javabasic.basic.server;

import java.io.*;
import java.net.Socket;

/**
 * @author wenchao.meng
 *         <p>
 *         May 13, 2018
 */
public class LineServer extends AbstractServerTest {

    public static void main(String[] argc) throws IOException, InterruptedException {

        new LineServer().start();

    }


    @Override
    protected void doWith(Socket socket) throws IOException {

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        while (true) {

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String s = br.readLine();
            if (s == null) {
                System.out.println("read null");
                return;
            }
            outputStream.write(s.getBytes());
            if (s.equalsIgnoreCase("quit")) {
                logger.info("quit");
                socket.close();
                break;
            }
        }

    }
}
