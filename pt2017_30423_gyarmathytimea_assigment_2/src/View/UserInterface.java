package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Functional user interface for the application
 * @author Gyarmathy Tímea
 *
 */
@SuppressWarnings("serial") //so we don't get warning because of the missing serial number of serializable class

public class UserInterface extends JFrame{

	private JLabel[][] lArr = new JLabel[51][8];
    
	//Elements regarding the input
	private JLabel l1 = new JLabel ("Min arriving time: ");
	private JTextField minArrTime = new JTextField(3);
	
	private JLabel l2 = new JLabel ("Max arriving time: ");
	private JTextField maxArrTime = new JTextField(3);
	
	private JLabel l3 = new JLabel ("Min service time: ");
	private JTextField minSerTime = new JTextField(3);
	
	private JLabel l4 = new JLabel ("Max service time: ");
	private JTextField maxSerTime = new JTextField(3);
	
	private JLabel l5 = new JLabel ("Number of queues: ");
	private JTextField nrQueues = new JTextField(3);
	
	private JLabel l6 = new JLabel ("Simulation interval: ");
	private JTextField simTime = new JTextField(3);
	
	//Buttons and general data
	private JButton button=new JButton("Start");
	private JButton buttonR=new JButton("Reset");
	private JLabel peak = new JLabel ("Peak time: ");
	private JLabel simT = new JLabel ("Simulation time: 0.0");
	
	//Elements for creating the panel
	private JPanel panel=new JPanel(new GridBagLayout());
    private GridBagConstraints constr=new GridBagConstraints();
    private JPanel panel2=new JPanel(new FlowLayout());
    private JTextArea log=new JTextArea(10,10);
    private JScrollPane scrollV = new JScrollPane(log);
    private JScrollPane scrollH = new JScrollPane(panel);
	
	public UserInterface(){
		
		//Main frame
        this.setTitle("Queue evolution");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200,600);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        scrollH.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollH);
        scrollV.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        log.setEditable(false);
        panel2.add(scrollV, constr);
        getContentPane().add(scrollV);
        
        //Input data
        constr.gridx=0;
        constr.gridy=0;
        panel.add(l1, constr);
        constr.gridx=1;
        constr.gridy=0;
        panel.add(minArrTime,constr);
        
        constr.gridx=0;
        constr.gridy=1;
        panel.add(l2, constr);
        constr.gridx=1;
        constr.gridy=1;
        panel.add(maxArrTime,constr);
        
        constr.gridx=0;
        constr.gridy=2;
        panel.add(l3, constr);
        constr.gridx=1;
        constr.gridy=2;
        panel.add(minSerTime,constr);
        
        constr.gridx=0;
        constr.gridy=3;
        panel.add(l4, constr);
        constr.gridx=1;
        constr.gridy=3;
        panel.add(maxSerTime,constr);
        
        constr.gridx=0;
        constr.gridy=4;
        panel.add(l5, constr);
        constr.gridx=1;
        constr.gridy=4;
        panel.add(nrQueues,constr);
        
        constr.gridx=0;
        constr.gridy=5;
        panel.add(l6, constr);
        constr.gridx=1;
        constr.gridy=5;
        panel.add(simTime,constr);
        
        //Buttons
        constr.gridx=1;
        constr.gridy=6;
        panel.add(button,constr);
        
        constr.gridx=1;
        constr.gridy=7;
        panel.add(buttonR,constr);
        
        //General data
        constr.gridx=1;
        constr.gridy=8;
        panel.add(simT,constr);
        
        constr.gridx=1;
        constr.gridy=9;
        panel.add(peak,constr);
        
        //Allocate the labels for the queue display
        for (int i = 0; i<50; i++)
        	for (int j = 0; j<7; j++)
        		lArr[i][j] = new JLabel();
	}
	
	/**
	 * Listener for the buttons
	 */
	public void addStartListener(ActionListener start) {
        button.addActionListener(start);
    }
	
	public void addResetListener(ActionListener reset) {
        buttonR.addActionListener(reset);
    }
	
	/**
	 * Pops up an error window if invalid input was introduced
	 * @param invalid - the bad input
	 */
	public void showError(String invalid){
		JOptionPane.showMessageDialog(null, "Invalid input: " + invalid, "Error", JOptionPane.ERROR_MESSAGE );
	}
    
	/**
	 * Setters for general data
	 */
	public void setPeak(float peakTime) {
		peak.setText("Peak time: " + peakTime);
	}
	
	public void setCounter(float simTime) {
		simT.setText("Simulation time: " + simTime);
	}

	/**
	 * Method displays the queue and its current state
	 * @param nr
	 * @param avgWait
	 * @param avgServ
	 * @param emptyTime
	 * @param cust
	 * @param size
	 */
	public void drawQueue(int nr, float avgWait, float avgServ, float emptyTime, int cust, int size){		
		lArr[nr][0].setText("Queue "+ nr);
		lArr[nr][1].setText("Average waiting time: "+ avgWait);
		lArr[nr][2].setText("Average service time: "+ avgServ);
		lArr[nr][3].setText("Queue empty time: "+ emptyTime);
		lArr[nr][4].setText("Now serving customer: "+ cust);
		lArr[nr][5].setText("Queue size: "+ size);
		
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i<size; i++)
			sb.append("o ");
		lArr[nr][6].setText(sb.toString());
        
		constr.gridx=2 + nr;
        constr.gridy=0;   
        panel.add(lArr[nr][0],constr);  
        
		constr.gridx=2 + nr;
        constr.gridy=1;   
        panel.add(lArr[nr][1],constr);
        
		constr.gridx=2 + nr;
        constr.gridy=2;   
        panel.add(lArr[nr][2],constr);
        
		constr.gridx=2 + nr;
        constr.gridy=3;   
        panel.add(lArr[nr][3],constr);
        
		constr.gridx=2 + nr;
        constr.gridy=4;   
        panel.add(lArr[nr][4],constr);
        
		constr.gridx=2 + nr;
        constr.gridy=5;   
        panel.add(lArr[nr][5],constr);
        
        constr.gridx=2 + nr;
        constr.gridy=6;   
        panel.add(lArr[nr][6],constr);
		
        this.setVisible(true);
	}
	
	/**
	 * Getters
	 */
	public String getMinArrTime() {
		return minArrTime.getText();
	}

	public String getMaxArrTime() {
		return maxArrTime.getText();
	}

	public String getMinSerTime() {
		return minSerTime.getText();
	}

	public String getMaxSerTime() {
		return maxSerTime.getText();
	}

	public String getNrQueues() {
		return nrQueues.getText();
	}

	public String getSimTime() {
		return simTime.getText();
	}
		
	public JTextArea getLog() {
		return log;
	}
}
