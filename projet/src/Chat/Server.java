package Chat;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

class Server {
    private ServerSocket serverSocket;

    private Map<String, Socket> clientConnections = new HashMap<>();

    private Socket lastClientSocket; // Ajoutez ceci à la classe Server

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        System.out.println("Server started...");
    }

    public void startListeningForClients(Controller controller) {
        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected.");
                    setLastClientSocket(clientSocket);
                    handleClientCommunication(clientSocket, controller);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error accepting client connection ...");
                }
            }
        }).start();
    }

    /*private void handleClientCommunication(Socket clientSocket, Controller controller) {
        new Thread(() -> {
            try {
                // Récupérer l'adresse IP du client
                String clientAddress = clientSocket.getInetAddress().getHostAddress();
                // Stocker la connexion client
                clientConnections.put(clientAddress, clientSocket);

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String messageFromClient;
                while ((messageFromClient = reader.readLine()) != null) {
                    System.out.println("Server received message from client: " + messageFromClient);
                    // Dans la méthode handleClientCommunication de la classe Server
                    //controller.receiveMessageFromServer("Server received your message: " + messageFromClient);
                    controller.receiveMessageFromClient(clientSocket, "Server received your message: " + messageFromClient);
                    // Envoyer la réponse uniquement au client qui a envoyé le message
                    sendMessageToClient(clientConnections.get(clientAddress), "Server received your message: " + messageFromClient);
                    controller.addMessage(messageFromClient, Color.BLUE, false);
                }

                closeEverything(clientSocket, reader, writer);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error handling client communication ...");
                closeEverything(clientSocket, null, null);
            }
        }).start();
    }*/
    /*private void handleClientCommunication(Socket clientSocket, Controller controller) {
        new Thread(() -> {
            try {
                // Récupérer l'adresse IP du client
                String clientAddress = clientSocket.getInetAddress().getHostAddress();

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String messageFromClient;
                while ((messageFromClient = reader.readLine()) != null) {
                    System.out.println("Server received message from client: " + messageFromClient);

                    // Envoyer la réponse uniquement au client qui a envoyé le message
                    sendMessageToClient(clientSocket, "Server received your message: " + messageFromClient);
                    controller.addMessage(messageFromClient, Color.BLUE, false);
                }

                closeEverything(clientSocket, reader, null);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error handling client communication ...");
                closeEverything(clientSocket, null, null);
            }
        }).start();
    }*/
    public void sendMessageToSpecificClient(Socket clientSocket, String message) {
        sendMessageToClient(clientSocket, message);
    }

    private void setLastClientSocket(Socket clientSocket) {
        this.lastClientSocket = clientSocket;
    }
    private void handleClientCommunication(Socket clientSocket, Controller controller) {
        new Thread(() -> {
            try {
                // Mettre à jour lastClientSocket
                setLastClientSocket(clientSocket);

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String messageFromClient;
                while ((messageFromClient = reader.readLine()) != null) {
                    System.out.println("Server received message from client: " + messageFromClient);

                    sendMessageToLastClient("Server received your message: " + messageFromClient);
                    controller.addMessage(messageFromClient, Color.BLUE, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error handling client communication ...");
            }
        }).start();
    }
    /*private void sendMessageToLastClient(String messageToClient) {
        if (lastClientSocket != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(lastClientSocket.getOutputStream()));
                writer.write(messageToClient);
                writer.newLine();
                writer.flush();
                System.out.println("Server sent message to last client: " + messageToClient);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error sending message to the last client ...");
            }
        }
    }*/
    public void sendMessageToLastClient(String message) {
        if (lastClientSocket != null) {
            sendMessageToClient(lastClientSocket, message);
        }
    }
    public Socket getLastClientSocket() {
        return lastClientSocket;
    }

    // Modifier la méthode sendMessageToClient pour envoyer uniquement à un client spécifique
    private void sendMessageToClient(Socket clientSocket, String messageToClient) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            writer.write(messageToClient);
            writer.newLine();
            writer.flush();
            System.out.println("Server sent message: " + messageToClient);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to the client ...");
            closeEverything(clientSocket, null, null);
        }
    }

    // Nouvelle méthode pour envoyer un message uniquement à un client spécifique
    public void sendMessageToOneClient(String messageToClient) {
        // Assumez ici que vous avez l'adresse IP du client qui a envoyé le message
        String clientAddress = ""; // Remplacez cette chaîne par l'adresse IP du client
        Socket clientSocket = clientConnections.get(clientAddress);
        if (clientSocket != null) {
            sendMessageToClient(clientSocket, messageToClient);
        }
    }

    private void closeEverything(Socket clientSocket, BufferedReader reader, BufferedWriter writer) {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
            System.out.println("Closed client connection.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Dans la classe Server

}