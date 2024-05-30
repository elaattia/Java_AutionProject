
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
public class MyConnection {
    public static Connection getConnection(String url,String username,String password) {
        //role :chargement driver et connection a la base

        //chargement driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver charged");
        } catch (ClassNotFoundException e) {
            System.out.println("erreur Driver" + e.getMessage());
        }
        //connexion a la base
        Connection con = null;
        Statement st = null;
        try {
            con = (Connection) DriverManager.getConnection(url, username, password);
            st = (Statement) con.createStatement();
            System.out.println("Connected ...");
        } catch (SQLException e) {
            System.out.println("erreur connection" + e.getMessage());
        }

        return con;
    }
}
