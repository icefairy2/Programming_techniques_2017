/**
 * 
 */

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import model.Account;
import model.Bank;
import model.Person;
import model.SavingAccount;
import model.SpendingAccount;

/**
 * @author Timi
 *
 */
public class TestBank {

	@Test
	public void testAddPerson() {
		Person person1 = new Person("John", 12345);
		Person person2 = new Person("Bill", 55555);
		Person person3 = new Person("Thomas", 11111);

		Bank bank = new Bank();
		bank.addPerson(person1);
		bank.addPerson(person2);
		bank.addPerson(person3);

		Map<Person, HashSet<Account>> test = bank.getAccounts();
		Map<Person, HashSet<Account>> golden = new HashMap<Person, HashSet<Account>>();

		golden.put(person1, new HashSet<Account>());
		golden.put(person2, new HashSet<Account>());
		golden.put(person3, new HashSet<Account>());

		Iterator<?> entries1 = test.entrySet().iterator();
		Iterator<?> entries2 = golden.entrySet().iterator();

		// iterate over all accounts for the selected client(cnp)
		while (entries1.hasNext() && entries2.hasNext()) {
			Entry<?, ?> thisEntry1 = (Entry<?, ?>) entries1.next();
			Entry<?, ?> thisEntry2 = (Entry<?, ?>) entries2.next();
			Person p1 = (Person) thisEntry1.getKey();
			Person p2 = (Person) thisEntry2.getKey();

			if (!p1.equals(p2)) {
				fail("Entries are different");
			}
		}

	}

	@Test
	public void testRemovePerson() {
		Person person = new Person("John", 12345);
		Bank bank = new Bank();
		bank.addPerson(person);
		bank.removePerson(person);
		if (bank.getAccounts().containsKey(person)) {
			fail("Key still in the bank");
		}
	}

	@Test
	public void testWithdraw() {
		Person person = new Person("John", 12345);
		Bank bank = new Bank();
		bank.addPerson(person);

		Account spendAccount = new SpendingAccount(500);
		Account saveAccount = new SavingAccount();
		
		bank.addNewAccount(spendAccount, person);
		bank.addNewAccount(saveAccount, person);
		bank.depositMoney(saveAccount.getAccountNumber(), person, 1000);
		
		bank.withdrawMoney(spendAccount.getAccountNumber(), person, 500);
		bank.withdrawMoney(saveAccount.getAccountNumber(), person, 1000);

		if (spendAccount.getMoney() != 0)
			fail("Money lost");
		
		if (saveAccount.getMoney() != 0)
			fail("Money lost");
	}

	@Test
	public void testDeposit() {
		Person person = new Person("John", 12345);
		Bank bank = new Bank();
		bank.addPerson(person);

		Account spendAccount = new SpendingAccount(500);
		Account saveAccount = new SavingAccount();
		bank.addNewAccount(spendAccount, person);
		bank.addNewAccount(saveAccount, person);

		bank.depositMoney(spendAccount.getAccountNumber(), person, 500);
		bank.depositMoney(saveAccount.getAccountNumber(), person, 1000);

		if (spendAccount.getMoney() != 1000)
			fail("Money lost");
		
		if (saveAccount.getMoney() != 1000)
			fail("Money lost");
	}
}
