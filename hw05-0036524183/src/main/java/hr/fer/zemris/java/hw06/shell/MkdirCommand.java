package hr.fer.zemris.java.hw06.shell;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Class for mkdir command in {@link MyShell}.
 * @author Antonio
 *
 */
public class MkdirCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.startsWith("\"")) {
			arguments = arguments.substring(1, arguments.length() - 1);
			char[] data= arguments.toCharArray();

			StringBuilder sb= new StringBuilder();
			for(int i=0;i<data.length;i++) {
				if(i==data.length-1) {
					sb.append(data[i]);
					break;
				}
				if(data[i]=='\\' && data[i+1]=='\"') {
					sb.append("\"");
					i++;
				}
				else if(data[i]=='\\' && data[i+1]=='\\') {
					sb.append("\\");
					i++;
				}
				else {
					sb.append(data[i]);
				}
			}
			arguments=sb.toString();
		}else {
			if(arguments.contains(" ")) {
				env.writeln("Your entry was incorrect, try again.");
				return ShellStatus.CONTINUE;
			}
		}
		File dir= new File(arguments);
		
		boolean created=dir.mkdirs();
		if(!created) {
			env.writeln("Error happend while crating directory, try again.");
			return ShellStatus.CONTINUE;
		}
		env.writeln("Creation was successful");
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("The mkdir command takes a single argument: directory name, and creates the appropriate directory structure.");
		return Collections.unmodifiableList(desc);
	}

}
