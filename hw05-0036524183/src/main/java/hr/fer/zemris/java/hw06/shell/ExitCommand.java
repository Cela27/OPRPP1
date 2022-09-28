package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExitCommand implements ShellCommand {
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc= new ArrayList<>();
		desc.add("Command terminates the shell.");
		return Collections.unmodifiableList(desc);
	}

}
