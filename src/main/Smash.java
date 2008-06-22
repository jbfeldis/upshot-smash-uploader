/**
 * Studio Melipone
 * June 2008
 * 
 * plugin for UpShot
 * http://www.upshotit.com
 * 
 */
package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import table.ButtonCellEditor;
import table.ButtonCellRender;
import table.DataModel;


import connect.UpConnection;


/**
 * UpShot ® plugin TransferHandler application
 * using a JDesktopPane to transfert from desktop to application
 * then listing it in a JTable, and send it it to distant host trhough secure Sockets.
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
	private JButton sender;
	private TransferHandler handler;

	public Smash(){
		
		/*
		 * STEP 1:  we prepare the visual aspect of the applcation
		 * 
		 * the desk pane and the button to send the dropped images
		 */
		
		super("UpShot SMASH");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        pane = new JPanel();
        desk = new JDesktopPane();
        model = new DataModel();
        table = new JTable(model);
        scroll = new JScrollPane(table);
        
        desk.setSize(300, 250);
        desk.setRequestFocusEnabled(true);
        desk.requestFocus();
        
        scroll.setPreferredSize(new Dimension(300,100));

		table.setAutoCreateRowSorter(false);
		table.setCellSelectionEnabled(true);
		table.setFillsViewportHeight(true);
		table.setAutoscrolls(true);
		table.setDoubleBuffered(true);
		table.setBackground(Color.WHITE);
		table.setGridColor(Color.WHITE);
		table.setEnabled(true);
		
        TableCellRenderer cellRender = new ButtonCellRender();
        TableCellEditor cellEditor = new ButtonCellEditor(new JCheckBox());
		
		TableColumn column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(5);
		column.setCellRenderer(cellRender);
		column.setCellEditor(cellEditor);
		
		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(145);
		
		column = table.getColumnModel().getColumn(2);
		column.setPreferredWidth(75);
		
		column = table.getColumnModel().getColumn(3);
		column.setPreferredWidth(75);
        
        sender = new JButton("SEND");
        sender.addActionListener(this);
        
        BorderLayout bl = new BorderLayout();
        pane.setLayout(bl);
        pane.add(desk, BorderLayout.NORTH);
        pane.add(scroll, BorderLayout.CENTER);
        pane.add(sender, BorderLayout.SOUTH);

        this.add(pane);
        pack();
        this.setLocationRelativeTo(null);
        
        /*
         * STEP 2 : The transferHandler
         * 
         * the whole functionnality of the application is resume by this transferHandler
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

                /*
                 * TODO list comporte uniquement les item droppée actuellement
                 * imageList comporte la totalité
                 * */
                
                try {
                	DataFlavor df = DataFlavor.javaFileListFlavor;
                	List<File> list = (List<File>)t.getTransferData(df);
                	
                    for (File f : list) {
                    	model.add(f);
                    	table.repaint();
                    }
                    
                } catch (UnsupportedFlavorException e) {
                    return false;
                } catch (IOException e) {
                    return false;
                }
                return true;
            }
        };
        
        /*
         * STEP 3 : enable the transfert functionnality
         */
        
        this.setTransferHandler(handler);
        desk.setTransferHandler(handler);
	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Smash up = new Smash();
            	up.setVisible(true);
            }
        });
	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		if(s.equals("SEND")){
			if(model.getRowCount()==0)
				JOptionPane.showMessageDialog(this, "Nothing to send !\nAdd image files and try again.", "Send what ?", JOptionPane.WARNING_MESSAGE);
			else{
				sender.setEnabled(false);
				
				/*DO THE HTTP JOB*/
				//try {
					UpConnection uc = new UpConnection();
					uc.getUserInfos();

				sender.setEnabled(true);
			}
		}
	}
}
