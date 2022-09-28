package hr.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerToken;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;


public class SmartScriptingLexerTests {
	
	
	@Test
	public void testConstructorNullArgument() {
		assertThrows(NullPointerException.class, ()->new SmartScriptLexer(null));
	}
	
	@Test
	public void testConstructorEmptyStringArgument() {
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptLexer(""));
	}
	
	@Test
	public void testNextTextToken() {
		SmartScriptLexer lexer= new SmartScriptLexer("  Only text  ");
		SmartScriptLexerToken token = lexer.nextToken();
		TextNode node = (TextNode) token.getNode();
		
		assertTrue(node.getTextAfterParsing().equals("Only text"));
	}
	
	@Test
	public void testNextTextTokenEscape1() {
		SmartScriptLexer lexer= new SmartScriptLexer("  Only text \\{");
		SmartScriptLexerToken token = lexer.nextToken();
		TextNode node = (TextNode) token.getNode();
		
		assertTrue(node.getTextAfterParsing().equals("Only text {"));
	}
	
	@Test
	public void testNextTextTokenEscape2() {
		SmartScriptLexer lexer= new SmartScriptLexer("  Only text  \\\\");
		SmartScriptLexerToken token = lexer.nextToken();
		TextNode node = (TextNode) token.getNode();
		System.out.println(node.getTextAfterParsing());
		assertTrue(node.getTextAfterParsing().equals("Only text \\"));
	}

	@Test
	public void testNextTagTokenOperatorEquals() {
		SmartScriptLexer lexer= new SmartScriptLexer("{$= zmaj * 12 @func \"dan \\\" dan\" $}");
		SmartScriptLexerToken token = lexer.nextToken();
		EchoNode node = (EchoNode) token.getNode();
		
		Element[] elms= node.getElements();
		boolean isTrue=true;
		
		ElementVariable variable=(ElementVariable) elms[0];
		if(!variable.getName().equals("zmaj")) isTrue=false;
		
		ElementOperator operator=(ElementOperator) elms[1];
		if(!operator.getSymbol().equals("*")) isTrue=false;
		
		ElementConstantInteger num=(ElementConstantInteger) elms[2];
		if(!(num.getValue()==12)) isTrue=false;
		
		ElementFunction function=(ElementFunction) elms[3];
		if(!function.getName().equals("func")) isTrue=false;
		
		ElementString string=(ElementString) elms[4];
		if(!string.getValue().equals("dan \" dan")) isTrue=false;
		
		assertTrue(isTrue);
	}

	@Test
	public void testNextTagTokenOperatorNegativeAndDecimalNumbers() {
		SmartScriptLexer lexer= new SmartScriptLexer("{$= -12.8$}");
		SmartScriptLexerToken token = lexer.nextToken();
		EchoNode node = (EchoNode) token.getNode();
		
		Element[] elms= node.getElements();
		boolean isTrue=true;
		
		ElementConstantDouble num2=(ElementConstantDouble) elms[0];
		if(!(num2.getValue()==-12.8)) isTrue=false;
		
		assertTrue(isTrue);
	}
	
	@Test
	public void testNextTagTokenOperator2DotsInNumber() {
		SmartScriptLexer lexer= new SmartScriptLexer("{$= 12..8 $}");
		assertThrows(SmartScriptParserException.class, ()->lexer.nextToken());
	}
	
	@Test
	public void testNextTagTokenAndTextToken() {
		SmartScriptLexer lexer= new SmartScriptLexer("tekst {$= zmaj $}  ");
		
		SmartScriptLexerToken token = lexer.nextToken();
		TextNode node = (TextNode) token.getNode();
		
		SmartScriptLexerToken token2 = lexer.nextToken();
		EchoNode node2 = (EchoNode) token2.getNode();
		
		Element[] elms= node2.getElements();
		
		ElementVariable variable=(ElementVariable) elms[0];
		
		assertEquals("tekstzmaj", node.getTextAfterParsing()+ variable.getName());
	}	
	
	@Test
	public void testNextTagToken2TagTokens() {
		SmartScriptLexer lexer= new SmartScriptLexer("{$= tekst$} {$= zmaj $}  ");
		
		SmartScriptLexerToken token = lexer.nextToken();
		EchoNode node = (EchoNode) token.getNode();
		
		SmartScriptLexerToken token2 = lexer.nextToken();
		EchoNode node2 = (EchoNode) token2.getNode();
		

		Element[] elms= node.getElements();
		
		ElementVariable variable=(ElementVariable) elms[0];
		
		Element[] elms2= node2.getElements();
		
		ElementVariable variable2=(ElementVariable) elms2[0];
		
		assertEquals("tekstzmaj", variable.getName()+ variable2.getName());
	}
	
	@Test
	public void testNextTagTokenAndTextTokenpPlusENDinBetween() {
		SmartScriptLexer lexer= new SmartScriptLexer("tekst {$END$} {$= zmaj $}  ");
		
		SmartScriptLexerToken token = lexer.nextToken();
		TextNode node = (TextNode) token.getNode();
		SmartScriptLexerToken token2 = lexer.nextToken();
		SmartScriptLexerToken token3 = lexer.nextToken();
		EchoNode node2 = (EchoNode) token3.getNode();
		
		
		
		Element[] elms= node2.getElements();
		
		ElementVariable variable=(ElementVariable) elms[0];
		
		assertEquals("tekstENDzmaj", node.getTextAfterParsing()+token2.getType()+ variable.getName());
	}
	
	@Test
	public void testNextTagTokenFor3parametrs() {
		SmartScriptLexer lexer= new SmartScriptLexer("{$FOR zmaj \"string\" -12 $} ");
		
		SmartScriptLexerToken token = lexer.nextToken();
		ForLoopNode node = (ForLoopNode) token.getNode();
		
		ElementVariable variable = node.getVariable();
		ElementString string=(ElementString) node.getStartExpresion();
		
		ElementConstantInteger num=(ElementConstantInteger) node.getEndExpresion();
		String str="";
		if(node.getStepExpresion()==null)
			str="1";
		else
			str="2";
		assertEquals("zmajstring-121", variable.getName()+string.getValue()+num.getValue()+str);
	}
	
	@Test
	public void testNextTagTokenFor4parametrs() {
		SmartScriptLexer lexer= new SmartScriptLexer("{$FOR zmaj \"string\" variable -12 $} ");
		
		SmartScriptLexerToken token = lexer.nextToken();
		ForLoopNode node = (ForLoopNode) token.getNode();
		
		ElementVariable variable = node.getVariable();
		ElementString string=(ElementString) node.getStartExpresion();
		ElementVariable variable2= (ElementVariable) node.getEndExpresion();
		ElementConstantInteger num=(ElementConstantInteger) node.getStepExpresion();
		
		assertEquals("zmajstringvariable-12", variable.getName()+string.getValue()+variable2.getName()+num.getValue());
	}
	
	@Test
	public void testNextTagTokenForExceptionVariable() {
		SmartScriptLexer lexer= new SmartScriptLexer("{$FOR \"zmaj\" \"string\" variable -12 $} ");
		assertThrows(SmartScriptParserException.class, ()->lexer.nextToken());
	}
	
	@Test
	public void testNextTagTokenForExceptionExpresion() {
		SmartScriptLexer lexer= new SmartScriptLexer("{$FOR zmaj @string variable -12 $} ");
		assertThrows(SmartScriptParserException.class, ()->lexer.nextToken());
	}
	
	@Test
	public void testNextTagTokenForExceptionExpresionTooLittleArguments() {
		SmartScriptLexer lexer= new SmartScriptLexer("{$FOR zmaj variable $} ");
		assertThrows(SmartScriptParserException.class, ()->lexer.nextToken());
	}
	
	@Test
	public void testNextTagTokenForAndText() {
		SmartScriptLexer lexer= new SmartScriptLexer("{$for zmaj \"string\" -12 $} tekst");
		
		SmartScriptLexerToken token = lexer.nextToken();
		ForLoopNode node = (ForLoopNode) token.getNode();
		
		ElementVariable variable = node.getVariable();
		ElementString string=(ElementString) node.getStartExpresion();
		
		ElementConstantInteger num=(ElementConstantInteger) node.getEndExpresion();
		String str="";
		if(node.getStepExpresion()==null)
			str="1";
		else
			str="2";
		SmartScriptLexerToken token2 = lexer.nextToken();
		TextNode node2=(TextNode) token2.getNode();
		assertEquals("zmajstring-121tekst", variable.getName()+string.getValue()+num.getValue()+str+node2.getTextAfterParsing());
	}
	
	
	@Test
	public void testNextTagTokenForAndAnotherTag() {
		SmartScriptLexer lexer= new SmartScriptLexer("{$FOR zmaj \"string\" -12 $} {$=zmaj$}");
		
		SmartScriptLexerToken token = lexer.nextToken();
		ForLoopNode node = (ForLoopNode) token.getNode();
		
		ElementVariable variable = node.getVariable();
		ElementString string=(ElementString) node.getStartExpresion();
		
		ElementConstantInteger num=(ElementConstantInteger) node.getEndExpresion();
		String str="";
		if(node.getStepExpresion()==null)
			str="1";
		else
			str="2";
		SmartScriptLexerToken token2 = lexer.nextToken();
		EchoNode node2=(EchoNode) token2.getNode();
		Element[] elms= node2.getElements();
		
		ElementVariable variable2=(ElementVariable) elms[0];
		assertEquals("zmajstring-121zmaj", variable.getName()+string.getValue()+num.getValue()+str+variable2.getName());
	}
	
	@Test
	public void testEmptyTag() {
		SmartScriptLexer lexer= new SmartScriptLexer("{$     $}");
		SmartScriptLexerToken token=lexer.nextToken();
		assertTrue(String.valueOf(token.getType()).equals("EMPTY"));
	}
}
