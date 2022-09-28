package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {
	
	@Test
	public void testIFilterFalse() {
		StudentDatabase db = null;
		try {
			List<String> list= Files.readAllLines(Path.of("C:\\Eclipse Radne Povrsine\\hw04-0036524183\\hw04-0036524183\\database.txt"));
			db= new StudentDatabase(list);	
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<StudentRecord> db2=db.filter(sr->sr.getFirstName().equals("zmaj"));
		assertTrue(db2==null);
	}
	
	
	@Test
	public void testIFilterTrue() {
		StudentDatabase db = null;
		try {
			List<String> list= Files.readAllLines(Path.of("C:\\Eclipse Radne Povrsine\\hw04-0036524183\\hw04-0036524183\\database.txt"));
			db= new StudentDatabase(list);	
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<StudentRecord> db2=db.filter(sr->Integer.parseInt(sr.getJmbag())>0);
		assertTrue(db2.size()==63);
	}
	
	@Test
	public void testForJMBAG() {
		StudentDatabase db = null;
		try {
			List<String> list= Files.readAllLines(Path.of("C:\\Eclipse Radne Povrsine\\hw04-0036524183\\hw04-0036524183\\database.txt"));
			db= new StudentDatabase(list);	
		} catch (IOException e) {
			e.printStackTrace();
		}
		StudentRecord sr=db.forJMBAG("0000000023");
		assertTrue(sr.getFirstName().equals("Ana") && sr.getLastName().equals("Kalvarešin") && sr.getFinalGrade()==4);
	}
}
