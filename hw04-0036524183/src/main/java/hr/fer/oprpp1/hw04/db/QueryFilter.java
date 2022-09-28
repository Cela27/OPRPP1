package hr.fer.oprpp1.hw04.db;

import java.util.List;
/**
 * Filter for queries.
 * @author Antonio
 *
 */
public class QueryFilter implements IFilter {
	/**
	 * List of {@link ConditionalExpression}
	 */
	private List<ConditionalExpression> list;
	/**
	 * Constructor for {@link QueryFilter} using {@link List} or {@link ConditionalExpression} as parameter
	 * @param list {@link List} or {@link ConditionalExpression}
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list=list;
	}
	
	
	@Override
	public boolean accepts(StudentRecord record) {
		
		for(ConditionalExpression ce: list) {
			if(!ce.getComparisonOperator().satisfied(ce.getFieldGetter().get(record), ce.getStringLiteral())){
				return false;
			}
		}
		return true;
	}

}
