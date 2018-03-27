
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
 * Class creates a window for entering data for a new product to be added to the database
 * Window will appear in when in the 'Admin' menu, 'Add product' button was pressed
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class AddProductFrame extends JFrame {
	private JLabel l1 = new JLabel("Name: ");
	private JTextField name = new JTextField(20);

	private JLabel l2 = new JLabel("Price: ");
	private JTextField price = new JTextField(20);

	private JLabel l3 = new JLabel("Weight: ");
	private JTextField weight = new JTextField(20);
	
	private JLabel l4 = new JLabel("Quantity: ");
	private JTextField quantity = new JTextField(20);

	private JButton save = new JButton("Save");

	private JPanel panel = new JPanel(new GridBagLayout());
	private GridBagConstraints constr = new GridBagConstraints();

	public AddProductFrame(){
		this.setTitle("Product Add");
        this.setSize(300,300);
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
        panel.add(price,constr);
        
        constr.gridx=0;
        constr.gridy=2;
        panel.add(l3, constr);
        constr.gridx=1;
        constr.gridy=2;
        panel.add(weight,constr);
        
        constr.gridx=0;
        constr.gridy=3;
        panel.add(l4, constr);
        constr.gridx=1;
        constr.gridy=3;
        panel.add(quantity,constr);
        
        constr.gridx=1;
        constr.gridy=4;
        panel.add(save,constr);
        
        this.setVisible(true);
	}

	public void addSaveListener(ActionListener s) {
		save.addActionListener(s);
	}

	public String getName() {
		return name.getText().toString();
	}

	public String getPrice() {
		return price.getText().toString();
	}

	public String getWeight() {
		return weight.getText().toString();
	}

	public String getQuantity() {
		return quantity.getText().toString();
	}
}
