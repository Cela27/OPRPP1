package hr.fer.oprpp1.custom.collections;

/**
 * Class which represents tester 
 *
 */
public interface Tester {
	/**
	 * Tests if given object passes the desired test
	 * 
	 * @param obj Object which is tested
	 * @return true if given object passes the test, else false
	 */
	public abstract boolean test(Object obj);
}
