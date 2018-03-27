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
 * Class creates a window for entering cnp of person to be deleted from database
 * Window will appear in when in the 'Person' menu, 'Delete person' button was pressed
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class DeletePersonFrame extends JFrame{
	private JLabel l1 = new JLabel ("Person CNP: ");
	private JTextField cnp = new JTextField(15);
	
	private JButton delete = new JButton("Delete");
	
	private JPanel panel=new JPanel(new GridBagLayout());
    private GridBagConstraints constr=new GridBagConstraints();
	
	public DeletePersonFrame(){
		this.setTitle("Delete person");
        this.setSize(300, 300);
        this.setContentPane(panel);
        this.setLocationRelativeTo(null);
		
		constr.gridx=0;
        constr.gridy=0;
        panel.add(l1, constr);
        constr.gridx=1;
        constr.gridy=0;
        panel.add(cnp,constr);
        
        constr.gridx=1;
        constr.gridy=3;
        panel.add(delete,constr);
        
        constr.gridx=1;
        constr.gridy=4;
        
        this.setVisible(true);
	}
	
	public void addDeleteListener(ActionListener s) {
		delete.addActionListener(s);
    }
	
	public String getCNP() {
		return cnp.getText().toString();
	}
}
