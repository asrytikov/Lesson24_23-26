package p1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
       try(ServerSocket serverSocket = new ServerSocket(8080)){
           try(Socket income = serverSocket.accept()) {
               InputStream inputStream = income.getInputStream();
               OutputStream outputStream = income.getOutputStream();
               try(Scanner scanner = new Scanner(inputStream)){
                   PrintWriter printWriter = new PrintWriter(outputStream, true);
                   printWriter.println("Hello!");
                   boolean done = false;
                   while (!done && scanner.hasNextLine()){
                       String line = scanner.nextLine();
                       printWriter.println("ECHO: " + line);
                       if (line.trim().equals("BYE")) done = true;
                   }
               }
           }
       }
    }
}