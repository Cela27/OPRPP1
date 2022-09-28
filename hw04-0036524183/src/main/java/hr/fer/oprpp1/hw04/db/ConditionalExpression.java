package hr.fer.oprpp1.hw04.db;

/**
 * Class which uses {@link IFieldValueGetter}, {@link String} and {@link IComparisonOperator} for conditional expresion
 * @author Antonio
 *
 */
public class ConditionalExpression {
	
	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;
	
	/**
	 * {@link ConditionalExpression} constructor with {@link IFieldValueGetter}, {@link String} and {@link IComparisonOperator} as parameters
	 * @param fieldGetter {@link IFieldValueGetter}
	 * @param stringLiteral {@link String}
	 * @param comparisonOperator {@link IComparisonOperator}
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}
	/**
	 * Returns fieldGetter
	 * @return {@link IFieldValueGetter} fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	/**
	 * Returns string literal
	 * @return {@link String} stringLiteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}
	/**
	 * Return comparisonOperator
	 * @return {@link IComparisonOperator} comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
