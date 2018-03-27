package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Person;
import view.AddAccountFrame;
import view.AddPersonFrame;
import view.DeleteAccountFrame;
import view.DeletePersonFrame;
import view.EditPersonsFrame;
import view.ListAccountsFrame;
import view.ListPersonsFrame;
import view.MainMenu;
import view.MoneyOperationsFrame;

/**
 * Class controls the main menu user interface
 * @author Timi
 *
 */
public class MainMenuControl {
	private static MainMenu mm;

	@SuppressWarnings("unused")
	private static MainMenuControl instance;
	
	private MainMenuControl() {
		mm = new MainMenu();
		mm.addPersonListener(new PersonApp());
		mm.addAccountListener(new AccountApp());
	}
	
	/**
	 * Creates a new instance of the MainmenuControl
	 */
	public static void start(){
		instance = new MainMenuControl();
	}
	
	public static void startAccount(Person pers){
		mm.addAccountOptionsPanel(pers);
		
		mm.addAddAccountListener(instance.new AddAccount());
		mm.addDeleteAccountListener(instance.new DeleteAccount());
		mm.addEditAccountsListener(instance.new MoneyOperations());
		mm.addListAccountsListener(instance.new ListAccounts());
		
		mm.addBackListener(instance.new BackMenu());
	}
	
	/**
	 * Class implements the persons submenu
	 * @author Timi
	 *
	 */
	class PersonApp implements ActionListener{
		public void actionPerformed(ActionEvent e){
			mm.addPersonOptionsPanel();
			
			mm.addAddPersonListener(new AddPerson());
			mm.addEditPersonsListener(new EditPersons());
			mm.addDeletePersonListener(new DeletePerson());
			mm.addListPersonsListener(new ListPersons());
			
			mm.addBackListener(new BackMenu());
		}
	}
	
	/**
	 * Class implements the accounts submenu
	 * @author Timi
	 *
	 */
	class AccountApp implements ActionListener{
		private ListPersonsFrame lpf;
		
		public void actionPerformed(ActionEvent e){
			lpf = new ListPersonsFrame();
			BankControl.selectPerson(lpf);
		}
	}
	
	//Actions on person data
	
	/**
	 * Class for the add person action
	 * @author Timi
	 *
	 */
	class AddPerson implements ActionListener{
		private AddPersonFrame ap;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ap = new AddPersonFrame();	
			BankControl.addPerson(ap);
		}
	}
	
	/**
	 * Class for the edit persons action
	 * @author Timi
	 *
	 */
	class EditPersons implements ActionListener{
		private EditPersonsFrame ep;
		
		@Override
		public void actionPerformed(ActionEvent e){
			ep = new EditPersonsFrame();
			BankControl.editPersons(ep);
		}
	}
	
	/**
	 * Class for the delete person action
	 * @author Timi
	 *
	 */
	class DeletePerson implements ActionListener{
		private DeletePersonFrame dp;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			dp = new DeletePersonFrame();	
			BankControl.deletePerson(dp);
		}
	}
	
	/**
	 * Class for the list persons action
	 * @author Timi
	 *
	 */
	class ListPersons implements ActionListener{
		private ListPersonsFrame lp;
		
		@Override
		public void actionPerformed(ActionEvent e){
			lp = new ListPersonsFrame();
			BankControl.listPersons(lp);
		}
	}
	
	//Actions on account data
	
		/**
		 * Class for the add person action
		 * @author Timi
		 *
		 */
		class AddAccount implements ActionListener{
			private AddAccountFrame aa;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				aa = new AddAccountFrame();	
				AccountsControl.addAccount(aa);
			}
		}
		
		/**
		 * Class for the edit persons action
		 * @author Timi
		 *
		 */
		class MoneyOperations implements ActionListener{
			private MoneyOperationsFrame mo;
			
			@Override
			public void actionPerformed(ActionEvent e){
				mo = new MoneyOperationsFrame();
				AccountsControl.withdrawDeposit(mo);
			}
		}
		
		/**
		 * Class for the delete person action
		 * @author Timi
		 *
		 */
		class DeleteAccount implements ActionListener{
			private DeleteAccountFrame da;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				da = new DeleteAccountFrame();	
				AccountsControl.deleteAccount(da);
			}
		}
		
		/**
		 * Class for the list persons action
		 * @author Timi
		 *
		 */
		class ListAccounts implements ActionListener{
			private ListAccountsFrame la;
			
			@Override
			public void actionPerformed(ActionEvent e){
				la = new ListAccountsFrame();
				AccountsControl.listAccounts(la);
			}
		}
	
	/**
	 * When back button is pressed, user is redirected to the start of the application
	 * @author Timi
	 *
	 */
	class BackMenu implements ActionListener{
		public void actionPerformed(ActionEvent e){
			mm.dispose();
			instance = new MainMenuControl();
		}
	}
}
