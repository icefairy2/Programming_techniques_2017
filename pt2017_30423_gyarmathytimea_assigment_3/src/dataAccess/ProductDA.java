package dataAccess;

import model.Product;

/**
 * Child of AbstractDA data access class, specific to product related actions
 * @author Timi
 *
 */
public class ProductDA extends AbstractDA<Product>{

	public ProductDA() {
		super(Product.class);
	}
}
