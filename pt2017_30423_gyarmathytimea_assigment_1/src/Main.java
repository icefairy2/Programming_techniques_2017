import Controller.Control;
import View.*;

/**
 * Main class for assignment 1:
 * Propose, design and implement a system for polynomial processing. 
 * Consider the polynomials of one variable and integer coefficients.
 * @author Gyarmathy Tímea
 *
 */

public class Main {

	public static void main(String[] args) {
		//calling the application user interface and control
		UI ui = new UI();
		Control ctrl = new Control(ui);
		ui.setVisible(true);
	}
}