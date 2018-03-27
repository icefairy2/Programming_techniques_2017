package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import businessLogic.CustomerAdministration;
import businessLogic.OrderProcessing;
import businessLogic.WarehouseAdministration;
import view.AddCustomerFrame;
import view.AddProductFrame;
import view.DeleteCustomerFrame;
import view.DeleteProductFrame;
import view.EditCustomersFrame;
import view.EditProductsFrame;
import view.ListCustomersFrame;
import view.ListProductsFrame;
import view.MainMenu;

/**
 * Class controls the main menu user interface
 * @author Timi
 *
 */
public class MainMenuControl {
	private MainMenu mm;

	@SuppressWarnings("unused")
	private static MainMenuControl instance;
	
	private MainMenuControl() {
		mm = new MainMenu();
		mm.addAdminListener(new AdminApp());
		mm.addCustomerListener(new ClientApp());
	}
	
	/**
	 * Creates a new instance of the MainmenuControl
	 */
	public static void start(){
		instance = new MainMenuControl();
	}
	
	/**
	 * Class implements the administrator submenu
	 * @author Timi
	 *
	 */
	class AdminApp implements ActionListener{
		public void actionPerformed(ActionEvent e){
			mm.addAdminOptionsPanel();
			
			mm.addAddCustomerListener(new AdminAddCustomer());
			mm.addEditCustomersListener(new AdminEditCustomers());
			mm.addDeleteCustomerListener(new AdminDeleteCustomer());
			mm.addListCustomersListener(new AdminListCustomers());
			
			mm.addAddProductListener(new AdminAddProduct());
			mm.addEditProductsListener(new AdminEditProducts());
			mm.addDeleteProductListener(new AdminDeleteProduct());
			mm.addListProductsListener(new AdminListProducts());
			
			mm.addBackListener(new BackMenu());
		}
	}
	
	/**
	 * Class implements the client submenu
	 * @author Timi
	 *
	 */
	class ClientApp implements ActionListener{
		public void actionPerformed(ActionEvent e){
			OrderProcessing.start();
		}
	}
	
	//Actions on customer data
	
	/**
	 * Class for the administrator add customer action
	 * @author Timi
	 *
	 */
	class AdminAddCustomer implements ActionListener{
		private AddCustomerFrame ac;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ac = new AddCustomerFrame();	
			CustomerAdministration.addCustomer(ac);
		}
	}
	
	/**
	 * Class for the administrator Edit Customers action
	 * @author Timi
	 *
	 */
	class AdminEditCustomers implements ActionListener{
		private EditCustomersFrame ec;
		
		@Override
		public void actionPerformed(ActionEvent e){
			ec = new EditCustomersFrame();
			CustomerAdministration.editCustomer(ec);
		}
	}
	
	/**
	 * Class for the administrator Delete Customer action
	 * @author Timi
	 *
	 */
	class AdminDeleteCustomer implements ActionListener{
		private DeleteCustomerFrame dc;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			dc = new DeleteCustomerFrame();	
			CustomerAdministration.deleteCustomer(dc);
		}
	}
	
	/**
	 * Class for the administrator List Customers action
	 * @author Timi
	 *
	 */
	class AdminListCustomers implements ActionListener{
		private ListCustomersFrame ec;
		
		@Override
		public void actionPerformed(ActionEvent e){
			ec = new ListCustomersFrame();
			CustomerAdministration.listCustomers(ec);
		}
	}
	
	//Actions on product data
	
	/**
	 * Class for the administrator Add product action
	 * @author Timi
	 *
	 */
	class AdminAddProduct implements ActionListener{
		private AddProductFrame ap;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ap = new AddProductFrame();
			WarehouseAdministration.addProduct(ap);
		}
	}
	
	/**
	 * Class for the administrator Edit product action
	 * @author Timi
	 *
	 */
	class AdminEditProducts implements ActionListener{
		private EditProductsFrame ep;
		
		@Override
		public void actionPerformed(ActionEvent e){
			ep = new EditProductsFrame();
			WarehouseAdministration.editProduct(ep);
		}
	}
	
	/**
	 * Class for the administrator Delete product action
	 * @author Timi
	 *
	 */
	class AdminDeleteProduct implements ActionListener{
		private DeleteProductFrame dp;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			dp = new DeleteProductFrame();	
			WarehouseAdministration.deleteProduct(dp);
		}
	}
	
	/**
	 * Class for the administrator Add List products action
	 * @author Timi
	 *
	 */
	class AdminListProducts implements ActionListener{
		private ListProductsFrame ep;
		
		@Override
		public void actionPerformed(ActionEvent e){
			ep = new ListProductsFrame();
			WarehouseAdministration.listProducts(ep);
		}
	}
	
	/**
	 * When back button is pressed, user is redirected to the start of the application
	 * @author Timi
	 *
	 */
	class BackMenu implements ActionListener{
		public void actionPerformed(ActionEvent e){
			mm.dispose();
			instance = new MainMenuControl();
		}
	}
}
