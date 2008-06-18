/**
 * Studio Melipone
 * June 2008
 * 
 * plugin for UpShot
 * http://www.upshotit.com
 * 
 */
package main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

/**
 * Model for the list of images dropped in the JDesktopPane
 * of the Smash application.
 * @author Gregory Durelle
 *
 */
public class DataModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"", "Name", "Format", "size"};
	private Vector<File> imagesList;
	private ImageIcon icon=null;
	
	public DataModel(){
		imagesList=new Vector<File>();
	}
	
	public void add(File f){
		imagesList.add(f);
		this.fireTableDataChanged();
	}

	public int getColumnCount() {
		return 4;
	}

	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	public int getRowCount() {
		return imagesList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0: return "X";//new ImgFButton(imagesList.elementAt(rowIndex)).get(); 
		case 1: return imagesList.elementAt(rowIndex).getName();
		case 2: return getMIMEType(imagesList.elementAt(rowIndex)) ;
		case 3: return imagesList.elementAt(rowIndex).length()/1024+"Ko";
		}
		return "ERROR";
	}

	public static String getMIMEType(File file){
	   if(file.isDirectory()){return "repertoire";}
	   if(!file.exists()){return "fichier inexistant";}
	   try{
	      URL url = file.toURI().toURL();
	      URLConnection connection = url.openConnection();
	      return connection.getContentType();
	   }catch(MalformedURLException mue){
	      return mue.getMessage();
	   }catch(IOException ioe){
	      return ioe.getMessage();
	      }
	}
	
	public ImageIcon getIcon(){
		if(icon==null){
			URL url = this.getClass().getResource("/images/cross.png");
			icon = new ImageIcon(url);
		}
		return icon;
	}
	
	class ImgFButton{
		private File file;
		private JButton del;
		
		public ImgFButton(File f){
			file=f;
			del=new JButton(icon);
		}
		
		public  File getFile(){
			return file;
		}
		
		public JButton get(){
			return del;
		}
	}
}
