/**
 * Studio Melipone
 * June 2008
 * 
 * plugin for UpShot
 * http://www.upshotit.com
 * 
 */
package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Image editor interface
 * will present a thumbnail of the image, its filename, size
 * and format, and display a textfield to let the user
 * change the title. (default title is filename)
 * @author Gregory Durelle
 *
 */
public class ImageEditor extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel name, size, titlelab;
	private JTextField title;
	private JButton ok;
	private Image image;
	private int w,h, scale;
	private String new_title;
	
	public ImageEditor(ImageFile imf){
		this.setResizable(false);
		this.setModal(true);
		scale=80;
		
		try {
			BufferedImage bi = ImageIO.read(imf.getFile());
			image = bi.getScaledInstance(-1, -1, Image.SCALE_SMOOTH);//only to get an Image instance
			
			w = image.getWidth(null);
			h = image.getHeight(null);
			
			if(w>h){
				if(w>=scale){
					image=bi.getScaledInstance(scale, -1, Image.SCALE_SMOOTH);
				}
			}
			else image=bi.getScaledInstance(-1, scale, Image.SCALE_SMOOTH);
			
		} catch (IOException ioe) {
			System.err.println("--> "+ioe.getMessage());
		}
		
		panel = new JPanel();
			panel.setPreferredSize(new Dimension(scale,scale));
		name = new JLabel(imf.getFile().getAbsolutePath());
		size = new JLabel(imf.getFile().length()/1024+"Ko");
		titlelab = new JLabel("Title :");
		title = new JTextField(imf.getTitle(),20);
			title.setActionCommand("OK");
			title.addActionListener(this);
		ok = new JButton("OK");
			ok.addActionListener(this);
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		Insets ins = new Insets(5,5,5,5);
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=2;
		gbc.gridheight=2;
		gbc.insets=ins;
		gbc.anchor=GridBagConstraints.NORTHWEST;
		gbl.setConstraints(panel, gbc);
		
		gbc.gridx=2;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		gbc.ipady=20;
		ins = new Insets(5,5,0,10);
		gbc.insets=ins;
		gbl.setConstraints(name, gbc);
		
		gbc.gridy=1;
		gbc.ipady=10;
		ins = new Insets(5,5,5,10);
		gbc.insets=ins;
		gbl.setConstraints(size, gbc);
		
		gbc.gridx=0;
		gbc.gridy=2;
		ins = new Insets(5,5,0,5);
		gbc.insets=ins;
		gbc.anchor=GridBagConstraints.SOUTHWEST;
		gbl.setConstraints(titlelab, gbc);
		
		gbc.gridx=1;
		gbc.gridwidth=2;
		gbc.ipady=0;
		gbl.setConstraints(title, gbc);
		
		gbc.gridy=3;
		gbc.gridx=2;
		gbc.anchor=GridBagConstraints.SOUTHEAST;
		gbl.setConstraints(ok, gbc);
		
		this.getContentPane().setLayout(gbl);
		this.getContentPane().add(panel);
		this.getContentPane().add(name);
		this.getContentPane().add(size);
		this.getContentPane().add(titlelab);
		this.getContentPane().add(title);
		this.getContentPane().add(ok);
		
		this.pack();
		this.setLocationRelativeTo(Smash.getFrames()[0]);
		this.setVisible(true);
	}
	
	/**
	 * Will draw a thumbnail of the image
	 */
	public void paint(Graphics g){
		super.paintComponents(g);
		
		w = image.getWidth(null);
		h = image.getHeight(null);
		
		g.drawImage(image, (panel.getWidth()-w)/2+5, (panel.getHeight()-h)/2+27,image.getWidth(null), image.getHeight(null), null);
	}

	public void actionPerformed(ActionEvent ae) {
		new_title=title.getText();
		this.dispose();
	}
	
	/**
	 * used by the editcelleditor to apply changes on the model
	 */
	public String getTitle(){
		return new_title;
	}
}
