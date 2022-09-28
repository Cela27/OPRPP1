package hr.fer.oprpp1.custom.collections;

/**
 * Exception which is thrown if stack is empty
 *
 */
public class EmptyStackException extends RuntimeException{
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a {@link EmptyStackException} with no detail
	 */
	public EmptyStackException() {
		super("Stack is empty.");
	}
}
