package client2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Message {

    private Socket socket;
    public Message(Socket socket){
        this.socket = socket;
    }

    public void historique(){
        try{
            String message;
            PrintWriter envoyeur = new PrintWriter(this.socket.getOutputStream(), true);
            envoyeur.println("historique");

            BufferedReader entree = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            message = entree.readLine();

            if(message != null){
                String messageData[] = message.split(",", 2);

                for (String contenu : messageData) {
                    System.out.println(contenu);
                }

            }
            else{
                System.out.println("Aucune data");
            }

        }
        catch(Exception e){

        }

    }
}
