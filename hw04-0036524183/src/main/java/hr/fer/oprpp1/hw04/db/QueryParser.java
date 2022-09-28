package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Parser of database queries.
 * 
 * @author Antonio
 *
 */
public class QueryParser {

	private List<ConditionalExpression> list;
	private QueryLexer lex;

	/**
	 * Basic constructor for {@link QueryParser} using {@link String} as parameter.
	 * 
	 * @param query {@link String} needed parameter
	 */
	public QueryParser(String query) {
		lex = new QueryLexer(query);
		list = lex.getListOfConditionalExpressions();
	}

	/**
	 * Method must return true if query was of of the form jmbag="xxx"(i.e. it must
	 * have only one comparison, on attribute jmbag, and operator must be equals).
	 * 
	 * 
	 * @return true if query is direct, else false
	 */
	public boolean isDirectQuery() {
		return lex.isDirect();
	}
	
	/**
	 * Method returns value form direct query.
	 * @throws IllegalStateException if query wasn't direct one.
	 * @return String value from query
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery())
			throw new IllegalStateException();

		return list.get(0).getStringLiteral();
	}

	/**
	 * Method returns a {@link List} of {@link ConditionalExpression} form query.
	 * @return {@link List} of {@link ConditionalExpression}
	 */
	public List<ConditionalExpression> getQuery() {
		return list;
	}
}
