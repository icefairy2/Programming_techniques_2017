package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;

import model.Customer;

/**
 * Class creates an editable window with the list of customers in database
 * Window will appear in when in the 'Admin' menu, 'Edit customer' button was pressed
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class EditCustomersFrame extends JFrame {

	private JTable table;
	String[] columnNames = { "ID", "Name", "E-mail", "Telephone"};
	Object[][] customers = new Object[100][10];
	
	public EditCustomersFrame() {
		this.setTitle("Customers");
		this.setSize(600, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(1, 0));
		
		this.setVisible(true);
	}
	
	/**
	 * Populates the view with customers
	 * 
	 * @param customersList
	 *            the list of customers to add to the view
	 */
	public void setList(final List<Customer> customersList) {
		
		table = new JTable(customers, columnNames){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return (column == 0)? false:true;
		    }
		};
		
        for (int i = 0; i < customersList.size(); i++) {
			customers[i][0] = customersList.get(i).getID();
			customers[i][1] = customersList.get(i).getName();
			customers[i][2] = customersList.get(i).getE_mail();
			customers[i][3] = customersList.get(i).getTelephone();
		}
        
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(true);
		this.setContentPane(scrollPane);

		this.repaint();
		this.setVisible(true);
	}
	
	public void addTableModelListener(TableModelListener l){
		table.getModel().addTableModelListener(l);
	}

	public JTable getTable() {
		return table;
	}
}
