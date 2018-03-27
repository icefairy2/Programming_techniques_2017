package Model;

/**
 * Subclass for nodes with real coefficients in a polynomial
 * @author Gyarmathy Tímea
 *
 */
public class MonomReal extends Monom{
	private Double coeff;              //the real coefficient of the current node
	
	/**
	 * constructor
	 * @param c --- coefficient, of real (double) type
	 */
	public MonomReal(Double c) {
		super(c);
		coeff=c;
	}

	
	/**
	 * Getter and setter
	 */
	@Override
	public Double getCoeff() {
		return coeff;
	}

	public void setCoeff(Double coeff) {
		this.coeff = coeff;
	}
}
