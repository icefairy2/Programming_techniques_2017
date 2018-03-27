package view;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Customer;
import model.Product;

/**
 * Class for the finalize order frame
 */
@SuppressWarnings("serial")
public class FinalizeOrderFrame extends JFrame{
	private int nr;
	private JLabel[] lArr = new JLabel[100];
	private JButton order = new JButton("Order");
	
	private JLabel l1 = new JLabel ("Full name: ");
	private JTextField name = new JTextField();
	
	private JLabel l2 = new JLabel ("E-mail address: ");
	private JTextField e_mail = new JTextField();
	
	private JLabel l3 = new JLabel ("Telefon: ");
	private JTextField tel = new JTextField();
	
	private JPanel panel=new JPanel(new GridBagLayout());
	private JPanel panelOrd = new JPanel(new FlowLayout());
	private JPanel panelProd=new JPanel(new GridBagLayout());
    private GridBagConstraints constr=new GridBagConstraints();
	
	public FinalizeOrderFrame(Customer cust) {
		this.setTitle("Order Finalize");
		this.setSize(600, 300);
		this.setLocation(0, 0);
		this.setLayout(new GridLayout(0, 1));
		
		nr = 0;
		
		constr.gridx=0;
        constr.gridy=0;
        panel.add(l1, constr);
        constr.gridx=1;
        constr.gridy=0;
        name.setText(cust.getName());
        name.setEditable(false);
        panel.add(name,constr);
        
        constr.gridx=0;
        constr.gridy=1;
        panel.add(l2, constr);
        constr.gridx=1;
        constr.gridy=1;
        e_mail.setText(cust.getE_mail());
        e_mail.setEditable(false);
        panel.add(e_mail,constr);
        
        constr.gridx=0;
        constr.gridy=2;
        panel.add(l3, constr);
        constr.gridx=1;
        constr.gridy=2;
        tel.setText(cust.getTelephone());
        tel.setEditable(false);
        panel.add(tel,constr);
        
        this.add(panel);
        panelOrd.add(order);
        this.add(panelOrd);
		
		this.setVisible(true);
	}
	
	public void addOrderListener(ActionListener s) {
		order.addActionListener(s);
	}
	
	public void addProduct(Product prod, int quantity){		
		
		StringBuilder sb = new StringBuilder("");
		sb.append(quantity);
		sb.append(" x ");
		sb.append(prod.getName());
		sb.append(" = ");
		sb.append(quantity*prod.getPrice());
		lArr[nr] = new JLabel(sb.toString());
        
		constr.gridx=0;
        constr.gridy=nr;   
        panelProd.add(lArr[nr], constr);  
        nr++;
        
        this.add(panelProd);
        this.setVisible(true);
	}
	
	public void setOrderTotal(float total){
		lArr[nr] = new JLabel("------------------------------------");
		constr.gridx=0;
        constr.gridy=nr;   
        panelProd.add(lArr[nr], constr);  
		lArr[nr + 1] = new JLabel("Total price: " + total);
		constr.gridx=0;
        constr.gridy=nr + 1;   
        panelProd.add(lArr[nr + 1], constr); 
        this.add(panelProd);
        this.setVisible(true);
	}
}
