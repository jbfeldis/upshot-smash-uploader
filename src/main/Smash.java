/**
 * Studio Melipone
 * June 2008
 * 
 * plugin for UpShot
 * http://www.upshotit.com
 * 
 */
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.table.TableColumn;

import table.DataModel;
import table.DeleteCellEditor;
import table.DeleteCellRender;
import table.EditCellEditor;
import table.EditCellRender;

import connect.Login;
import connect.UpConnection;


/**
 * UpShot plugin TransferHandler application
 * using a JDesktopPane to transfert from desktop to application
 * then listing it in a JTable, and send it it to distant host through secure Sockets.
 * @author Gregory Durelle
 *
 */
public class Smash extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;//useless but avoid warning ;)
	
	private JPanel pane;
	private JDesktopPane desk;
	private DataModel model;
	private JTable table;
	private JScrollPane scroll;
	private JButton sender, logger, helper;
	private TransferHandler handler;
	private UpConnection uc;
	private Login log;
	private String home, folder, file;
	private final Color background = Color.BLACK;
	private final Color foreground = Color.WHITE;

	public Smash(){
		super("UpShot SMASH");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(background);
        this.getContentPane().setForeground(foreground);
        this.setIconImage(getIcon("upshot.png").getImage());
        /*
         * TODO set undecorated to true, but
         * first move the frame with simple drag in it.
         */
        
        /* 
         * STEP 0: prepare folders and files needed
         * for user's preferences
         * */
		home = System.getProperty("user.home");
		folder = "/Smash/";
		file = "smash.config";
		File f = new File(home+folder);//create the folder at the right place
		f.mkdir();
		
		/*
		 * STEP 1:  we prepare the visual aspect of the application
		 * 
		 * send, login, help, 
		 * the desk pane, 
		 * the list of dropped images, 
		 */
        
        pane = new JPanel(){

			private static final long serialVersionUID = 1L;
//        	public void paint(Graphics g){
//				Graphics2D g2d = (Graphics2D)g;
//				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
//				super.paintComponents(g2d);	
//				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
//				Image back = getIcon("melipone.png").getImage();
//				g2d.drawImage(back, 0, g2d.getClipBounds().y, g2d.getClipBounds().width, g2d.getClipBounds().height, null);
//        	}
        };
        pane.setOpaque(false);
        desk = new JDesktopPane(){
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g){
				Image back = getIcon("d3.png").getImage();
				g.drawImage(back, 0, g.getClipBounds().y, g.getClipBounds().width, g.getClipBounds().height, null);
        	}
        };
        desk.setOpaque(false);
        		model = new DataModel();
        	table = new JTable(model);
        scroll = new JScrollPane(table);
        
        desk.setSize(650, 300);
        desk.setPreferredSize(new Dimension(650,300));
        desk.setRequestFocusEnabled(true);
        desk.requestFocus();
        
        scroll.setPreferredSize(new Dimension(650,150));
        scroll.setBackground(background);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());

		table.setAutoCreateRowSorter(false);//no sorting by clicking the headers
		table.setColumnSelectionAllowed(false);//no dragging
		table.setRowSelectionAllowed(false);
		table.setAutoscrolls(true);
		table.setDoubleBuffered(true);
		table.setShowGrid(false);
		table.setBackground(background);// used cells background
		table.setForeground(foreground);
		table.setSelectionBackground(background);// when you select a cell
		table.setSelectionForeground(foreground);
		Font font = new Font("Verdana",Font.BOLD,12);
		table.setFont(font);
		table.setTableHeader(null);
		
		/*The header of the table is quite independant
		 * so we have to customize it singly
		 * */
