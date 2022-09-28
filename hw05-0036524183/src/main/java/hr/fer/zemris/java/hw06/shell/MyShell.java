package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
/**
 * Class representing shell.
 * @author Antonio
 *
 */
public class MyShell {
	private static Character PROMPTSYMBOL='>';
	private static Character MULTILINESYMBOL='|';
	private static Character MORELINESSYMBOL='\\';
	private static Scanner s;
	private static SortedMap<String, ShellCommand> commands= new TreeMap<>();
	private static MyShellEnvironment env= new MyShellEnvironment();
	private static ShellStatus currentStatus=ShellStatus.CONTINUE;
	
	
	public static void main(String[] args) {
		env.writeln("Welcome to MyShell v 1.0");
		s= new Scanner(System.in);
		
		try {			
			while(!currentStatus.equals(ShellStatus.TERMINATE)) {
				env.write(PROMPTSYMBOL+ " ");
				String prompt=env.readLine();
				if(prompt.endsWith(MORELINESSYMBOL.toString())){
					multiLine(prompt);
				}
				else {
					singleLine(prompt);
				}
				if(currentStatus.equals(ShellStatus.TERMINATE)) {
					break;
				}
			}
		}catch( ShellIOException e) {
			
		}
	}

	private static void singleLine(String prompt) {
		String[] splits= prompt.split(" ");
		String commandName=splits[0];
		String arguments="";
		
		for(int i=1; i<splits.length;i++)
			arguments=arguments+" "+ splits[i];
		arguments=arguments.trim();
		ShellCommand command= commands.get(commandName);
		if(command==null) {
			currentStatus=ShellStatus.TERMINATE;
			return;
		}
		currentStatus=command.executeCommand(env, arguments);
	}

	private static void multiLine(String prompt) {
		String allLines=prompt.substring(0, prompt.lastIndexOf(MORELINESSYMBOL)-1);
		env.write(MULTILINESYMBOL+" ");
		prompt=env.readLine();
		
		while(prompt!=null) {
			if(prompt.endsWith(MORELINESSYMBOL.toString())){
				allLines=allLines+" "+prompt.substring(0, prompt.lastIndexOf(MORELINESSYMBOL)-1);
				env.write(MULTILINESYMBOL+" ");
				prompt=env.readLine();
			}
			else {
				allLines=allLines+" "+prompt;
				break;
			}
		}
		singleLine(allLines);
	}
		
	private static class MyShellEnvironment implements Environment{
		public MyShellEnvironment(){
			setCommands();
		}
		@Override
		public String readLine() throws ShellIOException {
			try {
				return s.nextLine();
				
			}catch (Throwable e) {
				throw new ShellIOException();
			}
		}

		@Override
		public void write(String text) throws ShellIOException {
			try {
				System.out.print(text);
				
			}catch (Throwable e) {
				throw new ShellIOException();
			}
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			try {
				System.out.println(text);
				
			}catch (Throwable e) {
				throw new ShellIOException();
			}
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands);
		}

		@Override
		public Character getMultilineSymbol() {
			return MULTILINESYMBOL;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			MULTILINESYMBOL=symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return PROMPTSYMBOL;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			PROMPTSYMBOL=symbol;
			
		}

		@Override
		public Character getMorelinesSymbol() {
			return MORELINESSYMBOL;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			MORELINESSYMBOL=symbol;
			
		}
		
		public void setCommands() {
			commands.put("symbol", new SymbolCommand());
			commands.put("charsets", new CommandCharsets());
			commands.put("exit", new ExitCommand());
			commands.put("help", new HelpCommand());
			commands.put("cat", new CatCommand());
			commands.put("ls", new LsCommand());
			commands.put("tree", new TreeCommand());
			commands.put("copy", new CopyCommand());
			commands.put("mkdir", new MkdirCommand());
			commands.put("hexdump", new HexdumpCommand());
		}
	}
}

