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

import java.awt.Color;
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
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private ResourceBundle msg;
	
	public ImageEditor(ImageFile imf){
		this.setResizable(false);
		this.setModal(true);
		this.getContentPane().setBackground(Color.decode("#656565"));
		this.getContentPane().setForeground(Color.WHITE);
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
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Smash.getFrames()[0], "ImageEditor() IOException : "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		panel = new JPanel(true);
			panel.setPreferredSize(new Dimension(scale,scale));
			panel.setDoubleBuffered(true);
			panel.setOpaque(false);
		name = new JLabel(imf.getFile().getAbsolutePath());
		size = new JLabel(imf.getFile().length()/1024+"Ko");
		titlelab = new JLabel();
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
	}
	
	/**
	 * Set the language resource as given in Smash class
	 * @param rb the ResourceBundle representing the language
	 */
	public void setResourceBundle(ResourceBundle rb){
		msg=rb;
		this.displayLanguage();
	}
	
	/**
	 * Redraw all labels and buttons in the appropriate language
	 */
	private void displayLanguage(){
		titlelab.setText(msg.getString("title")+" :");
	}
	
	public void paint(Graphics g){
		super.paintComponents(g);
		w = image.getWidth(null);
		h = image.getHeight(null);
		g.drawImage(image, (panel.getWidth()-w)/2+5, (panel.getHeight()-h)/2+27,image.getWidth(null), image.getHeight(null), null);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		new_title=title.getText();
		this.dispose();
	}
	
	/**
	 * Used by the EditCellEditor to apply changes on the model
	 * @return the new title of the ImageFile object
	 * @see EditCellEditor
	 * @see ImageFile
	 */
	public String getTitle(){
		return new_title;
	}
}
