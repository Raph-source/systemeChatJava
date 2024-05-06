package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Envoyeur implements Runnable {
    private Socket socket;

    public Envoyeur(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try{
            String message;
            PrintWriter envoyeur = new PrintWriter(this.socket.getOutputStream(), true);
            BufferedReader saisie = new BufferedReader(new InputStreamReader(System.in));
            
            while(true){
                System.out.print("Aggée: ");
                message = saisie.readLine();
                envoyeur.println(message);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sauverger(String Message){

    }
}
