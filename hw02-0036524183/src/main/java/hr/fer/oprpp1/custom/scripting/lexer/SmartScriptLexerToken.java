package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.nodes.Node;

/**
 * Clas representing {@link SmartScriptLexer} tokens.
 * @author Antonio
 *
 */
public class SmartScriptLexerToken {
	
	private SmartScriptLexerTokenTypes type;
	private Node node;
	/**
	 * Basic constructor with {@link SmartScriptLexerTokenTypes} and {@link Node} as parameters.
	 * @param type {@link SmartScriptLexerTokenTypes}
	 * @param node {@link Node}
	 */
	public SmartScriptLexerToken(SmartScriptLexerTokenTypes type, Node node) {
		this.type = type;
		this.node = node;
	}
	/**
	 * Getter for type of token.
	 * @return {@link SmartScriptLexerTokenTypes} type
	 */
	public SmartScriptLexerTokenTypes getType() {
		return type;
	}
	/**
	 * Getter for node.
	 * @return {@link Node} node
	 */
	public Node getNode() {
		return node;
	}
}
