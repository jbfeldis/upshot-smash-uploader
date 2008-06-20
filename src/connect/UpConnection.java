/**
 * Studio Melipone
 * June 2008
 * 
 * plugin for UpShot
 * http://www.upshotit.com
 * 
 */
package connect;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import main.Smash;

/**
 * HTTP conneciton module, to permit authentication and thus sending of datas 
 * from our users to our distant host.
 * @author Gregory Durelle
 */
public class UpConnection {

	private HttpURLConnection connection = null;

	
	public UpConnection() {
		
	       String username = "test@test.com";
	       String password = "test";
	       String UserPass = username+":"+password;

		try {
			URL url = new URL("http://localhost:3000/en/users/3/upshots/1.xml");
			connection=(HttpURLConnection)url.openConnection();
			connection.setAllowUserInteraction(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "text/xml");
			connection.setRequestProperty("charset","utf-8");
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.setRequestProperty("Host", "localhost:3000");
			connection.setRequestProperty("Accept", "*/*");
	        String encoding = new sun.misc.BASE64Encoder().encode(UserPass.getBytes());
	        connection.setRequestProperty ("Authorization", "Basic " + encoding);
	        
	        get();
	        
			if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
				JOptionPane.showMessageDialog(Smash.getFrames()[0], "Either your login/password are false, either their is a problem with the server", "ERROR", JOptionPane.ERROR_MESSAGE);
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
	
	
	
	public void get(){
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(connection.getInputStream());
			int c = isr.read();//1st char is the encodage (eg:utf-8)

			c = isr.read();
			while(c!=-1){
				System.out.print((char)c);
				c = isr.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
