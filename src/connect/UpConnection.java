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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP conneciton module, to permit authentication and thus sending of datas 
 * from our users to our distant host.
 * @author Gregory Durelle
 */
public class UpConnection {

	private URL url;
	private HttpURLConnection connection = null;
    private String username, password, userPass, encoding;
	
	public UpConnection() {
		username = "test@test.com";
		password = "test";
		userPass = username+":"+password;
		encoding = new sun.misc.BASE64Encoder().encode(userPass.getBytes());
	}
	
	public void getUserInfos(){
		InputStreamReader isr;
		
		try {
			url = new URL("http://localhost:3000/en/users/3.xml");
			connection=(HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "text/xml");
			connection.setRequestProperty("charset","utf-8");
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.setRequestProperty("Host", "localhost:3000");
			connection.setRequestProperty("Accept", "*/*");
	        connection.setRequestProperty ("Authorization", "Basic " + encoding);
			
			isr = new InputStreamReader(connection.getInputStream());
			int c = isr.read();//1st char is the encodage (eg:utf-8)

			c = isr.read();
			while(c!=-1){
				System.out.print((char)c);
				c = isr.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		finally{
			connection.disconnect();
		}
	}
}
