package Model;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Class models a market and the events in it
 * @author Timi
 *
 */

public class Market extends Thread{
	private int ID = 0;		//holds the serial number of the customer to come
	private boolean plzStahp;
	
	private int nrQueues;
	private Queue queue[];
	private int minArrTime;
	private int maxArrTime;

	/**
	 * Constructor
	 */
	public Market(int nrQueues, Queue queue[], int minArrTime, int maxArrTime) {
		this.nrQueues = nrQueues;
		this.queue = new Queue[nrQueues];
		this.minArrTime = minArrTime * 1000;
		this.maxArrTime = maxArrTime * 1000;
		plzStahp = false;
		
		for (int i = 0; i < nrQueues; i++) {
			this.queue[i] = queue[i];
		}
	}

	/**
	 * Method returns the index of the shortest queue
	 * @return
	 */
	private int minIndex() {
		int index = 0;
		try {
			int min = queue[0].queueLength();
			int length;
			for (int i = 1; i < nrQueues; i++) {
				length = queue[i].queueLength();
				if (length < min) {
					min = length;
					index = i;
				}
			}
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, e.toString(),"Error", JOptionPane.ERROR_MESSAGE );
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, e.toString(),"Error", JOptionPane.ERROR_MESSAGE );
		}
		return index;
	}

	/**
	 * Method returns the number of all customers currently in the market
	 * @return
	 */
	public int sumCustomers() {
		int sum = 0;
		try {
			for (int i = 0; i < nrQueues; i++) {
				sum += queue[i].queueLength();
			}
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, e.toString(),"Error", JOptionPane.ERROR_MESSAGE );
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, e.toString(),"Error", JOptionPane.ERROR_MESSAGE );
		}
		return sum;
	}
	
	@Override
	public void run() {
		try {
			int m, arr;

			//Run the thread until stop command arrives
			while (!plzStahp) {
				//Set random arrival time for next customer
				arr = (int) (minArrTime + Math.random() * (maxArrTime - minArrTime));
				sleep(arr);
				
				//Add him to the shortest queue
				Customer c = new Customer(++ID, new Date(), new Date());
				m = minIndex();
				if (!plzStahp)
					System.out.println("Customer " + Long.toString(ID) + " added to queue: " + Integer.toString(m + 1));
				queue[m].addCustomer(c);
			}
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Getters
	 */
	public Queue[] getQueue() {
		return queue;
	}

	public int getNrQueues() {
		return nrQueues;
	}

	//Setter for stop command
	public void setPlzStahp(boolean plzStahp) {
		this.plzStahp = plzStahp;
	}
}
