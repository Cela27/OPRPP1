package hr.fer.oprpp1.hw04.db;

/**
 * Interface for future filters.
 */
public interface IFilter {
	/**
	 * Returns acceptance of {@link StudentRecord}.
	 * @param record {@link StudentRecord} 
	 * @return true if record is accepted, else false 
	 */
	public boolean accepts(StudentRecord record);
}
