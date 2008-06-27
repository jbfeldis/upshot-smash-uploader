/**
 * Studio Melipone
 * June 2008
 * 
 * plugin for UpShot
 * http://www.upshotit.com
 * 
 */
package table;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import main.ImageFile;

/**
 * Model for the list of images dropped in the JDesktopPane
 * of the Smash application.
 * @author Gregory Durelle
 *
 */
public class DataModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"", "Title", "Format", "size", ""};
	private Vector<ImageFile> imagesList;
	
	public DataModel(){
		imagesList=new Vector<ImageFile>();
	}
	
	public void add(ImageFile imf){
		if(getMIMEType(imf.getFile()).substring(0, 5).equals("image"))
			imagesList.add(imf);
		this.fireTableDataChanged();
	}
	
	public void remove(int index){
		imagesList.remove(index);
		this.fireTableDataChanged();
	}
	
	public Vector<ImageFile> getImages(){
		return imagesList;
	}
	
	public ImageFile getImageFile(int row){
		return imagesList.elementAt(row);
	}

	public int getColumnCount() {
		return 5;
	}

	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	public int getRowCount() {
		return imagesList.size();
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex==0 || columnIndex==1 || columnIndex==4)
			return true;
		return false;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0: return null;
		case 1: return imagesList.elementAt(rowIndex).getTitle();
		case 2: return getMIMEType(imagesList.elementAt(rowIndex).getFile()) ;
		case 3: return imagesList.elementAt(rowIndex).getFile().length()/1024+"Ko";
		case 4: return null;
		}
		return "ERROR";
	}

	/**
	 * 
	 * @param file
	 * @return The MIME-Type
	 */
	private static String getMIMEType(File file){
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
}
