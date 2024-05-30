
import java.sql.*;
public interface TableDAOCRUD {
    int insertParticipant(String nom, String prenom, String num_tel,String date_naissance);

    ResultSet selection(String req);
    void afficheResultat(ResultSet rs);
    int supprimerToutParticipant();
    int getId(String tel);
}
