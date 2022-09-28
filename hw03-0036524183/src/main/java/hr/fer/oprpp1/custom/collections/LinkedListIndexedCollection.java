package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


/**
 * Class which represents linked list-backed collection of objects. You can work
 * with object using class methods.
 */
public class LinkedListIndexedCollection<T> implements List<T> {

	/**
	 * current size of collection (number of elements actually stored in elements
	 * array)
	 */
	private int size;
	/**
	 * reference to the first node of the linked list
	 */
	private ListNode<T> first;
	/**
	 * reference to the last node of the linked list
	 */
	private ListNode<T> last;
	/**
	 * {@link ElementsGetter} for this Collection
	 */
	private ElementsGetter<T> elementsGetter;
	/**
	 * Integer variable counting number of modifications on Collection
	 */
	private long modificationCount = 0;

	/**
	 * Basic constructor for class. Size is zero, and first=last=null
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
		this.first = null;
		this.last = null;
	}

	/**
	 * Constructor for class, you can choose desired collection whose items will be
	 * part of it
	 * 
	 * @param collection desired collection
	 * @throws NullPointerException if collection is null
	 */
	@SuppressWarnings("unchecked")
	public LinkedListIndexedCollection(Collection<T> collection) {
		if (collection == null)
			throw new NullPointerException();
		this.size = collection.size();
		Object[] array= collection.toArray();

		first = new ListNode<>((T)array[0]);
		ListNode<T> current = first;

		for (int i = 0; i < collection.size() - 1; i++) {
			current.setContent((T) array[i]);
			ListNode<T> next = new ListNode<>((T)array[i+1]);
			current.setNext(next);
			next.setPerivous(current);
			current = current.getNext();
		}
		last = current;
	}

