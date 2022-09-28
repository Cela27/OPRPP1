package hr.fer.oprpp1.hw04.db;

/**
 * Interface for future field getters.
 */
public interface IFieldValueGetter {
	/**
	 * Gets you field value from {@link StudentRecord}
	 * @param record {@link StudentRecord}
	 * @return asked field value as String
	 */
	public String get(StudentRecord record);
}