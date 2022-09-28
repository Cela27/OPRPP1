package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for copy command in {@link MyShell}.
 * @author Antonio
 *
 */
public class CopyCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String source = "";
		String destination = "";

		// prvi je s razmakom
		if (arguments.startsWith("\"")) {
			int curr=0;
			source = arguments.substring(1);
			
			//provjeri jel ima " u pathu
			int indexSljdNavodnika =source.indexOf("\"");
			if(source.charAt(indexSljdNavodnika+1)==' ') {
				
				curr=source.indexOf('\"');
				System.out.println(curr);
				source = source.substring(0, source.indexOf('\"'));
			}
			//ima
			else {
				char[] data= source.toCharArray();

				StringBuilder sb= new StringBuilder();
				for(int i=0;i<data.length;i++) {
					curr=i;
					if(i==data.length-1) {
						sb.append(data[i]);
						break;
					}
					if(data[i]=='\"' && data[i+1]==' ') {
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
				source=sb.toString();
			}
			// drugi je s razmakom
			if (arguments.endsWith("\"")) {
				destination = arguments.substring(0, arguments.length() - 1);
				curr+=4;
				destination = destination.substring(curr);
				char[] data= destination.toCharArray();
				System.out.println(curr);
				System.out.println(destination);
				StringBuilder sb= new StringBuilder();
				for(int i=0;i<data.length;i++) {
					if(i==data.length-1) {
						sb.append(data[i]);
						break;
					}
					if(data[i]=='\"' &&data[i+1]==' ') {
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
				destination=sb.toString();
			}
			// drugi bez razmaka
			else {
				destination = arguments.substring(arguments.lastIndexOf("\"") + 2);
			}
		// prvi bez razmaka
		} else {
			// drugi s razmakom
			if (arguments.contains("\"")) {
				source = arguments.substring(0, arguments.indexOf("\"") - 1);
				
				destination = arguments.substring(arguments.indexOf("\"")+1, arguments.length() - 1);
				
				char[] data= destination.toCharArray();

				StringBuilder sb= new StringBuilder();
				for(int i=0;i<data.length;i++) {
					if(i==data.length-1) {
						sb.append(data[i]);
						break;
					}
					if(data[i]=='\"' &&data[i+1]==' ') {
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
				destination=sb.toString();
				
			}
			// drugi bez razmaka
			else {
				String[] splits = arguments.split(" ");
				source = splits[0];
				destination = splits[1];
			}
		}
		System.out.println(source);
		System.out.println(destination);
		File src = new File(source);
		File dest = new File(destination);

		if (src.isDirectory()) {
			env.writeln("First argument must be a file.");
			return ShellStatus.CONTINUE;			
		}
		
		if (dest.exists() && dest.isFile()) {
			env.write("Should I overwrite destination file?(Y for yes): ");
			if (!env.readLine().equals("Y")) {
				env.writeln("I won't overwrite.");
				return ShellStatus.CONTINUE;
			}
		}

		if (dest.isDirectory()) {
			String fileName = source.substring(source.lastIndexOf("\\"));
			destination = destination + fileName;
			dest = new File(destination);
		}

		try (InputStream in = new BufferedInputStream(new FileInputStream(source));
				OutputStream out = new BufferedOutputStream(new FileOutputStream(destination))) {

			byte[] buffer = new byte[1024];
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
		}catch (Exception e) {
			env.writeln("Error happend.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add(
				"The copy command expects two arguments: source file name and destination file name (i.e. paths and names).");
		return Collections.unmodifiableList(desc);
	}

}
