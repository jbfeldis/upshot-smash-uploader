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
public class DataModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"", "Title", "Format", "size", ""};
	private Vector<ImageFile> imagesList;
	
	public DataModel(){
		imagesList=new Vector<ImageFile>();
	}
	
	/**
	 * This add the given ImageFile object to the Vector called imagesList
	 * @param imf the ImageFile object previously created
	 * @see ImageFile 
	 */
	public void add(ImageFile imf){
		if(getMIMEType(imf.getFile()).substring(0, 5).equals("image"))
			imagesList.add(imf);
		this.fireTableDataChanged();
	}
	
	/**
	 * Remove the ImageFile at the given index in the imagesList Vector
	 * @param index the place of the ImageFile in the Vector imagesList
	 */
	public void remove(int index){
		imagesList.remove(index);
		this.fireTableDataChanged();
	}
	
	/**
	 * retrieve the Vector imagesList
	 * @return Vector<ImageFile> 
	 */
	public Vector<ImageFile> getImages(){
		return imagesList;
	}
	
	/**
	 * Retrieve the ImageFile at the given row
	 * @param row the place of the desired ImageFile in imageList
	 * @return the ImageFile object
	 * @see ImageFile
	 */
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
	
	public void setValueAt(Object newTitle, int rowIndex, int columnIndex) {
		if(columnIndex==1)
			imagesList.elementAt(rowIndex).setTitle((String)newTitle);
	}

	/**
	 * This method retrieve the format of the image (jpg, png, etc) 
	 * it will be displayed as "image/png" for png files, as for all formats (image/format)
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
