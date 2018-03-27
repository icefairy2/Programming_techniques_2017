package model;
@SuppressWarnings("serial")
public class SpendingAccount extends Account {
	
	/**
	 * Constructor for the Account class
	 */
	public SpendingAccount(int accountNumber, double money){
		super(accountNumber, money);
	}
	
	public SpendingAccount(double money){
		super(money);
	}
	
	/**
	 * Parameterless constructor for the SpendingAccount class
	 */
	public SpendingAccount(){
		super();
	}
}
