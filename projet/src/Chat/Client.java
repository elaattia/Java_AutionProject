package Chat;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ControllerClient controller;


    public Client(Socket socket, ControllerClient controller) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.controller = controller; // Stocke la référence au ControllerClient
            System.out.println("Client connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating client ...");
            closeEverything();
        }
    }

    public void receiveMessagesFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String messageFromServer = bufferedReader.readLine();
                        if (messageFromServer != null) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    controller.addMessage(messageFromServer, Color.BLUE, false); // Utilise le controller
                                }
                            });
                            System.out.println("Client received message: " + messageFromServer);
                        } else {
                            System.out.println("Server closed the connection.");
                            closeEverything();
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error receiving message from the server ...");
                    closeEverything();
                }
            }
        }).start();
    }

    public void sendMessageToServer(String messageToServer) {
        try {
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("Client sent message: " + messageToServer);
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to the server ...");
            closeEverything();
        }
    }


    public void closeEverything() {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}