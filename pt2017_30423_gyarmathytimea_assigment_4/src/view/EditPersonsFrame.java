package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;

import model.Person;

/**
 * Class creates an editable window with the list of persons in database
 * Window will appear in when in the 'Person' menu, 'Edit persons' button was pressed
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class EditPersonsFrame extends JFrame {

	private JTable table;
	String[] columnNames = {"Name", "CNP"};
	Object[][] persons = new Object[100][10];
	
	public EditPersonsFrame() {
		this.setTitle("Edit persons");
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(1, 0));
		
		this.setVisible(true);
	}
	
	/**
	 * Populates the view with persons
	 * 
	 * @param personsList
	 *            the list of persons to add to the view
	 */
	public void setList(final List<Person> personsList) {
		
		table = new JTable(persons, columnNames){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return (column == 1)? false:true;
		    }
		};
		
        for (int i = 0; i < personsList.size(); i++) {
			persons[i][0] = personsList.get(i).getName();
			persons[i][1] = personsList.get(i).getCnp();
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
