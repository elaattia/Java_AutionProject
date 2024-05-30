import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.util.TimerTask;

public class ClassStop extends JPanel {
    private BufferedImage[] images;
    private int numFrames;
    private int frameIndex = 0;
    private int id;
    private ChatRemote cr = null;
    private Timer timer;
    private int iteration = 0; // Compteur d'itérations

    public ClassStop(int id, ChatRemote chatRemote) {
        this.id = id;
        this.cr = chatRemote;
        numFrames = 7; // Nombre d'images à charger

        setBackground(Color.WHITE);
        images = new BufferedImage[numFrames];

        try {
            for (int i = 1; i <= numFrames; i++) {
                images[i - 1] = ImageIO.read(new File("C:\\Users\\HP\\IdeaProjects\\projet\\src\\images\\image" + i + "_cleanup.png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(Color.WHITE);
        panelCenter.setLayout(new BorderLayout());

        JLabel labelCenter = new JLabel(new ImageIcon(images[0]));
        panelCenter.add(labelCenter, BorderLayout.CENTER);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (frameIndex >= numFrames) {
                    iteration++; // Incrémenter le compteur d'itérations
                    if (iteration >= 2) { // Vérifier si deux itérations ont été effectuées
                        Window parentWindow = SwingUtilities.getWindowAncestor(ClassStop.this);
                        if (parentWindow != null) {
                            parentWindow.dispose();
                        }
                        timer.cancel(); // Arrêter le timer
                    } else {
                        frameIndex = 0; // Réinitialiser l'index de l'image pour démarrer une nouvelle itération
                    }
                } else {
                    labelCenter.setIcon(new ImageIcon(images[frameIndex++]));
                }
            }
        }, 0, 100); // Afficher chaque image toutes les 100 millisecondes

        add(panelCenter, BorderLayout.CENTER);
    }
}
