package Chat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/*public class ControllerClient {
    private Client client;
    private JFrame frame;
    private JPanel messagePanel;
    private JScrollPane scrollPane;
    private JTextField messageField;


    public ControllerClient() {
        try {
            Socket socket = new Socket("localhost", 1234);
            client = new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error connecting to server ...");
            return;
        }

        frame = new JFrame("client");
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

        JButton sendButton = new JButton("Send");
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

        client.receiveMessagesFromServer(this);
    }

    public JPanel getMessagePanel() {
        return messagePanel;
    }

    private void sendMessage() {
        String messageToSend = messageField.getText();
        if (!messageToSend.isEmpty()) {
            addMessage(messageToSend, Color.BLUE, true);
            client.sendMessageToServer(messageToSend);
            messageField.setText("");
        }
    }

    public void addMessage(String message, Color color, boolean sentByMe) {
        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setOpaque(true);
        //messageLabel.setBackground(color);
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
}*/

/*public class ControllerClient {
    private Client client;
    private JFrame frame;
    private JPanel messagePanel;
    private JScrollPane scrollPane;
    private JTextField messageField;

    public ControllerClient() {
        frame = new JFrame("client");
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

        JButton sendButton = new JButton("Send");
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

        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 1234);
            client = new Client(socket,this);
            startClientListener(); // Commencer à écouter les messages du serveur
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error connecting to server ...");
        }
    }

    private void startClientListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                client.receiveMessagesFromServer(ControllerClient,this);
            }
        }).start();
    }

    private void sendMessage() {
        String messageToSend = messageField.getText();
        if (!messageToSend.isEmpty()) {
            addMessage(messageToSend, Color.BLUE, true);
            client.sendMessageToServer(messageToSend);
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
}*/

public class ControllerClient {
    private Client client;
    private JFrame frame;
    private JPanel messagePanel;
    private JScrollPane scrollPane;
    private JTextField messageField;

    public ControllerClient() {
        frame = new JFrame("Client");
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

        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 1234);
            client = new Client(socket, this);
            startClientListener(); // Commencer à écouter les messages du serveur
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la connexion au serveur...");
        }
    }

    private void startClientListener() {
        new Thread(() -> {
            client.receiveMessagesFromServer();
        }).start();
    }

    private void sendMessage() {
        String messageToSend = messageField.getText();
        if (!messageToSend.isEmpty()) {
            addMessage(messageToSend, Color.BLUE, true);
            client.sendMessageToServer(messageToSend);
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

