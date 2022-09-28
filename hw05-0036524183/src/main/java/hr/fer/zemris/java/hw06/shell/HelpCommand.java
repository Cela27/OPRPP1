package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

public class HelpCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.equals("")) {
			for(Entry<String, ShellCommand> entry : env.commands().entrySet()) {
				env.writeln(entry.getKey()+ "-");
				ShellCommand command=entry.getValue();
				for(String s: command.getCommandDescription()) {
					env.writeln("   "+ s);
				}
				env.writeln("");
			}
			return ShellStatus.CONTINUE;
		}else {
			ShellCommand command=env.commands().get(arguments);
			if(command==null) {
				env.writeln("No such command exits");
				return ShellStatus.CONTINUE;
			}
			env.writeln(arguments+"-");
			for(String s: command.getCommandDescription()) {
				env.writeln("   "+ s);
			}
			env.writeln("");
			return ShellStatus.CONTINUE;
		} 
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc= new ArrayList<>();
		desc.add("Command help takes no arguments and lists and descriptions of all commands.");
		return Collections.unmodifiableList(desc);
	}

}
