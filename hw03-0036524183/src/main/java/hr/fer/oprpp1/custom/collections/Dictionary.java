package hr.fer.oprpp1.custom.collections;

/**
 * {@link Dictionary} of different key and value pairs
 *
 * @param <K> type of key 
 * @param <V> type of value
 */
public class Dictionary<K, V> {
	
	/**
	 * {@link ArrayIndexedCollection} used in implementation
	 */
	private ArrayIndexedCollection<Pair<K, V>> array;
	
	/**
	 * Basic constructor for {@link Dictionary}
	 */
	public Dictionary() {
		array = new ArrayIndexedCollection<>();
	}
	
	/**
	* Returns a boolean variable as a result of asking is dictionary empty.
	* 
	* @return      true if dictionary is empty, otherwise false
	*/
	public boolean isEmpty() {
		return array.isEmpty();
	}
	/**
	* Returns the number of currently stored objects in this dictionary.
	* 
	* @return      size of dictionary
	*/
	public int size() {
		return array.size();
	}
	/**
	* Removes all elements from this collection. Implement it here as an empty method.
	*/
	public void clear() {
		array.clear();
	}
	/**
	 * Puts pair of key and value in {@link Dictionary}
	 * 
	 * @param key key of pair
	 * @param value value of pair
	 * @return value if key wasn't in {@link Dictionary} else old value
	 */
	public V put(K key, V value) {
		if (key == null)
			throw new NullPointerException();
		V returningValue= null;
		boolean added=false;
		for (int i = 0; i < array.size() - 1; i++) {
			@SuppressWarnings("unchecked")
			Pair<K, V> pair = (Pair<K, V>) array.get(i);
			if (pair.getKey().equals(key)) {
				returningValue = pair.getValue();
				pair.setValue(value);
				added=true;
				break;
			}
		}
		if(!added) {
			array.add(new Pair<>(key, value));
			returningValue=value;
			
		}
		return returningValue;
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
		for (int i = 0; i < array.size(); i++) {
			@SuppressWarnings("unchecked")
			Pair<K, V> pair = (Pair<K, V>) array.get(i);
			if (pair.getKey().equals(key)) {
				return pair.getValue();
			}
		}
		return null;
	}

	/**
	* Removes pair with given key
	* 
	* @param key <K> which you want to remove from {@link Dictionary}
	* @return removed value or null if value wasn't in {@link Dictionary}
	*/
	public V remove(K key) {
		if (key == null)
			throw new NullPointerException();
		for (int i = 0; i < array.size(); i++) {
			@SuppressWarnings("unchecked")
			Pair<K, V> pair = (Pair<K, V>) array.get(i);
			if (pair.getKey().equals(key)) {
				V value = pair.getValue();
				array.remove(i);
				return value;
			}
		}
		return null;
	}

	private static class Pair<K, V> {

		private K key;
		private V value;

		public Pair(K key, V value) {
			if (key == null)
				throw new NullPointerException();
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}
	}
}
