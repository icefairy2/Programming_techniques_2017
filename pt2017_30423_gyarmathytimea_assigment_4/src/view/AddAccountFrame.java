package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AddAccountFrame extends JFrame{
	private String[] types = { "Saving account", "Spending account"};
	private JComboBox<String> typesList = new JComboBox<String>(types);
	
	private JLabel l1 = new JLabel ("Amount of money: ");
	private JTextField money = new JTextField(10);
	
	private JButton save = new JButton("Save");
	
	private JPanel panel=new JPanel(new GridBagLayout());
    private GridBagConstraints constr=new GridBagConstraints();
	
	public AddAccountFrame(){
		this.setTitle("Add Account");
        this.setSize(300, 300);
        this.setContentPane(panel);
        this.setLocationRelativeTo(null);
		
        constr.gridx=0;
        constr.gridy=0;
        panel.add(typesList, constr);
        
		constr.gridx=0;
        constr.gridy=1;
        panel.add(l1, constr);
        constr.gridx=1;
        constr.gridy=1;
        panel.add(money,constr);
        
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

	public String getMoney() {
		return money.getText().toString();
	}

	public String getTypes() {
		return typesList.getSelectedItem().toString();
	}
}
