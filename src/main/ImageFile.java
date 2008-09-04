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
package main;

import java.io.File;

/**
 * This class is used as a structure to easily associate an image file
 * with the title the user wanna give to the future upshot.
 * @author Gregory Durelle
 *
 */
public class ImageFile {
	private File file;//the image file
	private String title; //to have an image's title different from the image filename
	private int status;//0:ready, 1:sending, 2:sent

	public ImageFile(File f){
		file=f;
		title=f.getName().substring(0, f.getName().lastIndexOf("."));
		status=0;
	}

	/**
	 * Retrieve the image's title that will be displayed as Title field
	 * @return the title of the image
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Change the title of the image
	 * @param title the desired title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Retrieve the image file
	 * @return the File object associated to the image
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * CHeck if uploading the file towards upshotit.com is in progress or not
	 * @return true if the current ImageFile object is being sent, false otherwise.
	 */
	public boolean isSending(){
		return status==1;
	}
	
	/**
	 * Check if the file has already been sent or not
	 * @return true is the ImageFile object has been sent, false otherwise
	 */
	public boolean isSent() {
		return status==2;
	}
	
	/**
	 * Change state of the image to indicates sending is in progress
	 */
	public void setSending(){
		status = 1;
	}

	/**
	 * Change state of the image to indicates that the file has already been sent
	 */
	public void setSent() {
		status = 2;
	}
	
}
