package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for symbol command in {@link MyShell}.
 * @author Antonio
 *
 */
public class SymbolCommand implements ShellCommand{
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] splits= arguments.split(" ");
		if(splits.length==1) {
			if(splits[0].equals("PROMPT")) {
				System.out.println("Symbol for PROMPT is '"+ env.getPromptSymbol()+"'");					
			}
			else if(splits[0].equals("MULTILINE")){
				System.out.println("Symbol for MULTILINE is '"+ env.getMultilineSymbol()+"'");
			}
			else if(splits[0].equals("MORELINES")){
				System.out.println("Symbol for MORELINES is '"+ env.getMorelinesSymbol()+"'");
			}
		}else if(splits.length==2) {
			if(splits[0].equals("PROMPT")) {
				System.out.println("Symbol for PROMPT changed from '"+env.getPromptSymbol()+"' to '"+splits[1]+"'");
				env.setPromptSymbol(splits[1].charAt(0));					
			}
			else if(splits[0].equals("MULTILINE")){
				System.out.println("Symbol for MULTILINE from '"+env.getMultilineSymbol()+"' to '"+splits[1]+"'");
				env.setMultilineSymbol(splits[1].charAt(0));	
			}
			else if(splits[0].equals("MORELINES")){
				System.out.println("Symbol for MORELINES changed from '"+env.getMorelinesSymbol()+"' to '"+splits[1]+"'");
				env.setMorelinesSymbol(splits[1].charAt(0));	
			}
			
		}else{
			env.writeln("Your entry was wrong. Try again.");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc= new ArrayList<>();
		desc.add("Command symbol takes 1 or 2 arguments. For 1 argument it shows desired symbol.");
		desc.add("For 2 arguments, desired symbol is changed to second argument.");
		desc.add("Possible symbols are PROMPT, MULTILINE and MORELINES");
		return Collections.unmodifiableList(desc);
	}

}
