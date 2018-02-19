package org.eventprocessor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketAnalyser {

	public static void main(String[] args) throws IOException {

		try (ServerSocket serverSocket = new ServerSocket(7070);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(
						clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));) {

			InputStream incomingStream = clientSocket.getInputStream();
						
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
			String line;
			
			br = new BufferedReader(new InputStreamReader(incomingStream));
			while ((line = br.readLine()) != null) {
				System.out.println("Data ------- "+line);
			}
		}

	}
}
