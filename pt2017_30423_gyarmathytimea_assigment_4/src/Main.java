import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import controller.MainMenuControl;
import model.Account;
import model.Bank;
import model.Person;
import model.SavingAccount;
import model.SpendingAccount;


public class Main {

	public static void main(String[] args) {

		MainMenuControl.start();

		/*Bank bank = new Bank();
		
		Person person = new Person("Fred", 100);
		
		bank.addPerson(person);
		
		SpendingAccount account = new SpendingAccount(1000, 0);
		Account account2 = new Account(1002, 0);
		SavingAccount account3 = new SavingAccount(1003, 0, 3);
		
		bank.addNewAccount(account, person);
		bank.addNewAccount(account2, person);
		bank.addNewAccount(account3, person);
		
		//bank.generateReport(person.getCnp());
		
		bank.removeAccount(1002, person);
		
		//account3.setMoney(1590);
		
		bank.depositMoney(account.getAccountNumber(), person, 900);
		bank.depositMoney(account.getAccountNumber(), person, 900);
		bank.depositMoney(account.getAccountNumber(), person, 900);
		bank.depositMoney(account3.getAccountNumber(), person, 8000);
		bank.withdrawMoney(account.getAccountNumber(), person, 99);
		bank.withdrawMoney(account3.getAccountNumber(), person, 8000);
		
		//bank.generateReport(person.getCnp());
		
		//Set<Account> accData = bank.getAccountData(person);
		//System.out.println("accdata: ");
		//for(Account acc: accData){
			//System.out.println(acc.getAccountNumber() + ", ");
		//}
		
		writeBankToFile(bank);
		Bank newBank = readBankFile();
		System.out.println(newBank.generateReport(person));*/
		
	}
	
	private static Bank readBankFile()
	{
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
	
	private static void writeBankToFile(Bank bank)
	{
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
