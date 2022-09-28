package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

public class QueryFilterTest {
	
	@Test
	public void testQueryFilterIsDirect() {
		StudentDatabase db=null;
		try {
			List<String> list= Files.readAllLines(Path.of("C:\\Eclipse Radne Povrsine\\hw04-0036524183\\hw04-0036524183\\database.txt"));
			db= new StudentDatabase(list);	
		} catch (IOException e) {
			e.printStackTrace();
		}
		QueryParser parser= new QueryParser("query jmbag=\"0000000011\"");
		
		if(parser.isDirectQuery()) {
			 StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			 assertEquals("Dvorničić", r.getLastName());
			 assertEquals("Jura", r.getFirstName());
			 assertEquals(4, r.getFinalGrade());
		}		
	}
	
	@Test
	public void testQueryFilterNotDirect() {
		StudentDatabase db=null;
		try {
			List<String> list= Files.readAllLines(Path.of("C:\\Eclipse Radne Povrsine\\hw04-0036524183\\hw04-0036524183\\database.txt"));
			db= new StudentDatabase(list);	
		} catch (IOException e) {
			e.printStackTrace();
		}
		QueryParser parser= new QueryParser("query jmbag<\"0000000011\" and jmbag>\"0000000005\"");
		
		int num=0;
		for(StudentRecord r: db.filter(new QueryFilter(parser.getQuery()))) {
			num++;
		}
		assertEquals(5, num);
	}

}
