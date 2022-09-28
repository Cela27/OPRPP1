package hr.fer.oprpp1.hw02.tester;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {
	public static void main(String[] args) throws IOException {
		SmartScriptParser parser=null;
		String docBody = new String(
				Files.readAllBytes(
						Paths.get("C:\\Eclipse Radne Povrsine\\hw02-0036524183\\hw02-0036524183\\primjer.txt")),
				StandardCharsets.UTF_8);
		
		String docBody1="This is \\\\ sample text.\r\n {$ FOR i \"str \\{ str\" 10 1 $}\r\n This is {$= i $}-th time this message is generated.\r\n {$END$}\r\n {$FOR i 0 10 2 $}\r\n sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n {$END$}";
		
		System.out.println(docBody);
		System.out.println("-------DOBAR--------");
		System.out.println(docBody1);
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		System.out.println(originalDocumentBody);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		System.out.println(document.equals(document2));
		
	}

}
