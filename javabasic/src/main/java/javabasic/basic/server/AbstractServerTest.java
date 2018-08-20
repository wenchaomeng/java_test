package javabasic.basic.server;

import javabasic.basic.AbstractSocketTest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao.meng
 *         <p>
 *         May 13, 2018
 */
public abstract class AbstractServerTest extends AbstractSocketTest {

    public static int port = Integer.parseInt(System.getProperty("port", "8888"));
    public static int backlog = Integer.parseInt(System.getProperty("backlog", "1"));
    public static int acceptSleepMilli = Integer.parseInt(System.getProperty("acceptSleepMilli", "1000"));

    @Override
    public void start() throws IOException, InterruptedException {

        ServerSocket ss = new ServerSocket(port, backlog);

        while (true) {

            Socket accept = ss.accept();

            System.out.println("accepted " + accept);
            accept.setTcpNoDelay(true);

            System.out.println("begin sleep " + acceptSleepMilli);
            TimeUnit.MILLISECONDS.sleep(acceptSleepMilli);
            System.out.println(" end  sleep " + acceptSleepMilli);
            executors.execute(new Task(accept));
        }

    }


    public class Task implements Runnable {

        protected Socket socket;

        public Task(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try {
                doWith(socket);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("close socket");
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected abstract void doWith(Socket socket) throws IOException;
}
