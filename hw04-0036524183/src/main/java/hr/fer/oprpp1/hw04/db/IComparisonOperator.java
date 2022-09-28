package hr.fer.oprpp1.hw04.db;

/**
 * Interface for future comparison operators.
 */
public interface IComparisonOperator {
	/**
	 * Checks the comparison for given values. 
	 * 
	 * @param value1 String
	 * @param value2 String
	 * @return {@link Boolean} true if satisfied, else false
	 */
	public boolean satisfied(String value1, String value2);
}
