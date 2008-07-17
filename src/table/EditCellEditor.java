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
package table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import main.ImageEditor;
import main.Smash;

/**
 * cell editor to implements button functionnality to a cell of the table
 * in order to lauchn an edition dialog box.
 * 
 * @author Gregory Durelle
 *
 */
public class EditCellEditor extends DefaultCellEditor implements ActionListener {

	private static final long serialVersionUID = 1L;
	private DataModel model;
	private int row;
	private JButton btn;
	
	public EditCellEditor(DataModel model) {
		super(new JCheckBox());
		btn = new JButton();
		btn.addActionListener(this);
		this.model=model;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
              										boolean isSelected, int row, int column) {
		btn.setBackground(table.getBackground());
		btn.setForeground(table.getForeground());
		btn.setBorderPainted(false);
		btn.setFocusable(false);
		btn.setIcon(Smash.getIcon("edit.png"));
		btn.setText("Edit");
		this.row=row;
		return btn;
	}
	
	@Override
	public Object getCellEditorValue() {
		return btn;
	}
	
	@Override
	public boolean stopCellEditing() {
		return super.stopCellEditing();
	}
	
	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		
		ImageEditor ie = new ImageEditor(model.getImageFile(row));
		
		if(ie.getTitle()!=null && !ie.getTitle().isEmpty())
			model.getImageFile(row).setTitle(ie.getTitle());
		this.fireEditingStopped();
	}
}
