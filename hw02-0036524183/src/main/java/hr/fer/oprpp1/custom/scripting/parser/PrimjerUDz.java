package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;

public class PrimjerUDz {
	public static void main(String[] args) {

		String docBody = "This is \\\\ sample text.\r\n" + "{$ FOR i \"str \\{ str\" 10 1 $}\r\n"
				+ " This is {$= i $}-th time this message is generated.\r\n" + "{$END$}\r\n" + "{$FOR i 0 10 2 $}\r\n"
				+ " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + "{$END$}";
		SmartScriptParser parser = null;
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
		System.out.println(originalDocumentBody); // should write something like original
		// content of docBody

	}
}
