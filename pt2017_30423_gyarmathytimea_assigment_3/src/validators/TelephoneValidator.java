package validators;

import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import model.Customer;

/**
 * Telephone number validator: verifies correctness of data
 * @author Timi
 *
 */
public class TelephoneValidator implements Validator<Customer>{
	private static final String TEL_PATTERN = "\\d{10}";
			
	public void validate(Customer t) {
		Pattern pattern = Pattern.compile(TEL_PATTERN);
		if (!pattern.matcher(t.getTelephone()).matches()) {
			throw new IllegalArgumentException("Telephone is not a valid telephone!");
		}
	}
}
