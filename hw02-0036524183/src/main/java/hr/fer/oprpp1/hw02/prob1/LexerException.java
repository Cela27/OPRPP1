package hr.fer.oprpp1.hw02.prob1;
/**
 * Exception for errors in {@link Lexer}.
 * @author Antonio
 *
 */
public class LexerException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public LexerException() {
		super();
	}
	
	public LexerException(String message) {
		super(message);
	}

}
