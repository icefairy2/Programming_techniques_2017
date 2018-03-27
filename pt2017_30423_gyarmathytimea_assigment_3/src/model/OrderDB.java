package model;

/**
 * Class for the order items
 * @author Timi
 *
 */
public class OrderDB {

	private int idCustomer;
	private int idProduct;
	private int quantity;
	
	/**
	 * Constructor for the OrderDB class
	 * @param idCustomer
	 * @param idProduct
	 * @param quantity
	 */
	public OrderDB(int idCustomer, int idProduct, int quantity) {
		super();
		this.idCustomer = idCustomer;
		this.idProduct = idProduct;
		this.quantity = quantity;
	}
	
	public int getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
