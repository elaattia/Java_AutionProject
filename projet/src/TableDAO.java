

import java.sql.*;
public class TableDAO  implements TableDAOCRUD{
    Connection con=null;

    public TableDAO(String url,String username,String password) {
        con = MyConnection.getConnection(url,username,password);

    }

    @Override
    public int insertParticipant(String nom, String prenom, String num_tel,String date_naissance ) {
        //String req="insert into user values(?,?,?,?,?)";
        String req = "INSERT INTO user (nom, prenom, num_tel, date_naiss) VALUES (?, ?, ?, ?)";
        PreparedStatement ps= null;
        try {
            ps = con.prepareStatement(req);
            ps.setString(1,nom);
            ps.setString(2,prenom);
            ps.setString(3,num_tel);
            ps.setString(4,date_naissance);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet selection(String req) {
        java.sql.ResultSet rs=null;
        try {
            Statement st = con.createStatement();
            return st.executeQuery(req);

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void afficheResultat(ResultSet rs) {
        try {

            while(rs.next())
            {
                int id=rs.getInt(1);
                String n=rs.getString(2);
                String p=rs.getString(3);
                int num_tel=rs.getInt(4);
                String  date_naiss=rs.getString(5);

                System.out.println(id+" "+n+" "+p+" "+num_tel+" "+date_naiss);

            }
        } catch (SQLException e) {
            System.out.println("erreur affichage"+e.getMessage());
        }
    }

    @Override
    public int supprimerToutParticipant() {
        String req = "DELETE FROM user"; // Supprimer tous les enregistrements
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(req);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getId(String tel) {
        String req = "SELECT id FROM user WHERE num_tel = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(req);
            ps.setString(1, tel);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Aucun utilisateur avec ce numéro de téléphone trouvé.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Fermez les ressources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public int getTel(String tel) {
        String req = "SELECT id FROM user WHERE num_tel = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(req);
            ps.setString(1, tel);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Aucun utilisateur avec ce numéro de téléphone trouvé.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Fermez les ressources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
