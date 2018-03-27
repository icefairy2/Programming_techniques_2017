/**
 * 
 */
package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Model.*;


/**
 * @author Gyarmathy Tímea
 *
 */
public class PolynomialTest {

	/**
	 * Test method for {@link Model.Polynomial#Polynomial(java.lang.String)}.
	 */
	@Test
	public void testPolynomial() {
		Polynomial pol  = new Polynomial("1 2 3 4"); 	//1*(x^0) + 2*(x^1) + 3*(x^2) + 4*(x^3)
		ArrayList<Monom> monomAL = pol.getPoli();
		ArrayList<Monom> golden = new ArrayList<Monom>();
		
		golden.add(0,new Monom(1));		//x^0 * 1
		golden.add(1,new Monom(2));		//x^1 * 2
		golden.add(2,new Monom(3));		//x^2 * 3
		golden.add(3,new Monom(4));		//x^3 * 4
		
		for(int i=0; i<golden.size();i++){
			Monom m1 = golden.get(i);
			Monom m2 = monomAL.get(i);
			if (!m1.getCoeff().equals(m2.getCoeff())){
				fail("Elements are different");
			}
		}
		
	}

	/**
	 * Test method for {@link Model.Polynomial#add(Model.Polynomial)}.
	 */
	@Test
	public void testAdd() {
		Polynomial firstPol  = new Polynomial("1 2 3 4"); 
		Polynomial secondPol = new Polynomial("3 2 1");
		
		//our polynomial is created
		Polynomial resultPol = firstPol.add(secondPol);	
		//golden polynomial is created, the correct result for addition
		Polynomial goldenPol = new Polynomial("4 4 4 4");
		
		//comparison
		for(int i=0; i<goldenPol.getPoli().size();i++){
			Monom m1 = goldenPol.getPoli().get(i);
			Monom m2 = resultPol.getPoli().get(i);
			if (!m1.getCoeff().equals(m2.getCoeff())){
				fail("Elements are different");
			}
		}
	}
	
	/**
	 * Test method for {@link Model.Polynomial#subtract(Model.Polynomial)}.
	 */
	@Test
	public void testSubtract() {
		Polynomial firstPol  = new Polynomial("1 2 3 4"); 
		Polynomial secondPol = new Polynomial("3 2 1");
		
		Polynomial resultPol = firstPol.subtract(secondPol);		
		Polynomial goldenPol = new Polynomial("-2 0 2 4");
		
		for(int i=0; i<goldenPol.getPoli().size();i++){
			Monom m1 = goldenPol.getPoli().get(i);
			Monom m2 = resultPol.getPoli().get(i);
			if (!m1.getCoeff().equals(m2.getCoeff())){
				fail("Elements are different");
			}
		}

	}
	
	/**
	 * Test method for {@link Model.Polynomial#multiply(Model.Polynomial)}.
	 */
	@Test
	public void testMultiply() {
		Polynomial firstPol  = new Polynomial("1 2 3 4"); 
		Polynomial secondPol = new Polynomial("3 2 1");
		
		Polynomial resultPol = firstPol.multiply(secondPol);		
		Polynomial goldenPol = new Polynomial("3 8 14 20");
		
		for(int i=0; i<goldenPol.getPoli().size();i++){
			Monom m1 = goldenPol.getPoli().get(i);
			Monom m2 = resultPol.getPoli().get(i);
			if (!m1.getCoeff().equals(m2.getCoeff())){
				fail("Elements are different");
			}
		}
	}

	/**
	 * Test method for {@link Model.Polynomial#divide(Model.Polynomial)}.
	 */
	@Test
	public void testDivide() {
		Polynomial firstPol  = new Polynomial("3 2 1"); 
		Polynomial secondPol = new Polynomial("1 1");
		
		Polynomial resultPol = firstPol.divide(secondPol)[0];		
		Polynomial goldenResPol = new Polynomial("1.0 1.0");
		
		Polynomial remainPol = firstPol.divide(secondPol)[1];		
		Polynomial goldenRemPol = new Polynomial("2");
		
		for(int i=0; i<goldenResPol.getPoli().size();i++){
			Monom m1 = goldenResPol.getPoli().get(i);
			Monom m2 = resultPol.getPoli().get(i);
			if (!m1.getCoeff().equals(m2.getCoeff())){
				fail("Elements are different");
			}
		}
		
		for(int i=0; i<goldenRemPol.getPoli().size();i++){
			Monom m1 = goldenRemPol.getPoli().get(i);
			Monom m2 = remainPol.getPoli().get(i);
			if (!m1.getCoeff().equals(m2.getCoeff())){
				fail("Elements are different");
			}
		}
	}

	/**
	 * Test method for {@link Model.Polynomial#integrate()}.
	 */
	@Test
	public void testIntegrate() {
		Polynomial pol  = new Polynomial("1 2 3 4"); 
		
		Polynomial resultPol = pol.integrate();	
		//System.out.println(resultPol.toString());
		Polynomial goldenPol = new Polynomial("0 1.0 1.0 1.0 1.0");
		//System.out.println(goldenPol.toString());
		
		for(int i=0; i<goldenPol.getPoli().size();i++){
			Monom m1 = goldenPol.getPoli().get(i);
			Monom m2 = resultPol.getPoli().get(i);
			if (!m1.getCoeff().equals(m2.getCoeff())){
				fail("Elements are different");
			}
		}
	}

	/**
	 * Test method for {@link Model.Polynomial#derive()}.
	 */
	@Test
	public void testDerive() {
		Polynomial pol  = new Polynomial("1 2 3 4"); 
		
		Polynomial resultPol = pol.derive();		
		Polynomial goldenPol = new Polynomial("2 6 12");
		
		for(int i=0; i<goldenPol.getPoli().size();i++){
			Monom m1 = goldenPol.getPoli().get(i);
			Monom m2 = resultPol.getPoli().get(i);
			if (!m1.getCoeff().equals(m2.getCoeff())){
				fail("Elements are different");
			}
		}
	}

}
