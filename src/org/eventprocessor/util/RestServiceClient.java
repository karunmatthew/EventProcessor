package org.eventprocessor.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;

public class RestServiceClient {

	private static final String POST = "POST";
	private static final String CONTENT_TYPE = "Content-Type";

	public static String postMessage(String link, String contentType,
			String messageContent) throws IOException {

		URL url = new URL(link);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(POST);
		conn.setRequestProperty(CONTENT_TYPE, contentType);

		OutputStream os = conn.getOutputStream();
		os.write(messageContent.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			return "";
		}
		
		String jsonResponse = IOUtils.toString(conn.getInputStream(),"UTF-8");
		IOUtils.closeQuietly(conn.getInputStream());
		os.close();	
		conn.disconnect();		
		return jsonResponse;

	}

	public static void main(String[] args) throws IOException {

		String link = "/services/rest/connect/v1.3/incidents/";
		String contentType = "application/json";
		String messageContent = "{\"primaryContact\": {\"id\": 2 }, \"subject\": \"AAA\"}";
		
		RestServiceClient.postMessage(link, contentType, messageContent);
	}

}