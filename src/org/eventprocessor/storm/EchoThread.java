package org.eventprocessor.storm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class EchoThread extends Thread {
    protected Socket socket;
    
    InputStream inputStream = null;
    BufferedReader bufReader = null;
    String line;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {       
       
    	try {
        	inputStream = socket.getInputStream();
        	bufReader = new BufferedReader(new InputStreamReader(inputStream));
            
        } catch (IOException e) {
        	e.printStackTrace();
            return;
        }
        
        while (true) {
            try {
                line = bufReader.readLine();
                if ((line == null)) {
                    socket.close();
                    return;
                } else {
                   System.out.println("Data ------------  "+line);
                   
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}