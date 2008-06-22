package table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ButtonCellEditor extends DefaultCellEditor implements ActionListener {

	private static final long serialVersionUID = 1L;
	protected JButton btn;
	protected boolean isPushed;
	private File file;
	
	public ButtonCellEditor(JCheckBox checkBox) {
		super(checkBox);
		btn = new JButton();
		btn.setOpaque(true);
		btn.addActionListener(this);
		btn.setBorderPainted(false);
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value,
              										boolean isSelected, int row, int column) {
		if (isSelected) {
			btn.setForeground(table.getSelectionForeground());
			btn.setBackground(table.getSelectionBackground());
		} else{
			btn.setForeground(table.getForeground());
			btn.setBackground(table.getBackground());
		}
		this.file=(File) value;
		isPushed = true;
		return btn;
	}
		
	public Object getCellEditorValue() {
		if (isPushed)  {
			JOptionPane.showMessageDialog(btn ,"Sure wanna do this ?");
		}
		isPushed = false;
		return "";
	}
		
	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}
		
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
	
	
	/**
	 * Method to retrieve the file associated with the current button
	 * @return File The file associated to the button of the current row
	 */
	public  File getFile(){
		return file;
	}

	public void actionPerformed(ActionEvent ae) {
		System.out.println("DELETE ");
		this.fireEditingStopped();
	}
}
