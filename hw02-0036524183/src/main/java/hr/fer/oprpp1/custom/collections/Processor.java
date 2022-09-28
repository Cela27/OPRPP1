package hr.fer.oprpp1.custom.collections;
/**
 * Class which represents a processor with process method. 
 *
 */
public interface Processor {

	/**
	 * Method that processes a given value
	 *  
	 * @param value Object which will be processed
	 */
	public abstract void process(Object value);
}
