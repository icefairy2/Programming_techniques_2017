package model;
import java.util.List;

/**
 * Interface used to define the contract of the Bank class
 * @author Timi
 *
 */
public interface BankProc {
	
	/**
	 * Method that adds a person
	 * @param person - the person to be added
	 * @pre name not be empty && cnp valid
	 * @pre person not registered
	 * 
	 * @post the number of persons must increment by 1
	 */
	public void addPerson(Person person);
	
	/**
	 * Method that removes a person
	 * @param cnp - the person's cnp to be removed
	 * @pre cnp valid
	 * @pre cnp be in the table
	 * 
	 * @post the number of persons must decrement by 1
	 */
	public void removePerson(Person person);
	

	/**
	 * Method that adds a new account for that person
	 * @param account - the account to add
	 * @pre deposited sum >= 0
	 * 
	 * @param cnp - the cnp of the account holder
	 * @pre cnp valid
	 * @pre cnp be in the table
	 * 
	 * @post the number of accounts must increment by 1 for that person (cnp)
	 */
	public void addNewAccount(Account account, Person person);
	
	/**
	 * Removes an account from a person
	 * @param account - the account to remove
	 * @pre the balance == 0
	 * 
	 * @param cnp - the cnp of the person
	 * 
	 * @post the number of accounts must decrement by 1 for that person (cnp)
	 */
	public void removeAccount(int accountNumber, Person person);
	
	/**
	 * Method that returns the accounts for that person
	 * @param cnp - the cnp of the person to retrieve the accounts for
	 * @pre cnp valid
	 * 
	 * @return - the accounts belonging to that person
	 * @post no change
	 */
	public List<Account> getAccountData(Person person);
	
	/**
	 * Method that deposits money into an account
	 * @param accountNumber - the account to deposit money into
	 * @param cnp - the cnp of the person to deposit money for
	 * @pre cnp valid
	 * 
	 * @param money
	 * @pre deposited sum > 0
	 * 
	 * @pre if saving account ==> initial account balance == 0 (only 1 deposit operation is allowed) 
	 * 
	 * @post the sum of money in that account == the initial balance + the deposited money value
	 * 
	 * @return amount deposited
	 */
	public double depositMoney(int accountNumber, Person person, double money);
	
	/**
	 * Method that withdraws money from an account
	 * @param accountNumber - the account to withdraw money from
	 * @pre if savings account  ==> all the money must be withdrawn at once (only 1 withdrawal is allowed)
	 * 
	 * @param cnp - the cnp of the person to withdraw the money from
	 * @pre cnp valid
	 * 
	 * @param money
	 * @pre withdrawn value must be > 0

	 * 
	 * @post the sum of money in that account must be the initial balance + the deposited money value
	 * @post the number of accounts must remain the same for that person
	 * 
	 * @return amount withdrawn
	 */
	public double withdrawMoney(int accountNumber, Person person, double money);
	
	/**
	 * Method used to generate a report
	 * @param cnp - the cnp of the person to generate the report for
	 * @pre cnp valid
	 * 
	 * @return a report in a String format
	 */
	public String generateReport(Person person);
}
