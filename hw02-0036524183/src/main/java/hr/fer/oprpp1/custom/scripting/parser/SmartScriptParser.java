package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerToken;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerTokenTypes;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;

/**
 * Class representing smart script parser.
 * @author Antonio
 *
 */
public class SmartScriptParser {
	private String text;
	private DocumentNode documentNode;
	
	/**
	 * Basic constructor with text as parameter.
	 * @param text String which will be parsed
	 */
	public SmartScriptParser(String text) {
		this.text = text;
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		ObjectStack stack = new ObjectStack();

		stack.push(new DocumentNode());

		while (true) {
			SmartScriptLexerToken token = lexer.nextToken();
			if (token == null) {
				break;
			}

			if (token.getType().equals(SmartScriptLexerTokenTypes.TEXT)
					|| token.getType().equals(SmartScriptLexerTokenTypes.EMPTY)
					|| token.getType().equals(SmartScriptLexerTokenTypes.ECHO)) {

				Node node = (Node) stack.pop();
				node.addChildNode(token.getNode());
				stack.push(node);

			} else if (token.getType().equals(SmartScriptLexerTokenTypes.FOR)) {
				ForLoopNode node = (ForLoopNode) token.getNode();
				stack.push(node);

			} else if (token.getType().equals(SmartScriptLexerTokenTypes.END)) {
				ForLoopNode node = (ForLoopNode) stack.pop();
				Node parentNode = (Node) stack.pop();
				parentNode.addChildNode(node);
				stack.push(parentNode);
			} else {
				throw new SmartScriptParserException();
			}
		}
		Node node = (Node) stack.pop();

		documentNode = (DocumentNode) node;

	}
	
	/**
	 * Gets document node after parsing the text.
	 * @return {@link DocumentNode} 
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
}
