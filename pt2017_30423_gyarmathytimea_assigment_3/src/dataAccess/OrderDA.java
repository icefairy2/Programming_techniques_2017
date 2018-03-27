package dataAccess;

import model.OrderDB;

/**
 * Child of AbstractDA data access class, specific to product related actions
 * @author Timi
 *
 */
public class OrderDA extends AbstractDA<OrderDB>{
	
	public OrderDA() {
		super(OrderDB.class);
	}
}
