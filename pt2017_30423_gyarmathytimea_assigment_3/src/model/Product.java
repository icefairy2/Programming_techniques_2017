package model;

/**
 * Class models a product and holds relevant attributes
 * Attributes correspond to those held in the application database
 * @author Timi
 *
 */
public class Product {
	private int ID;
	private String name;
	private float price;
	private float weight;
	private int quantity;

	/**
	 * Parameterless Constructor
	 */
	public Product(){
	}
	
	/**
	 * Constructor
	 */
	public Product(String name, float price, float weight, int quantity) {
		super();
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.quantity = quantity;
	}
	
	/**
	 * Constructor
	 */
	public Product(int iD, String name, float price, float weight, int quantity) {
		super();
		ID = iD;
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.quantity = quantity;
	}

	//Getters and setters
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
