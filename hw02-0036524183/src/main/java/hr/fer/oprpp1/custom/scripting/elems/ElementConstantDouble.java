package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class representing double as element.
 * @author Antonio
 *
 */
public class ElementConstantDouble extends Element{
	
	private double value;
	
	/**
	 * Basic constructor with {@link Double} as parameter.
	 * @param value {@link Double}
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}
	/**
	 * Getter for value.
	 * @return {@link Double} value
	 */
	public double getValue() {
		return value;
	}
	
	
}
