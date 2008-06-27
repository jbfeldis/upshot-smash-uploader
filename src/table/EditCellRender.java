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

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import main.Smash;

/**
 * Create a special cell render for the forth column of Smash images table
 * This cell render let us display a JButton associated with the given file in order
 * to permit edition
 * @author Gregory Durelle
 *
 */
public class EditCellRender extends JButton implements TableCellRenderer{

	private static final long serialVersionUID = 1L;
	
	public EditCellRender(){
		this.setOpaque(true);
	    this.setIcon(Smash.getIcon("edit.png"));
		this.setBorderPainted(false);
		this.setEnabled(true);
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
		return this;
	}
}
