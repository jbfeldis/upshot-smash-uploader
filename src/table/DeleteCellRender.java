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
import javax.swing.table.TableCellRenderer;

import main.Smash;

/**
 * Create a special cell render for the first column of Smash images table
 * This cell render let us display a JButton associated with the given file in order
 * to permit its deletion from the tablelist
 * @author Gregory Durelle
 *
 */
public class DeleteCellRender extends JButton implements TableCellRenderer{

	private static final long serialVersionUID = 1L;
	
	public DeleteCellRender(){
	    this.setIcon(Smash.getIcon("cross.png"));
		this.setBorderPainted(false);
		this.setOpaque(false);
		this.setFocusable(false);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
													boolean selected, boolean focused, 
													int row, int column) {
		this.setBackground(table.getBackground());
		return this;
	}
}
