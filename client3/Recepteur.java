package client3;

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
                if(message != null){
                    String messageData[] = message.split(":", 2);

                    if(!messageData[1].trim().equals("pas de personne avec ce nom"))
                        System.out.println(message);
                    else
                        System.out.println(messageData[1].trim());
                }
            }
        } catch (IOException e) {
        }
    }
}
