import model.Customer;
import model.Product;
import controller.MainMenuControl;
import dataAccess.CustomerDA;
import dataAccess.ProductDA;

/**
 * Main class for starting the application for assignment 3:
 * Consider an order management application for processing customer orders for a warehouse.
 * Relational databases are used to store the products, the clients and the orders.
 * @author Timi
 *
 */
public class Main {

	public static void main(String[] args) {
		MainMenuControl.start();

		
		/*CustomerDA cda = new CustomerDA();
		Customer c = new Customer("John Brown", "johnb@mail.com", "0123456789");
		cda.insert(c);
		c = new Customer("Jenna White", "jennaw@mail.com", "0789456123");
		cda.insert(c);
		c = new Customer("Herbert Black", "herbertb@mail.com", "5647891230");
		cda.insert(c);
		
		ProductDA pda = new ProductDA();
		Product p = new Product("Toothbrush", 7f, 0.2f, 100);
		pda.insert(p);
		p = new Product("Biscuits", 3f, 0.33f, 50);
		pda.insert(p);
		p = new Product("Toilet paper", 12f, 0.1f, 200);
		pda.insert(p);
		p = new Product("Milk", 4.5f, 1f, 30);
		pda.insert(p);
		p = new Product("Shampoo", 10.5f, 0.5f, 75);*/
	}
}
