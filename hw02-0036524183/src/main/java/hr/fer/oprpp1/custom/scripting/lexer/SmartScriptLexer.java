package hr.fer.oprpp1.custom.scripting.lexer;


import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;


/**
 * Class representing smart script lexer.
 * @author Antonio
 *
 */
public class SmartScriptLexer {
	private char[] data;

	private SmartScriptLexerToken token;

	private int currentIndex;
	
	/**
	 * Basic constructor with text as parameter.
	 * @param text
	 * @throws NullPointerException when text is null
	 * @throws SmartScriptParserException is text is empty
	 */
	public SmartScriptLexer(String text) {
		if (text == null)
			throw new NullPointerException();
		if (text == "")
			throw new SmartScriptParserException();
		data = text.toCharArray();
		currentIndex = 0;
	}
	
	/**
	 * Method returns next token of lexer.
	 * @return {@link SmartScriptLexerToken}
	 */
	public SmartScriptLexerToken nextToken() {
		skipBlanks();
		if (currentIndex >= data.length)
			return null;
		if (data[currentIndex] == '{') {
			return nextTagToken();
		} else {
			return nextTextToken();
		}
	}
	
	/**
	 * Return next tag token of lexer.
	 * @return {@link SmartScriptLexerToken}
	 * @throws SmartScriptParserException when error happens
	 */
	private SmartScriptLexerToken nextTagToken() {
		if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
			currentIndex += 2;
			skipBlanks();
			if (data[currentIndex] == '=') {
				currentIndex++;
				ArrayIndexedCollection elements = equalsTag();

				if (elements.size() == 0)
					throw new SmartScriptParserException();

				Element[] elms = new Element[elements.size()];

				for (int i = 0; i < elements.size(); i++) {
					elms[i] = (Element) elements.get(i);
				}

				if (data[currentIndex + 1] == '}') {
					token = new SmartScriptLexerToken(SmartScriptLexerTokenTypes.ECHO, new EchoNode(elms));
					currentIndex += 2;
					skipBlanks();
				} else {
					throw new SmartScriptParserException();
				}
			}
			else if (String.valueOf(data[currentIndex]).toUpperCase().equals("F")
					&& String.valueOf(data[currentIndex + 1]).toUpperCase().equals("O")
					&& String.valueOf(data[currentIndex + 2]).toUpperCase().equals("R")) {
				currentIndex += 3;
				token = new SmartScriptLexerToken(SmartScriptLexerTokenTypes.FOR, forTag());
			}
			else if (String.valueOf(data[currentIndex]).toUpperCase().equals("E")
					&& String.valueOf(data[currentIndex + 1]).toUpperCase().equals("N")
					&& String.valueOf(data[currentIndex + 2]).toUpperCase().equals("D")) {
				currentIndex = currentIndex + 3;
				endTag();
				skipBlanks();
				token = new SmartScriptLexerToken(SmartScriptLexerTokenTypes.END, new Node());
			}
			else if (data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
				token = new SmartScriptLexerToken(SmartScriptLexerTokenTypes.EMPTY, new Node());
			} else {
				throw new SmartScriptParserException();
			}
		} else {
			throw new SmartScriptParserException();
		}
		return token;
	}
	
	/**
	 * Gets next for tag.
	 * @return {@link ForLoopNode} for tag
	 * @throws SmartScriptParserException when error happens
	 */
	private ForLoopNode forTag() {
		skipBlanks();
		ElementVariable variable;
		if (Character.isLetter(data[currentIndex])) {
			variable = new ElementVariable(getVariable());
		} else {
			throw new SmartScriptParserException();
		}
		skipBlanks();
		Element expresion1;
		if (data[currentIndex] == '"') {
			expresion1 = new ElementString(getString());
			currentIndex++;
		} else if (Character.isLetter(data[currentIndex])) {
			expresion1 = new ElementVariable(getVariable());
		} else if (Character.isDigit(data[currentIndex])) {
			String number = getNumber();
			if (number.contains(".")) {
				expresion1 = new ElementConstantDouble(Double.parseDouble(number));
			} else {
				expresion1 = new ElementConstantInteger(Integer.parseInt(number));
			}
		} else if (data[currentIndex] == '-') {
			if (Character.isDigit(data[currentIndex + 1])) {
				currentIndex++;
				String number = "-" + getNumber();
				if (number.contains(".")) {
					expresion1 = new ElementConstantDouble(Double.parseDouble(number));
				} else {
					expresion1 = new ElementConstantInteger(Integer.parseInt(number));
				}
			} else {
				currentIndex++;
				throw new SmartScriptParserException();
			}
		} else {
			throw new SmartScriptParserException();
		}

		skipBlanks();

		Element expresion2;
		if (data[currentIndex] == '"') {
			expresion2 = new ElementString(getString());
		} else if (Character.isLetter(data[currentIndex])) {
			expresion2 = new ElementVariable(getVariable());
		} else if (Character.isDigit(data[currentIndex])) {
			String number = getNumber();
			if (number.contains(".")) {
				expresion2 = new ElementConstantDouble(Double.parseDouble(number));
			} else {
				expresion2 = new ElementConstantInteger(Integer.parseInt(number));
			}
		} else if (data[currentIndex] == '-') {
			if (Character.isDigit(data[currentIndex + 1])) {
				currentIndex++;
				String number = "-" + getNumber();
				if (number.contains(".")) {
					expresion2 = new ElementConstantDouble(Double.parseDouble(number));
				} else {
					expresion2 = new ElementConstantInteger(Integer.parseInt(number));
				}
			} else {
				currentIndex++;
				throw new SmartScriptParserException();
			}
		} else {
			throw new SmartScriptParserException();
		}
		skipBlanks();

		Element expresion3 = null;
		if (data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
			currentIndex += 2;
			return new ForLoopNode(variable, expresion1, expresion2, expresion3);

		} else {
			if (data[currentIndex] == '"') {
				expresion3 = new ElementString(getString());
			} else if (Character.isLetter(data[currentIndex])) {
				expresion3 = new ElementVariable(getVariable());
			} else if (Character.isDigit(data[currentIndex])) {
				String number = getNumber();
				if (number.contains(".")) {
					expresion3 = new ElementConstantDouble(Double.parseDouble(number));
				} else {
					expresion3 = new ElementConstantInteger(Integer.parseInt(number));
				}
			} else if (data[currentIndex] == '-') {
				if (Character.isDigit(data[currentIndex + 1])) {
					currentIndex++;
					String number = "-" + getNumber();
					if (number.contains(".")) {
						expresion3 = new ElementConstantDouble(Double.parseDouble(number));
					} else {
						expresion3 = new ElementConstantInteger(Integer.parseInt(number));
					}
				} else {
					currentIndex++;
					throw new SmartScriptParserException();
				}
			} else {
				throw new SmartScriptParserException();
			}
		}
		currentIndex += 2;
		return new ForLoopNode(variable, expresion1, expresion2, expresion3);
	}
	/**
	 * Gets next end tag.
	 * @throws SmartScriptParserException when error happens
	 */
	private void endTag() {
		if (data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
			currentIndex += 2;

		} else {
			throw new SmartScriptParserException();
		}
	}
	
	/**
	 * Gets next equals tag as elements of {@link ArrayIndexedCollection}.
	 * @return {@link ArrayIndexedCollection} of elements from equals tag
	 * @throws SmartScriptParserException when error happens
	 */
	private ArrayIndexedCollection equalsTag() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();

		while (data[currentIndex] != '$' && data[currentIndex + 1] != '}') {
			skipBlanks();
			if (Character.isLetter(data[currentIndex])) {
				String variable = getVariable();
				elements.add(new ElementVariable(variable));
			} else if (data[currentIndex] == '@') {
				String function = getFunction();
				elements.add(new ElementFunction(function));
			} else if (data[currentIndex] == '"') {
				String string = getString();
				currentIndex++;
				elements.add(new ElementString(string));
			} else if (Character.isDigit(data[currentIndex])) {
				String number = getNumber();
				if (number.contains(".")) {
					elements.add(new ElementConstantDouble(Double.parseDouble(number)));
				} else {
					elements.add(new ElementConstantInteger(Integer.parseInt(number)));
				}
			} else if (data[currentIndex] == '-') {
				if (Character.isDigit(data[currentIndex + 1])) {
					currentIndex++;
					String number = "-" + getNumber();
					if (number.contains(".")) {
						elements.add(new ElementConstantDouble(Double.parseDouble(number)));
					} else {
						elements.add(new ElementConstantInteger(Integer.parseInt(number)));
					}
				} else {
					currentIndex++;
					elements.add(new ElementOperator("-"));
				}
			} else if (data[currentIndex] == '=' || data[currentIndex] == '*' || data[currentIndex] == '+'
					|| data[currentIndex] == '/') {
				elements.add(new ElementOperator(String.valueOf(data[currentIndex])));
				currentIndex++;
			}

		}
		skipBlanks();
		return elements;

	}
	
	/**
	 * Gets next number.
	 * @return String number
	 * @throws SmartScriptParserException when something is wrong
	 */
	private String getNumber() {
		String number = "";
		int numberOfDots = 0;
		while ((Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') && numberOfDots < 2) {
			if (data[currentIndex] == '.') {
				numberOfDots++;
				if (numberOfDots == 2) {
					throw new SmartScriptParserException();
				}
			}
			number = number + String.valueOf(data[currentIndex]);
			if ((currentIndex + 1) >= data.length) {
				currentIndex++;
				break;
			}
			currentIndex++;
		}
		return number;
	}
	/**
	 * Gets next string.
	 * @return String value
	 * @throws SmartScriptParserException when something is wrong
	 */
	private String getString() {
		currentIndex++;
		String string = "";
		while (data[currentIndex] != '\"') {
			if (data[currentIndex] == '\\') {
				if ((currentIndex + 1) >= data.length)
					throw new SmartScriptParserException();

				if (data[currentIndex + 1] == '\\') {
					string = string + String.valueOf("\\");
					if ((currentIndex + 2) >= data.length) {
						currentIndex += 2;
						break;
					}
					currentIndex += 2;

				} else if (data[currentIndex + 1] == '\"') {
					string = string + String.valueOf("\"");
					if ((currentIndex + 2) >= data.length) {
						currentIndex += 2;
						break;
					}
					currentIndex += 2;

				} else if (data[currentIndex + 1] == '{') {
					string = string + String.valueOf("{");
					if ((currentIndex + 2) >= data.length) {
						currentIndex += 2;
						break;
					}
					currentIndex += 2;

				} else {
					throw new SmartScriptParserException();
				}
			} else {
				string = string + String.valueOf(data[currentIndex]);
				if ((currentIndex + 1) >= data.length) {
					currentIndex++;
					break;
				}
				currentIndex++;
			}
		}
		return string;
	}
	/**
	 * Gets next function.
	 * @return String name
	 * @throws SmartScriptParserException when something is wrong
	 */
	private String getFunction() {
		currentIndex++;
		String function = "";
		if (Character.isLetter(data[currentIndex])) {
			function = function + String.valueOf(data[currentIndex]);
			currentIndex++;
			while (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
					|| data[currentIndex] == '_') {
				function = function + String.valueOf(data[currentIndex]);
				if ((currentIndex + 1) >= data.length) {
					currentIndex++;
					break;
				}
				currentIndex++;
			}
			return function;
		} else {
			throw new SmartScriptParserException();
		}

	}
	/**
	 * Gets next variable.
	 * @return String name
	 * @throws SmartScriptParserException when something is wrong
	 */
	private String getVariable() {
		String variable = String.valueOf(data[currentIndex]);
		currentIndex++;
		while (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '_') {
			variable = variable + String.valueOf(data[currentIndex]);
			if ((currentIndex + 1) >= data.length) {
				currentIndex++;
				break;
			}
			currentIndex++;
		}
		return variable;

	}
	/**
	 * Skips all the blanks
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
	}
	
	/**
	 * Returns next text token of lexer.
	 * @return {@link SmartScriptLexerToken} text token
	 * @throws SmartScriptParserException when error happens.
	 */
	private SmartScriptLexerToken nextTextToken() {
		String text = "";
		skipBlanks();
		while (data[currentIndex] != '{' && currentIndex < (data.length)) {
			if (data[currentIndex] == ' ') {
				text = text + " ";
				skipBlanks();
				if ((currentIndex + 1) >= data.length) {
					currentIndex++;
					break;
				}
			} else if (data[currentIndex] == '\\') {
				if ((currentIndex + 1) >= data.length)
					throw new SmartScriptParserException();

				if (data[currentIndex + 1] == '\\') {
					text = text + String.valueOf("\\");
					if ((currentIndex + 2) >= data.length) {
						currentIndex += 2;
						break;
					}
					currentIndex += 2;
				} else if (data[currentIndex + 1] == '{') {
					text = text + String.valueOf(data[currentIndex + 1]);
					if ((currentIndex + 2) >= data.length) {
						currentIndex += 2;
						break;
					}
					currentIndex += 2;

				} else {
					throw new SmartScriptParserException();
				}
			} else {
				if(data[currentIndex]=='}') {
					currentIndex++;
				}
					
				text = text + String.valueOf(data[currentIndex]);
				if ((currentIndex + 1) >= data.length) {
					currentIndex++;
					break;
				}
				currentIndex++;
			}
		}
		if (text.substring(text.length() - 1, text.length()).equals(" ")) {
			text = text.substring(0, text.length() - 1);
		}

		token = new SmartScriptLexerToken(SmartScriptLexerTokenTypes.TEXT, new TextNode(text));
		return token;
	}

}
