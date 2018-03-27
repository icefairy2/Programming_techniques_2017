package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import model.Bank;
import model.Person;
import view.AddPersonFrame;
import view.DeletePersonFrame;
import view.EditPersonsFrame;
import view.ListPersonsFrame;

public class BankControl {
	private static BankControl instance = new BankControl();
	public static Bank bank;
	private static AddPersonFrame apf;
	private static DeletePersonFrame dpf;
	private static EditPersonsFrame epf;
	private static ListPersonsFrame lpf;

	public BankControl() {
		bank = Bank.readBankFile();
	}

	public static void addPerson(AddPersonFrame f) {
		apf = f;
		apf.addSaveListener(instance.new AddPersonToHash());
	}

	public static void deletePerson(DeletePersonFrame f) {
		dpf = f;
		dpf.addDeleteListener(instance.new DeletePersonFromHash());
	}

	public static void editPersons(EditPersonsFrame f) {
		epf = f;
		epf.setList(bank.getListOfPersons());
		epf.addTableModelListener(instance.new EditPersonInfo());
	}

	public static void listPersons(ListPersonsFrame f) {
		lpf = f;
		lpf.setList(bank.getListOfPersons());
	}

	public static void selectPerson(ListPersonsFrame f) {
		lpf = f;
		lpf.setList(bank.getListOfPersons());
		lpf.addListSelectionListener(instance.new AccountOperations());
	}

	class AddPersonToHash implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = apf.getName();
			long cnp = 0;
			try {
				cnp = Long.parseLong(apf.getCNP());
				Person person = new Person(name, cnp);
				bank.addPerson(person);
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "Invalid input: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} catch (AssertionError err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Bank.writeBankToFile(bank);
			apf.dispose();
		}

	}

	class DeletePersonFromHash implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				long cnp = Long.parseLong(dpf.getCNP());
				Person person = bank.getPersonBasedOnCNP(cnp);
				bank.removePerson(person);
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "Invalid input: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} catch (AssertionError err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Bank.writeBankToFile(bank);
			dpf.dispose();
		}

	}

	class EditPersonInfo implements TableModelListener {

		@Override
		public void tableChanged(TableModelEvent arg0) {
			int selectedRow = epf.getTable().getSelectedRows()[0];
			long cnp = (long) epf.getTable().getValueAt(selectedRow, 1);
			String name = (String) epf.getTable().getValueAt(selectedRow, 0);
			try {
				Person per = bank.getPersonBasedOnCNP(cnp);
				per.setName(name);
				epf.setList(bank.getListOfPersons());
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "Invalid input: " + exc.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} catch (AssertionError err) {
				JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Bank.writeBankToFile(bank);
		}
	}

	class AccountOperations implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selectedRow = lpf.getTable().getSelectedRows()[0];
			long cnp;
			try {
				cnp = (long) lpf.getTable().getValueAt(selectedRow, 1);
			} catch (NullPointerException err) {
				JOptionPane.showMessageDialog(null, "Invalid input: " + err.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Person per = bank.getPersonBasedOnCNP(cnp);
			AccountsControl.setPerson(per);
			lpf.dispose();
			MainMenuControl.startAccount(per);
			Bank.writeBankToFile(bank);
		}

	}
}
