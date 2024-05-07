package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Recepteur extends Message{
    private Socket socket;

    public Recepteur(Socket socket){
        this.socket = socket;
        
    }

    public void run(){
        try{
            BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;

            while(true){
                message = entree.readLine();

                if(message != null){
                    System.out.println("Aggée: " + message);
                
                    //sauvegarder le message
                    this.sauvegarder("Aggée", message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
