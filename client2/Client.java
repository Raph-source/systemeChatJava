package client2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {        
        try {
            Scanner saisie = new Scanner(System.in);

            //connexion au serveur
            Socket socket = new Socket("localhost", 5050);

            //authenfication
            System.out.print("Login: ");
            String login = saisie.next();
            System.out.print("mot de passe: ");
            String mdp = saisie.next();

            String requete = login + ":" + mdp;
            PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true);
            sortie.println(requete);

            BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String reponse = entree.readLine();

            String[] authData = reponse.split(":", 2);
            reponse = authData[1].trim();

            System.out.println(reponse);
            if(!reponse.equals("acces refusée")){
                //les object pour la gestion des messages
                Message message = new Message(socket);
                Thread recepteur = new Thread(new Recepteur(socket));
                Thread envoyeur = new Thread(new Envoyeur(socket));

                //entrée du user
                System.out.print("(C)hat\n(H)istorique:\n==>  ");
                String choix = saisie.next();

                if(choix.charAt(0) == 'C'){
                    if(recepteur.isAlive()){

                    }
                    else if(envoyeur.isAlive()){

                    }
                    else{
                        recepteur.start();
                        envoyeur.start();
                    }
                }
                else if(choix.charAt(0) == 'H'){
                    message.historique();
                }
                else{
                    System.out.println("choix incorrecte !!!");
                }
            }
            else{
                System.out.println(reponse);
            }
        } catch (IOException e) {
        }
    }
}
