package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


/**
 * Class which represents resizeble array-backed collection of objects. You can
 * work with object using class methods.
 */
public class ArrayIndexedCollection<T> implements List<T> {

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
	 * {@link ElementsGetter} for this Collection
	 */
	private ElementsGetter<T> elementsGetter;
	
	/**
	 * Integer variable counting number of modifications on Collection
	 */
	private long modificationCount=0;
	
	/**
	 * Basic constructor for class. Gives you collection with size 16.
	 */
	public ArrayIndexedCollection() {
		this.size = 0;
		this.elements = new Object[16];
	}

	/**
	 * Constructor for class, you can choose desired capacity
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
	 * Constructor for class, you can choose collection which elements are copied
	 * into this newly constructed collection
	 *
	 * @param collection represents collection you give
	 * @throws NullPointerException     if collection is null
	 * @throws IllegalArgumentException if capacity is > 16 (default size)
	 */
	public ArrayIndexedCollection(Collection<T> collection) {
		if (collection == null)
			throw new NullPointerException();
		if (collection.size() > 16)
			throw new IllegalArgumentException();

		this.size = collection.size();
		this.elements = collection.toArray();
	}

	/**
	 * Constructor for class, you can choose collection which elements are copied
	 * into this newly constructed collection and you can choose desired capacity
	 *
	 * @param collection      represents collection you give
	 * @param initialCapacity int represents desired capacity
	 * @throws NullPointerException     if collection is null
	 * @throws IllegalArgumentException if capacity is < 1
	 */
	public ArrayIndexedCollection(Collection<T> collection, int initialCapacity) {
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
			modificationCount++;
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
		modificationCount++;
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
	public void addAll(Collection<? extends T> other) {
		
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

		if ((other.size() + size) > elements.length) {
			Object[] tempElements = new Object[size + other.size()];
			for (int i = 0; i < elements.length; i++) {
				tempElements[i] = elements[i];
			}
			elements = tempElements;
			modificationCount++;
		}

		AddAllProcessor processor = new AddAllProcessor(this);

		other.forEach(processor);
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		modificationCount++;
		size = 0;
	}
	
	@Override
	public Object get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		return elements[index];
	}

	
	@Override
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
		modificationCount++;
	}

	
	@Override
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
	
	@Override
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
		modificationCount++;
	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		elementsGetter= new ElementsGetterArray<>(this);
		return elementsGetter;
	}
	
	@Override
	public void addAllSatisfying(Collection<T> col, Tester tester) {
		ElementsGetter<T> eG= col.createElementsGetter();
		while(eG.hasNextElement()) {
			Object next=eG.getNextElement();
			if(tester.test(next))
				this.add(next);
		}	
	}
	
	
	private static class ElementsGetterArray<T> implements ElementsGetter<T> {
		int counter;
		ArrayIndexedCollection<T> collection;
		long savedModificationCount;
 		public ElementsGetterArray(ArrayIndexedCollection<T> collection) {
			counter=0;
			this.collection=collection;
			this.savedModificationCount=collection.modificationCount;
		}
 		@Override
		public boolean hasNextElement() {
			if(savedModificationCount!=collection.modificationCount) throw new ConcurrentModificationException();
			return counter < collection.size;
		}
 		
 		@SuppressWarnings("unchecked")
		@Override
		public T getNextElement() {
			if(savedModificationCount!=collection.modificationCount) throw new ConcurrentModificationException();
			if (hasNextElement()) {
				counter++;
				return (T) collection.elements[counter-1];
			} else
				throw new NoSuchElementException();

		}
 		
 		@Override
 		public void processRemaining(Processor<? super T> p) {
 			while(hasNextElement())
 				p.process(getNextElement());
 		}
	}
}