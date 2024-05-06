package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Recepteur implements Runnable{
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
                System.out.println("Aggée: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
