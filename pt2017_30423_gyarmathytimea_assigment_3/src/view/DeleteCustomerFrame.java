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
 * Class creates a window for entering id of customer to be deleted from database
 * Window will appear in when in the 'Admin' menu, 'Delete customer' button was pressed
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class DeleteCustomerFrame extends JFrame{
	private JLabel l1 = new JLabel ("Customer ID: ");
	private JTextField id = new JTextField(15);
	
	private JButton delete = new JButton("Delete");
	
	private JPanel panel=new JPanel(new GridBagLayout());
    private GridBagConstraints constr=new GridBagConstraints();
	
	public DeleteCustomerFrame(){
		this.setTitle("Customer");
        this.setSize(300, 300);
        this.setContentPane(panel);
        this.setLocationRelativeTo(null);
		
		constr.gridx=0;
        constr.gridy=0;
        panel.add(l1, constr);
        constr.gridx=1;
        constr.gridy=0;
        panel.add(id,constr);
        
        constr.gridx=1;
        constr.gridy=3;
        panel.add(delete,constr);
        
        constr.gridx=1;
        constr.gridy=4;
        
        this.setVisible(true);
	}
	
	public void addDeleteListener(ActionListener s) {
		delete.addActionListener(s);
    }
	
	public String getId() {
		return id.getText().toString();
	}
}
