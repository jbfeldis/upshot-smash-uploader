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
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A nice JDialog to display the About box of Smash Uploader
 * resume the Smash Uploader goal
 * remind licence informations
 * give links to UpShot, StudioMelipone, and our blog
 * give mail contact
 * @author Gregory Durelle
 *
 */
public class About extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel upshot, real, melipone, content;
	private JPanel top;
	private JButton ok, links[];

	public About(JFrame origin){
		super(origin, true);
		this.setSize(new Dimension(680,340));
        this.getContentPane().setBackground(Color.decode("#656565"));
        this.getContentPane().setForeground(Color.WHITE);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		ImageIcon plainupshot = Smash.getIcon("upshot.png");
		ImageIcon reallife = Smash.getIcon("reallife.png");
		ImageIcon studiomelipone = Smash.getIcon("studiomelipone.png");
		
		top = new JPanel();
			top.setBackground(Color.decode("#333333"));
			top.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
		
		upshot = new JLabel(plainupshot);
			upshot.setOpaque(false);
		real = new JLabel(reallife);
			real.setOpaque(false);
		melipone = new JLabel(studiomelipone);
			melipone.setOpaque(false);
		
		GridBagLayout topgbl = new GridBagLayout();
		GridBagConstraints topgbc = new GridBagConstraints();
		top.setLayout(topgbl);
		
		topgbc.gridx = 0;
		topgbc.gridy = 0;
		topgbc.anchor = GridBagConstraints.NORTHWEST;
		topgbc.fill = GridBagConstraints.NONE;
		topgbc.insets = new Insets(1, 1, 0, 1);
		topgbl.setConstraints(upshot, topgbc);
		
		topgbc.gridx=1;
		topgbc.gridheight=2;
		topgbc.gridwidth=2;
		topgbc.insets = new Insets(10, 150, 1, 10);
		topgbc.anchor=GridBagConstraints.EAST;
		topgbl.setConstraints(melipone, topgbc);
		
		topgbc.gridy=1;
		topgbc.gridx=0;
		topgbc.gridheight=1;
		topgbc.gridwidth=1;
		topgbc.insets = new Insets(0, 40, 1, 5);
		topgbc.anchor=GridBagConstraints.NORTHWEST;
		topgbl.setConstraints(real, topgbc);
		
		top.add(upshot);
		top.add(melipone);
		top.add(real);
		
		content = new JLabel();
		content.setPreferredSize(new Dimension(this.getWidth(),200));
		content.setVerticalAlignment(JLabel.TOP);
	
		String txt = "<html><body><b><font size=6>Smash Uploader</font></b> is an <i>OpenSource</i> plugin for <b><font size=4>UpShot</font></b>, " +
							 "distributed by <b>Studio Melipone</b> under the GNU Lesser General Public License.<br>" +
				 			 "<br>" +
				 			 "The goal of this <i>multiplatform</i> application is to help you to <b><font size=5>drop</font></b> several <b>images</b> from " +
				 			 "<u><font size=4>your desktop</font></u> into a list of images, in order to instantly <b><font size=5>send</font></b> them to <u><font size=4>your UpShot account</font></u>.<br>" +
				 			 "<br><br>" +
				 			 "<font size=3>" +
				 			 	"Smash Uploader v.1.0<br>" +
				 			 	"Copyright 2008 Studio Melipone<br>" +
				 			 	"<i>GNU Lesser General Public License v.3</i>" +
				 			 "</font>" +
				 	"</body></html>";

		content.setText(txt);
	
		ok = new JButton("ok");
		ok.addActionListener(this);
		
		links = new JButton[4];// !!!! Remind the size to the number of elements ;)
		links[0] = new JButton("<html><body><u>http://upshotit.com</u></body></html>");
		links[1] = new JButton("<html><body><u>http://studiomelipone.com</u></body></html>");
		links[2] = new JButton("<html><body><u>http://freshfromthehive.eu</u></body></html>");
		links[3] = new JButton("contactez-nous", Smash.getIcon("email.png"));
		
		Font font = new Font("Verdana",Font.ITALIC, 11);
		
		for(JButton btn : links){
			btn.addActionListener(this);
			btn.setOpaque(false);
			btn.setBorderPainted(false);
			btn.setFocusable(false);
			btn.setFont(font);
			Cursor hand = new Cursor(Cursor.HAND_CURSOR);
			btn.setCursor(hand);
		}
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.getContentPane().setLayout(gbl);
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbl.setConstraints(top, gbc);
		
		gbc.gridy=1;
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		gbl.setConstraints(content, gbc);
		
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 5, 0, 5);
		
		for(int i=0; i<(links.length);i++){
			gbc.gridy=i+2;
			if(links[i].getText().equals("contactez-nous"))
				gbc.insets = new Insets(0, 20, 2, 5);
			gbl.setConstraints(links[i], gbc);
		}
		
		gbc.gridy=links.length+2;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 10, 10);
		gbl.setConstraints(ok, gbc);
		
		this.add(top);
		this.add(content);
		for(JButton btn : links){
			this.add(btn);
		}
		this.add(ok);
		
		this.pack();
		this.setLocationRelativeTo(origin);
		this.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		if(s.equals("ok"))
				this.dispose();
		else {
			try {
				if ( Desktop.isDesktopSupported() ) {//Test if the class Desktop is supported on the OS
					Desktop desktop = Desktop.getDesktop();
					
					if(s.equals("contactez-nous")){
						if(desktop.isSupported(Desktop.Action.MAIL))// test if the mail method is also supported
							desktop.mail(new URI("mailto:contact@studiomelipone.eu"));
					}
					else if (desktop.isSupported(Desktop.Action.BROWSE)) {//test if the browse method is also supported
						desktop.browse(new URI(s));
					}
				}
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(Smash.getFrames()[0], "About.actionPerformed() MalformedURException : "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(Smash.getFrames()[0], "About.actionPerformed() IOException : "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (URISyntaxException e) {
				JOptionPane.showMessageDialog(Smash.getFrames()[0], "About.actionPerformed() URISyntaxException : "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
