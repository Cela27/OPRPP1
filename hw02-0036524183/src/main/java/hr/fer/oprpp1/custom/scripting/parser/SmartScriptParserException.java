package hr.fer.oprpp1.custom.scripting.parser;

/**
 * {@link Exception} fpr {@link SmartScriptParser}.
 * @author Antonio
 *
 */
public class SmartScriptParserException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SmartScriptParserException() {
		super();
	}
	
	public SmartScriptParserException(String message) {
		super(message);
	}
}
