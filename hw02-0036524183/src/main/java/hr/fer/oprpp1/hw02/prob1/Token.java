package hr.fer.oprpp1.hw02.prob1;

/**
 * Class representing lexer token.
 * @author Antonio
 *
 */
public class Token {
	private TokenType type;
	private Object value;
	
	/**
	 * Basic constructor with type and value.
	 * @param type {@link TokenType}
	 * @param value {@link Object}
	 */
	public Token(TokenType type, Object value) {
		this.type=type;
		this.value=value;
	}
	/**
	 * Gets type of token.
	 * @return {@link TokenType} type
	 */
	public TokenType getType() {
		return type;
	}
	/**
	 * Gets value of token.
	 * @return {@link Object} value
	 */
	public Object getValue() {
		return value;
	}
	
	
}
