package businessLogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import dataAccess.ProductDA;
import model.Product;
import view.AddProductFrame;
import view.DeleteProductFrame;
import view.EditProductsFrame;
import view.ListProductsFrame;

/**
 * Class implements the business logic related to products:
 * Adding new, editing information, deleting a product.
 * @author Timi
 *
 */
public class WarehouseAdministration {
	private static ProductDA pda = new ProductDA();
	private static WarehouseAdministration instance = new WarehouseAdministration();
	private static AddProductFrame apf;
	private static EditProductsFrame epf;
	private static DeleteProductFrame dpf;
	private static ListProductsFrame lpf;

	/**
	 * This method implement the insert product logic, based on the user input data specific
	 * to the frame and action
	 * @param f user interface frame specific to action
	 */
	public static void addProduct(AddProductFrame f){
		apf = f;
		apf.addSaveListener(instance.new AddNewInsert());
	}
	
	/**
	 * This method implement the delete product logic, based on the user input data specific
	 * to the frame and action
	 * @param f user interface frame specific to action
	 */
	public static void deleteProduct(DeleteProductFrame f){
		dpf = f;
		dpf.addDeleteListener(instance.new AddNewDelete());
	}
	
	/**
	 * This method implement the edit products logic, based on the user input data specific
	 * to the frame and action
	 * @param f user interface frame specific to action
	 */
	public static void editProduct(EditProductsFrame f){
		epf = f;
		List<Product> products = pda.findAll();
		epf.setList(products);
		epf.addTableModelListener(instance.new AddNewEdit());
	}
	
	/**
	 * This method implement the list products logic, based on the user input data specific
	 * to the frame and action
	 * @param f user interface frame specific to action
	 */
	public static void listProducts(ListProductsFrame f){
		lpf = f;
		lpf.setList(pda.findAll());
	}
	
	/**
	 * Method returns the product data retrieved from the database
	 * @param id unique identifier for product to be retrieved
	 * @return object product with specified id
	 */
	public Product findProductById(int id) {
		Product prod = (Product) pda.findById(id);
		if (prod == null) {
			JOptionPane.showMessageDialog(null, "Product with id: " + id + "was not found", "Error", JOptionPane.ERROR_MESSAGE );
		}
		return prod;
	}
	
	/**
	 * These classes implement the action logic relative to the user interface frames
	 * @author Timi
	 *
	 */
	class AddNewInsert implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = apf.getName();
			float price = 0f;
			float weight = 0f;
			int quantity = 0;
			
			//parsing and validating user input
			try{
				price = Float.parseFloat(apf.getPrice());
				weight = Float.parseFloat(apf.getWeight());
				quantity = Integer.parseInt(apf.getQuantity());
				
				if (price<0 || weight<0 || quantity<0)
					throw new ArrayIndexOutOfBoundsException("Negative number");
			} catch(NumberFormatException err){
				JOptionPane.showMessageDialog(null, "Invalid input: " + err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
			} catch(ArrayIndexOutOfBoundsException err2){
				JOptionPane.showMessageDialog(null, "Invalid input: " + err2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
			}
				
			Product prod = new Product(name, price, weight, quantity);

			int ins = pda.insert(prod);
			if (ins<0)
				JOptionPane.showMessageDialog(null, "Insert product failed", "Error", JOptionPane.ERROR_MESSAGE );
			apf.dispose();
		}
	}
	
	/**
	 * These classes implement the action logic relative to the user interface frames
	 * @author Timi
	 *
	 */
	class AddNewEdit implements TableModelListener{

		@Override
		public void tableChanged(TableModelEvent e) {
			int selectedRow = epf.getTable().getSelectedRows()[0];
			int ID = (int) epf.getTable().getValueAt(selectedRow, 0);
			String name = "";
			float price = 0f;
			float weight = 0f;
			int quantity = 0;
			
			//parsing and validating user input
			try{
				name = (String) epf.getTable().getValueAt(selectedRow, 1);
				price = Float.parseFloat(epf.getTable().getValueAt(selectedRow, 2).toString());
				weight = Float.parseFloat(epf.getTable().getValueAt(selectedRow, 3).toString());
				quantity = Integer.parseInt(epf.getTable().getValueAt(selectedRow, 4).toString());
				
				if (price<0 || weight<0 || quantity<0)
					throw new ArrayIndexOutOfBoundsException("Negative number");
			} catch(NumberFormatException err){
				JOptionPane.showMessageDialog(null, "Invalid input: " + err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
			} catch(ArrayIndexOutOfBoundsException err2){
				JOptionPane.showMessageDialog(null, "Invalid input: " + err2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
			}
			
			Product prod = new Product(name, price, weight, quantity);
			int updatedRow = pda.updateById(ID, prod);
			if(updatedRow <0)
				JOptionPane.showMessageDialog(null, "Update product failed", "Error", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	/**
	 * These classes implement the action logic relative to the user interface frames
	 * @author Timi
	 *
	 */
	class AddNewDelete implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int id = 0;
			try{
				id = Integer.parseInt(dpf.getId());
			}catch (NumberFormatException err){
				JOptionPane.showMessageDialog(null, "Invalid input: " + err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int deletedRows = pda.deleteById(id);
			if(deletedRows <0)
				JOptionPane.showMessageDialog(null, "Delete product failed", "Error", JOptionPane.ERROR_MESSAGE );
			dpf.dispose();
		}
	}
}