/**
 *  Copyright 2008 Studio Melipone
 * 
 *  This file is part of "Smash Uploader".
 *  
 *  Smash Uploader is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Foobar is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *   
 * 
 * plugin for UpShot (c)
 * http://www.upshotit.com
 * 
 */
package connect;

import java.awt.Cursor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.swing.JOptionPane;

import table.DataModel;

import main.ImageFile;
import main.Smash;

/**
 * HTTP conneciton module, to permit authentication and thus sending of datas 
 * from our users to our distant host.
 * @author Gregory Durelle
 */
public class UpConnection implements Runnable{

	private URL url;
	private HttpURLConnection connection = null;
    private String userPass, encoding;
    private InputStreamReader isr=null;
    private OutputStreamWriter osr=null;
    private boolean logged, ready;
    private int id;
    private DataModel model;
    private Vector<ImageFile> list;
	
    public UpConnection(){
    	logged=false;
    	ready=false;
    }
    
    /**
     * This set the login and password the user will use
     * The login is the users email.
     * @param log The Login object used to retrieve the login and password
     */
    public void setUser(String login, String token){
		userPass = login+":"+token;
		encoding = new sun.misc.BASE64Encoder().encode(userPass.getBytes());
		logged=true;
		setup("users/get_id.xml");
		
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
	
	/**
	 * This give the references to the model
	 * @param model The DataModel object used by the JTable to list droped images
	 */
	public void setModel(DataModel model){
		this.model=model;
		list=model.getImages();
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
				id=Integer.parseInt(s);
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
	 * Setup connection with needed headers and user authentication properties
	 * charset UTF-8
	 * XML datas
	 * @param path The route used to access to wanted resource
	 */
	private void setup(String path){
		try {
			url = new URL("http://upshotit.com/en/"+path);
			connection=(HttpURLConnection)url.openConnection();
			connection.setAllowUserInteraction(true);
			//connection.setRequestProperty("charset","utf-8");
			connection.setRequestProperty("Host", "upshotit.com");
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Content-Type", "text/xml");
	        connection.setRequestProperty("Authorization", "Basic " + encoding);
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        ready=true;
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(Smash.getFrames()[0], "UpConnection.setup() MalformedURLException : "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Smash.getFrames()[0], "UpConnection.setup() IOException : "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Post request to setted up host
	 * it send the file given, with all its informations
	 * @param f The file that will be sent
	 * 
	 */
	private String sendData(ImageFile imf){
		String answer="";
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
				osr.write("<file_name>"+imf.getFile().getName()+"</file_name>");
				osr.write("<size>"+imf.getFile().length()+"</size>");
				osr.write("<javafile>"+encode+"</javafile>");
				osr.write("</upshot>");
				osr.flush();
				osr.close();
	
				/*You have to read the response of the host to make the changes happen*/
				isr = new InputStreamReader(connection.getInputStream());
				int c ;
				
				c = isr.read();
				while(c!=-1){
					answer+=(char)c;
					c = isr.read();
				}
				isr.close();
				
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(Smash.getFrames()[0], "UpConnection.sendData() : "+ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} 
			finally{
				connection.disconnect();
			}
		}
		return answer;
	}

	/**
	 * Run the thread for UpConnection, this permit to see the list of images
	 * being checked after each send finished
	 * 
	 */
	@Override
	public void run() {

		String answer="";
		Smash.getFrames()[0].setCursor(new Cursor(Cursor.WAIT_CURSOR));
		
		for(ImageFile imf : list){
			if(!imf.isSent()){
				imf.setSending();
				model.fireTableDataChanged();
				setup("users/"+id+"/upshots.xml");
				answer = sendData(imf);
				if(!answer.isEmpty())
					imf.setSent();
				model.fireTableDataChanged();
			}
		}
		Smash.getFrames()[0].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}
