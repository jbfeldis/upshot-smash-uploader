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

public class ImageFile {
	private File file;
	private String title;
	
	public ImageFile(File f){
		file=f;
		title=f.getName().substring(0, f.getName().lastIndexOf("."));
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
	
}
