package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTests {

	@Test
	public void testConstructorFirst() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>();
		assertEquals(0, col.size());
	}

	@Test
	public void testConstructorSecond() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>();
		assertEquals(0, col.size());
	}

	@Test
	public void testConstructorSecondCapacityError() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(-1));
	}

	@Test
	public void testPutKeyIsNull() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>();
		assertThrows(NullPointerException.class, () -> col.put(null, "zmaj"));
	}

	@Test
	public void testPut() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>(2);
		col.put(1, "zmaj1");
		col.put(2, "zmaj2");
		col.put(3, "zmaj3");
		assertEquals("zmaj1", col.get(1));
		assertEquals("zmaj2", col.get(2));
		assertEquals("zmaj3", col.get(3));
		assertEquals(3, col.size());
	}

	@Test
	public void testGetKeyIsNull() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>();
		assertThrows(NullPointerException.class, () -> col.get(null));
	}

	@Test
	public void testGet() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>(2);
		col.put(1, "zmaj1");
		col.put(2, "zmaj2");
		col.put(3, "zmaj3");
		assertEquals("zmaj1", col.get(1));
		assertEquals("zmaj2", col.get(2));
		assertEquals("zmaj3", col.get(3));
	}

	@Test
	public void testSize() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>(2);
		col.put(1, "zmaj1");
		col.put(2, "zmaj2");
		col.put(3, "zmaj3");
		assertEquals(3, col.size());
	}

	@Test
	public void testContainsKeyIsNull() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>(2);
		col.put(1, "zmaj1");
		col.put(2, "zmaj2");
		col.put(3, "zmaj3");
		assertThrows(NullPointerException.class, () -> col.containsKey(null));
	}

	@Test
	public void testContainsKey() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>(2);
		col.put(1, "zmaj1");
		col.put(2, "zmaj2");
		col.put(3, "zmaj3");
		assertTrue(col.containsKey(2));
	}

	@Test
	public void testContainsValueIsNull() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>(2);
		col.put(1, "zmaj1");
		col.put(2, "zmaj2");
		col.put(3, null);
		assertTrue(col.containsValue(null));
	}

	@Test
	public void testContainsValue() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>(2);
		col.put(1, "zmaj1");
		col.put(2, "zmaj2");
		col.put(3, "zmaj3");
		assertTrue(col.containsValue("zmaj2"));
	}

	@Test
	public void testRemoveKeyIsNull() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>();
		assertThrows(NullPointerException.class, () -> col.remove(null));
	}

	@Test
	public void testRemove() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>(2);
		col.put(1, "zmaj1");
		col.put(2, "zmaj2");
		col.put(3, "zmaj3");
		assertEquals("zmaj1", col.remove(1));
		assertEquals("zmaj3", col.remove(3));
		assertEquals(1, col.size());
	}

	@Test
	public void testIsEmptyTrue() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>();
		assertTrue(col.isEmpty());
	}

	@Test
	public void testIsEmptyFalse() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>();
		col.put(1, "zmaj1");
		assertFalse(col.isEmpty());
	}

	@Test
	public void testToArray() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>(2);
		col.put(1, "zmaj1");
		col.put(2, "zmaj2");
		col.put(3, "zmaj3");
		TableEntry<Integer, String>[] array = col.toArray();
		assertEquals(3, array.length);
		assertTrue(col.containsKey(array[0].getKey()));
		assertTrue(col.containsKey(array[1].getKey()));
		assertTrue(col.containsKey(array[2].getKey()));
	}

	@Test
	public void testClear() {
		SimpleHashtable<Integer, String> col = new SimpleHashtable<>(2);
		col.put(1, "zmaj1");
		col.put(2, "zmaj2");
		col.put(3, "zmaj3");
		col.clear();
		assertEquals(0, col.size());
		assertFalse(col.containsKey(1));
		assertFalse(col.containsKey(2));
		assertFalse(col.containsKey(3));
	}

	@Test
	public void testIteratorWorks() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		int counter = 0;
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			counter++;
		}
		assertEquals(4, counter);
	}

	@Test
	public void testIteratorHasNextAndNext() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		TableEntry<String, Integer> te;
		assertTrue(iter.hasNext());
		te = iter.next();
		assertTrue(iter.hasNext());
		te = iter.next();
		assertTrue(iter.hasNext());
		te = iter.next();
		assertTrue(iter.hasNext());
		te = iter.next();
		assertFalse(iter.hasNext());

	}
	
	@Test
	public void testIteratorNextNoSuchElementException() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		TableEntry<String, Integer> te;
		assertTrue(iter.hasNext());
		te = iter.next();
		te = iter.next();
		te = iter.next();
		te = iter.next();
		assertThrows(NoSuchElementException.class, ()->iter.next());
	}
	
	@Test
	public void testIteratorRemove() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}
		assertEquals(3, examMarks.size());
		assertFalse(examMarks.containsKey("Ivana"));
	}
	
	@Test
	public void testIteratorConcurrentModificationException() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		examMarks.remove("Ivana");
		assertThrows(ConcurrentModificationException.class, ()->iter.next());
	}
	
	@Test
	public void testIteratorIllegalStateExceptionn() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
		iter.remove();
		assertThrows(IllegalStateException.class, ()->iter.remove());
	}
}
