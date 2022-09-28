package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for cat command in {@link MyShell}.
 * @author Antonio
 *
 */
public class CatCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String arg1 = "", arg2 = "";
		if (arguments.startsWith("\"")) {
			arg1 = arguments.substring(1, arguments.lastIndexOf('\"'));
			char[] data= arg1.toCharArray();

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
			arg1=sb.toString();
			if (arguments.length() - 1 > arguments.lastIndexOf("\"")) {
				arg2 = arguments.substring(arguments.lastIndexOf("\"")+2);
			}
		} else {
			String[] splits = arguments.split(" ");
			arg1 = splits[0];
			if (splits.length == 2)
				arg2 = splits[1];
			else if (splits.length > 2) {
				env.writeln("Too many arguments, try again.");
				return ShellStatus.CONTINUE;
			}
				
		}
		File file = new File(arg1);
		if(!file.isFile()) {
			env.writeln("First argument must be a file.");
			return ShellStatus.CONTINUE;
		}
		Charset charset;
		if(arg2.equals(""))
			charset = Charset.defaultCharset();
		else
			charset = Charset.availableCharsets().get(arg2);
		
		if(charset==null) {
			env.writeln("Wrong charset, try again.");
			return ShellStatus.CONTINUE;
		}
		try (BufferedReader br = new BufferedReader(new FileReader(file, charset))) {
			String line;
			while ((line = br.readLine()) != null) {
				env.writeln(line);
			}
		} catch (IOException e) {
			env.writeln("Error happend, try again.");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Command cat takes one or two arguments. The first argument is path to desired file.");
		desc.add(
				"The second argument is charset name that should be used to interpret chars from bytes. If not given use the standard one.");
		return Collections.unmodifiableList(desc);
	}

}
