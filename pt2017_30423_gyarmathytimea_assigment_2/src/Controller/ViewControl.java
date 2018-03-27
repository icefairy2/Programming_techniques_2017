package Controller;

import java.util.Date;
import Model.Market;
import Model.Queue;
import View.UserInterface;

/**
 * Class dynamically controlling the user interface
 * @author Timi
 *
 */

public class ViewControl extends Thread{
	private UserInterface ui;
	private Market m;
	private int simTime;
	private boolean plzStahp;
	
	public ViewControl(UserInterface ui, Market m, int simTime) {
		this.ui = ui;
		this.m = m;
		this.simTime = simTime * 1000;
		plzStahp = false;
	}
	
	@Override
	public void run(){
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		long peakTime = 0L;
		
		//While simulation time is not over yet
		while (elapsedTime < simTime && !plzStahp)
		{
			int nrQueues = m.getNrQueues();
			Queue[] queues = m.getQueue();
			int sumMax = m.sumCustomers();
			int i;
			try {
				//Refresh the data on the queues
				for (i = 0; i < nrQueues; i++)
					ui.drawQueue(queues[i].getID(), queues[i].getAvgWait(), 
							queues[i].getAvgServ(), queues[i].getEmptyTime(), 
							queues[i].getCurrCust(), queues[i].queueLength());
				sleep(100);
				if (m.sumCustomers() > sumMax){
					peakTime = elapsedTime;
					sumMax = m.sumCustomers();
				}
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			} catch (Exception e){}
			
			elapsedTime = (new Date()).getTime() - startTime;
			ui.setPeak((float) peakTime / 1000);
			ui.setCounter((float) elapsedTime / 1000);
		}
		
		//Stop threads
		Queue[] queues = m.getQueue();
		for (int i = 0; i < m.getNrQueues(); i++)
		{
			queues[i].setPlzStahp(true);
		}
		m.setPlzStahp(true);
		m = null;
	}

	public void setPlzStahp(boolean plzStahp) {
		this.plzStahp = plzStahp;
	}
}
