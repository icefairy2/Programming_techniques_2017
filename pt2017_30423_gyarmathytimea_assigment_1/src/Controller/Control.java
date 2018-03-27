package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.Polynomial;
import View.UI;

/**
 * Class controlling the user interface
 * Establishing the connection between the inputs and returning the
 * corresponding output
 * Connecting the view with the model
 * @author Gyarmathy Tímea
 *
 */

public class Control implements ActionListener {

	private UI view;
	
	public Control (UI view){
		this.view = view;
		view.addComputeListener(this);
	}
	
	/**
	 * Method for redirecting if the 'Compute' button was pressed
	 */
	public void actionPerformed(ActionEvent e) {
			performOperation(view.getSelect());
	}
	
	/**
	 * Method for performing the right action on the polynomials
	 * @param operation --- the selected operation from the drop-down list
	 */
	public void performOperation(String operation){
		
		//Creating the two polynomials from the user input
		Polynomial p1 = view.getPol1();
		view.setPol1(p1);		
		Polynomial p2 = view.getPol2();
		view.setPol2(p2);

		//Performing the operation
		switch (operation){
			case "Add" : view.setOutputPol(p1.add(p2).toString()); break;
			case "Subtract" : view.setOutputPol(p1.subtract(p2).toString()); break;
			case "Multiply" : view.setOutputPol(p1.multiply(p2).toString()); break;
			case "Divide" : if (p1.getPoli().size() < p2.getPoli().size())
				view.setOutputPol("Second polynomial is larger, division returns zero.");
		                    else
		                    {
		                    	Polynomial[] qr = p1.divide(p2);
		                    	view.setOutputPol("Q: " + qr[0].toString() + ", R: " + qr[1].toString());
		                    } break;
			case "Integrate" : view.setOutputPol(p1.integrate().toString() + "+C"); break;
			case "Derive" : view.setOutputPol(p1.derive().toString()); break;
			default : break;
		}
	}
}
