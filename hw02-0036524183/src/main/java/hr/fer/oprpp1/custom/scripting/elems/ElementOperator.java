package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Class representing operator as element.
 * @author Antonio
 *
 */
public class ElementOperator extends Element{
	
	private String symbol;
	
	/**
	 * Basic constructor with a symbol for operator as parameter.
	 * @param symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	/**
	 * Getter for symbol of operator.
	 * @return {@link String} symobol
	 */
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String asText() {
		return String.valueOf(symbol);
	}
	
	
}
