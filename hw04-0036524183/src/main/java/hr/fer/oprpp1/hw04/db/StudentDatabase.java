package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class representing a database of students.
 * @author Antonio
 *
 */
public class StudentDatabase {
	private List<StudentRecord> listOfStudents=null;
	
	/**
	 * Basic constructor using {@link List} of Strings as parameter. 
	 * @param objects List containing {@link StudentRecord} values.
	 */
	public StudentDatabase(List<String> objects) {
		if(listOfStudents==null)
			listOfStudents= new ArrayList<>();
		for(String str: objects) {
			String splits[]= str.split("	");
			listOfStudents.add(new StudentRecord(splits[0], splits[1], splits[2], splits[3]));			
		}
	}
	
	/**
	 * Returns {@link StudentRecord} for given jmbag.
	 * @param jmbag String given jmbag
	 * @return {@link StudentRecord} for given jmbag
	 */
	public StudentRecord forJMBAG(String jmbag) {
		Map<String, StudentRecord> map =  listOfStudents.stream().collect(Collectors.toMap(StudentRecord::getJmbag, sr->sr));
		return map.get(jmbag);
	}
	
	/**
	 * Returns {@link List} of students after applying filter.
	 * @param filter {@link IFilter} 
	 * @return list of students after filtering
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> studentRecords= new ArrayList<>();
		
		for(StudentRecord sr: listOfStudents) {
			if(filter.accepts(sr))
				studentRecords.add(sr);
		}
		if(studentRecords.size()==0) return null;
		return studentRecords;
	}
}
