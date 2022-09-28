package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/***
 * Interface for making commands for {@link MyShell}.
 * @author Antonio
 *
 */
public interface ShellCommand {
	
	/***
	 * Method for executing command.
	 * @param env {@link Environment} used for communicating with {@link MyShell}.
	 * @param arguments command arguments
	 * @return {@link ShellStatus} after executing the command
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	/**
	 * Getter for command name.
	 * @return String command name
	 */
	String getCommandName();
	
	/**
	 * Writes command description.
	 * @return {@link List} of {@link String}s describing command.
	 */
	List<String> getCommandDescription();
}
