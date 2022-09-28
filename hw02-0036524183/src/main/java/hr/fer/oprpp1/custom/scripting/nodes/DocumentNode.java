package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

/*
 * A node representing an entire document. It inherits from Node class.
 */
public class DocumentNode extends Node{
	/**
	 * Returns string representation of {@link ForLoopNode}
	 * @param node {@link ForLoopNode}
	 * @return String {@link ForLoopNode} as string
	 */
	private String forNode(ForLoopNode node) {
		StringBuilder sb = new StringBuilder();
		sb.append("{$FOR ");

		ElementVariable variable = node.getVariable();
		sb.append(variable.asText()).append(" ");

		Element elm = node.getStartExpresion();

		sb.append(fLoopExpresion(elm)).append(" ");

		elm = node.getEndExpresion();
		sb.append(fLoopExpresion(elm)).append(" ");

		if (node.getStepExpresion() != null) {
			elm = node.getStepExpresion();
			sb.append(fLoopExpresion(elm)).append(" ");
		}
		sb.append("$}");

		if (node.numberOfChildren() != 0) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				Node tmpNode = node.getChild(i);
				if (tmpNode.getClass() == TextNode.class) {
					TextNode tNode = (TextNode) tmpNode;
					sb.append(textNode(tNode));
				} else if (tmpNode.getClass() == ForLoopNode.class) {
					ForLoopNode fNode = (ForLoopNode) tmpNode;
					sb.append(forNode(fNode));
				} else if (tmpNode.getClass() == EchoNode.class) {
					EchoNode eNode = (EchoNode) tmpNode;
					sb.append(echoNode(eNode));
				}
			}
		}
		sb.append("{$END$}");
		return sb.toString();
	}
	/**
	 * Returns string representation of {@link EchoNode}
	 * @param node {@link EchoNode}
	 * @return String {@link EchoNode} as string
	 */
	private String echoNode(EchoNode node) {
		StringBuilder sb = new StringBuilder();
		sb.append("{$=");
		Element[] elms = node.getElements();
		for (int i = 0; i < elms.length; i++) {
			Element elm = elms[i];
			if (elm.getClass() == ElementString.class) {
				sb.append('"').append(elm.asText()).append('"');
			} else if (elm.getClass() == ElementVariable.class) {
				sb.append(elm.asText());
			} else if (elm.getClass() == ElementOperator.class) {
				sb.append(elm.asText());
			} else if (elm.getClass() == ElementConstantDouble.class) {
				sb.append(elm.asText());
			} else if (elm.getClass() == ElementConstantInteger.class) {
				sb.append(elm.asText());
			}
			sb.append(" ");
		}
		sb.append("$}");
		return sb.toString();
	}

	/**
	 * Returns string representation of for loop expresion.
	 * @param elm {@link Element}
	 * @return {@link String} for loop expresion
	 */
	private String fLoopExpresion(Element elm) {
		if (elm.getClass() == ElementString.class) {
			ElementString str = (ElementString) elm;
			return (String.valueOf('"') + str.asText() + '"');

		} else if (elm.getClass() == ElementVariable.class) {
			ElementVariable variable = (ElementVariable) elm;
			return variable.asText();
		} else if (elm.getClass() == ElementConstantInteger.class) {
			ElementConstantInteger num = (ElementConstantInteger) elm;
			return num.asText();
		} else if (elm.getClass() == ElementConstantDouble.class) {
			ElementConstantDouble num = (ElementConstantDouble) elm;
			return num.asText();
		} else {
			throw new SmartScriptParserException();
		}
	}
	/**
	 * Returns string representation of {@link TextNode}
	 * @param node {@link TextNode}
	 * @return String {@link TextNode} as string
	 */
	private String textNode(TextNode node) {
		return node.getTextForParsing() + " ";
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < this.numberOfChildren(); i++) {
			Node tmpNode = this.getChild(i);
			if (tmpNode.getClass() == TextNode.class) {
				TextNode tNode = (TextNode) tmpNode;
				sb.append(textNode(tNode));

			} else if (tmpNode.getClass() == ForLoopNode.class) {
				ForLoopNode fNode = (ForLoopNode) tmpNode;
				sb.append(forNode(fNode));
			}
		}
		return sb.toString();

	}

	@Override
	public boolean equals(Object obj) {
		DocumentNode other=(DocumentNode) obj;
		if(this.numberOfChildren()!=other.numberOfChildren()) return false;
		
		return checkTheChildren(this, other);
	}
	
	/**
	 * Checks if nodes children have children when doing comparison.
	 * @param node1 First node
	 * @param node2 Second node
	 * @return true if nodes are equal, else false
	 */
	private boolean checkTheChildren(Node node1, Node node2) {
		for(int i=0; i<node1.numberOfChildren();i++) {
			Node child1=node1.getChild(i);
			Node child2=node2.getChild(i);
			if(node1.numberOfChildren()!=node2.numberOfChildren()) return false;
			
			if(node1.numberOfChildren()==0)
				continue;
			else {
				if(!checkTheChildren(child1, child2)) return false;	
			}
				
		}
		return true;
	}
}
