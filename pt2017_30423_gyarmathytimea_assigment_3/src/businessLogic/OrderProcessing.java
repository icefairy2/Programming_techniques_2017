package businessLogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dataAccess.CustomerDA;
import dataAccess.OrderDA;
import dataAccess.ProductDA;
import model.Customer;
import model.OrderDB;
import model.Product;
import view.FinalizeOrderFrame;
import view.OrderMenu;
import view.OrderProductFrame;

/**
 * Class for the order processing
 * @author Timi
 *
 */
public class OrderProcessing {
	private static CustomerDA cda = new CustomerDA();
	private static ProductDA pda = new ProductDA();
	private static OrderDA oda = new OrderDA();
	
	private Customer customer;
	private List<OrderDB> order = new ArrayList<OrderDB>();
	
	private OrderMenu oc;
	private FinalizeOrderFrame fof;
	
	
	@SuppressWarnings("unused")
	private static OrderProcessing instance;
	
	private OrderProcessing(){
		oc = new OrderMenu();
		List<Customer> cust = cda.findAll();
		oc.setList(Customer.class, cust);
		oc.addListSelectionListener(new CustomerSelected());
	}
	
	/**
	 * Creates a new order
	 */
	public static void start(){
		instance = new OrderProcessing();
	}
	
	/**
	 * Class for the select customer action listener
	 * @author Timi
	 *
	 */
	class CustomerSelected implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selectedRow = oc.getTable().getSelectedRows()[0];
			int ID;
			try{
				ID = (int) oc.getTable().getValueAt(selectedRow, 0);
			}catch (NullPointerException err){
				JOptionPane.showMessageDialog(null, "Invalid input: " + err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			String name = (String) oc.getTable().getValueAt(selectedRow, 1);
			String e_mail = (String) oc.getTable().getValueAt(selectedRow, 2);
			String telephone = (String) oc.getTable().getValueAt(selectedRow, 3);
			Customer cust = new Customer(ID, name, e_mail, telephone);

			customer = cust;

			fof = new FinalizeOrderFrame(cust);
			fof.addOrderListener(new FinalizeOrder());
			
			List<Product> prod = pda.findAll();
			oc.setList(Product.class, prod);
			oc.addListSelectionListener(new ProductSelected());
		}
	}
	
	/**
	 * Class for the select product action listener
	 * @author Timi
	 *
	 */
	class ProductSelected implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting())
			{
				int selectedRow = oc.getTable().getSelectedRows()[0];
				int ID;
				try{
					ID = (int) oc.getTable().getValueAt(selectedRow, 0);
				}catch (NullPointerException err){
					JOptionPane.showMessageDialog(null, "Invalid input: " + err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
					return;
				}
				String name = (String) oc.getTable().getValueAt(selectedRow, 1);
				float price = Float.parseFloat(oc.getTable().getValueAt(selectedRow, 2).toString());
				float weight = Float.parseFloat(oc.getTable().getValueAt(selectedRow, 3).toString());
				int quantity = Integer.parseInt(oc.getTable().getValueAt(selectedRow, 4).toString());
				Product prod = new Product(ID, name, price, weight, quantity);
				
				OrderProductFrame opf = new OrderProductFrame(prod);
				opf.addSaveListener(new AddProductToOrder(opf));
			}
		}	
	}
	
	/**
	 * Class for the add product to order action listener
	 * @author Timi
	 *
	 */
	class AddProductToOrder implements ActionListener{
		private OrderProductFrame opf;
		
		public AddProductToOrder(OrderProductFrame opf) {
			this.opf = opf;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Product prod = opf.getProd();
			int quantity = 0;
			try{
				quantity = Integer.parseInt(opf.getQuantity());
				if (quantity <= 0)
					throw new NumberFormatException("Negative or zero");
			}catch(NumberFormatException err){
				JOptionPane.showMessageDialog(null, "Invalid input: " + err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			int dec = decreaseStock(prod, quantity);
			if (dec > 0)
				opf.dispose();
			else return;
			
			OrderDB ord = new OrderDB(customer.getID(), prod.getID(), quantity);
			order.add(ord);
			
			fof.addProduct(prod, quantity);
			oc.setList(Product.class, pda.findAll());
			oc.addListSelectionListener(new ProductSelected());
		}	
	}
	
	/**
	 * Class for the finalize order action listener
	 * @author Timi
	 *
	 */
	class FinalizeOrder implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			float total = getOrderTotal();
			fof.setOrderTotal(total);	
			oc.dispose();
			for(OrderDB od: order) {
				oda.insert(od);
			}
			createBill();
		}		
	}
	
	/**
	 * Method used to decrease the stock in the database before finalizing an order
	 * @param prod the product to decrease the stock for
	 * @param quantity the quantity to decrease
	 * @return 1 if the operation succeded, -1 if it failed
	 */
	public int decreaseStock(Product prod, int quantity){
		int newQuantity = prod.getQuantity() - quantity;
		if (newQuantity < 0){
			JOptionPane.showMessageDialog(null, "Requested amount not on stock", "Error", JOptionPane.ERROR_MESSAGE );
			return -1;
		}
		prod.setQuantity(newQuantity);
		pda.updateById(prod.getID(), prod);
		prod.setQuantity(quantity);
		return 1;
	}
	
	/**
	 * Method used to create the bill after an order was finalized
	 */
	public void createBill(){
		long id = System.currentTimeMillis();
		try{
			PrintWriter writer = new PrintWriter("logs/order" + id +".txt", "UTF-8");
		    writer.println("Order " + id);
		    writer.println("Customer: " + customer.getName() );
		    writer.println("E-Mail: " + customer.getE_mail() );
		    writer.println("Telephone: " + customer.getTelephone());
		    writer.println("Products: ");
		    Product p;
		    for(OrderDB od: order) {
				p = pda.findById(od.getIdProduct());
				writer.println(od.getQuantity() + "x" + p.getName() + " = " + p.getPrice()*od.getQuantity());
			}
		    writer.println("---------------------------");
		    writer.println("Total price: " + getOrderTotal());
		    writer.close();
		} catch (IOException e) {
		   System.out.println("Failed to create text file");
		} catch (Exception e){
			System.out.println("Failed to create text file");
		}
	}
	
	/**
	 * Helper method used to compute the total price of the order
	 * @return the price total
	 */
	public float getOrderTotal(){
		float sum = 0f;
		Product p;
		for(OrderDB od: order) {
			p = pda.findById(od.getIdProduct());
			sum+= p.getPrice()*od.getQuantity();
		}
		return sum;
	}
}
