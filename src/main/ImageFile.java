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

/**
 * This class is used as a structure to easily associate an image file
 * with the title the user wanna give to the future upshot.
 * @author Gregory Durelle
 *
 */
public class ImageFile {
	private File file;
	private String title;
	private int status;//0:ready, 1:sending, 2:sent

	public ImageFile(File f){
		file=f;
		title=f.getName().substring(0, f.getName().lastIndexOf("."));
		status=0;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public File getFile() {
		return file;
	}
	
	public boolean isSending(){
		return status==1;
	}
	
	public boolean isSent() {
		return status==2;
	}
	
	public void setSending(){
		status = 1;
	}

	public void setSent() {
		status = 2;
	}
	
}
