/**
 * Studio Melipone
 * June 2008
 * 
 * plugin for UpShot
 * http://www.upshotit.com
 * 
 */
package table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import main.Smash;

/**
 * Create a special cell render for the first column of Smash images table
 * This cell render let us display a JButton associated with the given file in order
 * to permit its deletion from the tablelist
 * @author Gregory Durelle
 *
 */
public class DeleteCellRender extends JButton implements TableCellRenderer , Runnable{

	private static final long serialVersionUID = 1L;
	int row;
	JTable table;
	
	public DeleteCellRender(){
	    this.setIcon(Smash.getIcon("cross.png"));
		this.setBorderPainted(false);
		this.setForeground(Color.WHITE);
		this.setOpaque(false);
		this.setFocusable(false);
	}
	
	public Component getTableCellRendererComponent( JTable table, Object value,
													boolean selected, boolean focused, 
													int row, int column) {
		this.setBackground(table.getBackground());
		
		if(((DataModel)table.getModel()).getImageFile(row).isSent()){
			this.setIcon(Smash.getIcon("tick.png"));
			this.setToolTipText(null);
		}
		else if(((DataModel)table.getModel()).getImageFile(row).isSending()){
			this.setIcon(Smash.getIcon("loader.gif"));
			this.setToolTipText(null);
			this.row=row;
			this.table=table;
	        Thread t = new Thread(this);
	        t.start();
		}
		else{
			this.setIcon(Smash.getIcon("cross.png"));
			this.setToolTipText("Delete this image from the list");
		}
		return this;
	}

	public void run() {
		while(true){
			repaint();
		}
	}
}
