package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serveur {
    public static void main(String[] args) {
        Socket clientSocket;
        
        try {
            //connexion avec le client
            ServerSocket serverSocket = new ServerSocket(5050);
            System.out.println("Serveur de chat démarré sur le port 5050");

            clientSocket = serverSocket.accept();
            System.out.println("Client connecté : " + clientSocket);

            //les object pour la gestion des messages
            Message message = new Message();
            Thread recepteur = new Thread(new Recepteur(clientSocket));
            Thread envoyeur = new Thread(new Envoyeur(clientSocket));

            //entrée du user
            Scanner saisie = new Scanner(System.in);
            System.out.print("(C)hat\n(H)istorique:\n==>  ");
            String choix = saisie.next();

            if(choix.charAt(0) == 'C'){
                while (true) {
                    recepteur.start();
                    envoyeur.start();
                }
            }
            else if(choix.charAt(0) == 'H'){

                message.historique();

                System.exit(0);
            }
            else{
                System.out.println("choix incorrecte !!!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
