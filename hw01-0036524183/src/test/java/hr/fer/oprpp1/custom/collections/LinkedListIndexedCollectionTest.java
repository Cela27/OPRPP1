package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	

	@Test
	public void nullPointerExceptionConstructorTest() {
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
	}
	
	@Test
	public void testingIfConstructorWithCollectionWorks() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		collection.add("Minotaur");
		LinkedListIndexedCollection collection2 = new LinkedListIndexedCollection(collection);
		boolean works=false;
		if(collection2.contains("Dragon") && collection2.contains("Elf") && collection2.contains("Minotaur"))
			works=true;
		assertTrue(works);
	}
	
	@Test
	public void testIsEmptyTrue() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertTrue(collection.isEmpty());
	}

	@Test
	public void testIsEmptyFalse() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		assertFalse(collection.isEmpty());
	}

	@Test
	public void testMethodSize() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		collection.add("Minotaur");
		int expected = 3;
		assertEquals(expected, collection.size());
	}

	@Test
	public void nullAsAnArgumentExceptionForAdd() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> collection.add(null));
	}

	@Test
	public void testIfMethodAddWorks() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		collection.add("Minotaur");
		boolean works=false;
		if(collection.contains("Dragon") && collection.contains("Elf") && collection.contains("Minotaur")) {
			works=true;
		}
		
		assertTrue(works);
	}

	@Test
	public void testIfMethodContainsWorksTrue() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		collection.add("Minotaur");
		assertTrue(collection.contains("Elf"));
	}

	@Test
	public void testIfMethodContainsWorksFalse() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		assertFalse(collection.contains("Elf"));
	}

	@Test
	public void nullAsAnArgumentExceptionForRemoveByValue() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> collection.remove(null));
	}

	@Test
	public void testIfMethodRemoveByValueWorksTrueSizeIs1() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		assertTrue(collection.remove((Object) "Dragon"));
	}
	
	@Test
	public void testIfMethodRemoveByValueWorksTrueSizeIs2() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		assertTrue(collection.remove((Object) "Dragon"));
	}
	
	@Test
	public void testIfMethodRemoveByValueWorksTrueSizeIs3() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		collection.add("Minotaur");
		assertTrue(collection.remove((Object) "Elf"));
	}
	
	@Test
	public void testIfMethodRemoveByValueWorksFalse() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		assertFalse(collection.remove((Object) "Elf"));
	}

	@Test
	public void unsupportedOperationExceptionForToArray() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertThrows(UnsupportedOperationException.class, () -> collection.toArray());
	}

	@Test
	public void testIfMethodToArrayWorks() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		Object[] expectedArray = { "Dragon", "Elf" };
		Object[] array = collection.toArray();
		String expected = expectedArray[0].toString() + " " + expectedArray[1].toString();
		String functionGiven = array[0].toString() + " " + array[1].toString();
		assertEquals(expected, functionGiven);
	}

	@Test
	public void testAddAllWhichIsAlsoForEachTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
	
		ArrayIndexedCollection collection2 = new ArrayIndexedCollection();
		collection.add("Minotaur");
		collection.add("Unicorn");
		
		collection.addAll(collection2);
		
		String expected= "Dragon"+ "Elf"+ "Minotaur"+"Unicorn";
		String functionGiven= collection.get(0).toString() + collection.get(1).toString()+ 
				collection.get(2).toString()+ collection.get(3).toString();
		assertEquals(expected, functionGiven);
	}

	@Test
	public void testIfMethodClearWorks() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		collection.add("Minotaur");
		collection.clear();
		int expected = 0;
		assertEquals(expected, collection.size());
	}

	@Test
	public void indexOutOfBoundsExceptionForGet() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
	}

	@Test
	public void testIfMethodGetWorksGoingFromFront() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		collection.add("Minotaur");
		collection.add("Unicorn");
		collection.add("Demon");
		String expected = "Elf";
		assertEquals(expected,  (String)collection.get(1));
	}
	
	@Test
	public void testIfMethodGetWorksGoingFromBack() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		collection.add("Minotaur");
		collection.add("Unicorn");
		collection.add("Demon");
		String expected = "Unicorn";
		assertEquals(expected, (String)collection.get(3));
	}
	
	@Test
	public void indexOutOfBoundsExceptionForInsert() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(1, -1));
	}

	@Test
	public void nullPointerExceptionForInsert() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		assertThrows(NullPointerException.class, () -> collection.insert(null, 1));
	}

	@Test
	public void testIfMethodInsertWorks() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Minotaur");
		collection.insert("Elf", 1);
		String expected= "Elf";
		assertEquals(expected, collection.get(1));
	}

	@Test
	public void testIfMethodIndexOfWorksWhenDoesntContain() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		int expected = -1;
		assertEquals(expected, collection.indexOf("Minotaur"));
	}

	@Test
	public void testIfMethodIndexOfWorksWhenContains() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		int expected = 0;
		assertEquals(expected, collection.indexOf("Dragon"));
	}

	@Test
	public void indexOutOfBoundsExceptionForRemoveByIndex() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-1));
	}

	@Test
	public void testIfMethodRemoveByIndexWorks() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("Dragon");
		collection.add("Elf");
		collection.add("Minotaur");
		collection.remove(0);
		assertFalse(collection.contains("Dragon"));
	}

}
