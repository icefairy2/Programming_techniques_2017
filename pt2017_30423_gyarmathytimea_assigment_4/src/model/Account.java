package model;
import java.io.Serializable;
import java.util.Observable;

/**
 * Model class for the Account
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class Account extends Observable implements Serializable {
	private static int globalNumber;
	private int accountNumber;
	private double money;
	
	/**
	 * Parameterless constructor for the Account class
	 */
	public Account(){
		this.accountNumber = ++globalNumber;
		notifyObservers("account created");
	}
	
	/**
	 * Constructor for the Account class
	 */
	public Account(int accountNumber, double money) {
		super();
		this.accountNumber = accountNumber;
		this.money = money;
		setChanged();
		notifyObservers("account created");
	}
	
	public Account(double money) {
		super();
		this.accountNumber = ++globalNumber;
		this.money = money;
		setChanged();
		notifyObservers("account created");
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
		setChanged();
		notifyObservers("account number has been modified, new accountNumber: " + accountNumber);
	}
	
	public double getMoney() {
		return money;
	}
	
	public void setMoney(double money) {
		this.money = money;
		setChanged();
		notifyObservers("account balance set to: " + money);
	}
	
	public void addMoney(double money)
	{
		this.money = this.money+money;
		setChanged();
		notifyObservers(money + " value has been added to the account");
	}
	
    public void withdrawMoney(double money)
    {
    	this.money = this.money-money;
    	setChanged();
    	notifyObservers(money + " value has been withdrawn from the account");
    }
}
