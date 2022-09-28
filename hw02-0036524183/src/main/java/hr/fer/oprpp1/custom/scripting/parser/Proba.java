package hr.fer.oprpp1.custom.scripting.parser;

public class Proba {

	public static void main(String[] args) {
		SmartScriptParser parser= new SmartScriptParser("This is \\\\ sample text.\r\n"
				+ "{$ FOR i \"str \\{ str\" 10 1 $}\r\n"
				+ " This is {$= i $}-th time this message is generated.\r\n"
				+ "{$END$}\r\n"
				+ "{$FOR i 0 10 2 $}\r\n"
				+ " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n"
				+ "{$END$}"
				);
		
	}

}
