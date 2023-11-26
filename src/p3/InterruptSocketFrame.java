package p3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class InterruptSocketFrame extends JFrame {
    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLUMNS = 60;

    private Scanner in;
    private JButton interruptButton;
    private JButton blockButton;
    private JButton cancelButton;
    private JTextArea messages;
    private Thread connectThread;
    private TestServer server;

    public InterruptSocketFrame() {

        JPanel jPanel = new JPanel();
        add(jPanel, BorderLayout.NORTH);

        messages = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(messages));

        interruptButton = new JButton("InterruptThread");
        blockButton = new JButton("BlockThread");
        jPanel.add(interruptButton);
        jPanel.add(blockButton);

        interruptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interruptButton.setEnabled(false);
                blockButton.setEnabled(false);
                cancelButton.setEnabled(true);
                connectThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            connectInterrupt();
                        }catch (IOException exception){
                            messages.append("\nInterruptSocket:" + exception);
                        }
                    }
                });

                connectThread.start();
            }
        });

        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interruptButton.setEnabled(false);
                blockButton.setEnabled(false);
                cancelButton.setEnabled(true);
                connectThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            connectBloking();
                        }catch (IOException exception){
                            messages.append("\nBlockingSocket:" + exception);
                        }
                    }
                });
                connectThread.start();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);
        jPanel.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectThread.interrupt();
                cancelButton.setEnabled(false);
            }
        });

        server = new TestServer();
        new Thread(server).start();
        pack();
    }

    public void connectInterrupt() throws IOException{
        messages.append("Interrupt: ");
        try(SocketChannel socketChannel = SocketChannel.open(
                new InetSocketAddress("localhost", 8080))){
            in = new Scanner(socketChannel);
            while (!Thread.currentThread().isInterrupted()){
                messages.append("READING ");
                if (in.hasNextLine()){
                    String line = in.nextLine();
                    messages.append(line);
                    messages.append("\n");
                }
            }
        }finally {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    messages.append("Channel close\n");
                    interruptButton.setEnabled(true);
                    blockButton.setEnabled(true);
                }
            });
        }
    }

    public void connectBloking() throws IOException{
        messages.append("Blocking: ");
        try(Socket socket = new Socket("localhost", 8080)){
            in = new Scanner(socket.getInputStream());
            while (!Thread.currentThread().isInterrupted()){
                messages.append("READING ");
                if (in.hasNextLine()){
                    String line = in.nextLine();
                    messages.append(line);
                    messages.append("\n");
                }
            }
        }finally {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    messages.append("Channel close\n");
                    interruptButton.setEnabled(true);
                    blockButton.setEnabled(true);
                }
            });
        }
    }
}
