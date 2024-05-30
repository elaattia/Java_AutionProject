import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

public interface ChatRemote extends Remote {
    //decalaration du remote
    public  Object highestPrice() throws RemoteException;
    public  float highestPricefloat() throws RemoteException;
    public void setPrix(float prix,int id ) throws RemoteException;


    public ArrayList<Object> getALLData() throws RemoteException;
    public Date getServerStartTime() throws RemoteException;
    public ArrayList<String> getTab() throws  RemoteException;
    public void addToTable(String a) throws  RemoteException;


}