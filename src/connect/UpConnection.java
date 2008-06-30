/**
 * Studio Melipone
 * June 2008
 * 
 * plugin for UpShot
 * http://www.upshotit.com
 * 
 */
package connect;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import main.ImageFile;

/**
 * HTTP conneciton module, to permit authentication and thus sending of datas 
 * from our users to our distant host.
 * @author Gregory Durelle
 */
public class UpConnection {

	private URL url;
	private HttpURLConnection connection = null;
    private String userPass, encoding;
    private InputStreamReader isr=null;
    private OutputStreamWriter osr=null;
    private boolean logged, ready;
	
    public UpConnection(){
    	logged=false;
    	ready=false;
    }
    
    /**
     * This set the login and password the user will use
     * The login is the users email.
     * @param log The Login object used to retrieve the login and password
     */
    public void setUser(String login, String passwd){
		userPass = login+":"+passwd;
		encoding = new sun.misc.BASE64Encoder().encode(userPass.getBytes());
		System.out.println(login+":"+passwd);
		System.out.println("-----------==========>"+encoding);
		logged=true;
    }
	
	/**
	 * Setup connection with needed headers and user authentication properties
	 * charset UTF-8
	 * XML datas
	 * @param path The route used to access to wanted resource
	 */
	public void setup(String path){
		try {
			url = new URL("http://localhost:3000/en/"+path);
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
	        ready=true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Post request to setted up host
	 * it send the file given, with all its informations
	 * @param f The file that will be sent
	 * 
	 */
	public void sendData(ImageFile imf){
		if(logged & ready){
			try {
				connection.setRequestMethod("POST");
				
				osr = new OutputStreamWriter(connection.getOutputStream());
				osr.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			    
			    /*convert the file to 64base format*/
			    FileInputStream fis = new FileInputStream(imf.getFile());
	
			    byte[] buffer = new byte[(int)imf.getFile().length()];
			    fis.read(buffer);
			    fis.close();
			    String encode = new sun.misc.BASE64Encoder().encode(buffer); //Base64.encode(buffer);
			    
			    /*Then create the xml file to send, with encoded file inside*/
				osr.write("<upshot>");
				osr.write("<title>"+imf.getTitle()+"</title>");
				osr.write("<size>"+imf.getFile().length()+"</size>");
				osr.write("<javafile>"+encode+"</javafile>");
				osr.write("</upshot>");
				osr.flush();
				osr.close();
	
				/*You have to read the response of the host to make the changes happen*/
				isr = new InputStreamReader(connection.getInputStream());
				int c ;
				
				System.out.println("API responding...");
				c = isr.read();
				while(c!=-1){
					System.out.print((char)c);
					c = isr.read();
				}
				isr.close();
				
			} catch (IOException e) {
	//			e.printStackTrace();
			} 
			finally{
				connection.disconnect();
			}
		}
	}
	
	/**
	 * Simple get request to retrieve informations from
	 * setted up host
	 * @return A code value for what happened : <br />
	 *  0 : getUser() and/or setup() not yet called<br />
	 *  c : ok<br />
	 * -1 : login failed<br />
	 */
	public int getId(){
		if(logged & ready){
			try {
				connection.setRequestMethod("GET");
				isr = new InputStreamReader(connection.getInputStream());
				int c = 0;
	
				c = isr.read();
				String s = new String();
				while(c!=-1){
					s += (char)c;
					c = isr.read();
				}
				isr.close();
				return Integer.parseInt(s);
			} catch (IOException e) {
				return -1;
			} 
			finally{
				connection.disconnect();
			}
		}
		return 0;
	}

	/**
	 * If user's login and token information are given.
	 * @return true if user's informations have already been given, false otherwise
	 * @see setUser(String login, String passwd)
	 */
	public boolean isLogged() {
		return logged;
	}

	/**
	 * If the connection is opened with the previously given parameters
	 * @return true if connection parameters have already been given through the setup(String path) method
	 * @see setup(String path)
	 */
	public boolean isReady() {
		return ready;
	}
}
