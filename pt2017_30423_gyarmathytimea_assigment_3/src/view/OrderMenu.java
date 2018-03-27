package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;

/**
 * UI class used for creating the order menu 
 *
 */
@SuppressWarnings("serial")
public class OrderMenu extends JFrame {

	private JTable table;
	//String[] columnNames = new String[4];
	List<String> columnNames;
	Object[][] objects = new Object[100][10];
	
	public OrderMenu() {
		this.setTitle("Order Menu");
		this.setSize(600, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(1, 0));

		this.setVisible(true);
	}

	public <T> void setList(Class<T> type, List<T> objectList) {
		columnNames = new ArrayList<String>();
		
		int i = 0, j = 0;
		PropertyDescriptor propertyDescriptor;
		Method method;

		try {
			for (Field field : type.getDeclaredFields()) {
				columnNames.add(field.getName());
				for (j = 0; j < objectList.size(); j++) {
					propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					method = propertyDescriptor.getReadMethod();
					objects[j][i] = method.invoke(objectList.get(j));
				}
				i++;
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		table = new JTable(objects, columnNames.toArray(new String[columnNames.size()])){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(true);
		this.setContentPane(scrollPane);

		this.repaint();
		this.setVisible(true);
	}
	
	public void addListSelectionListener(ListSelectionListener l){
		table.getSelectionModel().addListSelectionListener(l);;
	}
	
	public JTable getTable() {
		return table;
	}
}
