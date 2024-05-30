
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClassHommeMicro extends JPanel {
    private BufferedImage[] images1;
    private int numFrames1;
    private int frameIndex1 = 0;
    private ChatRemote chatRemote;
    int id;
    float prix;
    private JLabel clientInfoLabel;
    JLabel priceLabel;

    public ClassHommeMicro(int id, ChatRemote chatRemote) {
        this.id = id;
        this.chatRemote = chatRemote;
        numFrames1 = 31;
        setBackground(Color.WHITE);
        images1 = new BufferedImage[numFrames1];

        try {
            for (int i = 1; i <= numFrames1; i++) {
                images1[i - 1] = ImageIO.read(new File("C:\\Users\\HP\\IdeaProjects\\projet\\src\\image_homme_micro\\image" + i + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        // Création de la minuterie pour arrêter l'affichage après 3 minutes
//        Timer timer = new Timer( 60 * 1000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Arrêter la minuterie
//                ((Timer) e.getSource()).stop();
//
//                // Fermer la fenêtre
//                Window window = SwingUtilities.getWindowAncestor(ClassHommeMicro.this);
//                if (window != null) {
//                    window.dispose();
//
//                }
//                // Afficher la classe Fin
//                JFrame frame = new JFrame("ClassHammer2");
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                Fin fin = new Fin(id, chatRemote);
//                frame.add(fin);
//                frame.setSize(800, 600);
//                frame.setVisible(true);
//            }
//        });
//        timer.setRepeats(false); // Ne pas répéter la minuterie

        JPanel paddingPanelTop = new JPanel();
        paddingPanelTop.setBackground(Color.WHITE);
        paddingPanelTop.setPreferredSize(new Dimension(10, 100));

        JPanel paddingPanelBottom = new JPanel();
        paddingPanelBottom.setBackground(Color.WHITE);
        paddingPanelBottom.setPreferredSize(new Dimension(10, 100));

        JPanel panelNORTH = new JPanel();
        panelNORTH.setBackground(Color.WHITE);
        panelNORTH.setLayout(new BoxLayout(panelNORTH, BoxLayout.Y_AXIS));

        JPanel panelImage = new JPanel();
        panelImage.setBackground(Color.WHITE);
        Dimension preferredSize = new Dimension(400, 700);
        panelImage.setPreferredSize(preferredSize);
        JLabel labelNORTH = new JLabel(new ImageIcon(images1[0]));
        JTextField text = new JTextField("");
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prixText = text.getText();
                prix = Float.parseFloat(prixText);
                updateClientInfo(id, prix);
                updateLabels();
            }
        });

        panelNORTH.add(paddingPanelTop);
        panelImage.add(labelNORTH);
        panelNORTH.add(panelImage);
        panelNORTH.add(text);
        panelNORTH.add(paddingPanelBottom);

        Timer timer1 = new Timer(100, e -> {
            frameIndex1++;
            if (frameIndex1 >= numFrames1) {
                frameIndex1 = 0;
            }
            labelNORTH.setIcon(new ImageIcon(images1[frameIndex1]));
        });
        timer1.start();
        add(panelNORTH, BorderLayout.WEST);

        JPanel panelCENTER = new JPanel();
        panelCENTER.setLayout(new BoxLayout(panelCENTER, BoxLayout.Y_AXIS));
        panelCENTER.setBackground(Color.WHITE);

        Border innerBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        Border marginBorder = new EmptyBorder(80, 0, 0, 0);
        Border paddingBorder = new EmptyBorder(10, 10, 10, 10);
        Border compoundBorder = BorderFactory.createCompoundBorder(innerBorder, paddingBorder);
        compoundBorder = BorderFactory.createCompoundBorder(marginBorder, compoundBorder);

        priceLabel = new JLabel("Prix: 100$");
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        priceLabel.setForeground(Color.BLUE);
        priceLabel.setBorder(compoundBorder);

        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pricePanel.setBackground(Color.WHITE);
        pricePanel.add(priceLabel);

        panelCENTER.add(Box.createVerticalGlue());
        panelCENTER.add(pricePanel);
        panelCENTER.add(Box.createVerticalGlue());

        add(panelCENTER, BorderLayout.CENTER);

        clientInfoLabel = new JLabel();
        add(clientInfoLabel, BorderLayout.EAST);

        // Création de la minuterie pour arrêter l'affichage après 1 minute
        Timer timer = new Timer(60 * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Arrêter la minuterie
                ((Timer) e.getSource()).stop();

                // Fermer la fenêtre
                Window window = SwingUtilities.getWindowAncestor(ClassHommeMicro.this);
                if (window != null) {
                    window.dispose();

                    // Afficher la classe Fin
                    JFrame frame = new JFrame("Fin");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    Fin fin = new Fin(id, chatRemote);
                    frame.add(fin);
                    frame.pack();
                    frame.setVisible(true);
                }
            }
        });
        timer.setRepeats(false); // Ne pas répéter la minuterie
        timer.start();

        // Démarrer le SwingWorker pour mettre à jour les labels en continu
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (true) {
                    updateLabels();
                    Thread.sleep(1000);
                }
            }

            @Override
            protected void done() {
                // Code à exécuter une fois que le travail en arrière-plan est terminé
            }
        };

        worker.execute();
    }

    public void updateClientInfo(int id, float prix) {
        this.id = id;
        this.prix = prix;
        clientInfoLabel.setText("ID: " + id + " | Prix: " + prix + "$");
    }

    private void updateLabels() {
        if (chatRemote == null) {
            System.err.println("Erreur : chatRemote est nul.");
            return;
        }

        try {
            float highestPrice = chatRemote.highestPricefloat();
            if (prix > highestPrice) {
                chatRemote.setPrix(prix, id);
                ClassStop stopDialog = new ClassStop(id, chatRemote);
                JFrame frame = new JFrame("Animation");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(stopDialog);
                frame.pack();
                frame.setVisible(true);
                priceLabel.setText("Prix: " + chatRemote.highestPricefloat() + "$");
                clientInfoLabel.setText("ID: " + id + " | Prix: " + prix + "$");
                setBackground(Color.BLUE);
            } else {
                priceLabel.setText("Prix: " + highestPrice + "$");
                clientInfoLabel.setText("ID: " + id + " | Prix: " + prix + "$");
                setBackground(Color.WHITE);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
