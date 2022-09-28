package hr.fer.oprpp1.custom.scripting.parser;

public class Proba2 {

	public static void main(String[] args) {
		String str="This \\";
		char[] data= str.toCharArray();
		int index=0;
		for(;index<data.length;index++) {
			if(data[index]=='{' || data[index]=='\\') {
				break;
			}
		}
		int i=0;
		String str1="";
		while(i<index) {
			str1=str1+data[i];
			i++;
		}
		str1=str1+"\\{";
		i++;
		while(i<data.length) {
			str1=str1+data[index];
			i++;
		}
		System.out.println(str1);
	}

}
