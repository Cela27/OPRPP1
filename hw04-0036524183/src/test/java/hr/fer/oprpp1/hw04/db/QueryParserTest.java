package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QueryParserTest {
	
	@Test
	public void testIsDirectQueryTrue() {
		QueryParser parser= new QueryParser("query jmbag=\"0036524183\"");
		assertTrue(parser.isDirectQuery());
	}
	
	@Test
	public void testIsDirectQueryFalse() {
		QueryParser parser= new QueryParser("query jmbag>\"00036524183\"");
		assertFalse(parser.isDirectQuery());
		
		parser= new QueryParser("query firstName=\"00036524183\"");
		assertFalse(parser.isDirectQuery());
		
		parser= new QueryParser("query jmbag=\"00036524183\" and  jmbag=\"00365\"");
		assertFalse(parser.isDirectQuery());
	}
	
	@Test
	public void testIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, ()->new QueryParser("qusery jmbag=\"0036524183\"      "));
		
		assertThrows(IllegalArgumentException.class, ()->new QueryParser("query sjmbag=\"0036524183\""));
		
		assertThrows(IllegalArgumentException.class, ()->new QueryParser("query jmbag==\"0036524183\""));
		
		assertThrows(IllegalArgumentException.class, ()->new QueryParser("query jmbag=\"0036524183\" amd jmbag=\"zmaj\""));
		
	}

	@Test
	public void testGetQuriedJMBAG() {
		QueryParser parser= new QueryParser("query jmbag=\"0036524183\"");
		assertEquals("0036524183", parser.getQueriedJMBAG());
	}
	
	@Test
	public void testIllegalStateExcptionForGetQuriedJMBAG() {
		QueryParser parser= new QueryParser("query jmbag>=\"0036524183\"");
		assertThrows(IllegalStateException.class, ()->parser.getQueriedJMBAG());
	}
}
