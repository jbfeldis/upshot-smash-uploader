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
package connect;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Dialog box used to register login and password/token in order to get the user's id
 * once the id retrieved, it will be used to compose the path to wanted resources.
 * This object is serializable to let the user register its login and token, thus, he wont have 
 * give it everytime.
 * @author Gregory Durelle
 *
 */
public class Login extends JDialog implements ActionListener, Serializable {

	private static final long serialVersionUID = 3399486907330854821L;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	private JLabel loginlab, passwdlab, message;
	private JTextField loginbox;
	private JPasswordField passwdbox;
	private JButton ok, cancel;
	private String login, passwd;
	private char [] pass;
	private transient UpConnection uc;//need to be transient because HttpURLConnection can't be serialized 
	private Integer answer;
	
	/**
	 * Constructor of logging dialog box
	 * @param origin will always be an instance of Smash class
	 * @param uc instane of UpConnection class (created in Smash)
	 */
	public Login(JFrame origin, UpConnection uc){
		super(origin, "Authentication");
		this.setModal(true);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.decode("#656565"));
		this.getContentPane().setForeground(Color.WHITE);
		
		answer=0;
		this.uc=uc;
		login=new String();
		passwd=new String();
		
		loginbox = new JTextField(10);
		passwdbox= new JPasswordField(10);
			passwdbox.setActionCommand("OK");
			passwdbox.addActionListener(this);
		ok = new JButton("OK");
			ok.addActionListener(this);
			ok.setOpaque(false);
		cancel = new JButton("Cancel");
			cancel.addActionListener(this);
			cancel.setOpaque(false);
		
		loginlab = new JLabel("E-mail :");
			loginlab.setLabelFor(loginbox);
		passwdlab = new JLabel("Password :");
			passwdlab.setLabelFor(passwdbox);
		message = new JLabel();
			Font font = new Font("Verdana",Font.BOLD,10);
			message.setFont(font);

		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		Insets ins = new Insets(2, 3, 0, 3);
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.insets=ins;
		gbc.gridwidth=1;
		gbc.ipady=20;
		gbc.anchor=GridBagConstraints.NORTHEAST;
		gbl.setConstraints(loginlab, gbc);
		
		gbc.gridx=1;
		gbc.gridwidth=2;
		gbc.ipady=0;
		gbc.anchor=GridBagConstraints.NORTHWEST;
		gbl.setConstraints(loginbox, gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=1;
		gbc.ipady=5;
		gbc.anchor=GridBagConstraints.SOUTHEAST;
		gbl.setConstraints(passwdlab, gbc);
		
		gbc.gridx=1;
		gbc.gridwidth=2;
		gbc.ipady=0;
		gbc.anchor=GridBagConstraints.SOUTHWEST;
		gbl.setConstraints(passwdbox, gbc);
		
		gbc.gridy=2;
		ins = new Insets(0, 3, 0, 0);
		gbc.insets=ins;
		gbc.anchor=GridBagConstraints.CENTER;
		gbl.setConstraints(message, gbc);
		
		gbc.gridx=0;
		gbc.gridy=3;
		ins = new Insets(2, 3, 5, 3);
		gbc.insets=ins;
		gbc.ipady=0;
		gbc.anchor=GridBagConstraints.SOUTHWEST;
		gbl.setConstraints(cancel, gbc);
		
		gbc.gridx=2;
		gbc.gridwidth=1;
		gbc.anchor=GridBagConstraints.SOUTHEAST;
		gbl.setConstraints(ok, gbc);
		
		this.getContentPane().setLayout(gbl);
		this.add(loginlab);
		this.add(loginbox);
		this.add(passwdlab);
		this.add(passwdbox);
		this.add(message);
		this.add(cancel);
		this.add(ok);
		
		init();
		this.pack();
		this.setLocationRelativeTo(origin);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		if(s.equals("OK")){
			login=new String();
			passwd=new String();
			answer=0;
			
			login=loginbox.getText();
			pass = new char[128];
			pass = passwdbox.getPassword();
			
			for(char c : pass)
				passwd+=c;
			pass=null;
			
			if(login.isEmpty() || passwd.isEmpty()){
				message.setForeground(Color.decode("#FF6600"));
				message.setText("Some fields are empty");
				this.pack();
			}
			else{
				uc.setUser(login, passwd);

				if((answer=uc.getId())<=0){
					init();
					message.setForeground(Color.RED);
					message.setText("Wrong login or password");
					this.pack();
				} else {
					message.setText("");
					this.dispose();
				}
			}
		}
		else if (s.equals("Cancel")){
			this.dispose();
		}
	}
	
	/**
	 * After unserialization of login params, the Login object thus created
	 * need to know the UpConnection params (because it is not serialized with the Login object)
	 * @param uc The current UpConnection 
	 */
	public void setConnectionConfig(UpConnection uc){
		this.uc=uc;
		uc.setUser(login, passwd);
	}
	
	/**
	 * Clear all fields. 
	 * Usefull for initialization
	 */
	public void init(){
		loginbox.setText("");
		passwdbox.setText("");
		login = new String("");
		passwd = new String("");
		message.setText("");
		pass = new char[128];
	}
	
	/**
	 * Used to retrieve either the user id, or the error code.
	 * @return the user's id or the error code
	 */
	public int getAnswer(){
		return answer;
	}
}
