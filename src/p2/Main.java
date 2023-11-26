package p2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try{
            int i =1;
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("Thread " + i);
                Runnable runnable = new EchoThread(socket);
                Thread thread = new Thread(runnable);
                thread.start();
                i++;
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

}
