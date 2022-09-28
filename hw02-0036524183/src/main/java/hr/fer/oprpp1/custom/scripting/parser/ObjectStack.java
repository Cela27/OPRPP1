package hr.fer.oprpp1.custom.scripting.parser;

import java.util.EmptyStackException;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Class which represents a e stack-like collection.
 *
 */
public class ObjectStack {
	
	/**
	 * {@link ArrayIndexedCollection} which is used to implement stack
	 */
	private ArrayIndexedCollection aiCollection;
	
	/**
	 * Basic constructor for class
	 */
	public ObjectStack(){
		this.aiCollection=new ArrayIndexedCollection();
	}
	
	/**
	* Returns a boolean variable as a result of asking is stack empty.
	* 
	* @return      true if collection is empty, otherwise false
	*/
	public boolean isEmpty() {
		return aiCollection.isEmpty();
	}
	
	/**
	* Returns the number of currently stored objects in this stack.
	* 
	* @return      size of stack
	*/
	public int size() {
		return aiCollection.size();
	}
	
	/**
	 * Pushes given value on the stack. 
	 * 
	 * @param value Object which is pushed on stack
	 * @throws NullPointerException if value is null
	 */
	public void push(Object value) {
		if(value ==null) throw new NullPointerException();
		aiCollection.add(value);
	}
	
	/**
	 * Removes last value pushed on stack from stack and returns it.
	 * 
	 * @return last Object pushed on stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object pop() {
		if(aiCollection.size()==0) throw new EmptyStackException();
		Object obj= aiCollection.get(aiCollection.size()-1);
		aiCollection.remove(aiCollection.size()-1);
		return obj;
	} 
	
	/**
	 * Returns last element placed on stack but does not delete it from stack.
	 * 
	 * @return last Object pushed on stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object peek() {
		if(aiCollection.size()==0) throw new EmptyStackException();
		return aiCollection.get(aiCollection.size()-1);
	}
	
	/**
	 * Remove all elements from stack.
	 */
	public void clear() {
		aiCollection.clear();
	}
	
	
}
