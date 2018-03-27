package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Product;

/**
 * UI class used to create the order product frame
 *
 */
@SuppressWarnings("serial")
public class OrderProductFrame extends JFrame{
	private JLabel l1 = new JLabel("Name: ");
	private JTextField name = new JTextField();

	private JLabel l2 = new JLabel("Price: ");
	private JTextField price = new JTextField();

	private JLabel l3 = new JLabel("Weight: ");
	private JTextField weight = new JTextField();
	
	private JLabel l4 = new JLabel("Quantity: ");
	private JTextField quantity = new JTextField(10);

	private JButton save = new JButton("Add to order");

	private JPanel panel = new JPanel(new GridBagLayout());
	private GridBagConstraints constr = new GridBagConstraints();
	
	private Product prod;
	
	public OrderProductFrame(Product prod){
		this.prod = prod;
		this.setTitle("Product Order");
        this.setSize(300,300);
        this.setContentPane(panel);
		this.setLocationRelativeTo(null);
        
		constr.gridx=0;
        constr.gridy=0;
        panel.add(l1, constr);
        constr.gridx=1;
        constr.gridy=0;
        name.setText(prod.getName());
        name.setEditable(false);
        panel.add(name,constr);
        
        constr.gridx=0;
        constr.gridy=1;
        panel.add(l2, constr);
        constr.gridx=1;
        constr.gridy=1;
        price.setText(Float.toString(prod.getPrice()));
        price.setEditable(false);
        panel.add(price,constr);
        
        constr.gridx=0;
        constr.gridy=2;
        panel.add(l3, constr);
        constr.gridx=1;
        constr.gridy=2;
        weight.setText(Float.toString(prod.getWeight()));
        weight.setEditable(false);
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
	
	public String getQuantity() {
		return quantity.getText().toString();
	}

	public Product getProd() {
		return prod;
	}
}
