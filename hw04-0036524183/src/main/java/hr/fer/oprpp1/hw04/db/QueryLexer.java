package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
/**
 * Lexer used in {@link QueryParser}
 * @author Antonio
 *
 */
public class QueryLexer {
	
	private String query;
	private boolean isDirect=true;
	private boolean done=false;
	private List<ConditionalExpression> list;
	
	/**
	 * Basic constructor for {@link QueryLexer} using {@link String} as parameter.
	 * @param query {@link String} needed parameter
	 */
	public QueryLexer(String query) {
		if (query == null)
			throw new NullPointerException();
		query=query.trim();
		if(query.startsWith("query")) {
			query=query.substring(5);
		}
		else {
			throw new IllegalArgumentException();
		}
		query=query.trim();
		this.query=query;
		list=new ArrayList<>();
	}	
	
	/**
	 * returns {@link List} of {@link ConditionalExpression} from given query.
	 * @return {@link List} or {@link ConditionalExpression}
	 */
	public List<ConditionalExpression> getListOfConditionalExpressions() {
		list.add(getNextConditionalExpresion());
		while(!done) {
			isDirect=false;
			query=query.trim();
			String and=query.substring(0,3);
			if(and.toLowerCase().equals("and")) {
				query=query.substring(3);
			}else {
				throw new IllegalArgumentException();
			}
			
			list.add(getNextConditionalExpresion());
		}
		
		return list;
	}
	
	private ConditionalExpression getNextConditionalExpresion() {
		IFieldValueGetter fieldGetter=getNextFieldGetter();
		IComparisonOperator comparisonOperator= getNextComparisonOperator();
		String stringLiteral=getNextStringLiteral();
		
		return new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator);
	}

	private IFieldValueGetter getNextFieldGetter() {
		IFieldValueGetter fieldGetter;
		query=query.trim();
		if(query.startsWith("jmbag")) {
			fieldGetter=FieldValueGetters.JMBAG;
			query=query.substring(5);
		}
		else if(query.startsWith("firstName")) {
			isDirect=false;
			fieldGetter=FieldValueGetters.FIRST_NAME;
			query=query.substring(9);
		}
		else if(query.startsWith("lastName")) {
			isDirect=false;
			fieldGetter=FieldValueGetters.LAST_NAME;
			query=query.substring(9);
		}
		else {
			throw new IllegalArgumentException();
		}
		return fieldGetter;
	}

	private IComparisonOperator getNextComparisonOperator() {
		IComparisonOperator comparisonOperator;
		query=query.trim();
		
		if(query.startsWith("=")) {
			comparisonOperator= ComparisonOperators.EQUALS;
			query=query.substring(1);
		}
		else if(query.startsWith(">=")) {
			isDirect=false;
			comparisonOperator= ComparisonOperators.GREATER_OR_EQUALS;
			query=query.substring(2);
		}
		else if(query.startsWith(">")) {
			isDirect=false;
			comparisonOperator= ComparisonOperators.GREATER;
			query=query.substring(1);
		}
		else if(query.startsWith("<=")) {
			isDirect=false;
			comparisonOperator= ComparisonOperators.LESS_OR_EQUALS;
			query=query.substring(2);
		}
		else if(query.startsWith("<")) {
			isDirect=false;
			comparisonOperator= ComparisonOperators.LESS;
			query=query.substring(1);
		}
		else if(query.startsWith("!=")) {
			isDirect=false;
			comparisonOperator= ComparisonOperators.NOT_EQUALS;
			query=query.substring(2);
		}
		else if(query.startsWith("LIKE")) {
			isDirect=false;
			comparisonOperator= ComparisonOperators.LIKE;
			query=query.substring(4);
		}
		else {
			throw new IllegalArgumentException();
		}
		
		return comparisonOperator;
		
	}

	private String getNextStringLiteral() {
		String stringLiteral;
		query=query.trim();
		if(!query.startsWith("\"")) {
			throw new IllegalArgumentException();
		}
		query=query.substring(1);
		stringLiteral=query.substring(0, query.indexOf('\"'));
		query=query.substring(query.indexOf('\"'));
		
		if(query.length()==1)
			done=true;
		else
			query=query.substring(1);
		return stringLiteral;
	}
	
	/**
	 * Checks if query is direct; form is jmbag="value".
	 * @return true if query is direct, else false.
	 */
	public boolean isDirect() {
		return isDirect;
	}
}
