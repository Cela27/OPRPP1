package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {
	
	@Test
	public void testOperatorLessTrue() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testOperatorLessFalse() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertFalse(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testOperatorLessOrEqualsTrue() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testOperatorLessOrEqualsFalse() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertFalse(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testOperatorGreaterTrue() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testOperatorGreaterFalse() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testOperatorGreaterOrEqualsTrue() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testOperatorGreaterOrEqualsFalse() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertFalse(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testOperatorEqualsTrue() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertTrue(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testOperatorEqualsFalse() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertFalse(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testOperatorNotEqualsTrue() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testOperatorNotEqualsFalse() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertFalse(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testOperatorLikeTrueWildcardMid() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Zagreb", "Z*eb"));
	}
	
	@Test
	public void testOperatorLikeTrueWildcardFirst() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Zagreb", "*reb"));
	}
	
	@Test
	public void testOperatorLikeTrueWildcardLast() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Zagreb", "Zag*"));
	}
	
	@Test
	public void testOperatorLikeFalse() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Ana", "Jas*"));
	}
	
	@Test
	public void testOperatorLikeFalseBecuseOfSize() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("AAA", "AA*AA"));
	}
	
	@Test
	public void testOperatorLikeIllegalArgumentException() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertThrows(IllegalArgumentException.class, ()->oper.satisfied("AAA", "A**A"));
	}
}
