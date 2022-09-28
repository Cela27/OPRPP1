package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {
	
	@Test
	public void testJmbagGetter() {
		StudentRecord sr= new StudentRecord("0036524183", "Celinscak", "Antonio", "5");
		
		assertEquals("0036524183", FieldValueGetters.JMBAG.get(sr));
		assertEquals("Celinscak", FieldValueGetters.LAST_NAME.get(sr));
		assertEquals("Antonio", FieldValueGetters.FIRST_NAME.get(sr));
	}
}
