package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Class to demonstrate how stack works.
 *
 */
public class StackDemo {
	public static void main(String[] args) throws Exception {		
		ObjectStack stack= new ObjectStack();		
		String[] splits= args[0].split(" ");
		for(int i=0; i<splits.length;i++) {
			try {
				int number=Integer.parseInt(splits[i]);
				stack.push(number);
			}catch(NumberFormatException e){
				int second=(int) stack.pop();
				int first= (int) stack.pop();

				if(splits[i].equals("+")) 
					stack.push((Object) (first+second));
					
				if(splits[i].equals("-")) 
					stack.push((Object) (first-second));

				
				if(splits[i].equals("*"))
					stack.push((Object) (first*second));
					
				if(splits[i].equals("/")) 
					stack.push((Object) (first/second));
				
				if(splits[i].equals("%"))
					stack.push((Object) (first%second));
			}
		}
		if(stack.size()!=1) throw new Exception("Pogreška");
		System.out.println(stack.pop());
	}
}
