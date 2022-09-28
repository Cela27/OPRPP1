package hr.fer.oprpp1.custom.scripting.nodes;

/*
 *  A node representing a piece of textual data. It inherits from Node class.
 */
public class TextNode extends Node {
	
	private String text;
	/**
	 * Basic constructor with text as parameter.
	 * @param text
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Returns text prepared for parsing.
	 * @return String text
	 */
	public String getTextForParsing() {

		String splits[]= text.split(" ");
		StringBuilder sbG= new StringBuilder();
		for(String split: splits) {
			char[] data= split.toCharArray();
			StringBuilder sb= new StringBuilder();
			int index=0;
			boolean breakHappend=false;
			for(;index<data.length;index++) {
				if(data[index]=='{' || data[index]=='\\') {
					breakHappend=true;
					break;
				}
			}
			if(!breakHappend) {
				sbG.append(split).append(" ");
				continue;
			}
				
			
			int i=0;
			while(i<index) {
				sb.append(data[i]);
				i++;
			}
			if(data[index]=='{')
				sb.append("\\{");
			else if(data[index]=='\\'){
				sb.append("\\\\");
			}
			i++;
			while(i<data.length) {
				sb.append(data[i]);
				i++;
			}
			sb.append(" ");
			sbG.append(sb.toString());
		}
		sbG.deleteCharAt(sbG.length()-1);
		return sbG.toString();
	}
	
	/**
	 * Returns text after parsing
	 * @return String text
	 */
	public String getTextAfterParsing() {
		return text;
	}
}
