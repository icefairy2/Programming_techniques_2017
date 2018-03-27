package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import Controller.*;

/**
 * Class for representing a polynomial and the operations on it
 * @author Gyarmathy Tímea
 *
 */

public class Polynomial {

	private List<Monom> monoms = new ArrayList<Monom>();  //list of nodes in the polynomial, the index is their grade
	private MonomOperations monop = new MonomOperations();     //operations on a single node
	
	/**
	 * Constructors
	 * @param polStr --- String containing the coefficients of x, in order from 0..n, separated by a space
	 */
	public Polynomial(String polStr){
		String [] polStrArr = polStr.split(" ");
		for (String it: polStrArr){
			try{
				monoms.add(new MonomInteger(Integer.parseInt(it)));     //creates an integer coeff monom
			}
			catch(NumberFormatException e){
				try{
					monoms.add(new MonomReal(Double.parseDouble(it)));  //creates a double coeff monom
				}
				catch(Exception e2){
					JOptionPane.showMessageDialog(null, "Invalid input: " + polStr, "Error", JOptionPane.ERROR_MESSAGE );
					break;
				}
			}
		}
	}
	
	public Polynomial(){
		}
	
	/**
	 * Method for adding polynomials
	 * @param polyIn --- the polynomial to be added
	 * @return current polynomial + polyIn
	 */
	public Polynomial add(Polynomial polyIn)
	{
		ArrayList<Monom> monomsList1 = (ArrayList<Monom>) monoms;
		ArrayList<Monom> monomsList2 = polyIn.getPoli();
		// Make resulting array big enough to fit both polynomials
		Monom[] resultMonoms = new Monom[Math.max(monomsList1.size(), monomsList2.size())];
		
		// Copy the first monom array into results
		for(Monom monom1: monomsList1)
		{
			int grade = monomsList1.indexOf(monom1);
			resultMonoms[grade]= monom1;
		}
		
		// Add the second monom array to results
		for(Monom monom2: monomsList2)
		{
			int grade = monomsList2.indexOf(monom2);
			
			// If there's NO monom of that grade, just put the object as is
			if(resultMonoms[grade] == null)
			{
				resultMonoms[grade] = monom2;
			}
			// Add values of the two monoms if one exists from the first list
			else
			{
				Monom monom1 = resultMonoms[grade];
				resultMonoms[grade] = monop.addMonoms(monom1, monom2);
			}
		}
		
		// return the result
		List<Monom> resultsAL = new ArrayList<Monom>(Arrays.asList(resultMonoms));
		Polynomial poliRes = new Polynomial();
		poliRes.setPoli((ArrayList<Monom>)resultsAL);
		return poliRes;
	}
	
	/**
	 * Method for subtracting polynomials
	 * @param polyIn --- the polynomial to be subtracted
	 * @return current polynomial - polyIn
	 */
	public Polynomial subtract(Polynomial polyIn)
	{
		ArrayList<Monom> monomsList1 = (ArrayList<Monom>) monoms;
		ArrayList<Monom> monomsList2 = polyIn.getPoli();
		
		ArrayList<Monom> resultsAL = subArrayMonoms(monomsList1, monomsList2);
		
		Polynomial poliRes = new Polynomial();
		poliRes.setPoli(resultsAL);
		return poliRes;
	}
	
	public Polynomial multiply(Polynomial polyIn){
		ArrayList<Monom> monomsList1 = (ArrayList<Monom>) monoms;
		ArrayList<Monom> monomsList2 = polyIn.getPoli();
		// Make resulting array big enough to fit the product of the polynomials
		Monom[] resultMonoms = new Monom[monomsList1.size() + monomsList2.size()];
		
		// Iterate through both polynomials 
		for(Monom monom1: monomsList1)
		{
			for(Monom monom2: monomsList2)
			{
				int grade = monomsList1.indexOf(monom1) + monomsList2.indexOf(monom2);

				// If there's NO monom of that grade, add the new object with the product of the coefficients
				if(resultMonoms[grade] == null)
				{
					resultMonoms[grade] = monop.multiplyMonoms(monom1, monom2);
				}
				// if an object of that grade already exists, add the product to this one
				else
				{
					Monom monomOld = resultMonoms[grade];
					resultMonoms[grade] = monop.addMonoms(monomOld, monop.multiplyMonoms(monom1, monom2));
				}
			}
		}
		
		// return the result
		ArrayList<Monom> resultsAL = new ArrayList<Monom>(Arrays.asList(resultMonoms));
		Polynomial poliRes = new Polynomial();
		poliRes.setPoli(resultsAL);
		return poliRes;
	}
	
	public Polynomial[] divide(Polynomial polyIn){
		ArrayList<Monom> N = (ArrayList<Monom>) monoms;
		ArrayList<Monom> D = polyIn.getPoli();
		ArrayList<Monom> d = new ArrayList<Monom>();
		ArrayList<Monom> r = new ArrayList<Monom>();
		
		Monom[] q = new Monom[Math.max(N.size(), D.size())];
		for(int i=0;i<q.length;i++)
		{
			q[i] = new Monom(0);
		}
				
		//while degree(N) >= degree(D)
		while (degree(N) >= degree(D))
		{
			//d <- D shifted right by (degree(N) - degree(D))
			d = shiftRight(D, degree(N) - degree(D));
			
			//q(degree(N) - degree(D)) <- N(degree(N)) / d(degree(d))
			double nval = N.get(degree(N)).getCoeff().doubleValue();
			double dval = d.get(degree(d)).getCoeff().doubleValue();
			double val = nval/dval;
			q[degree(N) - degree(D)] = new Monom(val);
			
			// by construction, degree(d) = degree(N) of course
			//d <- d * q(degree(N) - degree(D))
			int auxDeg = degree(N) - degree(D);
			Monom aux = q[auxDeg];
			d = multiplyWithMonom(d, aux);
			
		    //N <- N - d
			N = subArrayMonoms(N, d);
		}
		//  r <- N
		r = N;
		
		// return an array of two polynomials {result of division, remainder}
		Polynomial poliRes = new Polynomial();
		ArrayList<Monom> resultQ = new ArrayList<Monom>(Arrays.asList(q));
		poliRes.setPoli(resultQ);
		
		Polynomial poliRem = new Polynomial();
		poliRem.setPoli(r);
		
		Polynomial[] polArr = {poliRes, poliRem};
		return polArr;
	}
	