//		JTableHeader jth = table.getTableHeader();
//		jth.setBackground(background);
//		jth.setForeground(foreground);
//		font = new Font("Verdana",Font.BOLD,10);
//		jth.setFont(font);
//		jth.setOpaque(false);
//		jth.setResizingAllowed(false);
//		jth.enableInputMethods(false);
//		jth.setReorderingAllowed(false);
		
		/*Column Delete*/
		TableColumn column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(5);
        DeleteCellRender delCellRender = new DeleteCellRender();
        DeleteCellEditor delCellEditor = new DeleteCellEditor(model);
		column.setCellRenderer(delCellRender);
		column.setCellEditor(delCellEditor);
		column.setResizable(false);
		
		/*Column Title*/
		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(140);
		column.setResizable(false);
		
		/*Column Format*/
		column = table.getColumnModel().getColumn(2);
		column.setPreferredWidth(80);
		column.setResizable(false);
		
		/*Column Size*/
		column = table.getColumnModel().getColumn(3);
		column.setPreferredWidth(70);
		column.setResizable(false);
		
		/*Column Edit*/
		column = table.getColumnModel().getColumn(4);
		column.setPreferredWidth(5);
		column.setResizable(false);
        EditCellRender editCellRender = new EditCellRender();
        EditCellEditor editCellEditor = new EditCellEditor(model);
		column.setCellRenderer(editCellRender);
		column.setCellEditor(editCellEditor);
        
		/* Now let's display our elements as we want */
		
        logger = new JButton(Smash.getIcon("user.png"));
        	logger.setActionCommand("login");
        	logger.addActionListener(this);
        	logger.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        	logger.setOpaque(false);
        	logger.setBackground(background);
        helper = new JButton(Smash.getIcon("help.png"));
        	helper.setActionCommand("help");
        	helper.addActionListener(this);
        	helper.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        	helper.setOpaque(false);
        	helper.setBackground(background);
        sender = new JButton("SEND");
        	sender.addActionListener(this);
        	sender.setEnabled(false);
        	sender.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        	sender.setOpaque(false);
        	sender.setBackground(background);
        	sender.setForeground(foreground);
        	
        /*
         * Wiredly ugly coded customized header
         * */
        JLabel title,format, size;
        title=new JLabel("Title                           ");
        	title.setBackground(background);
        	title.setForeground(foreground);
        format=new JLabel("          Format                       ");
        	format.setBackground(background);
        	format.setForeground(foreground);
        size=new JLabel("Size                              ");
        	size.setBackground(background);
        	size.setForeground(foreground);
        JPanel myHead = new JPanel();
        myHead.setFont(font);
        myHead.setBackground(background);
        myHead.setForeground(foreground);
        myHead.add(title);
        myHead.add(format);
        myHead.add(size);
        
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=3;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.NORTH;
        gbl.setConstraints(desk, gbc);
        
        gbc.gridy=1;
        gbc.gridx=2;
        gbc.anchor=GridBagConstraints.CENTER;
        gbl.setConstraints(myHead, gbc);
        
        gbc.gridy=2;
        gbc.gridx=0;
        gbl.setConstraints(scroll, gbc);
        
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.ipady=10;
        gbc.ipadx=10;
        gbc.insets=new Insets(0,2,1,1);
        gbc.anchor=GridBagConstraints.SOUTHWEST;
        gbl.setConstraints(logger, gbc);
        
        gbc.gridx=1;
        gbc.insets=new Insets(0,1,1,1);
        gbl.setConstraints(helper, gbc);
        
        gbc.gridx=2;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.ipady=10;
        gbc.insets=new Insets(0,1,1,2);
        gbl.setConstraints(sender, gbc);
        
        pane.setLayout(gbl);
        pane.add(desk);
        pane.add(myHead);
        pane.add(scroll);
        pane.add(logger);
        pane.add(helper);
        pane.add(sender);

        this.add(pane);
        this.pack();
        this.setLocationRelativeTo(null);
        
        /*
         * STEP 2 : The transferHandler
         * 
         * One of the fundamental functionnality of the application is this transferHandler
         */
        handler = new TransferHandler() {

    		private static final long serialVersionUID = 1L;

    		public boolean canImport(TransferHandler.TransferSupport support) {
                if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    return false;
                }
                support.setDropAction(COPY);
                return true;
            }

            @SuppressWarnings("unchecked")
			public boolean importData(TransferHandler.TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }
                
                Transferable t = support.getTransferable();
                
                try {
                	DataFlavor df = DataFlavor.javaFileListFlavor;
                	List<File> list = (List<File>)t.getTransferData(df);//list are the items we have just dropped
                	
                    for (File f : list) {
                    	ImageFile imf = new ImageFile(f);
                    	model.add(imf);
                    	table.repaint();
                    }
                    
                    if (list.size()>0)
                    	sender.setEnabled(true);
                    
                } catch (UnsupportedFlavorException e) {
                    return false;
                } catch (IOException e) {
                    return false;
                }
                return true;
            }
        };
        
        this.setTransferHandler(handler);// FIXME choose if drop is possible everywhere or not
        desk.setTransferHandler(handler);
    	this.setVisible(true);
        
		/*
		 * STEP 3 : Logging in
		 * Retrieve the user's id by sending its email+password
		 * throught simple GET HTTP request
		 * The id is needed for future request paths
		 */
		uc = new UpConnection();
		
		/*
		 * check if login information already given in the past
		 * and load it if yes
		 * */

		File fconfig = new File(home+folder+file);
		if(fconfig.exists())
			load();
		else
			log = new Login(this, uc);
		
		if(log.getAnswer()>0){
			save();
		}
		
		/*Add some stuff in the deskpane*/
		desk.add(new JLabel("Drop your images here"));
	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Smash up = new Smash();
            	up.repaint();
            }
        });
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		if(s.equals("SEND")){
			if(model.getRowCount()==0)
				JOptionPane.showMessageDialog(this, "Nothing to send !\nAdd image files and try again.", "Send what ?", JOptionPane.WARNING_MESSAGE);
			else{
				
				/* 
				 * DO THE HTTP JOB
				 * send each file with its informations to create drafts upshots
				 * */
				
				
				Vector<ImageFile> imClone = (Vector<ImageFile>)model.getImages().clone();
				for(ImageFile imf : imClone){
					uc.setup("users/"+log.getAnswer()+"/upshots.xml");
					uc.sendData(imf);
					// TODO progressbar 
					/*
					 * TODO not deleting, prefer finding a nice way to tell the user that it as been succesfully sended
					 * 
					 */
					model.remove(imf);
				}
				sender.setEnabled(false);
			}
		}
		else if(s.equals("login")){
			
			sender.setEnabled(false);
			log.setVisible(true);
			
			if(log.getAnswer()>0){
				save();
			}
		}
		else if(s.equals("help")){
			JOptionPane.showMessageDialog(this, "about text\nwill be dark backgrounf with real component", "About", JOptionPane.INFORMATION_MESSAGE, getIcon("logo.png"));
		}
	}
	
	/**
	 * Save login information in serialized file
	 */
	public void save(){// TODO encrypt serialized object (JCE)
		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		try {
			fos = new FileOutputStream(home+folder+file);
			out = new ObjectOutputStream(fos);
			out.writeObject(log);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load serialized file
	 * and recreate login object thanks to it
	 */
	public void load(){
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try{
			fis = new FileInputStream(home+folder+file);
			in = new ObjectInputStream(fis);
		    log = (Login)in.readObject();
		    in.close();
		    }
		catch(IOException ex){
		    ex.printStackTrace();
		}
		catch(ClassNotFoundException ex){
		   ex.printStackTrace();
		}
	    log.setConnectionConfig(uc);
	}
	
	/**
	 * Helping method to get an image
	 * @return ImageIcon using the package path
	 */
	public static ImageIcon getIcon(String name){
		URL url = Smash.class.getResource("/images/"+name);
		return new ImageIcon(url);
	}
}
