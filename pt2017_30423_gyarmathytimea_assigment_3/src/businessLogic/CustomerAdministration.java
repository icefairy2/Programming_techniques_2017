package businessLogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import dataAccess.CustomerDA;
import model.Customer;
import validators.EmailValidator;
import validators.TelephoneValidator;
import validators.Validator;
import view.AddCustomerFrame;
import view.DeleteCustomerFrame;
import view.EditCustomersFrame;
import view.ListCustomersFrame;

/**
 * Class implements the business logic related to customers: Adding new, editing
 * information, deleting a customer.
 * 
 * @author Timi
 *
 */
public class CustomerAdministration {
	private static CustomerDA cda = new CustomerDA();
	private static CustomerAdministration instance = new CustomerAdministration();
	private static AddCustomerFrame acf;
	private static EditCustomersFrame ecf;
	private static DeleteCustomerFrame dcf;
	private static ListCustomersFrame lcf;

	// List used to validate entered data
	private List<Validator<Customer>> validators;

	/**
	 * Constructor
	 */
	public CustomerAdministration() {
		validators = new ArrayList<Validator<Customer>>();
		validators.add(new EmailValidator());
		validators.add(new TelephoneValidator());
	}

	/**
	 * This method implement the insert customer logic, based on the user input data specific
	 * to the frame and action
	 * 
	 * @param f user interface frame specific to action
	 */
	public static void addCustomer(AddCustomerFrame f) {
		acf = f;
		acf.addSaveListener(instance.new AddNewInsert());
	}

	/**
	 * This method implement the delete customer logic, based on the user input data specific
	 * to the frame and action
	 * @param f user interface frame specific to action
	 */
	public static void deleteCustomer(DeleteCustomerFrame f) {
		dcf = f;
		dcf.addDeleteListener(instance.new AddNewDelete());
	}

	/**
	 * This method implement the edit customers logic, based on the user input data specific
	 * to the frame and action
	 * @param f user interface frame specific to action
	 */
	public static void editCustomer(EditCustomersFrame f) {
		ecf = f;
		List<Customer> customers = cda.findAll();
		ecf.setList(customers);
		ecf.addTableModelListener(instance.new AddNewEdit());
	}

	/**
	 * This method implement the list customers logic, based on the user input data specific
	 * to the frame and action
	 * @param f user interface frame specific to action
	 */
	public static void listCustomers(ListCustomersFrame f) {
		lcf = f;
		lcf.setList(cda.findAll());
	}

	/**
	 * Method returns the customer data retrieved from the database
	 * 
	 * @param id unique identifier for customer to be retrieved
	 * @return object customer with specified id
	 */
	public Customer findCustomerById(int id) {
		Customer cust = (Customer) cda.findById(id);
		if (cust == null) {
			JOptionPane.showMessageDialog(null, "Customer with id: " + id + "was not found", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return cust;
	}

	/**
	 * These classes implement the action logic relative to the user interface
	 * frames
	 * 
	 * @author Timi
	 *
	 */
	class AddNewInsert implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = acf.getName();
			String e_mail = acf.getE_mail();
			String tel = acf.getTel();
			Customer cust = new Customer(name, e_mail, tel);
			try {
				for (Validator<Customer> v : validators) {
					v.validate(cust);
				}
			} catch (IllegalArgumentException err) {
				JOptionPane.showMessageDialog(null, "Invalid input: "+ err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int ins = cda.insert(cust);
			if (ins < 0)
				JOptionPane.showMessageDialog(null, "Insert customer failed", "Error", JOptionPane.ERROR_MESSAGE);
			acf.dispose();
		}
	}

	/**
	 * These classes implement the action logic relative to the user interface
	 * frames
	 * 
	 * @author Timi
	 *
	 */
	class AddNewEdit implements TableModelListener {

		@Override
		public void tableChanged(TableModelEvent e) {
			int selectedRow = ecf.getTable().getSelectedRows()[0];
			int ID = (int) ecf.getTable().getValueAt(selectedRow, 0);
			String name = (String) ecf.getTable().getValueAt(selectedRow, 1);
			String e_mail = (String) ecf.getTable().getValueAt(selectedRow, 2);
			String telephone = (String) ecf.getTable().getValueAt(selectedRow, 3);
			Customer cust = new Customer(ID, name, e_mail, telephone);
			try {
				for (Validator<Customer> v : validators) {
					v.validate(cust);
				}
			} catch (IllegalArgumentException err) {
				JOptionPane.showMessageDialog(null, "Invalid input: "+ err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int updatedRow = cda.updateById(ID, cust);
			if (updatedRow < 0)
				JOptionPane.showMessageDialog(null, "Update customer failed", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * These classes implement the action logic relative to the user interface
	 * frames
	 * 
	 * @author Timi
	 *
	 */
	class AddNewDelete implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int id = 0;
			try{
				id = Integer.parseInt(dcf.getId());
			}catch (NumberFormatException err){
				JOptionPane.showMessageDialog(null, "Invalid input: " + err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int deletedRows = cda.deleteById(id);
			if (deletedRows < 0)
				JOptionPane.showMessageDialog(null, "Delete customer failed", "Error", JOptionPane.ERROR_MESSAGE);
			dcf.dispose();
		}
	}
}
