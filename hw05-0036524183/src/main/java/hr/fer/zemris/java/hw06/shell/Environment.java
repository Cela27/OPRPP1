package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Interface for environments.
 * @author Antonio
 *
 */
public interface Environment {
	/**
	 * Reads next line.
	 * @return next line
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes on environments output.
	 * @param text which you want to write
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	/**
	 * Writes line on environments output.
	 * @param text which you want to write
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns unmodifiable map of commands.
	 * @return map of commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Getter for multi line symbol.
	 * @return multi line symbol
	 */
	Character getMultilineSymbol();
	/**
	 * Setter for multi line symbol.
	 * @param symbol
	 */
	void setMultilineSymbol(Character symbol);
	/**
	 * Getter for prompt symbol.
	 * @return prompt symbol
	 */
	Character getPromptSymbol();
	/**
	 * Setter for prompt symbol.
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);
	/**
	 * Getter for more lines symbol.
	 * @return more lines symbol
	 */
	Character getMorelinesSymbol();
	/**
	 * Setter for more lines symbol.
	 * @param symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
