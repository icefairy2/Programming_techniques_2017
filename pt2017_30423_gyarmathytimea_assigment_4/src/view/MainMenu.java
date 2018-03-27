package view;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Person;

/**
 * Main Menu user interface
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class MainMenu extends JFrame{
	//First frame
	private JButton person = new JButton("Person operations");
	private JButton account = new JButton("Account operations");
	
	//Second frame - persons
	private JButton addPerson = new JButton("Add person");
	private JButton editPerson = new JButton("Edit persons");
	private JButton deletePerson = new JButton("Delete person");
	private JButton listPersons = new JButton("List persons");
	
	//Second frame  - accounts
	private JLabel l1 = new JLabel ("Name: ");
	private JTextField name = new JTextField();
	
	private JLabel l2 = new JLabel ("CNP: ");
	private JTextField cnp = new JTextField();
	
	private JButton addAccount  =new JButton("Add account");
	private JButton editAccount = new JButton("Withdraw/deposit money");
	private JButton deleteAccount = new JButton("Delete account");
	private JButton listAccounts = new JButton("List accounts");
	
	private JButton back = new JButton ("Back");
	
	private JPanel p = new JPanel(new GridLayout(2, 0));
	private JPanel pcust = new JPanel(new GridLayout(2, 0));
	private JPanel opt = new JPanel(new GridLayout(2, 0));
	private JPanel pback = new JPanel(new FlowLayout());
	
	private GridBagConstraints constr=new GridBagConstraints();
	
	public MainMenu(){
		
		this.setTitle("Main Menu");
		this.setSize(600, 600);
		this.setContentPane(p);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		p.add(person);
		p.add(account);
		
		this.setVisible(true);
	}
	
	//Listeners for first frame
	public void addAccountListener(ActionListener acc) {
        account.addActionListener(acc);
    }
	
	public void addPersonListener(ActionListener cl) {
        person.addActionListener(cl);
    }
	
	//Listeners for second frame
	/**
	 * Person related listeners
	 */
	public void addAddPersonListener(ActionListener cl) {
        addPerson.addActionListener(cl);
    }

	public void addEditPersonsListener(ActionListener cl) {
        editPerson.addActionListener(cl);
    }

	public void addListPersonsListener(ActionListener cl) {
        listPersons.addActionListener(cl);
    }

	public void addDeletePersonListener(ActionListener cl) {
        deletePerson.addActionListener(cl);
    }
	
	/**
	 * Account related listeners
	 */
	public void addAddAccountListener(ActionListener pr) {
        addAccount.addActionListener(pr);
    }

	public void addEditAccountsListener(ActionListener cl) {
        editAccount.addActionListener(cl);
    }

	public void addListAccountsListener(ActionListener cl) {
        listAccounts.addActionListener(cl);
    }

	public void addDeleteAccountListener(ActionListener cl) {
        deleteAccount.addActionListener(cl);
    }
	
	/**
	 * Back button listener
	 */
	public void addBackListener(ActionListener b) {
        back.addActionListener(b);
    }
	
	/**
	 * Creation of administrative options panel
	 */
	public void addPersonOptionsPanel(){
		opt.removeAll();
		
		opt.add(addPerson);
		opt.add(editPerson);
		opt.add(deletePerson);
		opt.add(listPersons);
		
		pback.add(back);
		
		p.removeAll();
		
		p.add(opt);
		p.add(pback);
		
		p.repaint();
		this.setVisible(true);
	}
	
	public void addAccountOptionsPanel(Person pers){
		pcust.removeAll();
		
		constr.gridx=0;
        constr.gridy=0;
        pcust.add(l1, constr);
        constr.gridx=1;
        constr.gridy=0;
        name.setEditable(false);
        name.setText(pers.getName());
        pcust.add(name,constr);
        
        constr.gridx=0;
        constr.gridy=1;
        pcust.add(l2, constr);
        constr.gridx=1;
        constr.gridy=1;
        cnp.setEditable(false);
        cnp.setText(Long.toString(pers.getCnp()));
        pcust.add(cnp,constr);
		opt.removeAll();
		
		opt.add(addAccount);
		opt.add(editAccount);
		opt.add(deleteAccount);
		opt.add(listAccounts);
		
		pback.add(back);
		
		p.removeAll();
		
		p.add(opt);
		p.add(pback);
		
		p.repaint();
		this.setVisible(true);
	}
}
