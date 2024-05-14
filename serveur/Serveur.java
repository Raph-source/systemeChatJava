package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Serveur {
    public static void main(String[] args) {
        Socket clientSocket;
        
        try {
            //connexion avec le client
            ServerSocket serverSocket = new ServerSocket(5050);
            System.out.println("Serveur de chat démarré sur le port 5050");


            Map<String, Socket> utilisateurConnecte = new HashMap<>();

            while (true) {
                //connexion des clients
                clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion : " + clientSocket);

                new Thread(new Client(clientSocket, utilisateurConnecte)).start();
            }

        } 
        catch (IOException e) {
        }
    }
}
