package hr.fer.oprpp1.custom.collections;

/**
 * 
 * Expansion of {@link Collection} with added functions
 *
 */
public interface List<T> extends Collection<T>{
	/**
	 * Returns object from {@link ArrayIndexedCollection} at given index
	 * 
	 * @param index Integer represents position of element in array
	 * @return element at index position
	 */
	public abstract Object get(int index);
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in array.
	 * 
	 * @param value    Object you want to insert
	 * @param position at which you want to insert
	 * 
	 * @throws IndexOutOfBoundsException if position isn't in range 0 to size
	 * @throws NullPointerException      if value is null
	 */
	public abstract void insert(T value, int position);
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found.
	 * 
	 * @param value whose index you want to know
	 * @throws NullPointerException if value is null
	 * @return index of value if fount, otherwise -1
	 */
	public abstract int indexOf(Object value);
	
	/**
	 * Removes element at specified index from collection.
	 * 
	 * @param index Object which you want to remove
	 * @throws IndexOutOfBoundsException if isn't in range o to size-1
	 */
	public abstract void remove(int index);
}
