/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.awt.event.ActionEvent;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public final class SprzetClient {

    private static ProducentPomiarowClient producent;
    private static DataOutputStream out;
    private final int MY_TIME = 3000; //trzy sekundy
    String serverName = "192.168.1.103";
    int port = 3333;

    public SprzetClient() {
        producent = new ProducentPomiarowClient();
        //connect(); 
        wlaczTimer();
    }

    public void connect() {
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            out = new DataOutputStream(outToServer);
            
            String pomiary = producent.produkujNowePomiary();
            System.out.println("Wyprodukowano nowe pomiary");
            out.writeUTF(pomiary);
            
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void wlaczTimer() {
        Timer timer = new Timer(MY_TIME, (ActionEvent e) -> {
            connect();
        });
        timer.start();
    }
}
