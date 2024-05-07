package serveur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.PreparableStatement;
import com.mysql.cj.xdevapi.Statement;

public class Message implements Runnable {
    private Connection bdd = null;

    public Message(){
        try {
            Class.forName("com.mysql.jdbc.Driver");  

            this.bdd = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat", "root", "");
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    public void run(){}
    
    public void sauvegarder(String auteur, String message){
        String sql = "INSERT INTO message (auteur, contenu) VALUES ('" + auteur + "', '" + message + "')";
        try{
            java.sql.Statement requete = this.bdd.createStatement();

            ((java.sql.Statement) requete).executeUpdate(sql);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }    
    }

    public void historique(){
        String sql = "SELECT * FROM message ORDER BY id ASC";
        try{
            java.sql.Statement requete = this.bdd.createStatement();

            ResultSet trouver = ((java.sql.Statement) requete).executeQuery(sql);

            while (trouver.next()) {
                String auteur = trouver.getString("auteur");
                String contenu = trouver.getString("contenu");

                System.out.println(auteur + ": " + contenu);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }    }
}
