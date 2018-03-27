package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Customer;

/**
 * Class creates an non editable window with the list of customers in database
 * Window will appear in when in the 'Admin' menu, 'List customers' button was pressed
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class ListCustomersFrame extends JFrame {

	private JTable table;
	String[] columnNames = { "ID", "Name", "E-mail", "Telephone" };
	Object[][] customers = new Object[100][10];
	
	public ListCustomersFrame() {
		this.setTitle("Customers");
		this.setSize(600, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(1, 0));
		
		this.setVisible(true);
	}
	
	/**
	 * Populates the view with customers
	 * @param customersList the list of customers to add to the view
	 */
	public void setList(List<Customer> customersList){
		for(int i=0;i<customersList.size();i++)
		{
			customers[i][0] = customersList.get(i).getID();
			customers[i][1] = customersList.get(i).getName();
			customers[i][2] = customersList.get(i).getE_mail();
			customers[i][3] = customersList.get(i).getTelephone();
		}

		table = new JTable(customers, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(true);
		this.setContentPane(scrollPane);
		
		this.repaint();
		this.setVisible(true);
	}
}
