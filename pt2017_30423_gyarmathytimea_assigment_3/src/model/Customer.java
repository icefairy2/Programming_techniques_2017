package model;

/**
 * Class models a customer and holds relevant attributes
 * Attributes correspond to those saved in the application database
 * @author Timi
 *
 */
public class Customer {
	private int ID;
	private String name;
	private String e_mail;
	private String telephone;
	
	/**
	 * Parameterless Constructor
	 */
	public Customer(){
	}
	
	/**
	 * Constructor
	 */
	public Customer(int id, String name, String e_mail, String tel) {
		super();
		ID = id;
		this.name = name;
		this.e_mail = e_mail;
		this.telephone = tel;
	}
	
	/**
	 * Constructor
	 */
	public Customer(String name, String e_mail, String tel) {
		super();
		this.name = name;
		this.e_mail = e_mail;
		this.telephone = tel;
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
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String tel) {
		this.telephone = tel;
	}	
}
