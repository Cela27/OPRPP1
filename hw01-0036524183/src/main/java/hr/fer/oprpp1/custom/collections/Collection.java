package hr.fer.oprpp1.custom.collections;

/** 
*Class which represents a basic collection of objects. You can work with object using class methods. 
*/
public class Collection {
	
	/** 
	*Basic constructor for class. 
	*/
	protected Collection() {
		
	}
	
	/**
	* Returns a boolean variable as a result of asking is collection empty.
	* 
	* @return      true if collection is empty, otherwise false
	*/
	public boolean isEmpty() {
		return false;
	}
	
	/**
	* Returns the number of currently stored objects in this collections.
	* 
	* @return      size of collection
	*/
	public int size() {
		return 0;
	}
	
	/**
	* Adds the given object into this collection.
	* 
	* @param value object you want to add
	*/
	public void add(Object value) {
	}
	
	/**
	* Returns true only if the collection contains given value, as determined by equals method.
	* 
	* @param value object for which you test if its contained in collection
	* @return true if contains, false if not
	*/
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	* Returns true only if the collection contains given value as determined by equals method and removes
	* one occurrence of it
	* 
	* @param value object which you want to remove from collection
	* @return true if remove worked, false if not
	*/
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	* Allocates new array with size equals to the size of this collections, fills it with collection content and
	*returns the array.
	* 
	* @param value object which you want to remove from collection
	* @return array made from collection
	* @throws UnsupportedOperationException if array is null
	*/
	public Object[] toArray() {
		return null;
	}
	
	/**
	* Method calls processor.process(.) for each element of this collection. The order in which elements
	*will be sent is undefined in this class.
	* 
	* @param processor processor which will process each element
	*/
	
	public void forEach(Processor processor) {
	}
	
	/**
	* Method adds into the current collection all elements from the given collection. This other collection remains unchanged.
	*will be sent is undefined in this class.
	* 
	* @param other other collection which will be added in desired collection
	*/
	public void addAll(Collection other) {
	}
	
	/**
	* Removes all elements from this collection. Implement it here as an empty method.
	*/
	public void clear() {
	}
	
	/**
	* Returns the object that is stored in backing array at position index
	* 
	* @param index Integer which represents position in collection starting with 0
	* @throws IndexOutOfBoundsException if index isn't in range 0 to size-1
	* @return object at asked index
	*/
	public Object get(int index) {
		return null;
	}
}
