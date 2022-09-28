package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void negativeInitialCapacityConstructorTest() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-1));
	}
	
	@Test
	public void nullAsAnArgumentInConstructorWithoutInitialCapacity() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void tooBigCollectionAsArgumentForConstructorWithoutInitialCapacityWithCollection() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		for(int i=0; i<17;i++) {
			collection.add(i);
		}
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(collection));
	}
	
	@Test
	public void nullAsAnArgumentInConstructorWithInitialCapacity() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 12));
	}
	
	@Test
	public void negativeInitialCapacityInConstructorWithInitialCapacityAndCollection() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		collection.add("test");
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(collection, -1));
	}
	
	@Test
	public void testIsEmptyTrue() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		assertTrue(collection.isEmpty());
	}
	
	@Test
	public void testIsEmptyFalse() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		collection.add(5);
		assertFalse(collection.isEmpty());
	}
	
	@Test
	public void testMethodSize() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		collection.add(5);
		collection.add(2);
		int expected = 2;
		assertEquals(expected, collection.size());
	}
	
	@Test
	public void nullAsAnArgumentExceptionForAdd() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> collection.add(null));
	}
	
	@Test
	public void testIfMethodAddWorks() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection(2);
		collection.add(1);
		collection.add(2);
		collection.add(3);
		boolean works=false;
		if(collection.contains(1) && collection.contains(2) && collection.contains(3)) {
			works=true;
		}
		
		assertTrue(works);
	}
	
	@Test
	public void testIfMethodContainsWorksTrue() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		collection.add(1);
		collection.add(2);
		collection.add(3);
		assertTrue(collection.contains(2));
	}
	
	@Test
	public void testIfMethodContainsWorksFalse() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		collection.add(1);
		assertFalse(collection.contains(2));
	}
	
	@Test
	public void nullAsAnArgumentExceptionForRemoveByValue() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> collection.remove(null));
	}
	
	@Test
	public void testIfMethodRemoveByValueWorksTrue() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection(1);
		collection.add(1);
		collection.add(2);
		collection.add(3);
		assertTrue(collection.remove((Object)2));
	}
	
	@Test
	public void testIfMethodRemoveByValueWorksFalse() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection(2);
		collection.add(1);
		assertFalse(collection.remove((Object)2));
	}
	
	@Test
	public void unsupportedOperationExceptionForToArray() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		assertThrows(UnsupportedOperationException.class, () -> collection.toArray());
	}
	
	@Test
	public void testIfMethodToArrayWorks() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		collection.add(1);
		collection.add(2);
		Object[] expectedArray= {1,2}; 
		Object[] array= collection.toArray();
		String expected= expectedArray[0].toString()+ " " + expectedArray[1].toString();
		String functionGiven= array[0].toString() + " " + array[1].toString();
		assertEquals(expected, functionGiven);
	}
	
	@Test
	public void testAddAllWhichIsAlsoForEachTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(1);
		collection.add(2);
	
		ArrayIndexedCollection collection2 = new ArrayIndexedCollection();
		collection2.add(3);
		collection2.add(4);
		
		collection.addAll(collection2);
		
		String expected= "1"+ "2"+ "3"+"4";
		String functionGiven= collection.get(0).toString() + collection.get(1).toString()+ 
				collection.get(2).toString()+ collection.get(3).toString();
		assertEquals(expected, functionGiven);
	}
	
	@Test
	public void testIfMethodClearWorks() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		collection.add(1);
		collection.add(2);
		collection.add(3);
		collection.clear();
		int expected=0;
		assertEquals(expected, collection.size());
	}
	
	@Test
	public void indexOutOfBoundsExceptionForGet() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
	}
	
	@Test
	public void testIfMethodGetWorks() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		collection.add(1);
		collection.add(2);
		int expected =2;
		assertEquals(expected, collection.get(1));
	}
	
	@Test
	public void indexOutOfBoundsExceptionForInsert() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(1, -1));
	}
	
	@Test
	public void nullPointerExceptionForInsert() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> collection.insert(null, 1));
	}
	
	@Test
	public void testIfMethodInsertWorks() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		collection.add(1);
		collection.add(3);
		collection.insert(2, 1);
		int expected=2;
		assertEquals(expected,collection.get(1));
	}
	
	@Test
	public void nullPointerExceptionForIndexOf() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, ()-> collection.indexOf(null));
	}
	
	@Test
	public void testIfMethodIndexOfWorksWhenContains() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		collection.add(1);
		collection.add(2);
		int expected=1;
		assertEquals(expected, collection.indexOf(2));
	}
	
	@Test
	public void testIfMethodIndexOfWorksWhenDoesntContain() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		collection.add(1);
		collection.add(2);
		int expected=-1;
		assertEquals(expected, collection.indexOf(3));
	}
	
	@Test
	public void indexOutOfBoundsExceptionForRemoveByIndex() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-1));
	}
	
	@Test
	public void testIfMethodRemoveByIndexWorks() {
		ArrayIndexedCollection collection= new ArrayIndexedCollection(1);
		collection.add(1);
		collection.add(2);
		collection.add(3);
		collection.remove(1);
		assertFalse(collection.contains(2));
	}
		
}
