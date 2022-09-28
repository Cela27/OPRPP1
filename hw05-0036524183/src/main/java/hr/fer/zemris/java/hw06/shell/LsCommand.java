package hr.fer.zemris.java.hw06.shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Class for ls command in {@link MyShell}.
 * @author Antonio
 *
 */
public class LsCommand implements ShellCommand {

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

		File dir = new File(arguments);
		if(!dir.isDirectory()) {
			env.writeln("Argument for ls command must be directory.");
			return ShellStatus.CONTINUE;
			
		}
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			if(directoryListing.length==0)
				env.writeln("Directory has no files.");
			for (File child : directoryListing) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Path path = Paths.get(arguments);
				BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes;
				
				try {
					attributes = faView.readAttributes();
					FileTime fileTime = attributes.creationTime();
					String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
					
					if(child.isDirectory())
						env.write("d");
					else
						env.write("-");
					
					if(child.canRead())
						env.write("r");
					else
						env.write("-");
					
					if(child.canWrite())
						env.write("w");
					else
						env.write("-");
					
					if(child.canExecute())
						env.write("e");
					else
						env.write("-");
					
					String size= String.valueOf(attributes.size());
					env.write(" ");
					for(int i=0;i<10-size.length();i++)
						env.write(" ");
					env.write(size);
					
					env.write(" "+ formattedDateTime);
					
					env.writeln(" "+ child.getName());
				} catch (IOException e) {
					env.writeln("Error happend while getting attributes, try again.");
					return ShellStatus.CONTINUE;
				}	
			}
		}else {
			env.writeln("Error happend while getting files, try again.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Command ls takes a single argument – directory – and writes a directory listing (not recursive).");
		return Collections.unmodifiableList(desc);
	}

}
