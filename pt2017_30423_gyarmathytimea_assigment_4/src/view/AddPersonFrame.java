package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class creates a window for entering data for a new person to be added to the database
 * Window will appear in when in the 'Person' menu, 'Add person' button was pressed
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class AddPersonFrame extends JFrame{
	private JLabel l1 = new JLabel ("Name: ");
	private JTextField name = new JTextField(15);
	
	private JLabel l2 = new JLabel ("CNP: ");
	private JTextField cnp = new JTextField(15);
	
	private JButton save = new JButton("Save");
	
	private JPanel panel=new JPanel(new GridBagLayout());
    private GridBagConstraints constr=new GridBagConstraints();
	
	public AddPersonFrame(){
		this.setTitle("Add person");
        this.setSize(300, 300);
        this.setContentPane(panel);
        this.setLocationRelativeTo(null);
		
		constr.gridx=0;
        constr.gridy=0;
        panel.add(l1, constr);
        constr.gridx=1;
        constr.gridy=0;
        panel.add(name,constr);
        
        constr.gridx=0;
        constr.gridy=1;
        panel.add(l2, constr);
        constr.gridx=1;
        constr.gridy=1;
        panel.add(cnp,constr);
        
        constr.gridx=1;
        constr.gridy=3;
        panel.add(save,constr);
        
        this.setVisible(true);
	}
	
	public void addSaveListener(ActionListener s) {
        save.addActionListener(s);
    }
	
	public String getName() {
		return name.getText().toString();
	}

	public String getCNP() {
		return cnp.getText().toString();
	}
}
