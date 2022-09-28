package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Class representing function as element.
 * @author Antonio
 *
 */
public class ElementFunction extends Element {
	private String name;
	
	/**
	 * Basic constructor with function name as parameter.
	 * @param name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	/**
	 * Getter for function name.
	 * @return {@link String} function name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return String.valueOf(name);
	}
}
