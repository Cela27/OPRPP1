package hr.fer.oprpp1.custom.collections;

/**
 * Class which represents {@link ElementsGetter} for {@link Collection}
 * 
 */
public interface ElementsGetter<T> {
	
	/**
	 * Returns if {@link Collection} has next element
	 * 
	 * @return true if there is next element, else false
	 */
	 public abstract boolean hasNextElement();
	 /**
	  * Returns next element
	  * 
	  * @return next Element
	  */
	 public abstract T getNextElement();
	 /**
	  * Puts remaining elemenzts in given {@link Processor}
	  * @param p {@link Processor} used for processing
	  */
	 public abstract void processRemaining(Processor<? super T> p);
}
