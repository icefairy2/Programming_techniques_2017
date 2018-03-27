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
 * Class creates a window for entering data for a new customer to be added to the database
 * Window will appear in when in the 'Admin' menu, 'Add customer' button was pressed
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class AddCustomerFrame extends JFrame{
	private JLabel l1 = new JLabel ("Full name: ");
	private JTextField name = new JTextField(15);
	
	private JLabel l2 = new JLabel ("E-mail address: ");
	private JTextField e_mail = new JTextField(15);
	
	private JLabel l3 = new JLabel ("Telephone: ");
	private JTextField tel = new JTextField(15);
	
	private JButton save = new JButton("Save");
	
	private JPanel panel=new JPanel(new GridBagLayout());
    private GridBagConstraints constr=new GridBagConstraints();
	
	public AddCustomerFrame(){
		this.setTitle("Customer");
        this.setSize(300, 300);
        this.setContentPane(panel);
        this.setLocationRelativeTo(null);
		
		constr.gridx=0;
        constr.gridy=0;
        panel.add(l1, constr);
        constr.gridx=1;
        constr.gridy=0;
        panel.add(name,constr);
        
        constr.gridx=0;
        constr.gridy=1;
        panel.add(l2, constr);
        constr.gridx=1;
        constr.gridy=1;
        panel.add(e_mail,constr);
        
        constr.gridx=0;
        constr.gridy=2;
        panel.add(l3, constr);
        constr.gridx=1;
        constr.gridy=2;
        panel.add(tel,constr);
        
        constr.gridx=1;
        constr.gridy=3;
        panel.add(save,constr);
        
        constr.gridx=1;
        constr.gridy=4;
        
        this.setVisible(true);
	}
	
	public void addSaveListener(ActionListener s) {
        save.addActionListener(s);
    }
	
	public String getName() {
		return name.getText().toString();
	}

	public String getE_mail() {
		return e_mail.getText().toString();
	}

	public String getTel() {
		return tel.getText().toString();
	}
}
