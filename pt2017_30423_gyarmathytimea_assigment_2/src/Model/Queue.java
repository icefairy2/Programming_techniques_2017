package Model;

import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Class models a single queue and its events
 * @author Timi
 *
 */
public class Queue extends Thread {
	private Vector customers = new Vector();

	//Variables for creating events
	private int minSerTime;
	private int maxSerTime;
	private int simTime;
	private boolean plzStahp;

	//Variables specific to the queue
	private int ID;
	private float sumWait;
	private int emptyTime;
	private int currCust;
	private int servedCust;
	private int sumServ;

	/**
	 * Constructor
	 */
	public Queue(int ID, int minSerTime, int maxSerTime, int simTime) {
		this.ID = ID + 1;
		this.minSerTime = minSerTime * 1000;
		this.maxSerTime = maxSerTime * 1000;
		this.simTime = simTime * 1000;
		emptyTime = 0;
		currCust = 0;
		servedCust = 0;
		sumServ = 0;
		sumWait = 0;
		plzStahp = false;
	}

	@Override
	public void run() {
		try {
			int ser = 0;
			
			//Run it until stop signal arrives
			while (plzStahp == false) {
				if (customers.size() > 0) {
					
					//Serve the first customer in the queue
					Customer c = (Customer) customers.elementAt(0);
					currCust = (int) c.getCustID();
					
					//Generate a random service time for him
					ser = (int) (minSerTime + Math.random() * (maxSerTime - minSerTime));
					sleep(ser);
					deleteCustomer();
					sumServ += ser;					
				} else {
					
					//No customers in the queue, count empty time
					while (customers.size() == 0) {
						currCust = 0;
						int startTime = (int) System.currentTimeMillis();
						sleep(100);
						emptyTime += (int) System.currentTimeMillis() - startTime;
					}
				}
			}
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, e.toString(),"Error", JOptionPane.ERROR_MESSAGE );
		} catch (Exception excg){}
	}

	//Setter for stopping command
	public void setPlzStahp(boolean plzStahp) {
		this.plzStahp = plzStahp;
	}

	/**
	 * Add a customer to the end of the queue
	 * @param c
	 * @throws InterruptedException
	 */
	public synchronized void addCustomer(Customer c) throws InterruptedException {
		customers.addElement(c);
		notifyAll();
	}

	/**
	 * Remove i.e. serve the customer from the front of the queue
	 * @throws InterruptedException
	 */
	public synchronized void deleteCustomer() throws InterruptedException {
		Customer c = (Customer) customers.elementAt(0);
		customers.removeElementAt(0);
		c.setLeavingTime(new Date());
		servedCust++;
		sumWait += c.getLeavingTime().getTime() - c.getArrivalTime().getTime();
		currCust = (int) c.getCustID();
		if (!plzStahp)
			System.out.println("Customer " + currCust + " served in the queue: " + ID);
		notifyAll();
	}

	/**
	 * Getters
	 * @return
	 */
	public synchronized int queueLength() throws InterruptedException {
		notifyAll();
		int size = customers.size();
		return size;
	}

	public synchronized float getAvgWait() throws InterruptedException {
		notifyAll();
		if (servedCust != 0)
			return (float) (sumWait / servedCust) / 1000;
		else 
			return 0f;
	}

	public synchronized float getAvgServ() throws InterruptedException {
		notifyAll();
		if (servedCust != 0)
			return (float) (sumServ / servedCust) / 1000;
		else
			return 0f;
	}

	public synchronized float getEmptyTime() throws InterruptedException {
		notifyAll();
		return (float) emptyTime / 1000;
	}

	public synchronized int getCurrCust() throws InterruptedException {
		notifyAll();
		return currCust;
	}

	public int getID() {
		return ID;
	}
}
