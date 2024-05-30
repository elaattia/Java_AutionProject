


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class MainServeur {
    public static void main(String[] args) {
        //lancement serveur
        System.out.println("demarrage serveur");

        try {
            ChatImplementation chat = new ChatImplementation();
            String url="rmi://127.0.0.1:2000/chat";
            LocateRegistry.createRegistry(2000);
            try {
                Naming.rebind(url,chat);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("serveur en ecoute");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }
}
