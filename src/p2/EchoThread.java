package p2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoThread implements Runnable{

    private Socket socket;

    public EchoThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            try {
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                Scanner in = new Scanner(inputStream);
                PrintWriter out = new PrintWriter(outputStream, true);
                out.println("Hello!");
                boolean done = false;
                while (!done && in.hasNextLine()){
                    String line = in.nextLine();
                    out.println("ECHO: " + line);
                    if (line.trim().equals("BYE")) done = true;
                }
            }finally {
                socket.close();
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
