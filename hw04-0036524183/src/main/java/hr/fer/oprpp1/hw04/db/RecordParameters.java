package hr.fer.oprpp1.hw04.db;
import java.util.List;

/**
 * Class for {@link StudentRecord} length parameters.
 * @author Antonio
 *
 */
public class RecordParameters {
	private List<StudentRecord> list;
	private int jmbagLength;
	private int lastNameLength;
	private int firstNameLength;
	
	/**
	 * Basic constructor using list of conditional expressions as parameter.
	 * @param list
	 */
	public RecordParameters(List<StudentRecord> list) {
		this.list=list;
		getParameters();
	}

	private void getParameters() {
		int maxJmbag=0;
		int maxFirstname=0;
		int maxLastName=0;
		
		for(StudentRecord r: list) {
			if(r.getJmbag()=="0000000015")
				System.out.println("Length je:"+ r.getLastName().length());
			
			if(r.getJmbag().length()>maxJmbag)
				maxJmbag=r.getJmbag().length();

			if(r.getFirstName().length()>maxFirstname)
				maxFirstname=r.getFirstName().length();

			if(r.getLastName().length()>maxLastName)
				maxLastName=r.getLastName().length();
			
			jmbagLength=maxJmbag;
			firstNameLength=maxFirstname;
			lastNameLength=maxLastName;
		}
	}
	
	/**
	 * Getter of jmbag length.
	 * @return {@link Integer} jmbag length
	 */
	public int getJmbagLength() {
		return jmbagLength;
	}
	/**
	 * Getter of last name length.
	 * @return {@link Integer} last name length
	 */
	public int getLastNameLength() {
		return lastNameLength;
	}
	/**
	 * Getter of first name length.
	 * @return {@link Integer} first name length
	 */
	public int getFirstNameLength() {
		return firstNameLength;
	}

	
}
