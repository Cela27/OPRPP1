package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/*
 * A node representing a single for-loop construct. It inherits from Node class.
 */
public class ForLoopNode extends Node{
	private ElementVariable variable;
	private Element startExpresion;
	private Element endExpresion;
	private Element stepExpresion;
	
	/**
	 * Basic constructor with variable, start expresion, end expression and step expresion as parameters.
	 * @param variable {@link ElementVariable}
	 * @param startExpresion {@link Element}
	 * @param endExpresion {@link Element}
	 * @param stepExpresion {@link Element}
	 */
	public ForLoopNode(ElementVariable variable, Element startExpresion, Element endExpresion, Element stepExpresion) {
		if(variable==null || startExpresion==null || endExpresion==null) throw new NullPointerException();
		this.variable = variable;
		this.startExpresion = startExpresion;
		this.endExpresion = endExpresion;
		this.stepExpresion = stepExpresion;
	}
	/**
	 * Getter for start expression.
	 * @return Element start expression
	 */
	public Element getStartExpresion() {
		return startExpresion;
	}
	/**
	 * Getter for end expression.
	 * @return Element end expression
	 */
	public Element getEndExpresion() {
		return endExpresion;
	}
	/**
	 * Getter for step expression.
	 * @return Element step expression
	 */
	public Element getStepExpresion() {
		return stepExpresion;
	}
	
	/**
	 * Getter for variable.
	 * @return {@link ElementVariable} variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	
	
}
