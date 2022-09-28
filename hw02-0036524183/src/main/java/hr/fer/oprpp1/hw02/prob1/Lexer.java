package hr.fer.oprpp1.hw02.prob1;

/**
 * Class representing lexer.
 * @author Antonio
 *
 */
public class Lexer {
	
	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;
	private boolean switchedState=false;
	
	/**
	 * Basic constructor with text as parameter.
	 * @param text {@link String}
	 */
	public Lexer(String text) {
		if (text == null)
			throw new NullPointerException();
		data = text.toCharArray();
		state = LexerState.BASIC;
		currentIndex = 0;
	}

	/**
	 * Gets next lexer token.
	 * @return {@link Token}
	 * @throws LexerException
	 */
	public Token nextToken() {
		if (state == null)
			throw new NullPointerException();
		if (state == LexerState.BASIC) {
			token = nextTokenBasicRules();
		} else if (state == LexerState.EXTENDED) {
			token = nextTokenExtendedRules();
		}
		return token;
	}
	
	/**
	 * Gets next lexer token using extended rules.
	 * @return {@link Token}
	 * @throws LexerException
	 */
	private Token nextTokenExtendedRules() {
		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException("Token was expected but null was returned.");

		skipBlanks();

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		String word="";
		while(data[currentIndex] != ' ' && data[currentIndex] != '\t' 
				&& data[currentIndex] != '\r' && data[currentIndex] != '\n') {
			if(data[currentIndex]=='#') {
				setState(LexerState.BASIC);
;
				currentIndex++;
				if(word!="") {
					switchedState=true;
					token= new Token(TokenType.WORD, word);
					return token;
				}else {
					switchedState=false;
					token = new Token(TokenType.SYMBOL, '#');
					return token;
				}
			}
			
			word=word+String.valueOf(data[currentIndex]);
			if ((currentIndex + 1) >= data.length) {
				currentIndex++;
				break;
			}
			currentIndex++;
		}
		token= new Token(TokenType.WORD, word);
		return token;
	}
	/**
	 * Gets next lexer token using basic rules.
	 * @return {@link Token}
	 * @throws LexerException
	 */
	public Token nextTokenBasicRules() {

		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException("Token was expected but null was returned.");

		skipBlanks();
		
		if(switchedState) {
			switchedState=false;
			token = new Token(TokenType.SYMBOL, '#');
			return token;
		}
		
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		//dodaj promjenu stanja
		// ako \ na kraju jel exception?
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			String word = "";
			while (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
				if (data[currentIndex] == '\\') {
					if ((currentIndex + 1) >= data.length)
						throw new LexerException();

					if (data[currentIndex + 1] == '\\') {
						currentIndex += 2;
						word = word + String.valueOf("\\");
					} else if (Character.isDigit(data[currentIndex + 1])) {
						word = word + String.valueOf(data[currentIndex + 1]);
						currentIndex += 2;
					} else {
						throw new LexerException("Wrong input");
					}
				} else {
					word = word + String.valueOf(data[currentIndex]);
					if ((currentIndex + 1) >= data.length) {
						currentIndex++;
						break;
					}
					currentIndex++;
				}
			}

			token = new Token(TokenType.WORD, word);
			return token;
		}

		if (Character.isDigit(data[currentIndex])) {
			String number = "";
			while (Character.isDigit(data[currentIndex])) {
				number = number + String.valueOf(data[currentIndex]);

				if ((currentIndex + 1) >= data.length) {
					currentIndex++;
					break;
				}
				currentIndex++;
			}
			try {
				token = new Token(TokenType.NUMBER, Long.parseLong(number));
				return token;
			} catch (NumberFormatException e) {
				throw new LexerException();
			}

		}
		
		if(data[currentIndex]=='#') {
			setState(LexerState.EXTENDED);
			currentIndex++;
			token= new Token(TokenType.SYMBOL, '#');
			return token;
		}
		// pogledali smo slucaj broja rijeci i eof-a znaci ostali samo simboli
		token = new Token(TokenType.SYMBOL, data[currentIndex]);
		currentIndex++;
		return token;
	}

	/**
	 * Gets last generated token.
	 * @return {@link Token}
	 */
	public Token getToken() {
		return token;
	}
	/**
	 * Skips all the blanks.
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
	 * Sets lexer state.
	 * @param newState {@link LexerState}
	 * @throws NullPointerException when newState is null
	 */
	public void setState(LexerState newState) {
		if (newState == null)
			throw new NullPointerException();
		state = newState;
	}
}
