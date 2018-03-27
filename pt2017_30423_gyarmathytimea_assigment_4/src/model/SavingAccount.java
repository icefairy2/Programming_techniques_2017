package model;
@SuppressWarnings("serial")
public class SavingAccount extends Account  {
	
	// interest percent (e.g 3 for 3%)
	private double interest;
	
	/**
	 * Constructor for the Account class
	 */
	public SavingAccount(int accountNumber, double money, double interest){
		super(accountNumber, money);
		this.interest = interest;
	}
	
	public SavingAccount(double money, double interest){
		super(money);
		this.interest = interest;
	}
	
	public SavingAccount(double interest){
		super();
		this.interest = interest;
	}
	
	/**
	 * Parameterless constructor for the SpendingAccount class
	 */
	public SavingAccount(){
		super();
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}
	
	public void addInterest(){
		super.setMoney(super.getMoney() + super.getMoney() * interest / 100);
		setChanged();
		notifyObservers(interest + "% interest has been added, new account balance: " + super.getMoney());
	}
}
