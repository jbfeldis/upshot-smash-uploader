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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 * cell editor to implements button functionnality to a cell of the table 
 * in order delete the associated row
 * 
 * @author Gregory Durelle
 *
 */
public class DeleteCellEditor extends DefaultCellEditor implements ActionListener {

	private static final long serialVersionUID = 1L;
	private DataModel model;
	private int row;
	private JButton btn;
	
	public DeleteCellEditor(DataModel model) {
		super(new JCheckBox());
		btn = new JButton();
		btn.addActionListener(this);
		this.model=model;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value,
              										boolean isSelected, int row, int column) {
		btn.setBackground(table.getBackground());
		btn.setBorderPainted(false);
		btn.setFocusable(false);
		this.row=row;
		return btn;
	}
	
	public Object getCellEditorValue() {
		return btn;
	}
		
	public boolean stopCellEditing() {
		return super.stopCellEditing();
	}
		
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}

	public void actionPerformed(ActionEvent ae) {
		model.remove(row);
		this.fireEditingStopped();
	}
}
