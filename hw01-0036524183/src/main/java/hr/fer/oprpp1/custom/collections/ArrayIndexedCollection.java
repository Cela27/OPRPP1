package hr.fer.oprpp1.custom.collections;

/**
 * Class which represents resizable array-backed collection of objects. You can
 * work with object using class methods.
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * current size of collection (number of elements actually stored in elements
	 * array)
	 */
	private int size;
	/**
	 * an array of object references which length determines its current capacity
	 * (obviously, at any time size can not be greater than array length)
	 */
	private Object[] elements;

	/** 
	*Basic constructor for class. Gives you collection with size 16. 
	*/
	public ArrayIndexedCollection() {
		this.size = 0;
		this.elements = new Object[16];
	}
	
	/** 
	*Constructor for class, you can choose desired capacity
	*
	* @param initialCapacity int represents desired capacity
	* @throws IllegalArgumentException if capacity is < 1
	*/
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException();
		this.size = 0;
		this.elements = new Object[initialCapacity];
	}
	
	/** 
	*Constructor for class, you can choose collection which elements are copied into this newly
	*constructed collection
	*
	* @param collection represents collection you give
	* @throws NullPointerException if collection is null
	* @throws IllegalArgumentException if capacity is > 16 (default size)
	*/
	public ArrayIndexedCollection(Collection collection) {
		if (collection == null)
			throw new NullPointerException();
		if (collection.size() > 16)
			throw new IllegalArgumentException();

		this.size = collection.size();
		this.elements = collection.toArray();
	}
	
	/** 
	*Constructor for class, you can choose collection which elements are copied into this newly
	*constructed collection and you can choose desired capacity
	*
	* @param collection represents collection you give
	* @param initialCapacity int represents desired capacity
	* @throws NullPointerException if collection is null
	* @throws IllegalArgumentException if capacity is < 1 
	*/
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if (collection == null)
			throw new NullPointerException();
		if (initialCapacity < 1)
			throw new IllegalArgumentException();
		this.size = initialCapacity;

		if (initialCapacity > collection.size()) {
			this.elements = new Object[initialCapacity];
		} else {
			this.elements = new Object[collection.size()];
		}
		this.elements = collection.toArray();
	}

	
	@Override
	public boolean isEmpty() {
		if (size() == 0)
			return true;
		else
			return false;
	}

	@Override
	public int size() {
		return size;
	}
	
	/**
	 * 	* Adds the given object into this collection. (reference is added into first
	* empty place in the elements array
	* 
	* @throws NullPointerException if value is null
	*/
	@Override
	public void add(Object value) {
		if (value == null)
			throw new NullPointerException();

		if (elements.length == size) {
			Object[] tempElements = new Object[size * 2];

			for (int i = 0; i < elements.length; i++) {
				tempElements[i] = elements[i];
			}
			tempElements[elements.length] = value;

			elements = tempElements;
			size++;
		} else {
			for (int i = 0; i < elements.length; i++) {
				if (elements[i] == null) {
					elements[i] = value;
					size++;
					break;
				}
			}
		}
	}

	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value))
				return true;
		}
		return false;
	}
	
	/** 
	* @throws NullPointerException if value is null
	*/
	@Override
	public boolean remove(Object value) {
		if (value == null)
			throw new NullPointerException();

		if (contains(value) == false)
			return false;

		int indexOfRemoval = 0;
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				elements[i] = null;
				indexOfRemoval = i;
				break;
			}
		}

		for (int i = indexOfRemoval; i < elements.length - 1; i++) {
			elements[i] = elements[i + 1];
		}
		elements[elements.length - 1] = null;
		size--;
		return true;
	}

	@Override
	public Object[] toArray() {
		Object[] newElements = new Object[size];
		int j = 0;
		for (int i = 0; i < size; i++) {
			if (elements[i] != null) {
				newElements[j] = elements[i];
				j++;
			}
		}
		boolean allAreNull = true;
		for (int i = 0; i < newElements.length; i++) {
			if (newElements[i] != null) {
				allAreNull = false;
				break;
			}
		}
		if (allAreNull) {
			throw new UnsupportedOperationException();
		}
		return newElements;
	}

	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	@Override
	public void addAll(Collection other) {
		class AddAllProcessor extends Processor {
			Collection collection;

			public AddAllProcessor(Collection collection) {
				this.collection = collection;
			}

			@Override
			public void process(Object value) {
				collection.add(value);
			}
		}

		if ((other.size() + size) > elements.length) {
			Object[] tempElements = new Object[size + other.size()];
			for (int i = 0; i < elements.length; i++) {
				tempElements[i] = elements[i];
			}
			elements = tempElements;
		}

		AddAllProcessor processor = new AddAllProcessor(this);

		other.forEach(processor);
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	@Override
	public Object get(int index) {
		if (index < 0 || index > (elements.length - 1))
			throw new IndexOutOfBoundsException();
		return elements[index];
	}
	/**
	 * Inserts (does not overwrite) the given value at the given position in array.
	 * 
	 * @param value Object you want to insert
	 * @param position at which you want to insert
	 * 
	 * @throws IndexOutOfBoundsException if position isn't in range 0 to size
	 * @throws NullPointerException if value is null
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > (elements.length))
			throw new IndexOutOfBoundsException();
		if (value == null)
			throw new NullPointerException();

		if (elements.length == size) {
			Object[] tempElements = new Object[size * 2];
			tempElements = elements;
			elements = tempElements;
		}

		for (int i = elements.length - 1; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		elements[position] = value;
		size++;
	}
	/**
	 * Searches the collection and returns the index of the first occurrence of the 
	 * given value or -1 if the value is not found.
	 * 
	 * @param value whose index you want to know
	 * @throws NullPointerException if value is null
	 * @return index of value if fount, otherwise -1
	 */
	public int indexOf(Object value) {
		if (value == null)
			throw new NullPointerException();

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Removes element at specified index from collection.
	 * 
	 * @param index Object which you want to remove
	 * @throws IndexOutOfBoundsException if isn't in range o to size-1
	 */
	public void remove(int index) {
		if (index < 0 || index > (size - 1))
			throw new IndexOutOfBoundsException();

		if (index == (size - 1)) {
			elements[index] = null;
		} else {
			for (int i = index; i < (size - 1); i++) {
				elements[i] = elements[i + 1];
			}
			elements[size - 1] = null;
		}
		size--;
	}

}