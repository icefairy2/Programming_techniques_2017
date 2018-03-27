package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import model.Product;

/**
 * Class creates an editable window with the list of Products in database
 * Window will appear in when in the 'Admin' menu, 'Edit Product' button was pressed
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class EditProductsFrame extends JFrame {

	private JTable table;
	String[] columnNames = { "ID", "Name", "Price", "Weight", "Quantity"};
	Object[][] products = new Object[100][10];
	
	public EditProductsFrame() {
		this.setTitle("Products");
		this.setSize(600, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(1, 0));
		
		this.setVisible(true);
	}
	
	/**
	 * Populates the view with products
	 * @param productsList the list of products to add to the view
	 */
	public void setList(final List<Product> productsList) {
		
		table = new JTable(products, columnNames){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return (column == 0)? false:true;
		    }
		};
		
        for (int i = 0; i < productsList.size(); i++) {
			products[i][0] = productsList.get(i).getID();
			products[i][1] = productsList.get(i).getName();
			products[i][2] = productsList.get(i).getPrice();
			products[i][3] = productsList.get(i).getWeight();
			products[i][4] = productsList.get(i).getQuantity();
		}
        
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(true);
		this.setContentPane(scrollPane);

		this.repaint();
		this.setVisible(true);
	}
	
	public void addTableModelListener(TableModelListener l){
		table.getModel().addTableModelListener(l);
	}

	public JTable getTable() {
		return table;
	}
}
