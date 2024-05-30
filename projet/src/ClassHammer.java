

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List; // Importez la classe List
public class ClassHammer extends JPanel {
    private BufferedImage[] images1;

    private int numFrames1;

    private int frameIndex1 = 0;
    Timer timer1;
    int id;
    ChatRemote cr = null;


    public ClassHammer(int id, ChatRemote chatRemote) {
        this.id=id;
        this.cr=chatRemote;
        numFrames1 = 37; // Nombre d'images à charger

        setBackground(Color.WHITE);
        images1 = new BufferedImage[numFrames1];


        try {
            for (int i = 1; i <= numFrames1; i++) {
                images1[i - 1] = ImageIO.read(new File("C:\\Users\\HP\\IdeaProjects\\projet\\src\\hammer_image\\image" + i + "_cleanup.png"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(Color.WHITE);
        panelCenter.setLayout(new BorderLayout());

        JLabel labelCenter = new JLabel(new ImageIcon(images1[0]));

        panelCenter.add(labelCenter, BorderLayout.CENTER);

        timer1 = new Timer(100, e -> {
            //frameIndex1++;
            if (frameIndex1 >= numFrames1) {
                //frameIndex1 = 0;
                timer1.stop();
                // Fermer la fenêtre actuelle
                Window window = SwingUtilities.getWindowAncestor(this);
                window.dispose();
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("ClassHommeMicro");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    ClassHommeMicro classHommeMicro = new ClassHommeMicro(this.id,cr);
                    frame.add(classHommeMicro);
                    frame.setSize(800, 600);
                    frame.setVisible(true);
                });


            }
            else {
                labelCenter.setIcon(new ImageIcon(images1[frameIndex1]));
                frameIndex1++;
            }

            //labelCenter.setIcon(new ImageIcon(images1[frameIndex1]));
        });
        timer1.start();
        add(panelCenter, BorderLayout.CENTER);



    }


    public void startTimers() {
        Timer timer1 = new Timer(100, e -> {
            frameIndex1++;
            if (frameIndex1 >= numFrames1) {
                frameIndex1 = 0;
            }

        });
        // Démarrer les timers pour l'animation
        timer1.start();

    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Image Animation");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.add(new ClassHammer());
//            frame.setSize(800, 600); // Ajustez la taille de la fenêtre selon vos besoins
//            frame.setVisible(true);
//        });
//    }
}
///baich nzid sout mtar9a