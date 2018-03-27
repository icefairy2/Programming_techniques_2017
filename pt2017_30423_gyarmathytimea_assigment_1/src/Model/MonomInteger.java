package Model;

/**
 * Subclass for nodes with integer coefficients in a polynomial
 * @author Gyarmathy Tímea
 *
 */
public class MonomInteger extends Monom{
	private Integer coeff;              //the integer coefficient of the current node

	/**
	 * constructor
	 * @param c --- coefficient, must be integer
	 */
	public MonomInteger(Integer c) {
		super(c);
		coeff=c;
	}
	
	/**
	 * Getter and setter
	 */
	@Override
	public Integer getCoeff() {
		return coeff;
	}

	public void setCoeff(Integer coeff) {
		this.coeff = coeff;
	}
}
