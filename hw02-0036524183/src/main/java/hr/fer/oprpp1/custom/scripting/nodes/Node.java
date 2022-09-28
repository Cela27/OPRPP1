package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/*
 * Base class for all graph nodes.
 */
public class Node {
	private ArrayIndexedCollection collection=null;
	
	/**
	 * Basic constructor
	 */
	public Node() {
		
	}
	
	/**
	 * Adds given child to an internally managed collection of children.
	 * @param child {@link Node}
	 */
	public void addChildNode(Node child) {		
		if(collection==null) {
			collection= new ArrayIndexedCollection();
		}
		collection.add(child);
	}
	
	/**
	 * Returns a number of (direct) children.
	 * @return
	 */
	public int numberOfChildren() {
		if(collection==null)
			return 0;
		return collection.size();
	}
	/**
	 * Gets child of node at index.
	 * @param index {@link Integer}
	 * @return {@link Node} at given index
	 * @throws IndexOutOfBoundsException
	 */
	public Node getChild(int index) {
		if(index<0 || index>=collection.size()) throw new IndexOutOfBoundsException();
		return (Node) collection.get(index);
	}

	@Override
	public String toString() {
		return "Node [collection=" + collection + "]";
	}

	
}
