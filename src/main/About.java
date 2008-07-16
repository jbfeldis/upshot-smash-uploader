package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class About extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel upshot, real, melipone, content;
	private JButton ok;

	public About(JFrame origin){
		super(origin, true);
		this.setSize(680, 340);
		this.setBackground(Color.DARK_GRAY);
		this.setForeground(Color.WHITE);
		
		ImageIcon plainupshot = Smash.getIcon("upshot.png");
		ImageIcon reallife = Smash.getIcon("reallife.png");
		ImageIcon studiomelipone = Smash.getIcon("studiomelipone.png");
		
		upshot = new JLabel(plainupshot);
			upshot.setOpaque(false);
		real = new JLabel(reallife);
			real.setOpaque(false);
		melipone = new JLabel(studiomelipone);
			melipone.setOpaque(false);
		content = new JLabel();
			content.setOpaque(false);
			content.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
			
		
		String txt = "<html><body><b>Smash Uploader</b> is an OpenSource plugin for UpShot.<br> " +
								 "Distributed by Studio Melipone under GNU LGPL Licence.<br>" +
					 			 "<br>" +
					 			 "Smash Uploader v.1.0 <br>" +
					 			 "http://upshotit.com" +
					 "</body></html>";
		
		content.setText(txt);
		
		ok = new JButton("ok");
		ok.setOpaque(false);
		ok.addActionListener(this);
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.getContentPane().setLayout(gbl);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(1, 1, 0, 1);
		gbl.setConstraints(upshot, gbc);
		
		gbc.gridx=1;
		gbc.gridheight=2;
		gbc.gridwidth=2;
		gbc.insets = new Insets(10, 120, 1, 5);
		gbc.anchor=GridBagConstraints.EAST;
		gbl.setConstraints(melipone, gbc);
		
		gbc.gridy=1;
		gbc.gridx=0;
		gbc.gridheight=1;
		gbc.gridwidth=1;
		gbc.insets = new Insets(0, 40, 1, 5);
		gbc.anchor=GridBagConstraints.NORTHWEST;
		gbl.setConstraints(real, gbc);
		
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridheight=1;
		gbc.gridwidth=3;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor=GridBagConstraints.CENTER;
		gbl.setConstraints(content, gbc);
		
		gbc.gridx=2;
		gbc.gridy=3;
		gbc.gridwidth=1;
		gbc.anchor=GridBagConstraints.SOUTHEAST;
		gbl.setConstraints(ok, gbc);
		
		this.add(upshot);
		this.add(melipone);
		this.add(real);
		this.add(content);
		this.add(ok);
		
		this.pack();
		this.setLocationRelativeTo(origin);
		this.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		this.dispose();
	}
}
