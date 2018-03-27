package View;

import java.awt.FlowLayout;

import java.awt.event.ActionListener;

import javax.swing.*;

import Model.Polynomial;

/**
 * Functional user interface for the application
 * @author Gyarmathy Tímea
 *
 */
@SuppressWarnings("serial") //so we don't get warning because of the missing serial number of serializable class

public class UI extends JFrame{

	//List of operations that can be selected
	private static final String[] OPERATIONS={"Add","Subtract","Multiply","Divide","Integrate","Derive"};
	
	//Instruction text for user
	private JLabel instr = new JLabel("<html><BR><BR>Enter the coefficients of the two polynomials,"
			+ " in order, from x^0 to x^n (the highest order element),"
			+ " separated by ONLY ONE whitespace each."
			+ "<BR>Example: enter |1 2 3 4| for 1*x^0+2*x^1+3*x^2+4*x^3 (without the symbols | )<BR><BR></html>");
	
	//Graphical elements regarding the first polynomial
	private JLabel l1 = new JLabel ("First polynomial: ");
	private JTextField firstPol = new JTextField("0");
	private JTextField pol1 = new JTextField();
	
	//Graphical elements regarding the second polynomial
	private JLabel l2 = new JLabel ("Second polynomial: ");
	private JTextField secondPol = new JTextField("0");
	private JTextField pol2 = new JTextField();
	
	//Graphical elements for the drop-down list of operations
	private JLabel oper  = new JLabel("Select operation:");
	private JComboBox<String> selectOperation = new JComboBox<String>(OPERATIONS);
	
	//The compute button
	private JButton compute =  new JButton("Compute");
	
	//Graphical elements regarding the resulted polynomial
	private JLabel lo = new JLabel ("Result: ");
	private JTextField outputPol = new JTextField(35);
	
	public UI(){

		JPanel panelInstr = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panelOp = new JPanel();
		JPanel panelOutput = new JPanel();
	
		//First panel containing the text of instructions
		instr.setHorizontalAlignment(SwingConstants.CENTER);
		panelInstr.add(instr);
		panelInstr.setLayout(new BoxLayout(panelInstr, BoxLayout.X_AXIS));
		
		//Second panel containing the input for the first polynomial
		panel1.add(l1);
		panel1.add(firstPol);
		pol1.setEditable(false);
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
	
		//Third panel containing the input for the seond polynomial
		panel2.add(l2);
		panel2.add(secondPol);
		pol2.setEditable(false);
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		
		//Fourth panel containing the drop-down list and its label
		panelOp.add(oper);
		panelOp.add(selectOperation);
		panelOp.setLayout(new FlowLayout());

		//Fifth panel containing the input for the result polynomial
		outputPol.setEditable(false);
		panelOutput.add(lo);
		panelOutput.add(outputPol);
		panelOutput.setLayout(new BoxLayout(panelOutput, BoxLayout.X_AXIS));
				
		//Setting up the whole frame
		JPanel p = new JPanel();
		p.add(panelInstr);
		p.add(panel1);
		p.add(pol1);
		p.add(panel2);
		p.add(pol2);
		p.add(panelOp);
		p.add(compute);			//button
		p.add(panelOutput);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		this.setSize(500, 500);
        this.setContentPane(p);
        
        this.setTitle("Polynomials");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
	}
	
	/**Action listener on the compute button
	 * @param comp
	 */
	public void addComputeListener(ActionListener comp) {
        compute.addActionListener(comp);
    }
	
	/**
	 * Getters and setters
	 */
	public Polynomial getPol1() {
		Polynomial p1 = new Polynomial(firstPol.getText());
		return p1;
	}

	public void setPol1(Polynomial p1) {
		pol1.setText(p1.toString());
	}

	public Polynomial getPol2() {
		Polynomial p2 = new Polynomial(secondPol.getText());
		return p2;
	}

	public void setPol2(Polynomial p2) {
		pol2.setText(p2.toString());
	}

	public String getSelect() {
		return (String)selectOperation.getSelectedItem();
	}
	
	public void setOutputPol(String result) {
		outputPol.setText(result);;
	}
		
	
	
}
