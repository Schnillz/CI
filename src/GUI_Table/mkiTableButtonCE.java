package GUI_Table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class mkiTableButtonCE extends AbstractCellEditor implements TableCellEditor, ActionListener{
    
	private static final long serialVersionUID = 1L;
	private JButton button;

    public mkiTableButtonCE(JButton button) {
        this.button = button;
    }
  
    public Object getCellEditorValue() {
       return button;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
       return button;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("blabla");
		
	}

}