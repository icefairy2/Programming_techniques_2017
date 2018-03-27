package model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Bank class used to hold the account and client information and expose the
 * operations
 * 
 * @author Timi
 *
 */
public class Bank implements BankProc, Serializable {
	private Map<Person, HashSet<Account>> accounts;
	public double interest = 3;

	public Bank() {
		accounts = new HashMap<Person, HashSet<Account>>();
		assert isWellFormed() : "Bank is not well formed";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPerson(Person person) {
		// validate precondition: name must not be empty, cnp must be valid
		assert validateCnp(person.getCnp()) : "cnp must be valid";
		assert !person.getName().isEmpty() : "the person's name can't be empty";
		assert !accounts.containsKey(person) : "person already registered";
		int numberOfClients = accounts.size();

		// use the person as unique key in the map
		accounts.put(person, new HashSet<Account>());

		// validate postcondition: the number of persons must increment by 1
		assert accounts.size() == numberOfClients + 1 : "the number of persons must increment by 1";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removePerson(Person person) {
		// validate precondition: cnp must be valid
		assert validateCnp(person.getCnp()) : "cnp must be valid";
		assert !accounts.containsKey(person.getCnp()) : "cnp not registered";

		int clientsCount = accounts.size();
		accounts.remove(person);

		// validate postcondition: the number of persons must decrement by 1
		assert clientsCount - 1 == accounts.size() : "the number of persons must decrement by 1 when removing a person";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNewAccount(Account account, Person person) {
		// validate precondition: cnp must be valid
		assert validateCnp(person.getCnp()) : "cnp must be valid";
		// validate precondition: deposited value must be > 0
		assert account.getMoney() >= 0 : "the deposited sum must be >= 0";

		// add the client holder as the observer
		account.addObserver(person);
		HashSet<Account> existingAccounts = accounts.get(person);
		int initialAccountCount = existingAccounts.size();
		existingAccounts.add(account);
		accounts.put(person, existingAccounts);

		// validate postcondition: the number of accounts must increment by 1
		// for that person (cnp)
		assert accounts.get(person).size() == initialAccountCount
				+ 1 : "the number of accounts must increment by 1 for that person (cnp): " + person.getCnp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAccount(int accountNumber, Person person) {
		// validate precondition: cnp must be valid
		assert validateCnp(person.getCnp()) : "cnp must be valid";

		HashSet<Account> existingAccounts = accounts.get(person);
		HashSet<Account> newAccounts = new HashSet<Account>(accounts.get(person));
		int initialAccountCount = existingAccounts.size();

		// find the account we want to remove
		for (Account acc : existingAccounts) {
			if (acc.getAccountNumber() == accountNumber) {
				newAccounts.remove(acc);
			}
		}

		// put the rest of the accounts back
		accounts.put(person, newAccounts);

		// validate postcondition: the number of accounts must decrement by 1
		// for that person (cnp)
		assert accounts.get(person).size() == initialAccountCount
				- 1 : "the number of accounts must decrement by 1 for person cnp: " + person.getCnp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Account> getAccountData(Person person) {
		// validate precondition: cnp must be valid
		assert validateCnp(person.getCnp()) : "cnp must be valid";

		for(Account acc: accounts.get(person)){
			acc.addObserver(person);
		}
		
		List<Account> accs = new ArrayList<Account>(accounts.get(person));
		return accs;
	}

	public Person getPersonBasedOnCNP(long cnp) {
		// validate precondition: cnp must be valid
		assert validateCnp(cnp) : "cnp must be valid";

		Iterator<?> entries = accounts.entrySet().iterator();
		// iterate over all accounts for the selected client(cnp)
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Person p = (Person) thisEntry.getKey();
			if (p.getCnp() == cnp) {
				return p;
			}
		}

		return null;
	}

	public List<Person> getListOfPersons() {
		List<Person> personList = new ArrayList<Person>();
		for (Person key : accounts.keySet()) {
			personList.add(key);
		}
		return personList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double depositMoney(int accountNumber, Person person, double money) {
		// validate precondition: cnp must be valid
		assert validateCnp(person.getCnp()) : "cnp must be valid";
		// validate precondition: deposited value must be > 0
		assert money > 0 : "the deposited sum must be > 0";

		double amountDeposited = 0;
		
		HashSet<Account> existingAccounts = new HashSet<Account>(accounts.get(person));
		int initialAccountCount = existingAccounts.size();
		double initialMoney = 0;

		// find the account we want to update
		for (Account acc : existingAccounts) {
			if (acc.getAccountNumber() == accountNumber) {

				initialMoney = acc.getMoney();
				// add money to account
				if (acc instanceof SavingAccount) {
					// for a savingsAccount only 1 deposit is allowed
					// e.g initial balance must be 0
					assert acc
							.getMoney() == 0 : "for a savings account only 1 deposit operation is allowed ==> the initial balance must be 0: actual balance: "
									+ acc.getMoney();
				}
				acc.addMoney(money);
				amountDeposited = money;
			}
		}

		// validate postcondition: the sum of money in that account must be the
		// initial balance + the deposited money value
		for (Account acc : accounts.get(person)) {
			if (acc.getAccountNumber() == accountNumber) {
				assert initialMoney + money == acc
						.getMoney() : "the sum of money in the account must be the initial balance + the deposited money value";
			}
		}

		// validate postcondition: postcondition: the number of accounts must
		// remain the same for that person
		assert accounts.get(person)
				.size() == initialAccountCount : "postcondition: the number of accounts must remain the same for that persons: "
						+ person.getCnp();
		
		return amountDeposited;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double withdrawMoney(int accountNumber, Person person, double money) {
		// validate precondition: cnp must be valid
		assert validateCnp(person.getCnp()) : "cnp must be valid";
		// validate precondition: withdrawn value must be > 0
		assert money > 0 : "the withdrawn sum must be > 0";

		double amountWithdrawn = 0;
		
		HashSet<Account> existingAccounts = new HashSet<Account>(accounts.get(person));
		int initialAccountCount = existingAccounts.size();
		double initialMoney = 0;

		// find the account we want to update
		for (Account acc : existingAccounts) {
			if (acc.getAccountNumber() == accountNumber) {

				initialMoney = acc.getMoney();
				// add money to account
				if (acc instanceof SavingAccount) {
					// for a savingsAccount only 1 withdrawal is allowed
					// e.g all money must be withdrawn at once
					assert acc
							.getMoney() == money : "for a savings account only 1 withdrawal is allowed ==> all the money must be withdrawn at once";

					// adding interest
					((SavingAccount) acc).addInterest();
					amountWithdrawn = acc.getMoney();
					((SavingAccount) acc).withdrawMoney(amountWithdrawn);
				} else {
					amountWithdrawn = money;
					acc.withdrawMoney(money);
				}
			}
		}

		// validate postcondition: the sum of money in that account must be the
		// initial balance + the deposited money value
		for (Account acc : accounts.get(person)) {
			if (acc.getAccountNumber() == accountNumber) {
				if (acc instanceof SpendingAccount) {
					assert initialMoney - money == acc
							.getMoney() : "the sum of money in the account must be the initial balance + the deposited money value";
				}
			}
		}

		// validate postcondition: postcondition: the number of accounts must
		// remain the same for that person
		assert accounts.get(person)
				.size() == initialAccountCount : "postcondition: the number of accounts must remain the same for that persons: "
						+ person.getCnp();
		
		return amountWithdrawn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String generateReport(Person person) {
		// validate precondition: cnp must be valid
		assert validateCnp(person.getCnp()) : "cnp must be valid";

		StringBuilder sb = new StringBuilder();

		Iterator<?> entries = accounts.entrySet().iterator();
		// System.out.println("--------------------------------\n");
		sb.append("--------------------------------\n");

		// iterate over all accounts for the selected client(cnp)
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Person p = (Person) thisEntry.getKey();
			if (p.getCnp() == person.getCnp()) {
				HashSet<Account> personAccounts = (HashSet<Account>) thisEntry.getValue();
				sb.append("Client name: " + p.getName());
				sb.append(", cnp: " + p.getCnp() + "\n");

				for (Account acc : personAccounts) {
					sb.append("account number: " + acc.getAccountNumber() + ", money: " + acc.getMoney() + "\n");
				}

				sb.append("--------------------------------\n");
			}
		}

		return sb.toString();
	}

	/**
	 * @invariant isWellFormed()
	 */
	protected boolean isWellFormed() {
		return accounts != null;
	}

	// helper methods
	private boolean validateCnp(long cnp) {
		if (cnp > 1 && cnp < 9000000000000L) {
			return true;
		} else {
			return false;
		}
	}

	public double getInterest() {
		return interest;
	}

	public Map<Person, HashSet<Account>> getAccounts() {
		return accounts;
	}
	
	public static Bank readBankFile() {
		try {
			FileInputStream fi = new FileInputStream(new File("myBank.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read from bank file
			Bank newBank = (Bank) oi.readObject();

			oi.close();
			fi.close();

			return newBank;

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static void writeBankToFile(Bank bank) {
		try {
			FileOutputStream f = new FileOutputStream(new File("myBank.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write bank to file
			o.writeObject(bank);

			o.close();
			f.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
	}
}

