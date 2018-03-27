package dataAccess;

import model.Customer;

/**
 * Child of AbstractDA data access class, specific to customer related actions
 * @author Timi
 *
 */
public class CustomerDA extends AbstractDA<Customer>{
	
	public CustomerDA() {
		super(Customer.class);
	}
}

