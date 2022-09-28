package hr.fer.oprpp1.hw04.db;

/**
 * Class representing student records.
 * @author Antonio
 *
 */
public class StudentRecord {

	private String jmbag;
	private String firstName;
	private String lastName;
	private String finalGrade;
	/**
	 * Basic constructor using Strings as parameters.
	 * @param jmbag Students jmbag
	 * @param lastName Students last name
	 * @param firstName Students first name
	 * @param finalGrade Students final grade 
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		
		if(Integer.parseInt(finalGrade)>5 || Integer.parseInt(finalGrade)<1) throw new IndexOutOfBoundsException();
		
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * Getter for {@link StudentRecord} jmbag.
	 * @return String jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for {@link StudentRecord} first name.
	 * @return String first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Getter for {@link StudentRecord} last name.
	 * @return String last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for {@link StudentRecord} final grade.
	 * @return Integer final grade
	 */
	public Integer getFinalGrade() {
		return Integer.parseInt(finalGrade);
	}

	@Override
	public int hashCode() {
		return jmbag.hashCode();
	}


	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StudentRecord))
			return false;
		StudentRecord other= (StudentRecord) obj;
		return this.getJmbag().equals(other.getJmbag());
	}	
}
