package validators;

/**
 * Data validators, useful when inserting/modifying data in the database
 * @author Timi
 *
 * @param <T> generic object type
 */
public interface Validator<T> {

	public void validate(T t);
}