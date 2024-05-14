package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class Client implements Runnable {
    private Message message;
    private Socket socket;
    private final Map<String, Socket> utilisateurConnecte;
    private Connection bdd = null;

    public Client(Socket socket,  Map<String, Socket> utilisateurConnecte) {
        this.socket = socket;
        this.utilisateurConnecte = utilisateurConnecte;
        this.message = new Message();

        //connexion bdd
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  

            this.bdd = DriverManager.getConnection("jdbc:mysql://localhost:3307/chat", "root", "");
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader entree = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String authenfication = entree.readLine();

            String[] partieAuth = authenfication.split(":", 2);
            String login = partieAuth[0].trim();
            String mdp = partieAuth[1].trim();

            this.utilisateurConnecte.put(login, this.socket);

            if(this.authenfication(login, mdp)){
                sendMessage("serveur", login, "acces valide");//envoi de succès de la connexion

                String message;
                while (true) {
                    entree = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    
                    message = entree.readLine();

                    if(!message.equals("historique")){
                        String[] parties = message.split(":", 2);

                        if (parties.length == 2) {
                            String destinataire = parties[0].trim();
                            String contenu = parties[1].trim();
                            

                            for (char dest : destinataire.toCharArray()) {
                                if(dest == 'A'){
                                    sendMessage(login, "Aggee", contenu);
                                    System.out.println(message);
                                    //sauvegarder le message
                                    this.message.sauvegarder(login, message);                                }
                                else if(dest == 'E'){
                                    sendMessage(login, "Elise", contenu);
                                    System.out.println(message);
                                    //sauvegarder le message
                                    this.message.sauvegarder(login, message);
                                }
                                else if(dest == 'D'){
                                    sendMessage(login, "Danico", contenu);
                                    System.out.println(message);
                                    //sauvegarder le message
                                    this.message.sauvegarder(login, message);                                }
                                else{
                                    sendMessage("serveur", login, "pas de personne avec ce nom");
                                }
                            }

                        }
                    }
                    else{
                        String historique = this.message.historique();
                        sendMessage("serveur", login, historique);
                    }

                }
            }
            else{
                sendMessage("serveur", login, "acces refusée");//envoi d'échec de connexion
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //pour l'envoi des messages
    private void sendMessage(String login, String destinataire, String message) throws IOException {
        Socket socketDestinataire = utilisateurConnecte.get(destinataire);

        if (socketDestinataire != null) {
            PrintWriter recipientOut = new PrintWriter(socketDestinataire.getOutputStream(), true);
            recipientOut.println(login + ":" + message); // Envoi du message au destinataire
        } else {
            System.out.println("Erreur: Destinataire non trouvé.");
        }
    }

    private boolean authenfication(String login, String mdp){
        String sql = "SELECT * FROM utilisateur WHERE login = '" + login + "' AND mdp = '" + mdp + "'";
        int i = 0;

        try{
            java.sql.Statement requete = this.bdd.createStatement();

            ResultSet trouver = ((java.sql.Statement) requete).executeQuery(sql);
            System.out.println(trouver);
            while (trouver.next()) {
                i++;
            }
        }
        catch (SQLException e) {
        } 
        System.out.println("i = " + i);
        if(i > 0)
            return true;
        return false;
    }
}