	@Override
	public boolean isEmpty() {
		if (size == 0)
			return true;
		else
			return false;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * * Adds the given object into this collection. (reference is added into first
	 * empty place in the elements array
	 * 
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(T value) {
		if (value == null)
			throw new NullPointerException();

		if (size == 0) {

			first = new ListNode<>(value);
			size++;
			last = first;
			modificationCount++;
		} else if (size == 1) {
			last = new ListNode<>(value);
			first.setNext(last);
			last.setPerivous(first);
			size++;
			modificationCount++;
		} else {
			last.setNext(new ListNode<>(value));
			(last.getNext()).setPerivous(last);
			last = last.getNext();
			size++;
			modificationCount++;
		}
	}

	@Override
	public Object get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		ListNode<T> current;
		if (index < (size / 2)) {
			current = first;
			for (int i = 0; i < index; i++) {
				current = current.getNext();
			}
		} else {
			current = last;
			for (int i = size - 1; i > index; i--) {
				current = current.getPerivous();
			}
		}
		return current.getContent();
	}

	@Override
	public boolean contains(Object value) {
		ListNode<T> current = first;
		for (int i = 0; i < size; i++) {
			if (current.getContent().equals(value))
				return true;
			current = current.getNext();
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

		if (!contains(value))
			return false;

		ListNode<T> current = first;

		if (size == 1) {
			if (current.getContent().equals(value)) {
				size--;
				first = null;
				last = null;
				modificationCount++;
				return true;
			}
		}

		if (first.getContent().equals(value)) {
			first = first.getNext();
			size--;
			modificationCount++;
			return true;
		}

		if (last.getContent().equals(value)) {
			last = last.getPerivous();
			size--;
			modificationCount++;
			return true;
		}

		current = first.getNext();

		for (int i = 0; i < size - 1; i++) {
			if (current.getContent().equals(value))
				break;
			current = current.getNext();
		}
		ListNode<T> perivous = current.getPerivous();
		perivous.setNext(current.getNext());
		size--;
		ListNode<T> next = current.getNext();
		next.setPerivous(current.getPerivous());
		modificationCount++;
		return true;

	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode<T> current = first;
		for (int i = 0; i < size; i++) {
			array[i] = current.getContent();
			current = current.getNext();
		}
		boolean allAreNull = true;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				allAreNull = false;
				break;
			}
		}
		if (allAreNull) {
			throw new UnsupportedOperationException();
		}
		return array;
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
				this.collection.add(value);
			}
		}

		AddAllProcessor processor = new AddAllProcessor(this);

		other.forEach(processor);
		modificationCount++;
	}

	@Override
	public void clear() {
		first.setNext(null);
		first.setContent(null);
		last.setPerivous(null);
		first.setContent(null);
		size = 0;
		modificationCount++;
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in array.
	 * 
	 * @param value    Object you want to insert
	 * @param position at which you want to insert
	 * 
	 * @throws IndexOutOfBoundsException if position isn't in range 0 to size
	 * @throws NullPointerException      if value is null
	 */
	@Override
	public void insert(T value, int position) {
		if (position < 0 || position > (size))
			throw new IndexOutOfBoundsException();
		if (value == null)
			throw new NullPointerException();

		ListNode<T> current = first;
		for (int i = 0; i < position; i++) {
			current = current.getNext();
		}

		ListNode<T> newNode = new ListNode<>(value);
		current.getPerivous().setNext(newNode);
		newNode.setPerivous(current.getPerivous());
		newNode.setNext(current);
		current.setPerivous(newNode);
		size++;
		modificationCount++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found.
	 * 
	 * @param value whose index you want to know
	 * @throws NullPointerException if value is null
	 * @return index of value if fount, otherwise -1
	 */
	@Override
	public int indexOf(Object value) {
		ListNode<T> current = first;
		for (int i = 0; i < size; i++) {
			if (current.getContent().equals(value))
				return i;
			current = current.getNext();
		}
		return -1;
	}

	/**
	 * Removes element at specified index from collection.
	 * 
	 * @param index Object which you want to remove
	 * @throws IndexOutOfBoundsException if isn't in range o to size-1
	 */
	@Override
	public void remove(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		ListNode<T> current = first;
		if (index == 0) {
			first = first.getNext();
			first.setPerivous(null);
		} else if (index == (size - 1)) {
			last = last.getPerivous();
			last.setNext(null);
		} else {
			for (int i = 0; i < index; i++)
				current = current.getNext();
			ListNode<T> perivous = current.getPerivous();
			perivous.setNext(current.getNext());

			ListNode<T> next = current.getNext();
			next.setPerivous(current.getPerivous());
		}
		modificationCount++;
		size--;
	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		elementsGetter = new ElementsGetterLinked<>(this);
		return elementsGetter;
	}
	
	@Override
	public void addAllSatisfying(Collection<T> col, Tester tester) {
		ElementsGetter<T> eG= col.createElementsGetter();
		while(eG.hasNextElement()) {
			T next=eG.getNextElement();
			if(tester.test(next))
				this.add(next);
		}	
	}

	private static class ElementsGetterLinked<T> implements ElementsGetter<T> {
		LinkedListIndexedCollection<T> collection;
		ListNode<T> current;
		boolean hasNext = true;
		long savedModificationCount;

		public ElementsGetterLinked(LinkedListIndexedCollection<T> collection) {
			this.collection = collection;
			current = collection.first;
			this.savedModificationCount = collection.modificationCount;
		}
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != collection.modificationCount)
				throw new ConcurrentModificationException();
			return (current.getContent() != null && hasNext);
		}
		@Override
		public T getNextElement() {
			if (savedModificationCount != collection.modificationCount)
				throw new ConcurrentModificationException();
			if (hasNextElement()) {
				T object = current.getContent();
				if (current.getNext() != null)
					current = current.getNext();
				else
					hasNext = false;
				return object;
			} else
				throw new NoSuchElementException();

		}
		
		@Override
 		public void processRemaining(Processor<? super T> p) {
			while(hasNextElement())
 				p.process(getNextElement());
 		}
	}

	private static class ListNode<T> {

		private T content;
		private ListNode<T> next;
		private ListNode<T> perivous;

		public ListNode(T content) {
			this.content = content;
		}

		public ListNode<T> getNext() {
			return next;
		}

		public void setNext(ListNode<T> next) {
			this.next = next;
		}

		public ListNode<T> getPerivous() {
			return perivous;
		}

		public void setPerivous(ListNode<T> perivous) {
			this.perivous = perivous;
		}

		public T getContent() {
			return content;
		}

		public void setContent(T content) {
			this.content = content;
		}

	}
}
