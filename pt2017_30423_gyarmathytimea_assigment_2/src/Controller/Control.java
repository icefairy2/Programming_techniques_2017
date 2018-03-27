package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.Market;
import Model.Queue;
import View.UserInterface;

/**
 * Main control of the events, creates and starts the threads
 * @author Timi
 *
 */

public class Control {
	private UserInterface ui;
	
	private Market m;
	private ViewControl vc;
	private Queue queues[];

	public Control(UserInterface ui) {
		this.ui = ui;
		ui.addStartListener(new StartApp());
		ui.addResetListener(new ResetApp());
	}
		
	/**
	 * Getter
	 * @return user interface
	 */
	public UserInterface getUI() {
		return ui;
	}
	
	/**
	 * Class for describing the action that should be done
	 * when Start button was pressed
	 * @author Timi
	 *
	 */
    class StartApp implements ActionListener
    {
        public void actionPerformed ( ActionEvent e)  
        {
        	try {
        		//Parsing user input data
        		int minArrTime = Integer.parseInt(ui.getMinArrTime());
        		int maxArrTime = Integer.parseInt(ui.getMaxArrTime());
        		int minSerTime = Integer.parseInt(ui.getMinSerTime());
        		int maxSerTime = Integer.parseInt(ui.getMaxSerTime());
        		int simTime = Integer.parseInt(ui.getSimTime());
        		int nrQueues = Integer.parseInt(ui.getNrQueues());
        		
        		//Validating data
        		if (minArrTime > maxArrTime || minSerTime > maxSerTime || nrQueues == 0){
        			throw new ArrayIndexOutOfBoundsException();
        		}
        		
        		//Creating the queues and starting them one by one
        		int i = 0;
        		queues = new Queue[nrQueues];
        		for (i = 0; i < nrQueues; i++) {
        			queues[i] = new Queue(i, minSerTime, maxSerTime, simTime);
        			//drawQueue(i, avgWait, avgServ, emptyTime, cust, size);
        			ui.drawQueue(queues[i].getID(), 0f, 0f, 0, 0, 0);
        			queues[i].start();
        		}
        		
        		//Creating the market and starting it
        		if(m==null || !m.isAlive())
        		{
	        		m = new Market(nrQueues, queues, minArrTime, maxArrTime);
	        		m.start();
        		}
        		
        		//Starting the dynamic control of the user interface
        		if(vc==null || !vc.isAlive())
        		{
        			vc = new ViewControl(ui, m, simTime);
        			vc.start();
        		}
        	}
        	catch(NumberFormatException exc){
        		ui.showError(exc.getMessage());
        	}
        	catch(ArrayIndexOutOfBoundsException exc2){
        		ui.showError("numerical data");
        	} catch (Exception excg){}
        }
    }
       
	/**
	 * Class for describing the action that should be done
	 * when Start button was pressed
	 * @author Timi
	 *
	 */
    class ResetApp implements ActionListener
    {
    	public void actionPerformed ( ActionEvent e)  
        {
    		// stop queues
    		for (int i = 0; i < queues.length; i++)
    		{
    			if(queues[i] != null && queues[i].isAlive())
    			{
    				queues[i].setPlzStahp(true);
    				queues[i] = null;
    			}
    		}
    		
    		//stop market
    		if(m != null && m.isAlive())
    		{
    			m.setPlzStahp(true);
    		}
    		
    		if(vc != null && vc.isAlive())
    		{
    			vc.setPlzStahp(true);
    		}
    		
    		m = null;
    		vc = null;
    		
    		System.out.println();
        }
    }
}
