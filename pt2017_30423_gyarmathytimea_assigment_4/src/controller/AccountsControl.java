package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Account;
import model.Bank;
import model.Person;
import model.SavingAccount;
import model.SpendingAccount;
import view.AddAccountFrame;
import view.DeleteAccountFrame;
import view.ListAccountsFrame;
import view.MoneyOperationsFrame;

public class AccountsControl {
	private static AccountsControl instance = new AccountsControl();

	private static Person person;
	private static AddAccountFrame aaf;
	private static DeleteAccountFrame daf;
	private static ListAccountsFrame laf;
	private static MoneyOperationsFrame mof;

	public static Person getPerson() {
		return person;
	}

	public static void setPerson(Person person) {
		AccountsControl.person = person;
	}

	public static void addAccount(AddAccountFrame f) {
		aaf = f;
		aaf.addSaveListener(instance.new AddAccountToHash());
	}

	public static void deleteAccount(DeleteAccountFrame f) {
		daf = f;
		daf.addDeleteListener(instance.new DeleteAccountFromHash());
	}

	public static void listAccounts(ListAccountsFrame f) {
		laf = f;
		laf.setList(BankControl.bank.getAccountData(person));
	}

	public static void withdrawDeposit(MoneyOperationsFrame f) {
		mof = f;
		mof.addWithdrawListener(instance.new WithdrawMoney());
		mof.addDepositListener(instance.new DepositMoney());
	}

	class AddAccountToHash implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String type = aaf.getTypes();
			double money = 0;
			try {
				money = Double.parseDouble(aaf.getMoney());
				Account acc;
				if (type.equals("Spending account"))
					acc = new SpendingAccount(money);
				else
					acc = new SavingAccount(money, BankControl.bank.getInterest());
				BankControl.bank.addNewAccount(acc, person);
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "Invalid input: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} catch (AssertionError err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Bank.writeBankToFile(BankControl.bank);
			aaf.dispose();
		}

	}

	class DeleteAccountFromHash implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				int nr = Integer.parseInt(daf.getNumber());
				BankControl.bank.removeAccount(nr, person);
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "Invalid input: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} catch (AssertionError err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Bank.writeBankToFile(BankControl.bank);
			daf.dispose();
		}

	}

	class WithdrawMoney implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int accNr;
			double money;
			try {
				accNr = Integer.parseInt(mof.getNumber());
				money = Double.parseDouble(mof.getAmount());
				double amount = BankControl.bank.withdrawMoney(accNr, person, money);
				if (amount > 0)
					JOptionPane.showMessageDialog(null, amount + " succesfully withdrawn");
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "Invalid input: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} catch (AssertionError err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Bank.writeBankToFile(BankControl.bank);
		}

	}

	class DepositMoney implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int accNr;
			double money;
			try {
				accNr = Integer.parseInt(mof.getNumber());
				money = Double.parseDouble(mof.getAmount());
				double amount = BankControl.bank.depositMoney(accNr, person, money);
				if (amount > 0)
					JOptionPane.showMessageDialog(null, amount + " succesfully deposited");
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "Invalid input: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} catch (AssertionError err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Bank.writeBankToFile(BankControl.bank);
		}

	}
}
