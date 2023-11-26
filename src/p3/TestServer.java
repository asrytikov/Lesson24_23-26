package p3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer implements Runnable{
    @Override
    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true){
                Socket socket = serverSocket.accept();
                Runnable runnable = new TestServerHandler(socket);
                Thread thread = new Thread(runnable);
                thread.start();
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
