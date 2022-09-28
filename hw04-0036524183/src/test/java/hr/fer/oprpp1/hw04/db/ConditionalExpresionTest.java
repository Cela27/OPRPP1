package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConditionalExpresionTest {

	@Test
	public void testConditionalExpresionTrue() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Cel*",
				ComparisonOperators.LIKE);
		
		StudentRecord sr= new StudentRecord("0036524183", "Celinscak", "Antonio", "5");
		boolean recordSatisfies = expr.getComparisonOperator()
				.satisfied(expr.getFieldGetter().get(sr), expr.getStringLiteral());
		assertTrue(recordSatisfies);
	}
	
	@Test
	public void testConditionalExpresionFalse() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Cei*",
				ComparisonOperators.LIKE);
		
		StudentRecord sr= new StudentRecord("0036524183", "Celinscak", "Antonio", "5");
		boolean recordSatisfies = expr.getComparisonOperator()
				.satisfied(expr.getFieldGetter().get(sr), expr.getStringLiteral());
		assertFalse(recordSatisfies);
	}
}
