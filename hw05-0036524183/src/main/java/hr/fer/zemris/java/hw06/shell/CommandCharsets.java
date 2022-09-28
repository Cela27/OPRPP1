package hr.fer.zemris.java.hw06.shell;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

/**
 * Class for charsets command in {@link MyShell}.
 * @author Antonio
 *
 */
public class CommandCharsets implements ShellCommand {
	
	public CommandCharsets() {
		
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments!="") {
			env.writeln("Command charsets takes no arguments");
			return ShellStatus.CONTINUE;
		}
		
		try {
			for(Entry<String, Charset> entry : Charset.availableCharsets().entrySet()) {
				env.writeln(entry.getKey());
			}			
		}catch(Throwable e){
			env.writeln("Error happend while getting charsets. Try again.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc= new ArrayList<>();
		desc.add("Command charsets takes no arguments and lists names of supported charsets for your Java platform.");
		return Collections.unmodifiableList(desc);
	}

}
