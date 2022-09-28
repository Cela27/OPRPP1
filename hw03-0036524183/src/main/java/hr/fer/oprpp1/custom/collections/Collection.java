package hr.fer.oprpp1.custom.collections;


/** 
*Class which represents a basic collection of objects. You can work with object using class methods. 
*/
public interface Collection<T> {
	
	/**
	* Returns a boolean variable as a result of asking is collection empty.
	* 
	* @return      true if collection is empty, otherwise false
	*/
	public default boolean isEmpty() {
		return size()==0;
	}
	
	/**
	* Returns the number of currently stored objects in this collections.
	* 
	* @return      size of collection
	*/
	public abstract int size();
	
	/**
	* Adds the given object into this collection.
	* 
	* @param value object you want to add
	*/
	public abstract void add(T value);

	/**
	* Returns true only if the collection contains given value, as determined by equals method.
	* 
	* @param value object for which you test if its contained in collection
	* @return true if contains, false if not
	*/
	public abstract boolean contains(Object value);
	
	/**
	* Returns true only if the collection contains given value as determined by equals method and removes
	* one occurrence of it
	* 
	* @param value object which you want to remove from collection
	* @return true if remove worked, false if not
	*/
	public abstract boolean remove(Object value);
	
	/**
	* Allocates new array with size equals to the size of this collections, fills it with collection content and
	*returns the array.
	* 
	* @param value object which you want to remove from collection
	* @return array made from collection
	* @throws UnsupportedOperationException if array is null
	*/
	public abstract Object[] toArray();
	
	/**
	* Method calls processor.process(.) for each element of this collection. The order in which elements
	*will be sent is undefined in this class.
	* 
	* @param processor processor which will process each element
	*/
	
	public default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> eG= this.createElementsGetter();
		while(eG.hasNextElement()) {
			processor.process(eG.getNextElement());
		}
	}
	
	/**
	* Method adds into the current collection all elements from the given collection. This other collection remains unchanged.
	*will be sent is undefined in this class.
	* 
	* @param other other collection which will be added in desired collection
	*/
	public default void addAll(Collection<? extends T> other) {
		
		class AddAllProcessor implements Processor<T> {
			Collection<T> collection;

			public AddAllProcessor(Collection<T> collection) {
				this.collection = collection;
			}

			@Override
			public void process(T value) {
				collection.add(value);
			}
		}
		
		AddAllProcessor processor = new AddAllProcessor(this);

		other.forEach(processor);
	}
	
	/**
	* Removes all elements from this collection. Implement it here as an empty method.
	*/
	public abstract void clear();
	
	/**
	 * Returns new ElementsGetter for a given Collection
	 * @return {@link ElementsGetter}
	 */
	public abstract ElementsGetter<T> createElementsGetter();
	
	/**
	 * Adds to collection all Objects from given collection which satisfy Tester's test
	 * 
	 * @param col
	 * @param tester
	 */
	public abstract void addAllSatisfying(Collection<T> col, Tester tester);
}
