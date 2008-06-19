package connect;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpConnection {

	private HttpURLConnection connection = null;
	private DataOutputStream dos;
	
	public UpConnection() {
		try {
			URL url = new URL("localhost:3000");
			connection=(HttpURLConnection)url.openConnection();
			connection.setAllowUserInteraction(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "multipart/form-data");
			
			dos = new DataOutputStream(connection.getOutputStream());
			
			if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
				System.out.println("go");
				
			}
			
		} catch (MalformedURLException e) {
			System.err.println("MalformedURL : "+e.getMessage());
		} catch (IOException e) {
			System.err.println("Unable to open connection : "+e.getMessage());
		}
		finally{
			connection.disconnect();
		}
	}
}
