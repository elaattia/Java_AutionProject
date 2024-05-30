import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageAnimation extends JPanel {
    private BufferedImage[] images1;
    private BufferedImage[] images2;
    private int numFrames1;
    private int numFrames2;
    private int frameIndex1 = 0;
    private int frameIndex2 = 0;
    private JLabel timerLabel;
    private static long remainingTime; // Temps restant en millisecondes
    private Date serverStartTime;
    private boolean hammerDisplayed = false;
    Timer updateTimer;
    int id;
    ChatRemote cr = null;
    public ImageAnimation() {

        numFrames1 = 91; // Nombre d'images à charger
        numFrames2 = 71; // Nombre d'images à charger pour la deuxième animation
        setBackground(Color.WHITE);
        images1 = new BufferedImage[numFrames1];
        images2 = new BufferedImage[numFrames2];

        try {
            for (int i = 1; i <= numFrames1; i++) {
                images1[i - 1] = ImageIO.read(new File("C:\\Users\\HP\\IdeaProjects\\projet\\src\\hometime\\image" + i + "_cleanup.png"));
            }
            for (int j = 1; j <= numFrames2; j++) {
                images2[j - 1] = ImageIO.read(new File("C:\\Users\\HP\\IdeaProjects\\projet\\src\\imageheure\\image" + j + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //remainingTime = 60000; // Temps initial: 1 minute

        setLayout(new BorderLayout());
        //JPanel panelCenter = new JPanel();
        //panelCenter.setBackground(Color.WHITE);
        //panelCenter.setLayout(new GridBagLayout()); // Utilisation de GridBagLayout pour centrer le label
        timerLabel = new JLabel("");
        timerLabel.setFont(timerLabel.getFont().deriveFont(Font.BOLD, 50f)); // Augmenter la taille de la police
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        //panelCenter.add(timerLabel, gbc); // Ajouter le label centré

        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(Color.WHITE);
        panelLeft.setLayout(new BorderLayout());
        JPanel panelRight = new JPanel();
        panelRight.setBackground(Color.WHITE);
        panelRight.setLayout(new BorderLayout());

        JLabel labelLeft = new JLabel(new ImageIcon(images1[0]));
        JLabel labelRight = new JLabel(new ImageIcon(images2[0]));

        panelLeft.add(labelLeft, BorderLayout.CENTER);

        // Ajouter labelRight (images2) au centre de panelRight
        panelRight.add(labelRight, BorderLayout.CENTER);
        // Ajouter le timerLabel en haut de panelRight et centrer horizontalement
        panelRight.add(timerLabel, BorderLayout.NORTH);

        add(panelLeft, BorderLayout.WEST);
        add(panelRight, BorderLayout.CENTER);
        //add(panelCenter, BorderLayout.NORTH); // Ajouter panelCenter au centre

        Timer timer1 = new Timer(100, e -> {
            frameIndex1++;
            if (frameIndex1 >= numFrames1) {
                frameIndex1 = 0;
            }
            labelLeft.setIcon(new ImageIcon(images1[frameIndex1]));
        });
        timer1.start();

        Timer timer2 = new Timer(150, e -> {
            frameIndex2++;
            if (frameIndex2 >= numFrames2) {
                frameIndex2 = 0;
            }
            labelRight.setIcon(new ImageIcon(images2[frameIndex2]));
        });
        timer2.start();
        // Calculer remainingTime en utilisant un temps de serveur valide
        Date serverStartTime = new Date(); // Utilisez un temps de serveur valide, par exemple, l'instant présent
        long clientStartTime = System.currentTimeMillis();
        long serverTime = serverStartTime.getTime(); // Obtenez le temps de démarrage du serveur en millisecondes
        remainingTime = 120000 - (clientStartTime - serverTime); // Calculez la différence en millisecondes


        // Timer pour mettre à jour le label du timer
        Timer updateTimer = new Timer(1000, e -> {
            remainingTime -= 1000; // Décrémenter le temps restant de 1 seconde
            if (remainingTime <= 0) {
                // Arrêtez les timers une fois que le temps est écoulé
                timer1.stop();
                timer2.stop();
                timerLabel.setText(" 00:00"); // Afficher 00:00 lorsque le temps est écoulé
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                timerLabel.setText( sdf.format(new Date(remainingTime)));
            }
        });
        updateTimer.start();



    }


    public ImageAnimation(Date serverStartTime,int id,ChatRemote cr ) {
        this.cr=cr;
        numFrames1 = 91; // Nombre d'images à charger
        numFrames2 = 71; // Nombre d'images à charger pour la deuxième animation
        setBackground(Color.WHITE);
        images1 = new BufferedImage[numFrames1];
        images2 = new BufferedImage[numFrames2];
        this.id=id;

        try {
            for (int i = 1; i <= numFrames1; i++) {
                images1[i - 1] = ImageIO.read(new File("C:\\Users\\HP\\IdeaProjects\\projet\\src\\hometime\\image" + i + "_cleanup.png"));
            }
            for (int j = 1; j <= numFrames2; j++) {
                images2[j - 1] = ImageIO.read(new File("C:\\Users\\HP\\IdeaProjects\\projet\\src\\imageheure\\image" + j + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //remainingTime = 60000; // Temps initial: 1 minute

        setLayout(new BorderLayout());
        //JPanel panelCenter = new JPanel();
        //panelCenter.setBackground(Color.WHITE);
        //panelCenter.setLayout(new GridBagLayout()); // Utilisation de GridBagLayout pour centrer le label
        timerLabel = new JLabel("");
        timerLabel.setFont(timerLabel.getFont().deriveFont(Font.BOLD, 50f)); // Augmenter la taille de la police
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        //panelCenter.add(timerLabel, gbc); // Ajouter le label centré

        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(Color.WHITE);
        panelLeft.setLayout(new BorderLayout());
        JPanel panelRight = new JPanel();
        panelRight.setBackground(Color.WHITE);
        panelRight.setLayout(new BorderLayout());

        JLabel labelLeft = new JLabel(new ImageIcon(images1[0]));
        JLabel labelRight = new JLabel(new ImageIcon(images2[0]));

        panelLeft.add(labelLeft, BorderLayout.CENTER);

        // Ajouter labelRight (images2) au centre de panelRight
        panelRight.add(labelRight, BorderLayout.CENTER);
        // Ajouter le timerLabel en haut de panelRight et centrer horizontalement
        panelRight.add(timerLabel, BorderLayout.NORTH);

        add(panelLeft, BorderLayout.WEST);
        add(panelRight, BorderLayout.CENTER);
        //add(panelCenter, BorderLayout.NORTH); // Ajouter panelCenter au centre

        Timer timer1 = new Timer(100, e -> {
            frameIndex1++;
            if (frameIndex1 >= numFrames1) {
                frameIndex1 = 0;
            }
            labelLeft.setIcon(new ImageIcon(images1[frameIndex1]));
        });
        timer1.start();

        Timer timer2 = new Timer(150, e -> {
            frameIndex2++;
            if (frameIndex2 >= numFrames2) {
                frameIndex2 = 0;
            }
            labelRight.setIcon(new ImageIcon(images2[frameIndex2]));
        });
        timer2.start();

        // Initialiser les autres variables et configurations
        this.serverStartTime = new Date();
        // Calculer remainingTime en utilisant serverStartTime
        long clientStartTime = System.currentTimeMillis();
        long serverTime = serverStartTime.getTime(); // Obtenez le temps de démarrage du serveur en millisecondes
        remainingTime = 120000 - (clientStartTime - serverTime); // Calculez la différence en millisecondes


        // Timer pour mettre à jour le label du timer
        updateTimer = new Timer(1000, e -> {
            remainingTime -= 1000; // Décrémenter le temps restant de 1 seconde
            if (remainingTime <= 0 &!hammerDisplayed) {
                // Arrêtez les timers une fois que le temps est écoulé
                timer1.stop();
                timer2.stop();
                timerLabel.setText(" 00:00"); // Afficher 00:00 lorsque le temps est écoulé
                updateTimer.stop();

                // Fermer la fenêtre actuelle
                Window window = SwingUtilities.getWindowAncestor(this);
                window.dispose();

                // Créer une nouvelle instance de ClassHammer et l'afficher
                SwingUtilities.invokeLater(() -> {

                    JFrame frame = new JFrame("ClassHammer");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    ClassHammer classHammer = new ClassHammer(this.id,cr); // Créez une instance de ClassHammer
                    frame.add(classHammer);
                    frame.setSize(800, 600);
                    frame.setVisible(true);


                    hammerDisplayed = true;
                });
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                timerLabel.setText( sdf.format(new Date(remainingTime)));
            }
        });
        updateTimer.start();




    }

    public void setServerStartTime(Date serverStartTime) {
        long clientStartTime = System.currentTimeMillis();
        long serverTime = serverStartTime.getTime(); // Obtenez le temps de démarrage du serveur en millisecondes
        remainingTime = 120000 - (clientStartTime - serverTime); // Calculez la différence en millisecondes
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image Animation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ImageAnimation());
            frame.setSize(800, 600); // Ajustez la taille de la fenêtre selon vos besoins
            frame.setVisible(true);
        });
    }
}
