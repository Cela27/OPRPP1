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
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptLexerNew {
	private char[] data;

	private SmartScriptLexerToken token;

	int currentIndex;

	public SmartScriptLexerNew(String text) {
		if (text == null)
			throw new NullPointerException();
		data = text.toCharArray();
		currentIndex = 0;
	}

	public SmartScriptLexerToken nextToken() {
		if (data[currentIndex] == '{') {
			return nextTagToken();
		} else {
			return nextTextToken();
		}
	}

	private SmartScriptLexerToken nextTagToken() {
		if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
			skipBlanks();
			currentIndex += 2;

			// Checking for diferent tags
			// = tag
			if (data[currentIndex] == '=') {
				currentIndex++;
				ArrayIndexedCollection elements=equalsTag();
				if (elements.size()==0) throw new SmartScriptParserException();
				Element[] elms= (Element[]) elements.toArray();
				
				if(data[currentIndex+1]=='}') {
					token = new SmartScriptLexerToken(SmartScriptLexerTokenTypes.ECHO,
							new EchoNode(elms));
				}
				else {
					throw new SmartScriptParserException();
				}
			}
			// for tag
			else if (String.valueOf(data[currentIndex]).toUpperCase() == "F"
					&& String.valueOf(data[currentIndex + 1]).toUpperCase() == "O"
					&& String.valueOf(data[currentIndex + 2]).toUpperCase() == "R") {
				currentIndex+=3;
				token =new SmartScriptLexerToken(SmartScriptLexerTokenTypes.FOR, forTag());
			}
			// END TAG
			else if (String.valueOf(data[currentIndex]).toUpperCase() == "E"
					&& String.valueOf(data[currentIndex + 1]).toUpperCase() == "N"
					&& String.valueOf(data[currentIndex + 2]).toUpperCase() == "D") {
				currentIndex+=3;
				endTag();
			}

		} else {
			throw new SmartScriptParserException();
		}
		return token;
	}

	private ForLoopNode forTag() {
		ElementVariable variable=new ElementVariable(getVariable()); 
		skipBlanks();
		Element expresion1;
		if(data[currentIndex]=='"') {
			expresion1 =new ElementString(getString()); 
		}
		else if(Character.isLetter(data[currentIndex])) {
			expresion1 =new ElementVariable(getVariable()); 
		}else if(Character.isLetter(data[currentIndex])){
			String number = getNumber();
			if (number.contains(".")) {
				expresion1= new ElementConstantDouble(Double.parseDouble(number));
			} else {
				expresion1= new ElementConstantInteger(Integer.parseInt(number));
			}
		}else if (data[currentIndex] == '-') {
			if (Character.isDigit(data[currentIndex + 1])) {
				String number = "-" + getNumber();
				if (number.contains(".")) {
					expresion1= new ElementConstantDouble(Double.parseDouble(number));
				} else {
					expresion1= new ElementConstantInteger(Integer.parseInt(number));
				}
			} else {
				currentIndex++;
				throw new SmartScriptParserException();
			}
		}else {
			throw new SmartScriptParserException();
		} 
		
		skipBlanks();
		//get second expresion
		
		Element expresion2;
		if(data[currentIndex]=='"') {
			expresion2 =new ElementString(getString()); 
		}
		else if(Character.isLetter(data[currentIndex])) {
			expresion2 =new ElementVariable(getVariable()); 
		}else if(Character.isLetter(data[currentIndex])){
			String number = getNumber();
			if (number.contains(".")) {
				expresion2= new ElementConstantDouble(Double.parseDouble(number));
			} else {
				expresion2= new ElementConstantInteger(Integer.parseInt(number));
			}
		}else if (data[currentIndex] == '-') {
			if (Character.isDigit(data[currentIndex + 1])) {
				String number = "-" + getNumber();
				if (number.contains(".")) {
					expresion2= new ElementConstantDouble(Double.parseDouble(number));
				} else {
					expresion2= new ElementConstantInteger(Integer.parseInt(number));
				}
			} else {
				currentIndex++;
				throw new SmartScriptParserException();
			}
		}
		else {
			throw new SmartScriptParserException();
		}
		skipBlanks();
		//3rd if needed
		Element expresion3=null;
		if(data[currentIndex]=='$' && data[currentIndex+1]=='}') {
			return new ForLoopNode(variable, expresion1, expresion2, expresion3);
		}else {
			if(data[currentIndex]=='"') {
				expresion3 =new ElementString(getString()); 
			}
			else if(Character.isLetter(data[currentIndex])) {
				expresion3 =new ElementVariable(getVariable()); 
			}else if(Character.isLetter(data[currentIndex])){
				String number = getNumber();
				if (number.contains(".")) {
					expresion3= new ElementConstantDouble(Double.parseDouble(number));
				} else {
					expresion3= new ElementConstantInteger(Integer.parseInt(number));
				}
			}else if (data[currentIndex] == '-') {
				if (Character.isDigit(data[currentIndex + 1])) {
					String number = "-" + getNumber();
					if (number.contains(".")) {
						expresion3= new ElementConstantDouble(Double.parseDouble(number));
					} else {
						expresion3= new ElementConstantInteger(Integer.parseInt(number));
					}
				} else {
					currentIndex++;
					throw new SmartScriptParserException();
				}
			}
			else {
				throw new SmartScriptParserException();
			}
		}
		return new ForLoopNode(variable, expresion1, expresion2, expresion3);
	}

	private void endTag() {
		if(data[currentIndex]=='$' && data[currentIndex+1]=='}') {
			currentIndex=+2;
		}
		else {
			throw new SmartScriptParserException();
		}
		
	}

	private ArrayIndexedCollection equalsTag() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();

		while (data[currentIndex] != '$') {
			skipBlanks();
			if (Character.isLetter(data[currentIndex])) {
				String variable=getVariable();
				elements.add(new ElementVariable(variable)); 
			} else if (data[currentIndex] == '@') {
				String function = getFunction();
				elements.add(new ElementFunction(function));
			} else if (data[currentIndex] == '"') {
				String string = getString();
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
		return elements;

	}

	private String getNumber() {
		String number = "";
		int numberOfDots = 0;
		while (Character.isDigit(data[currentIndex]) && numberOfDots < 2) {
			if (data[currentIndex] == '.') {
				numberOfDots++;
				if (numberOfDots == 2) {
					throw new SmartScriptParserException("Worong number format");
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

	private String getString() {
		currentIndex++;
		String string = "";
		while (data[currentIndex] != '"') {
			if (data[currentIndex] == '\\') {
				if ((currentIndex + 1) >= data.length)
					throw new SmartScriptParserException();

				if (data[currentIndex + 1] == '\\') {
					currentIndex += 2;
					string = string + String.valueOf("\\");
				} else if (data[currentIndex + 1] != '"') {
					string = string + String.valueOf(data[currentIndex + 1]);
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

	private SmartScriptLexerToken nextTextToken() {
		String text = "";
		while (data[currentIndex] != '{') {
			if (data[currentIndex] == '\\') {
				if ((currentIndex + 1) >= data.length)
					throw new SmartScriptParserException();

				if (data[currentIndex + 1] == '\\') {
					currentIndex += 2;
					text = text + String.valueOf("\\");
				} else if (data[currentIndex + 1] != '{') {
					text = text + String.valueOf(data[currentIndex + 1]);
					currentIndex += 2;
				} else {
					throw new SmartScriptParserException();
				}
			} else {
				text = text + String.valueOf(data[currentIndex]);
				if ((currentIndex + 1) >= data.length) {
					currentIndex++;
					break;
				}
				currentIndex++;
			}
		}
		token = new SmartScriptLexerToken(SmartScriptLexerTokenTypes.TEXT, new TextNode(text));
		return token;
	}

}
