import java.io.PrintStream;
import Controller.Control;
import Controller.LogWriter;
import View.UserInterface;

/**
 * Main class for assignment 1:
 * Design and implement a simulation application aiming to analyze queuing 
 * based systems for determining and minimizing clients’ waiting time.
 * @author Gyarmathy Tímea
 *
 */

public class Main {

	public static void main(String[] args) {
		UserInterface ui = new UserInterface();	
		//redirecting the system output to the user interface
		System.setOut(new PrintStream(new LogWriter(ui.getLog())));
		Control ctrl = new Control(ui);
		ui.setVisible(true);
	}
}
