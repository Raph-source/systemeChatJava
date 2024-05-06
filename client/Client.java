package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {        
        try {
            //connexion au serveur
            Socket socket = new Socket("localhost", 5050);

            //les object pour la gestion des messages
            Message message = new Message();
            Thread recepteur = new Thread(new Recepteur(socket));
            Thread envoyeur = new Thread(new Envoyeur(socket));

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
            }
            else{
                System.out.println("choix incorrecte !!!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
