package Chat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller {
    private Server server;
    private JFrame frame;
    private JPanel messagePanel;
    private JScrollPane scrollPane;
    private JTextField messageField;

    public Controller() {
        frame = new JFrame("Serveur");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(messagePanel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JButton sendButton = new JButton("Envoyer");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);

        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            server = new Server(serverSocket);
            startServerListener(); // Commencer à écouter les messages des clients
        } catch (IOException i) {
            i.printStackTrace();
            System.out.println("Erreur lors de la création du serveur...");
        }
    }

    private void startServerListener() {
        new Thread(() -> {
            server.startListeningForClients(this);
        }).start();
    }
    // Ajoutez cette méthode à la classe Controller
    public void receiveMessageFromServer(String message) {
        addMessage(message, Color.BLACK, false); // Utilisez une couleur différente pour les messages du serveur
    }

    public void receiveMessageFromClient(Socket clientSocket, String message) {
        addMessage(message, Color.BLACK, false);

    }
    private void sendMessageToLastClient(String message) {
        if (server != null && server.getLastClientSocket() != null) {
            server.sendMessageToSpecificClient(server.getLastClientSocket(), message);
        }
    }

    private void sendMessage() {
        String messageToSend = messageField.getText();
        if (!messageToSend.isEmpty()) {
            addMessage(messageToSend, Color.BLUE, true);
            // Envoyer le message uniquement au client qui l'a envoyé
            //server.sendMessageToOneClient(messageToSend);
            server.sendMessageToLastClient(messageToSend);
            System.out.println("server send a message ");
            messageField.setText("");
        }
    }



    public void addMessage(String message, Color color, boolean sentByMe) {
        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setOpaque(true);
        messageLabel.setBackground(sentByMe ? Color.BLUE : Color.GRAY);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        if (sentByMe) {
            messageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        } else {
            messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        }

        messagePanel.add(Box.createVerticalStrut(10));
        messagePanel.add(messageLabel);

        messagePanel.revalidate();
        messagePanel.repaint();
    }

}
