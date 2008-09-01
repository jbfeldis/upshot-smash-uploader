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
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private JLabel loginlab, tokenlab, message;
	private JTextField loginbox;
	private JPasswordField tokenbox;
	private JButton ok, cancel;
	private JCheckBox remember;
	private String login, token;
	private char [] pass;
	private transient UpConnection uc;//need to be transient because HttpURLConnection can't be serialized 
	private transient ResourceBundle msg;//can't be serialized either
	private Integer answer;

	public boolean hasMemory() {
		return remember.isSelected();
	}

	/**
	 * Constructor of logging dialog box
	 * @param origin will always be an instance of Smash class
	 * @param uc instane of UpConnection class (created in Smash)
	 */
	public Login(JFrame origin, UpConnection uc){
		super(origin, true);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.decode("#656565"));
		this.getContentPane().setForeground(Color.WHITE);
		
		answer=0;
		this.uc=uc;
		login=new String();
		token=new String();
		
		loginbox = new JTextField(10);
		tokenbox= new JPasswordField(15);
			tokenbox.setActionCommand("OK");
			tokenbox.addActionListener(this);
		ok = new JButton("OK");
			ok.addActionListener(this);
			ok.setOpaque(false);
			ok.setBackground(this.getContentPane().getBackground());
		cancel = new JButton();
			cancel.setActionCommand("Cancel");
			cancel.addActionListener(this);
			cancel.setOpaque(false);
			cancel.setBackground(this.getContentPane().getBackground());
		remember = new JCheckBox("Remember me", false);
			remember.setOpaque(false);
			remember.setBackground(this.getContentPane().getBackground());
		
		loginlab = new JLabel("E-mail :");
			loginlab.setLabelFor(loginbox);
		tokenlab = new JLabel("Token :");
			tokenlab.setLabelFor(tokenbox);
		message = new JLabel();
			Font font = new Font("Verdana",Font.BOLD,11);
			message.setFont(font);

		init();
			
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		
		gbc.gridx=0;
		gbc.gridy=0;
		Insets ins = new Insets(5, 5, 2, 0);
		gbc.insets=ins;
		gbc.gridwidth=3;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL ;
		gbl.setConstraints(message, gbc);
		
		gbc.gridy=1;
		ins = new Insets(0, 5, 0, 0);
		gbc.insets=ins;
		gbc.gridwidth=1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE ;
		gbl.setConstraints(loginlab, gbc);
		
		gbc.gridx=1;
		gbc.gridwidth=2;
		ins = new Insets(0, 0, 0, 2);
		gbc.insets=ins;
		gbc.anchor=GridBagConstraints.WEST;
		gbl.setConstraints(loginbox, gbc);
		
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=1;
		ins = new Insets(0, 5, 0, 0);
		gbc.insets=ins;
		gbc.anchor=GridBagConstraints.EAST;
		gbl.setConstraints(tokenlab, gbc);
		
		gbc.gridx=1;
		gbc.gridwidth=2;
		ins = new Insets(0, 0, 0, 2);
		gbc.insets=ins;
		gbc.anchor=GridBagConstraints.WEST;
		gbl.setConstraints(tokenbox, gbc);
		
		gbc.gridy=3;
		gbc.gridx=0;
		ins = new Insets(0, 5, 0, 0);
		gbc.insets=ins;
		gbc.anchor=GridBagConstraints.EAST;
		gbl.setConstraints(remember, gbc);
		
		gbc.gridx=0;
		gbc.gridy=4;
		ins = new Insets(0, 2, 2, 0);
		gbc.insets=ins;
		gbc.anchor=GridBagConstraints.SOUTHWEST;
		gbl.setConstraints(cancel, gbc);
		
		gbc.gridx=2;
		ins = new Insets(0, 0, 2, 2);
		gbc.insets=ins;
		gbc.anchor=GridBagConstraints.SOUTHEAST;
		gbl.setConstraints(ok, gbc);
		
		this.getContentPane().setLayout(gbl);
		this.add(message);
		this.add(loginlab);
		this.add(loginbox);
		this.add(tokenlab);
		this.add(tokenbox);
		this.add(remember);
		this.add(cancel);
		this.add(ok);
		
		init();
		this.pack();
		this.setLocationRelativeTo(origin);
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
	public void displayLanguage(){
		this.setTitle(msg.getString("login_title"));
		cancel.setText(msg.getString("cancel"));
	}
	
	/**
	 * After unserialization of login params, the Login object thus created
	 * need to know the UpConnection params (because it is not serialized with the Login object)
	 * @param uc The current UpConnection 
	 */
	public void setConnectionConfig(UpConnection uc){
		this.uc=uc;
		uc.setUser(login, token);
	}
	
	/**
	 * Clear all fields. 
	 * Usefull for initialization
	 */
	public void init(){
		loginbox.setText("");
		tokenbox.setText("");
		login = new String("");
		token = new String("");
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
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		if(s.equals("OK")){
			login=new String();
			token=new String();
			answer=0;
			
			login=loginbox.getText();
			pass = new char[128];
			pass = tokenbox.getPassword();
			
			for(char c : pass)
				token+=c;
			pass=null;
			
			if(login.isEmpty() || token.isEmpty()){
				message.setForeground(Color.decode("#FF6600"));
				message.setText(msg.getString("warn_empty"));
				this.pack();
			}
			else{
				uc.setUser(login, token);

				if((answer=uc.getId())<=0){
					init();
					message.setForeground(Color.RED);
					message.setText(msg.getString("err_wrong"));
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
}
