package p3;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TestServerHandler implements Runnable{

    private Socket socket;
    private int counter;

    public TestServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            try {
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter out = new PrintWriter(outputStream, true);
                while (counter<100){
                    counter++;
                    if (counter <=10)out.println(counter);
                    if (counter > 95)out.println(counter);
                    Thread.sleep(100);
                }
            }finally {
                socket.close();
                System.out.println("Close server \n");
            }
    }catch (Exception exception){
        exception.printStackTrace();
        }
    }
}
