package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Class representing integer as element.
 * @author Antonio
 *
 */
public class ElementConstantInteger extends Element {
	private int value;
	/**
	 * Basic constructor with {@link Integer} as parameter.
	 * @param value {@link Integer}
	 */
	public ElementConstantInteger(int value) {
		this.value=value;
	}
	
	/**
	 * Getter for value.
	 * @return {@link Integer} value
	 */
	public int getValue() {
		return value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
