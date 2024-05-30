
import javax.swing.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
public class ChatImplementation extends UnicastRemoteObject implements ChatRemote {
    ArrayList<Object> tab=new ArrayList<>();
    float initialPrice;
    float highestPrice;
    private Date serverStartTime;
    private Timer timer;
    int id;
    ArrayList<String> tableau;
    public ChatImplementation() throws RemoteException {
        tableau=new ArrayList<>();
        serverStartTime = new Date();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
            }
        }, 120000); // 2 minutes
    }

    @Override
    public Date getServerStartTime() throws RemoteException {
        return serverStartTime;
    }

    @Override
    public ArrayList<String> getTab() throws RemoteException {
        return tableau;
    }

    @Override
    public void addToTable(String a) throws RemoteException {
        tableau.add(a);
    }

    @Override
    public Object highestPrice() throws RemoteException {
        float maxPrice=initialPrice;
        for(Object o:tab)
        {
            if(o.getPrice()>maxPrice)
            {
                maxPrice=o.getPrice();
            }
        }
        highestPrice=maxPrice;
        if(maxPrice==initialPrice)
        {
            return new Object(initialPrice,1000);
        }
        else
        {
            for(Object o:tab)
            {
                if(o.getPrice()==maxPrice)
                {
                    return o;
                }
            }
            return new Object(initialPrice,1000);
        }
    }

    @Override
    public float highestPricefloat() throws RemoteException {
        return highestPrice;
    }

    @Override
    public void setPrix(float prix, int id) throws RemoteException {
        if(prix>highestPrice().price)
        {
            highestPrice=prix;
            for(Object o:tab)
            {
                if(o.id==id)
                {
                    o.setPrice(prix);
                }
            }
        }
    }




    @Override
    public ArrayList<Object> getALLData() throws RemoteException {
        return tab;
    }



}