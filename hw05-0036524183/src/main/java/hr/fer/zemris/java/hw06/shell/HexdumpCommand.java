package hr.fer.zemris.java.hw06.shell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import hr.fer.oprpp1.hw05.crypto.Util;

/***
 * Class for hexdump command in {@link MyShell}.
 *
 */
public class HexdumpCommand implements ShellCommand {

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

		File file = new File(arguments);
		if (file.isDirectory()) {
			env.writeln("Argument for hexdump command must be file.");
			return ShellStatus.CONTINUE;
		}
		StringBuilder sb = new StringBuilder();
		try (Scanner myReader = new Scanner(file)) {
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				sb.append(data);
			}
		} catch (FileNotFoundException e1) {
			env.writeln("File not found.");
			return ShellStatus.CONTINUE;
		}

		String sadrzaj = sb.toString();
		Path path = Paths.get(arguments);

		try {
			byte[] arr = Files.readAllBytes(path);
			int k = 0;
			int granica= arr.length/16;
			granica++;
			for (int i = 0; i < granica; i++) {
				if (k < 10) {
					env.write("000000" + k + "0: ");
				} else if (k < 100) {
					env.write("00000" + k + "0: ");
				} else if (k < 1000) {
					env.write("0000" + k + "0: ");
				} else if (k < 10000) {
					env.write("000" + k + "0: ");
				} else if (k < 100000) {
					env.write("00" + k + "0: ");
				} else if (k < 1000000) {
					env.write("0" + k + "0: ");
				} else if (k < 10000000) {
					env.write(k + "0: ");
				}
				for (int j = 0; j < 16; j++) {
					if ((i*16 + j) >= arr.length) {
						env.write("  ");
					} else {
						byte[] temp = { arr[i*16 + j] };
						if (temp[0] < 32 || temp[0] > 127) {
							env.write(". ");
						} else {
							env.write((Util.bytetohex(temp)).toUpperCase());
						}
					}
					if (j == 7) {
						env.write("|");
					} else if (j == 15) {
						env.write(" | ");
						if(sadrzaj.length()>16) {
							String tmp = sadrzaj.substring(0, 16);
							env.writeln(tmp);
							sadrzaj = sadrzaj.substring(16);
						}else {
							env.writeln(sadrzaj);
						}
						
					} else {
						env.write(" ");
					}
				}
				k++;
			}
		} catch (IOException e) {
			env.writeln("Error happend try again.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("The hexdump command expects a single argument: file name, and produces hex-output.");
		return Collections.unmodifiableList(desc);
	}

}
