
//import RMIServeur.ChatImplementation;
//import RMIServeur.ChatRemote;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.Calendar;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Projet111 extends JFrame implements ActionListener{
    JPanel panelgrand1, panelmain, panelgauche1, pDate, panel1, panelInformations ;
    private JTextField textFieldNom, textFieldPrenom, textFieldTelephone;
    private JComboBox<String> jourComboBox, moisComboBox, anneeComboBox;
    JButton btnEnvoyer, btnAnnuler, btnAfficherPanel;
    JCheckBox checkBox;
    ChatRemote cr;
    int id;

    public Projet111(ChatRemote cr) {

        this.setSize(1000, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setResizable(true);


        panelmain = new JPanel();
        panelmain.setLayout(new BoxLayout(panelmain, BoxLayout.Y_AXIS));

        panelgrand1=new JPanel(new BorderLayout());

        panelInformations = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelInformations = new JLabel("Informations personnelles");
        btnAfficherPanel = new JButton("▼"); // Flèche vers le bas
        btnAfficherPanel.addActionListener(this); // Ajouter le gestionnaire d'événements

        panelInformations.add(btnAfficherPanel);
        panelInformations.add(labelInformations);
        panelgrand1.add(panelInformations, BorderLayout.NORTH);


        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 2));

        panelgauche1 = new JPanel();
        panelgauche1.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel labelNom = new JLabel("Nom:");
        textFieldNom = new JTextField();
        textFieldNom.setColumns(20);

        JLabel labelPrenom = new JLabel("Prenom:");
        textFieldPrenom = new JTextField();
        textFieldPrenom.setColumns(20);

        JLabel labelTelephone = new JLabel("Tel:");
        textFieldTelephone = new JTextField();
        textFieldTelephone.setColumns(20);



        JLabel labelDateNaissance = new JLabel("Date de Naissance:");

        jourComboBox = new JComboBox<>(getJours());
        moisComboBox = new JComboBox<>(getMois());
        anneeComboBox = new JComboBox<>(getAnnees());
        pDate = new JPanel();
        pDate.setLayout(new FlowLayout());
        pDate.add(jourComboBox);
        pDate.add(moisComboBox);
        pDate.add(anneeComboBox);



        gbc.gridx = 0;
        gbc.gridy = 0;
        panelgauche1.add(labelNom, gbc);
        gbc.gridx = 1;
        panelgauche1.add(textFieldNom, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelgauche1.add(labelPrenom, gbc);
        gbc.gridx = 1;
        panelgauche1.add(textFieldPrenom, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelgauche1.add(labelTelephone, gbc);
        gbc.gridx = 1;
        panelgauche1.add(textFieldTelephone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelgauche1.add(labelDateNaissance, gbc);
        gbc.gridx = 1;
        panelgauche1.add(pDate, gbc);


        panel1.add(panelgauche1);

        ImageIcon originalIcon = new ImageIcon("C:\\Users\\admin\\Downloads\\istockphoto-1209654046-612x612.jpg");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel labelImage = new JLabel(resizedIcon);

        panel1.add(labelImage);


        Border panelBorder = BorderFactory.createLineBorder(Color.BLUE, 2);
        panel1.setBorder(panelBorder);
        panelgrand1.add(panel1,BorderLayout.CENTER);

        panelmain.add(panelgrand1);




        JPanel panelSouth = new JPanel(new GridBagLayout());
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.anchor = GridBagConstraints.WEST;
        gbc4.insets = new Insets(5, 5, 5, 5);


        checkBox = new JCheckBox();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelSouth.add(checkBox, gbc);


        this.id=-1;


        btnEnvoyer = new JButton("Participer");
        btnEnvoyer.addActionListener(this);
        btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelSouth.add(btnEnvoyer, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelSouth.add(btnAnnuler, gbc);
        panelmain.add(panelSouth);

        this.add(panelmain);

        this.setResizable(false);

        this.setVisible(true);

        // Initialisation de l'interface...
        // Appel à la méthode RMI pour récupérer le temps de début du serveur
        try {
            //ChatRemote chat = (ChatRemote) Naming.lookup("rmi://localhost:9001/chat");
            this.cr=cr;
            Date serverStartTime = cr.getServerStartTime();
            Date clientStartTime = new Date(); // Temps de connexion du client

            long elapsedTime = clientStartTime.getTime() - serverStartTime.getTime();
            if (elapsedTime >= 0 && elapsedTime <= 120000) { // Si le client s'est connecté dans les 2 premières minutes
                System.out.println("Client connected at: " + clientStartTime);
            } else {
                System.out.println("Client connection time out of range.");
            }


            // Ajoutez un ActionListener au bouton "Participer"
            btnEnvoyer.addActionListener(e -> {
                // Fermer la fenêtre de Projet111
                dispose();

                // Créer une nouvelle instance d'ImageAnimation et l'afficher
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("Image Animation");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // Utiliser le temps du serveur récupéré via RMI
                    ImageAnimation imageAnimation = new ImageAnimation(serverStartTime,this.id,cr);

                    frame.add(imageAnimation);
                    frame.setSize(800, 600);
                    frame.setVisible(true);
                });
            });




        } catch (Exception e) {
            e.printStackTrace();
        }







    }

    private String[] getJours() {
        String[] jours = new String[31];
        for (int i = 1; i <= 31; i++) {
            jours[i - 1] = String.valueOf(i);
        }
        return jours;
    }

    private String[] getMois() {
        return new String[]{"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
    }

    private String[] getAnnees() {
        String[] annees = new String[100];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 100; i++) {
            annees[i] = String.valueOf(currentYear - i);
        }
        return annees;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAfficherPanel) {
            panelgrand1.setVisible(!panelgrand1.isVisible());
            revalidate();
            repaint();
        } else if (e.getSource() == btnAnnuler) {
            System.exit(0);
        } else if (e.getSource() == btnEnvoyer) {
            String nom = textFieldNom.getText();
            String prenom = textFieldPrenom.getText();
            String tel = textFieldTelephone.getText();
            String jour = (String) jourComboBox.getSelectedItem();
            String mois = (String) moisComboBox.getSelectedItem();
            String annee = (String) anneeComboBox.getSelectedItem();
            //String dateNaissance = jour + "/" + mois + "/" + annee;

            // Construire la date au format "jour/mois/année"
            String dateNaissance = String.format("%s/%s/%s", jour, mois, annee);

            // Limiter la longueur de la chaîne à 10 caractères maximum
            dateNaissance = dateNaissance.substring(0, Math.min(dateNaissance.length(), 10));


            TableDAO dao= new TableDAO(Confg_new.Url, Confg_new.Username, Confg_new.Password);
            int a=dao.insertParticipant(nom,prenom,tel,dateNaissance);
            this.id=dao.getId(tel);
            try {
                cr.addToTable(nom);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            //int a=dao.insertEtudiant("Nour", "Ab1",126,18);



        }

    }


    public int getId() {

        return this.id;
    }




}
