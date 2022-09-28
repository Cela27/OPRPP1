package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class representing string as element.
 * @author Antonio
 *
 */
public class ElementString extends Element{
	private String value;
	/**
	 * Basic constructor with string value as parameter.
	 * @param value String
	 */
	public ElementString(String value) {
		this.value = value;
	}
	/**
	 * Getter for value of string.
	 * @return {@link String} value
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String asText() {
		String splits[]= value.split(" ");
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
}
