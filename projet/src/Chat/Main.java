package Chat;
import javax.swing.*;

public class Main {
    private static boolean serverStarted = false;
    public static void main(String[] args) {
        if (!serverStarted) {
            serverStarted = true;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Controller();
                }
            });
        }
    }
}