package hr.fer.oprpp1.hw04.db;

/**
 * Class with many different comparison operators.
 *
 */
public class ComparisonOperators {
	
	/**
	 * {@link IComparisonOperator} Less
	 */
	public static final IComparisonOperator LESS= (s1, s2)-> {
		return s1.compareTo(s2)<0;
	};
	
	/**
	 * {@link IComparisonOperator} Less or equals
	 */
	public static final IComparisonOperator LESS_OR_EQUALS= (s1, s2)-> {
		return s1.compareTo(s2)<=0;
	};
	
	/**
	 * {@link IComparisonOperator} Greater
	 */
	public static final IComparisonOperator GREATER= (s1, s2)-> {
		return s1.compareTo(s2)>0;
	};
	
	/**
	 * {@link IComparisonOperator} Greater or equals
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS= (s1, s2)-> {
		return s1.compareTo(s2)>=0;
	};
	
	/**
	 * {@link IComparisonOperator} not equals
	 */
	public static final IComparisonOperator NOT_EQUALS= (s1, s2)-> {
		return s1.compareTo(s2)!=0;
	};
	
	/**
	 * {@link IComparisonOperator} equals
	 */
	public static final IComparisonOperator EQUALS= (s1, s2)-> {
		return s1.compareTo(s2)==0;
	};
	
	/**
	 * {@link IComparisonOperator} LIKE, checks if value1 is like value2 using wildcard *
	 */
	public static final IComparisonOperator LIKE=(value1, value2)->{
		if(value2.indexOf("*")!= value2.lastIndexOf("*")) throw new IllegalArgumentException();
		
		if(value2.startsWith("*")) {
			return value1.endsWith(value2.substring(1));
		}
		
		if(value2.endsWith("*"))
			return value1.startsWith(value2.substring(0, value2.length()-1));
		
		String firstPart=value2.substring(0, value2.indexOf("*"));
		String secondPart=value2.substring(value2.indexOf("*")+1);
		
		if((firstPart.length()+secondPart.length())>value1.length()) return false;
		
		return value1.startsWith(firstPart) && value1.endsWith(secondPart);
	};
}
