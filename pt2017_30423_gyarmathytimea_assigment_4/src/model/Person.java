package model;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * Model class for the Person
 * @author Timi
 *
 */
@SuppressWarnings("serial")
public class Person implements Serializable, Observer {
	private long cnp;
	private String name;
	
	/**
	 * Parameterless constructor for the Person class
	 */
	public Person(){
		
	}
	
	/**
	 * Constructor for the Person class
	 */
	public Person(String name, long cnp) {
		super();
		this.name = name;
		this.cnp = cnp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCnp() {
		return cnp;
	}
	public void setCnp(long cnp) {
		this.cnp = cnp;
	}

	@Override
	public void update(Observable o, Object arg) {
		Account acc = (Account)o;
		System.out.println("Notifying Client: " + name + ", acc number: " + acc.getAccountNumber() + ", message: " + arg);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cnp ^ (cnp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (cnp != other.cnp)
			return false;
		return true;
	}
}
