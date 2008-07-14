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
 * Create a special cell render for the forth column of Smash images table
 * This cell render let us display a JButton associated with the given file in order
 * to permit edition
 * @author Gregory Durelle
 *
 */
public class EditCellRender extends JButton implements TableCellRenderer{

	private static final long serialVersionUID = 1L;
	
	public EditCellRender(){
	    this.setIcon(Smash.getIcon("edit.png"));
	    this.setText("Edit");
		this.setBorderPainted(false);
		this.setOpaque(false);
		this.setForeground(Color.WHITE);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
													boolean selected, boolean focused, 
													int row, int column) {
	    this.setBackground(table.getBackground());
		return this;
	}
}
