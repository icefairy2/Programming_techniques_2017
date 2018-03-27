package Controller;

import Model.*;

/**
 * Class for operations on two monoms (their coefficients to be precise)
 * @author Gyarmathy Tímea
 *
 */

public class MonomOperations {

	/**
	 * This method knows how to add monoms of both Integer and Double types
	 * @param monom1
	 * @param monom2
	 * @return their sum
	 */
	public Monom addMonoms(Monom monom1, Monom monom2)
	{
		if(monom1 instanceof MonomReal || monom2 instanceof MonomReal)
		{
			MonomReal res = new MonomReal(monom1.getCoeff().doubleValue() + monom2.getCoeff().doubleValue());
			return res;
		}
		else
		{
			MonomInteger res = new MonomInteger(monom1.getCoeff().intValue() + monom2.getCoeff().intValue());
			return res;
		}
	}
	
	/**
	 * This method multiplies with a scalar Number monoms of both Integer and Double types
	 * @param monom
	 * @param nr --- Number type 
	 * @return nr*monom --- if monom of type Integer and nr of type Double -> res of type Double
	 */
	public Monom multiplyMonomWithScalar(Monom monom, Number nr)
	{
		if(monom instanceof MonomReal || nr instanceof Double)
		{
			MonomReal res = new MonomReal(monom.getCoeff().doubleValue() * nr.doubleValue());
			return res;
		}
		else
		{
			MonomInteger res = new MonomInteger(monom.getCoeff().intValue() * nr.intValue());
			return res;
		}
	}
	
	/**
	 * This method knows how to multiply monoms of both Integer and Double types
	 * @param monom1
	 * @param monom2
	 * @return their product
	 */
	public Monom multiplyMonoms(Monom monom1, Monom monom2)
	{
		if(monom1 instanceof MonomReal || monom2 instanceof MonomReal)
		{
			MonomReal res = new MonomReal(monom1.getCoeff().doubleValue() * monom2.getCoeff().doubleValue());
			return res;
		}
		else
		{
			MonomInteger res = new MonomInteger(monom1.getCoeff().intValue() * monom2.getCoeff().intValue());
			return res;
		}
	}
}
