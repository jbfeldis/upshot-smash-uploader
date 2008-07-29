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
import java.util.Locale;
import java.util.ResourceBundle;

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
	private JPanel top, flags;
	private JButton ok, links[], fr, en;
	private Locale locale = Locale.getDefault();
	private ResourceBundle msg;
	private String languages;
	private Smash origin;

	public About(JFrame origin){
		super(origin, true);
		this.origin=(Smash)origin;
		this.setSize(new Dimension(680,340));
        this.getContentPane().setBackground(Color.decode("#CCCCCC"));
        this.getContentPane().setForeground(Color.WHITE);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setUndecorated(true);
		
		languages = "doc/languages/trans";
		
		ImageIcon plainupshot = Smash.getIcon("upshot.png");
		ImageIcon reallife = Smash.getIcon("reallife.png");
		ImageIcon studiomelipone = Smash.getIcon("studiomelipone.png");
		ImageIcon lfr = Smash.getIcon("fr.png");
		ImageIcon len = Smash.getIcon("enus.png");
		
		Cursor hand = new Cursor(Cursor.HAND_CURSOR);
		
		flags = new JPanel();
			flags.setBackground(Color.BLACK);
			flags.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
			
		fr = new JButton(lfr);
			fr.setActionCommand("fr");
			fr.addActionListener(this);
			fr.setOpaque(false);
			fr.setBorder(BorderFactory.createEmptyBorder());
			fr.setFocusable(false);
			fr.setCursor(hand);
		en = new JButton(len);
			en.setActionCommand("en");
			en.addActionListener(this);
			en.setOpaque(false);
			en.setBorder(BorderFactory.createEmptyBorder());
			en.setFocusable(false);
			en.setCursor(hand);
			
		GridBagLayout flagbl = new GridBagLayout();
		GridBagConstraints flagbc = new GridBagConstraints();
		flags.setLayout(flagbl);
		
		flagbc.gridx=0;
		flagbc.gridy=0;
		flagbc.fill=GridBagConstraints.NONE;
		flagbc.anchor=GridBagConstraints.NORTHWEST;
		flagbc.insets=new Insets(5, 10,5,2);
		flagbl.setConstraints(fr, flagbc);
		
		flagbc.gridx=1;
		flagbc.insets=new Insets(5,0,5,this.getWidth()+5);
		flagbl.setConstraints(en, flagbc);
		
		flags.add(fr);
		flags.add(en);
		
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
		topgbc.insets = new Insets(0, 30, 1, 5);
		topgbc.anchor=GridBagConstraints.NORTHWEST;
		topgbl.setConstraints(real, topgbc);
		
		top.add(upshot);
		top.add(melipone);
		top.add(real);
		
		content = new JLabel();
		content.setPreferredSize(new Dimension(this.getWidth(),200));
		content.setVerticalAlignment(JLabel.TOP);
	
		ok = new JButton("ok");
		ok.addActionListener(this);
		ok.setBackground(this.getContentPane().getBackground());
		
		links = new JButton[4];// !!!! Remind the size to the number of elements ;)
		links[0] = new JButton("<html><body><u>http://upshotit.com</u></body></html>");
			links[0].setActionCommand("http://upshotit.com");
		links[1] = new JButton("<html><body><u>http://studiomelipone.com</u></body></html>");
			links[1].setActionCommand("http://studiomelipone.com");
		links[2] = new JButton("<html><body><u>http://freshfromthehive.eu</u></body></html>");
			links[2].setActionCommand("http://freshfromthehive.eu");
		links[3] = new JButton(Smash.getIcon("email.png"));
			links[3].setActionCommand("contactus");
		
		Font font = new Font("Verdana",Font.ITALIC, 11);
		
		for(JButton btn : links){
			btn.addActionListener(this);
			btn.setOpaque(false);
			btn.setBorderPainted(false);
			btn.setFocusable(false);
			btn.setFont(font);
			btn.setCursor(hand);
			btn.setBackground(this.getContentPane().getBackground());
		}
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.getContentPane().setLayout(gbl);
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=2;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		gbl.setConstraints(flags, gbc);
		
		gbc.gridy=1;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbl.setConstraints(top, gbc);
		
		gbc.gridy=2;
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		gbl.setConstraints(content, gbc);
		
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 5, 0, 5);
		gbc.gridwidth=1;
		
		for(int i=0; i<(links.length);i++){
			gbc.gridy=i+3;
			if(links[i].getActionCommand().equals("contactus"))
				gbc.insets = new Insets(10, 20, 10, 5);
			gbl.setConstraints(links[i], gbc);
		}
		
		gbc.gridy=links.length+2;
		gbc.gridx=1;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 10, 10);
		gbl.setConstraints(ok, gbc);
		
		this.add(flags);
		this.add(top);
		this.add(content);
		for(JButton btn : links){
			this.add(btn);
		}
		this.add(ok);
		
		this.pack();
		this.setLocationRelativeTo(origin);
	}
	
	/**
	 * Set the language resource as given in Smash class
	 * @param rb the ResourceBundle representing the language
	 */
	public void setResourceBundle(ResourceBundle rb){
		msg=rb;
		locale=rb.getLocale();
		this.displayLanguage();
	}
	
	/**
	 * Redraw all labels and buttons in the appropriate language
	 */
	private void displayLanguage(){
		links[3].setText("<html><body><u>"+msg.getString("contactus")+"</u></body><html>");
		String txt="";
		if(locale.getLanguage().equals("en")){
			txt = "<html><body><b><font size=6>Smash Uploader</font></b> is an <i>OpenSource</i> plugin for <b><font size=4>UpShot</font></b>, " +
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
			}
			else if(locale.getLanguage().equals("fr")){
				txt = "<html><body><b><font size=6>Smash Uploader</font></b> est un plugin <i>OpenSource</i> pour <b><font size=4>UpShot</font></b>, " +
								 "distribu&eacute; par <b>Studio Melipone</b> sous la licence : GNU Lesser General Public License.<br>" +
								 "<br>" +
								 "Le but de cette application <i>multi-platformes</i> est de vous aider &agrave; <b><font size=5>d&eacute;poser</font></b> plusieur <b>images</b> depuis " +
								 "<u><font size=4>votre bureau</font></u> jusque dans une liste d'images, pour les <b><font size=5>envoyer</font></b> instantan&eacute;ment sur <u><font size=4>votre compte UpShot</font></u>.<br>" +
								 "<br><br>" +
								 "<font size=3>" +
								 	"Smash Uploader v.1.0<br>" +
								 	"Copyright 2008 Studio Melipone<br>" +
								 	"<i>GNU Lesser General Public License v.3</i>" +
								 "</font>" +
					  "</body></html>";
			}
		content.setText(txt);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		if(s.equals("ok"))
				this.dispose();
		else if(s.equals("fr")){
			locale = new Locale("fr","FR");
			msg = ResourceBundle.getBundle(languages, locale);
			this.displayLanguage();
			origin.setResourceBundle(msg);
		}
		else if(s.equals("en")){
			locale = new Locale("en","US");
			msg = ResourceBundle.getBundle(languages, locale);
			this.displayLanguage();
			origin.setResourceBundle(msg);
		}
		else {
			try {
				if ( Desktop.isDesktopSupported() ) {//Test if the class Desktop is supported on the OS
					Desktop desktop = Desktop.getDesktop();
					
					if(s.equals("contactus")){
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
