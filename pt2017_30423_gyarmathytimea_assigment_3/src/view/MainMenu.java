package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main Menu user interface
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class MainMenu extends JFrame{
	//First frame
	private JButton admin = new JButton("Administrator");
	private JButton customer = new JButton("Customer");
	
	//Second frame
	private JButton addCustomer = new JButton("Add customer");
	private JButton editCustomer = new JButton("Edit customers");
	private JButton deleteCustomer = new JButton("Delete customer");
	private JButton listCustomers = new JButton("List customers");
	
	private JButton addProduct  =new JButton("Add product");
	private JButton editProduct = new JButton("Edit products");
	private JButton deleteProduct = new JButton("Delete product");
	private JButton listProducts = new JButton("List products");
	
	private JButton back = new JButton ("Back");
	
	private JPanel p = new JPanel(new GridLayout(2, 0));
	private JPanel opt = new JPanel(new GridLayout(2, 0));
	private JPanel pback = new JPanel(new FlowLayout());
	
	public MainMenu(){
		
		this.setTitle("Main Menu");
		this.setSize(600, 600);
		this.setContentPane(p);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		p.add(customer);
		p.add(admin);
		
		this.setVisible(true);
	}
	
	//Listeners for first frame
	public void addAdminListener(ActionListener adm) {
        admin.addActionListener(adm);
    }
	
	public void addCustomerListener(ActionListener cl) {
        customer.addActionListener(cl);
    }
	
	//Listeners for second frame
	/**
	 * Customer related listener
	 */
	public void addAddCustomerListener(ActionListener cl) {
        addCustomer.addActionListener(cl);
    }
	
	/**
	 * Customer related listener
	 */
	public void addEditCustomersListener(ActionListener cl) {
        editCustomer.addActionListener(cl);
    }
	
	/**
	 * Customer related listener
	 */
	public void addListCustomersListener(ActionListener cl) {
        listCustomers.addActionListener(cl);
    }
	
	/**
	 * Customer related listener
	 */
	public void addDeleteCustomerListener(ActionListener cl) {
        deleteCustomer.addActionListener(cl);
    }
	
	/**
	 * Product related listener
	 */
	public void addAddProductListener(ActionListener pr) {
        addProduct.addActionListener(pr);
    }
	
	/**
	 * Product related listener
	 */
	public void addEditProductsListener(ActionListener cl) {
        editProduct.addActionListener(cl);
    }
	
	/**
	 * Product related listener
	 */
	public void addListProductsListener(ActionListener cl) {
        listProducts.addActionListener(cl);
    }
	
	/**
	 * Product related listener
	 */
	public void addDeleteProductListener(ActionListener cl) {
        deleteProduct.addActionListener(cl);
    }
	
	/**
	 * Back button listener
	 */
	public void addBackListener(ActionListener b) {
        back.addActionListener(b);
    }
	
	/**
	 * Creation of administrative options panel
	 */
	public void addAdminOptionsPanel(){
		opt.removeAll();
		
		opt.add(addCustomer);
		opt.add(editCustomer);
		opt.add(deleteCustomer);
		opt.add(listCustomers);
		
		opt.add(addProduct);
		opt.add(editProduct);
		opt.add(deleteProduct);
		opt.add(listProducts);
		
		pback.add(back);
		
		p.removeAll();
		
		p.add(opt);
		p.add(pback);
		
		p.repaint();
		this.setVisible(true);
	}
}
