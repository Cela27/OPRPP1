package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class representing variable as element.
 * @author Antonio
 *
 */
public class ElementVariable extends Element{
	
	private String name;
	
	/**
	 * Basic constructor with variable name as parameter.
	 * @param name String
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	/**
	 * Getter of variable name.
	 * @return {@link String} name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return String.valueOf(name);
	}	
}
