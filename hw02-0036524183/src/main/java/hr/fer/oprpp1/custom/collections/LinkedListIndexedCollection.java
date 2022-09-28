package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * CONSTRUCTOR BEZ GET
 * 
 */

/**
 * Class which represents linked list-backed collection of objects. You can work
 * with object using class methods.
 */
public class LinkedListIndexedCollection implements List {

	/**
	 * current size of collection (number of elements actually stored in elements
	 * array)
	 */
	private int size;
	/**
	 * reference to the first node of the linked list
	 */
	private ListNode first;
	/**
	 * reference to the last node of the linked list
	 */
	private ListNode last;

	private ElementsGetter elementsGetter;
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
	public LinkedListIndexedCollection(Collection collection) {
		if (collection == null)
			throw new NullPointerException();
		this.size = collection.size();
		Object[] array= collection.toArray();

		first = new ListNode(array[0]);
		ListNode current = first;

		for (int i = 0; i < collection.size() - 1; i++) {
			current.setContent(array[i]);
			ListNode next = new ListNode(array[i+1]);
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
	public void add(Object value) {
		if (value == null)
			throw new NullPointerException();

		if (size == 0) {

			first = new ListNode(value);
			size++;
			last = first;
			modificationCount++;
		} else if (size == 1) {
			last = new ListNode(value);
			first.setNext(last);
			last.setPerivous(first);
			size++;
			modificationCount++;
		} else {
			last.setNext(new ListNode(value));
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
		ListNode current;
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
		ListNode current = first;
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

		ListNode current = first;

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
		ListNode perivous = current.getPerivous();
		perivous.setNext(current.getNext());
		size--;
		ListNode next = current.getNext();
		next.setPerivous(current.getPerivous());
		modificationCount++;
		return true;

	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode current = first;
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
	public void addAll(Collection other) {
		class AddAllProcessor implements Processor {
			Collection collection;

			public AddAllProcessor(Collection collection) {
				this.collection = collection;
			}

			@Override
			public void process(Object value) {
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
	public void insert(Object value, int position) {
		if (position < 0 || position > (size))
			throw new IndexOutOfBoundsException();
		if (value == null)
			throw new NullPointerException();

		ListNode current = first;
		for (int i = 0; i < position; i++) {
			current = current.getNext();
		}

		ListNode newNode = new ListNode(value);
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
		ListNode current = first;
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
		ListNode current = first;
		if (index == 0) {
			first = first.getNext();
			first.setPerivous(null);
		} else if (index == (size - 1)) {
			last = last.getPerivous();
			last.setNext(null);
		} else {
			for (int i = 0; i < index; i++)
				current = current.getNext();
			ListNode perivous = current.getPerivous();
			perivous.setNext(current.getNext());

			ListNode next = current.getNext();
			next.setPerivous(current.getPerivous());
		}
		modificationCount++;
		size--;
	}

	@Override
	public ElementsGetter createElementsGetter() {
		elementsGetter = new ElementsGetterLinked(this);
		return elementsGetter;
	}
	
	@Override
	public void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter eG= col.createElementsGetter();
		while(eG.hasNextElement()) {
			Object next=eG.getNextElement();
			if(tester.test(next))
				this.add(next);
		}	
	}

	private static class ElementsGetterLinked implements ElementsGetter {
		LinkedListIndexedCollection collection;
		ListNode current;
		boolean hasNext = true;
		long savedModificationCount;

		public ElementsGetterLinked(LinkedListIndexedCollection collection) {
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
		public Object getNextElement() {
			if (savedModificationCount != collection.modificationCount)
				throw new ConcurrentModificationException();
			if (hasNextElement()) {
				Object object = current.getContent();
				if (current.getNext() != null)
					current = current.getNext();
				else
					hasNext = false;
				return object;
			} else
				throw new NoSuchElementException();

		}
		
		@Override
 		public void processRemaining(Processor p) {
			while(hasNextElement())
 				p.process(getNextElement());
 		}
	}

	private static class ListNode {

		private Object content;
		private ListNode next;
		private ListNode perivous;

		public ListNode(Object content) {
			this.content = content;
		}

		public ListNode getNext() {
			return next;
		}

		public void setNext(ListNode next) {
			this.next = next;
		}

		public ListNode getPerivous() {
			return perivous;
		}

		public void setPerivous(ListNode perivous) {
			this.perivous = perivous;
		}

		public Object getContent() {
			return content;
		}

		public void setContent(Object content) {
			this.content = content;
		}

	}
}
