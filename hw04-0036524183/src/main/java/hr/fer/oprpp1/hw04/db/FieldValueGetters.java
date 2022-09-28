package hr.fer.oprpp1.hw04.db;

/**
 * Class for getting different field values.
 *
 */
public class FieldValueGetters {
	/**
	 * {@link IFieldValueGetter} for JMBAG.
	 */
	public static final IFieldValueGetter JMBAG= sr->sr.getJmbag();
	
	/**
	 * {@link IFieldValueGetter} for first name.
	 */
	public static final IFieldValueGetter FIRST_NAME=sr->sr.getFirstName();
	
	/**
	 * {@link IFieldValueGetter} for last name.
	 */
	public static final IFieldValueGetter LAST_NAME=sr->sr.getLastName();	
	
	
}
