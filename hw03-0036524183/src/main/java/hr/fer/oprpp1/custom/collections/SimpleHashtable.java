package hr.fer.oprpp1.custom.collections;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;
/**
 * 
 * Hash table of different key and value pairs
 *
 * @param <K> type of key 
 * @param <V> type of value
 */
public class SimpleHashtable<K, V> implements Iterable<TableEntry<K, V>> {
	/**
	 * Table used in implementation
	 */
	private TableEntry<K, V>[] table; 
	/**
	 * Number of pair in table
	 */
	private int size; 
	/**
	 * Capacity of table
	 */
	private int capacity;
	/**
	 * Integer variable counting number of modifications on Collection
	 */
	private int modificationCount=0;

	/**
	 * Basic constructor for SimpleHashtable
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		capacity = 16;
		table = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, 16);
		size = 0;
	}
	/**
	 * Constructor for SimpleHashtable with given capacity
	 * @param capacity Integer capacity is nearest potention of number 2 to given capacity
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException();
		this.capacity = capacity;
		int current = 1;
		int lowestDifference = Math.abs(capacity - current);
		while (true) {
			current = current * 2;
			if (Math.abs(capacity - current) < lowestDifference) {
				lowestDifference = Math.abs(capacity - current);
				continue;
			} else {
				break;
			}
		}

		table = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, current / 2);
		size = 0;
	}
	/**
	 * Puts pair of key and value in {@link SimpleHashtable}
	 * 
	 * @param key key of pair
	 * @param value value of pair
	 * @return value if key wasn't in {@link SimpleHashtable} else old value
	 */
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		if (key == null)
			throw new NullPointerException();
		float popunjenost = ((float) size) / capacity;
		if (popunjenost > 0.75) {
			TableEntry<K, V>[] array = this.toArray();
			clear();
			table = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, capacity * 2);
			capacity *= 2;
			for (int i = 0; i < array.length; i++) {
				this.put(array[i].getKey(), array[i].getValue());
			}
		}
		if (containsKey(key)) {
			TableEntry<K, V> current;
			for (int i = 0; i < table.length; i++) {
				if (table[i] == null)
					continue;
				current = table[i];
				do {
					if (current != table[i]) {
						current = current.getNext();
					}
					if (current.getKey().equals(key)) {
						V returningValue = current.getValue();
						current.setValue(value);
						return returningValue;
					}
				} while (current.getNext() != null);
			}
		} else {
			modificationCount++;
			size++;
			int slot = Math.abs(key.hashCode()) % (table.length);
			if (table[slot] == null) {

				TableEntry<K, V> current = new TableEntry<>(key, value);
				table[slot] = current;
				return value;
			}
			else {
				TableEntry<K, V> current = table[slot];
				if (current.getNext() == null) {
					current.setNext(new TableEntry<>(key, value));
					return value;
				} else {
					do {
						current = current.getNext();
						if (current.getNext() == null) {
							current.setNext(new TableEntry<>(key, value));
							return value;
						}

					} while (current.getNext() != null);
				}
			}

		}
		return value;
	}
	/**
	 * Returns object from table for given key
	 * 
	 * @param key Object from which we are looking for value
	 * @return value for given key
	 */
	public V get(Object key) {
		if (key == null)
			throw new NullPointerException();
		if (containsKey(key)) {
			TableEntry<K, V> current;
			for (int i = 0; i < table.length; i++) {
				if (table[i] == null)
					continue;
				current = table[i];

				if (current.getKey().equals(key)) {
					return current.getValue();
				}

				while (current.getNext() != null) {
					current = current.getNext();
					if (current.getKey().equals(key)) {
						return current.getValue();
					}
				}
			}
		}
		return null;
	}
	/**
	* Returns true only if the {@link SimpleHashtable} contains given value as determined by equals method and removes
	* one occurrence of it
	* 
	* @param key <K> which you want to remove from {@link SimpleHashtable}
	* @return removed value or null if value wasn't in {@link SimpleHashtable}
	*/
	public int size() {
		return size;
	}
	/**
	 * Checks if {@link SimpleHashtable} contains given key
	 * @param key Object which is given
	 * @return true if contains, else false
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			throw new NullPointerException();

		TableEntry<K, V> current;
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null)
				continue;
			current = table[i];
			if (current.getKey().equals(key)) {
				return true;
			}
			while (current.getNext() != null) {
				current = current.getNext();
				if (current.getKey().equals(key)) {
					return true;
				}
			}
		}

		return false;
	}
	/**
	 * Checks if {@link SimpleHashtable} contains given value
	 * @param value Object which is given
	 * @return true if contains, else false
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> current;
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null)
				continue;
			current = table[i];
			if (value == null && current.getValue() == null) {
				return true;
			}
			if (current.getValue().equals(value)) {
				return true;
			}

			while (current.getNext() != null) {
				current = current.getNext();
				if (value == null && current.getValue() == null) {
					return true;
				}
				if (current.getValue().equals(value)) {
					return true;
				}
			}
		}

		return false;
	}
	/**
	* Removes pair with given key
	* 
	* @param key <K> which you want to remove from {@link SimpleHashtable}
	* @return removed value or null if value wasn't in {@link SimpleHashtable}
	*/
	public V remove(Object key) {
		if (key == null)
			throw new NullPointerException();

		if (containsKey(key)) {
			size--;
			modificationCount++;
			TableEntry<K, V> current;
			for (int i = 0; i < table.length; i++) {
				if (table[i] == null)
					continue;
				current = table[i];
				do {
					if (current != table[i]) {
						current = current.getNext();
					}
					if (current.getNext() == null) {
						if (current.getKey().equals(key)) {
							table[i] = null;
							return current.getValue();
						}
					} else {
						if (current.getKey().equals(key)) {
							V value = current.getValue();
							table[i] = current.getNext();
							return value;
						}
						else {

							do {
								TableEntry<K, V> perivous = current;
								current = current.getNext();

								if (current.getKey().equals(key)) {
									TableEntry<K, V> next;
									if (current.getKey().equals(key)) {
										if (current.getNext() != null) {
											next = current.getNext();
											perivous.setNext(next);
											return current.getValue();
										}
										else {
											perivous.setNext(null);
											return current.getValue();
										}
									}
								}
							} while (current.getNext() != null);
						}
					}

				} while (current.getNext() != null);
			}
		}
		return null;
	}
	/**
	* Returns a boolean variable as a result of asking is {@link SimpleHashtable} empty.
	* 
	* @return      true if dictionary is empty, otherwise false
	*/
	public boolean isEmpty() {
		if(size==0)
			return true;
		return false;
	}
	/**
	 * Returns {@link SimpleHashtable} as string
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		TableEntry<K, V> current;
		boolean appended = false;
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null)
				continue;
			current = table[i];
			if (appended) {
				sb.append(", ");
			}
			sb.append(current.getKey()).append("=").append(current.getValue());
			appended = true;
			while (current.getNext() != null) {
				current = current.getNext();
				sb.append(", ").append(current.getKey()).append("=").append(current.getValue());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray() {
		TableEntry<K, V>[] array = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, this.size);
		TableEntry<K, V> current;
		int counter = 0;
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null)
				continue;
			current = table[i];
			array[counter] = current;
			counter++;
			while (current.getNext() != null) {
				current = current.getNext();
				array[counter] = current;
				counter++;
			}
		}
		return array;
	}

	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}
	
	@Override
	public Iterator<TableEntry<K,V>> iterator() {
		return new IteratorImpl();
	}

	private class IteratorImpl implements Iterator<TableEntry<K, V>> {
		private int counter=0;
		private int currentSlot=0;
		private int modificationCountIterator;
		TableEntry<K, V> current=null;
		
		
		
		public IteratorImpl() {
			modificationCountIterator = modificationCount;
		}

		public boolean hasNext() {
			if(modificationCountIterator!=modificationCount)
				throw new ConcurrentModificationException();
			if(counter<size) return true;
			return false;
		}

		public TableEntry<K,V> next() {
			if(modificationCountIterator!=modificationCount)
				throw new ConcurrentModificationException();
			if(hasNext()) {
				if(current==null){
					for(int i=currentSlot;i<table.length ;i++) {
						if(table[i]!=null) {
							currentSlot=i;
							current=table[currentSlot];
							counter++;
							return current;
						}
					}
				}
				else if(current.getNext()==null) {
					currentSlot++;
					for(int i=currentSlot;i<table.length ;i++) {
						if(table[i]!=null) {
							currentSlot=i;
							current=table[currentSlot];
							counter++;
							return current;
						}
					}
				}
				else {
					current=current.getNext();
					counter++;
					return current;
				}
			}
			else {
				throw new NoSuchElementException();
			}
			return current;
		}

		public void remove() {
			if(modificationCountIterator!=modificationCount)
				throw new ConcurrentModificationException();
			if(!containsKey(current.getKey()))
				throw new IllegalStateException();
			SimpleHashtable.this.remove((Object)current.getKey());
			modificationCountIterator++;
			counter--;
		}
	}

	public static class TableEntry<K, V> {
		private K key;
		private V value;

		private TableEntry<K, V> next = null;

		public TableEntry(K key, V value) {
			if (key == null)
				throw new NullPointerException();
			this.key = key;
			this.value = value;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public TableEntry<K, V> getNext() {
			return next;
		}

		public void setNext(TableEntry<K, V> next) {
			this.next = next;
		}

		public K getKey() {
			return key;
		}

		@Override
		public String toString() {
			return "TableEntry [key=" + key + ", value=" + value + "]";
		}
		
		
	}
}
