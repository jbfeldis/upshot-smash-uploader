/**
 * Studio Melipone
 * June 2008
 * 
 * plugin for UpShot
 * http://www.upshotit.com
 * 
 */
package table;

import java.awt.Component;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * Create a special cell render for the first column of Smash images table
 * This cell render let us display a JButton associated with the given file in order
 * to permit its deletion from the tablelist
 * @author Gregory Durelle
 *
 */
public class ButtonCellRender extends JButton implements TableCellRenderer{

	private static final long serialVersionUID = 1L;
	private ImageIcon icon=null;
	
	public ButtonCellRender(){
		this.setOpaque(true);

	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
													boolean selected, boolean focused, 
													int row, int column) {
	    if (selected) {
	        setForeground(table.getSelectionForeground());
	        setBackground(table.getSelectionBackground());
	      } else{
	        setForeground(table.getForeground());
	        setBackground(UIManager.getColor("Button.background"));
	      }
	    
		this.setIcon(getDelIcon());
		this.setBorderPainted(true);
		this.setEnabled(true);
		return this;
	}
	
	/**
	 * Helping method to get the deletion button from its package
	 * @return ImageIcon using the package path
	 */
	private ImageIcon getDelIcon(){
		if(icon==null){
			URL url = this.getClass().getResource("/images/cross.png");
			icon = new ImageIcon(url);
		}
		return icon;
	}

}