	public Polynomial integrate(){
		ArrayList<Monom> monomsList = (ArrayList<Monom>) monoms;

		Monom[] resultMonoms = new Monom[monomsList.size() + 1];
		resultMonoms[0] = new Monom(0);
		
		// Integrate all monoms one by one
		for(Monom monom: monomsList)
		{
			int grade = monomsList.indexOf(monom);
			resultMonoms[grade + 1]= monop.multiplyMonomWithScalar(monom, 1.0/(grade+1));
		}
		
		// return the result
		ArrayList<Monom> resultsAL = new ArrayList<Monom>(Arrays.asList(resultMonoms));
		Polynomial poliRes = new Polynomial();
		poliRes.setPoli(resultsAL);
		return poliRes;
	}
	
	public Polynomial derive(){
		ArrayList<Monom> monomsList = (ArrayList<Monom>) monoms;
 
		Monom[] resultMonoms = new Monom[monomsList.size()-1];
		
		// Derive all monoms one by one
		for(Monom monom: monomsList)
		{
			int grade = monomsList.indexOf(monom);
			
			//we skip the constant
			if (grade == 0)
				continue;
			
			resultMonoms[grade - 1]= monop.multiplyMonomWithScalar(monom, grade);
		}
		
		// return the result
		ArrayList<Monom> resultsAL = new ArrayList<Monom>(Arrays.asList(resultMonoms));
		Polynomial poliRes = new Polynomial();
		poliRes.setPoli(resultsAL);
		return poliRes;
	}
	
	/**
	 * Getter and setter
	 * @return
	 */
	public ArrayList<Monom> getPoli() {
		return (ArrayList<Monom>) monoms;
	}

	public void setPoli(ArrayList<Monom> monoms) {
		this.monoms = monoms;
	}

	/**
	 * ToString method to return the literal form of the polynomial
	 */
	@Override
	public String toString() {
		
		String result = new String(
				(monoms.get(monoms.size()-1) != null && monoms.get(monoms.size()-1).getCoeff().doubleValue() != 0) ? (monoms.get(monoms.size()-1).getCoeff().toString() + "*x^" + (monoms.size()-1)) : "");
		for (int i = monoms.size()-2; i>=0; i--){
			String monom = ((monoms.get(i) != null && monoms.get(i).getCoeff().doubleValue() != 0 )? ("+" + monoms.get(i).getCoeff() + "*x^" + i) : "");
			result = result + monom;
		}
		return result;
	}
	
	/**
	 * Helper methods
	 */

	// find max monom list degree (non zero coeff and non null)
	private int degree(ArrayList<Monom> monomList)
	{
		int deg = 0;
		for(int i=0; i < monomList.size(); i++)
		{
			if(monomList.get(i) != null && monomList.get(i).getCoeff().intValue() != 0 )
				deg = i;
		}
		return deg;
	}
	
	private ArrayList<Monom> shiftRight(ArrayList<Monom> monomList, int shift)
	{
		Monom[] result = new Monom[monomList.size() + shift];
		for(int i=0; i < monomList.size(); i++)
		{
			if(monomList.get(i) != null)
			{
				Number coeff = monomList.get(i).getCoeff();
				result[i+shift] = new Monom(coeff);
			}
		}
		
		return new ArrayList<Monom>(Arrays.asList(result));
	}
	
	private ArrayList<Monom> multiplyWithMonom(ArrayList<Monom> monomList, Monom monom)
	{
		Monom[] result = new Monom[monomList.size()];
		for(int i=0; i < monomList.size(); i++)
		{
			if(monomList.get(i) != null)
			{
				Number coeff = monomList.get(i).getCoeff();
				result[i] = new Monom(coeff.doubleValue() * monom.getCoeff().doubleValue());
			}
		}
		
		return new ArrayList<Monom>(Arrays.asList(result));
	}
	
	private ArrayList<Monom> subArrayMonoms(ArrayList<Monom> monomsList1, ArrayList<Monom> monomsList2)
	{
		// Make resulting array big enough to fit both polynomials
		Monom[] resultMonoms = new Monom[Math.max(monomsList1.size(), monomsList2.size())];
		
		// Copy the first monom array into results
		for(Monom monom1: monomsList1)
		{
			int grade = monomsList1.indexOf(monom1);
			resultMonoms[grade]= monom1;
		}
		
		// Add the second monom array to results with a minus sign
		for(Monom monom2: monomsList2)
		{
			int grade = monomsList2.indexOf(monom2);
			
			// If there's NO monom of that grade, put the object with a minus as it is
			if(resultMonoms[grade] == null)
			{
				resultMonoms[grade] = monop.multiplyMonomWithScalar(monom2, -1);
			}
			// subtract values of the two monoms if one exists from the first list
			else
			{
				if(monom2 != null)
				{
					Monom monom1 = resultMonoms[grade];
					resultMonoms[grade] = monop.addMonoms(monom1, monop.multiplyMonomWithScalar(monom2, -1));
				}
			}
		}
		
		// return the result
		return new ArrayList<Monom>(Arrays.asList(resultMonoms));
	}
}

