package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class creates a window for entering data for a new person to be added to the database
 * Window will appear in when in the 'Person' menu, 'Add person' button was pressed
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class MoneyOperationsFrame extends JFrame{
	private JLabel l1 = new JLabel ("Account number: ");
	private JTextField nr = new JTextField(10);
	
	private JLabel l2 = new JLabel ("Money amount: ");
	private JTextField moneya = new JTextField(10);
	
	private JButton withdraw = new JButton("Withdraw");
	private JButton deposit = new JButton("Deposit");
	
	private JPanel panel=new JPanel(new GridBagLayout());
    private GridBagConstraints constr=new GridBagConstraints();
	
	public MoneyOperationsFrame(){
		this.setTitle("Add person");
        this.setSize(300, 300);
        this.setContentPane(panel);
        this.setLocationRelativeTo(null);
		
		constr.gridx=0;
        constr.gridy=0;
        panel.add(l1, constr);
        constr.gridx=1;
        constr.gridy=0;
        panel.add(nr,constr);
        
        constr.gridx=0;
        constr.gridy=1;
        panel.add(l2, constr);
        constr.gridx=1;
        constr.gridy=1;
        panel.add(moneya,constr);
        
        constr.gridx=0;
        constr.gridy=3;
        panel.add(withdraw,constr);
        
        constr.gridx=1;
        constr.gridy=3;
        panel.add(deposit,constr);
        
        this.setVisible(true);
	}
	
	public void addWithdrawListener(ActionListener s) {
        withdraw.addActionListener(s);
    }
	
	public void addDepositListener(ActionListener s) {
        deposit.addActionListener(s);
    }
	
	public String getNumber() {
		return nr.getText().toString();
	}

	public String getAmount() {
		return moneya.getText().toString();
	}
}