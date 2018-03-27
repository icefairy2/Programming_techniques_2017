package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;

import model.Account;
import model.SavingAccount;


/**
 * Class creates an non editable window with the list of accounts in database
 * Window will appear in when in the 'account' menu, 'List accounts' button was pressed
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class ListAccountsFrame extends JFrame {

	private JTable table;
	String[] columnNames = {"Number", "Money", "Type"};
	Object[][] accounts = new Object[100][10];
	
	public ListAccountsFrame() {
		this.setTitle("List accounts");
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(1, 0));
		
		this.setVisible(true);
	}
	
	/**
	 * Populates the view with customers
	 * @param customersList the list of customers to add to the view
	 */
	public void setList(List<Account> accountsList){
		for(int i=0;i<accountsList.size();i++)
		{
			accounts[i][0] = accountsList.get(i).getAccountNumber();
			accounts[i][1] = accountsList.get(i).getMoney();
			if (accountsList.get(i) instanceof SavingAccount)
				accounts[i][2] = "saving";
			else
				accounts[i][2] = "spending";
		}

		table = new JTable(accounts, columnNames){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(true);
		this.setContentPane(scrollPane);
		
		this.repaint();
		this.setVisible(true);
	}
	
	public void addListSelectionListener(ListSelectionListener l){
		table.getSelectionModel().addListSelectionListener(l);;
	}
	
	public JTable getTable() {
		return table;
	}
}