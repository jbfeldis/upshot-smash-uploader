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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A really simple JDialog to display the About box of Smash Uploader
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
		this.setUndecorated(true);
		
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
		content.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		content.setVerticalAlignment(JLabel.TOP);
	
		String txt = "<html><body><b><font size=6>Smash Uploader</font></b> is an <i>OpenSource</i> plugin for <b><font size=4>UpShot</font></b>, " +
							 "distributed by <b>Studio Melipone</b> under the GNU Lesser General Public License.<br>" +
				 			 "<br>" +
				 			 "The goal of this <i>multiplatform</i> application is to help you to <b><font size=5>drop</font></b> several <b>images</b> from " +
				 			 "<u><font size=4>your desktop</font></u> into a list of images, in order to instantly <b><font size=5>send</font></b> them to <u><font size=4>your UpShot account</font></u>.<br>" +
				 			 "<br><br><br>" +
				 			 "<font size=3>" +
				 			 	"Smash Uploader v.1.0<br>" +
				 			 	"Copyright 2008 Studio Melipone<br>" +
				 			 	"<i>GNU Lesser General Public License v.3</i>" +
				 			 "</font>" +
				 	"</body></html>";

		content.setText(txt);
	
		ok = new JButton("ok");
		ok.addActionListener(this);
		
		links = new JButton[3];
		links[0] = new JButton("http://upshotit.com");
		links[1] = new JButton("http://studiomelipone.com");
		links[2] = new JButton("http://freshfromthehive.eu");
		
		for(JButton btn : links){
			btn.setOpaque(false);
			btn.setBorderPainted(false);
			btn.addActionListener(this);
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
		gbc.insets = new Insets(10, 5, 0, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		gbl.setConstraints(content, gbc);
		
//		gbc.gridy=2;
//		gbc.anchor = GridBagConstraints.SOUTHWEST;
//		gbc.fill = GridBagConstraints.NONE;
//		gbl.setConstraints(links[0], gbc);
		
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		
		for(int i=0; i<(links.length);i++){
			gbc.gridy=i+2;
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
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		if(s.equals("ok"))
				this.dispose();
		else if(s.equals("http://upshotit.com")){
			System.out.println("-->"+s);
		}
		else if(s.equals("http://studiomelipone.com")){
			System.out.println("-->"+s);
		}
		else if(s.equals("http://freshfromthehive.eu")){
			System.out.println("-->"+s);
		}
	}
}
