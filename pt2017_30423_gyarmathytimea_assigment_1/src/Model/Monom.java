package Model;

/**
 * Class for representing a node in a polynomial
 * @author Gyarmathy Tímea
 *
 */
public class Monom {
	private Number coeff;       //the coefficient of the current node
	
	/**
	 * constructor
	 * @param c --- the coefficient
	 */
	public Monom(Number c){
		coeff=c;
	}

	/**
	 * Getter and setter
	 */
	public Number getCoeff() {
		return coeff;
	}

	public void setCoeff(Number coeff) {
		this.coeff = coeff;
	}	
}
