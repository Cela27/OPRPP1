package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/*
 * A node representing a command which generates some textual output dynamically. It inherits from Node class.
 */
public class EchoNode extends Node{
	private Element[] elements;
	
	/**
	 * Basic constructor with elemnts array as parameter.
	 * @param elements
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Getter for elements array.
	 * @return {@link Element} array
	 */
	public Element[] getElements() {
		return elements;
	}
	
	
}
