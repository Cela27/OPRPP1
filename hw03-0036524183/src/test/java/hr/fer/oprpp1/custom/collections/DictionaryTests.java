package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.Test;

public class DictionaryTests {
	@Test
	public void testFunctionIsEmptyTrue() {
		Dictionary<Integer, String> dictionary= new Dictionary<>();
		assertTrue(dictionary.isEmpty());
	}

	@Test
	public void testFunctionIsEmptyFalse() {
		Dictionary<Integer, String> dictionary= new Dictionary<>();
		dictionary.put(1, "zmaj");
		assertFalse(dictionary.isEmpty());
	}
	
	@Test
	public void testFunctionSize() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "zmaj1");
		dictionary.put(2, "zmaj2");
		dictionary.put(3, "zmaj3");
		assertEquals(3, dictionary.size());
	}

	@Test
	public void testFunctionClear() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "zmaj1");
		dictionary.put(2, "zmaj2");
		dictionary.put(3, "zmaj3");
		dictionary.clear();
		assertTrue(dictionary.isEmpty());
	}

	@Test
	public void testFunctionPutKeyIsNull() {
		Dictionary<Integer, String> dictionary= new Dictionary<>();
		assertThrows(NullPointerException.class, ()-> dictionary.put(null, "zmaj"));
	}
	
	@Test
	public void testFunctionPutKeyWorks() {
		Dictionary<Integer, String> dictionary= new Dictionary<>();
		dictionary.put(1, "zmaj1");
		dictionary.put(2, "zmaj2");
		dictionary.put(3, "zmaj3");
		assertEquals("zmaj1", dictionary.get(1));
		assertEquals("zmaj2", dictionary.get(2));
		assertEquals("zmaj3", dictionary.get(3));
	}
	
	@Test
	public void testFunctionGetKeyInDictionary() {
		Dictionary<Integer, String> dictionary= new Dictionary<>();
		dictionary.put(1, "zmaj1");
		dictionary.put(2, "zmaj2");
		dictionary.put(3, "zmaj3");
		assertEquals("zmaj2", dictionary.get(2));
	}
	
	@Test
	public void testFunctionGetKeyNotInDictionary() {
		Dictionary<Integer, String> dictionary= new Dictionary<>();
		dictionary.put(1, "zmaj1");
		dictionary.put(2, "zmaj2");
		dictionary.put(3, "zmaj3");
		assertEquals(null, dictionary.get(4));
	}
	
	@Test
	public void testFunctionRemoveKeyInDictionary() {
		Dictionary<Integer, String> dictionary= new Dictionary<>();
		dictionary.put(1, "zmaj1");
		dictionary.put(2, "zmaj2");
		dictionary.put(3, "zmaj3");
		assertEquals("zmaj2", dictionary.remove(2));
		assertEquals("zmaj1", dictionary.get(1));
		assertEquals("zmaj3", dictionary.get(3));
		assertEquals(2, dictionary.size());
	}
	
	@Test
	public void testFunctionRemoveKeyNotInDictionary() {
		Dictionary<Integer, String> dictionary= new Dictionary<>();
		dictionary.put(1, "zmaj1");
		dictionary.put(2, "zmaj2");
		dictionary.put(3, "zmaj3");
		assertEquals(null, dictionary.remove(5));
	}
}
