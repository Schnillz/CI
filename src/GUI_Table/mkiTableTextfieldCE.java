package GUI_Table;


import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellEditor;

public class mkiTableTextfieldCE extends AbstractCellEditor implements TableCellEditor {
	
	JTextField component = new JTextField();
	
	private static final long serialVersionUID = 1L;

	public mkiTableTextfieldCE(){
//		addKeyListener();
		setBorderTextfield();
	}

	private void setBorderTextfield() {
		component.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
	}

	@Override
	public Object getCellEditorValue() {
		return ((JTextField)component).getText();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// Configure the component with the specified value
        ((JTextField)component).setText((String)value);
		return component;
	}
	
	
	
}
	

