package hr.fer.oprpp1.custom.scripting.lexer;


import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

public class Proba {

	public static void main(String[] args) {
		SmartScriptLexer lexer= new SmartScriptLexer("{$   $}\r\n");
		SmartScriptLexerToken token=lexer.nextToken();
		
		System.out.println(token);
	}

}
