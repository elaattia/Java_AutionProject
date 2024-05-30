
import java.io.Serializable;
import java.net.Socket;
public class Object implements Serializable {
    float price;
    int id;

    public Object(float price, int id) {
        this.price = price;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


}
