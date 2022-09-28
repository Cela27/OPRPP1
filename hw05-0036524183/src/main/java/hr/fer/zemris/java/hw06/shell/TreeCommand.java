package hr.fer.zemris.java.hw06.shell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for tree command in {@link MyShell}.
 * @author Antonio
 *
 */
public class TreeCommand implements ShellCommand{

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
		if(!dir.isDirectory()) {
			env.writeln("Argument for tree command must be a directory.");
			return ShellStatus.CONTINUE;
			
		}
		try {
			printLevel(0, dir, env);
		} catch (IOException e) {
			env.writeln("Error happend while printing, try again.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc= new ArrayList<>();
		desc.add("The tree command expects a single argument: directory name and prints a tree.");
		return Collections.unmodifiableList(desc);
	}
	
	private void printLevel(int indent, File file, Environment env) throws IOException {
	    for (int i = 0; i < indent; i++)
	      env.write(" ");
	    env.writeln(file.getName());
	    if (file.isDirectory()) {
	      File[] files = file.listFiles();
	      for (int i = 0; i < files.length; i++)
	        printLevel(indent + 2, files[i], env);
	    }
	  }
}
