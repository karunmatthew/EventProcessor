package org.eventprocessor.storm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.tuple.Values;

public class SpoutThread extends Thread {
	
	private Socket socket;
    private SpoutOutputCollector spoutOutputCollector;
    private InputStream inputStream = null;
    private BufferedReader bufReader = null;
    private String line;
    
    public SpoutThread(Socket clientSocket, SpoutOutputCollector spoutOutputCollector) {
        this.socket = clientSocket;
    }

    public void run() {
        
        try {
        	inputStream = socket.getInputStream();
            bufReader = new BufferedReader(new InputStreamReader(inputStream));
            
        } catch (IOException e) {
            return;
        }
        while (true) {
            try {
                line = bufReader.readLine();
                if ((line == null)) {
                	bufReader.close();
                    inputStream.close();
                    socket.close();
                    return;
                } else {
                   System.out.println("Data ------------>      "+line);
                   spoutOutputCollector.emit(new Values(line));                   
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}