import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class main2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {


            System.out.println("lancement du client");
            String url = "rmi://127.0.0.1:2000/chat";
            // Récupérer les informations de temps du serveur
            Date serverStartTime = null;
            ChatRemote cr = null;
            try {
                cr = (ChatRemote) Naming.lookup(url);
                serverStartTime = cr.getServerStartTime();

                Projet111 p=new Projet111(cr);
                int id=p.getId();


            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        });


    }
}


