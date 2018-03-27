package Model;

import java.util.Date;

/**
 * Class models the data on a customer
 * @author Timi
 *
 */

public class Customer {
	private int custID;
	private Date arrivalTime;
	private Date leavingTime;

	public Customer(int custID, Date arrivalTime, Date leavingTime) {
		this.custID = custID;
		this.arrivalTime = arrivalTime;
		this.leavingTime = leavingTime;
	}

	/**
	 * Getters and setter
	 */
	public int getCustID() {
		return custID;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public Date getLeavingTime() {
		return leavingTime;
	}

	public void setLeavingTime(Date leavingTime) {
		this.leavingTime = leavingTime;
	} 
}
