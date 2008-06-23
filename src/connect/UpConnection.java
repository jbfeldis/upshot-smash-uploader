/**
 * Studio Melipone
 * June 2008
 * 
 * plugin for UpShot
 * http://www.upshotit.com
 * 
 */
package connect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * HTTP conneciton module, to permit authentication and thus sending of datas 
 * from our users to our distant host.
 * @author Gregory Durelle
 */
public class UpConnection {

	private URL url;
	private HttpURLConnection connection = null;
    private String username, password, userPass, encoding;
    private InputStreamReader isr=null;
    private OutputStreamWriter osr=null;
	
	public UpConnection() {
		username = "test@test.com";
		password = "test";
		userPass = username+":"+password;
		encoding = new sun.misc.BASE64Encoder().encode(userPass.getBytes());
	}
	
	/**
	 * Setup connection with needed headers and user authentication properties
	 * charset UTF-8
	 * XML datas
	 * 
	 */
	public void setup(){
		try {
			url = new URL("http://localhost:3000/en/users/3/upshots.xml");
			connection=(HttpURLConnection)url.openConnection();
			connection.setAllowUserInteraction(true);
			//connection.setRequestProperty("charset","utf-8");
			//connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.setRequestProperty("Host", "localhost:3000");
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Content-Type", "text/xml");
	        connection.setRequestProperty("Authorization", "Basic " + encoding);
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Post request to setted up host
	 * it send the file given, with all its informations
	 */
	public void sendData(File f){
		try {
			connection.setRequestMethod("POST");
			
			osr = new OutputStreamWriter(connection.getOutputStream());
			osr.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			
			/*content type*/
		    URL url = f.toURI().toURL();
		    URLConnection fcon = url.openConnection();
		    
		    System.out.println(f.getName());
		    System.out.println(fcon.getContentType());
		    
		    /*convert the file to 64base format*/
		    FileInputStream fis = new FileInputStream(f);
		    
		    String file="";
			int ch ;

			ch = fis.read();
			while(ch!=-1){
				ch = fis.read();
				file+=(char)ch;
			}
		    
			osr.write("<upshot>");
			osr.write("<title>"+f.getName()+"</title>");
			osr.write("<uploaded_data>"+new sun.misc.BASE64Encoder().encode(file.getBytes())+"</uploaded_data>");
			osr.write("</upshot>");
			osr.flush();
			osr.close();
			
			/*You have to read the response of the host to make the changes happen*/
			isr = new InputStreamReader(connection.getInputStream());
			int c ;

			c = isr.read();
			while(c!=-1){
				System.out.print((char)c);
				c = isr.read();
			}
			
			isr.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		finally{
			connection.disconnect();
		}
	}
	
	/**
	 * Simple get request to retrieve informations from
	 * setted up host
	 */
	public void getUserInfos(){
		
		try {
			
			connection.setRequestMethod("GET");
			
			isr = new InputStreamReader(connection.getInputStream());
			int c ;

			c = isr.read();
			while(c!=-1){
				System.out.print((char)c);
				c = isr.read();
			}
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		finally{
			connection.disconnect();
		}
	}
}
