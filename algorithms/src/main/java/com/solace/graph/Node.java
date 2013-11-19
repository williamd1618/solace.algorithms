package com.solace.graph;

/**
 * A node in a {@link Graph} can represent many things:
 * <p>
 * <ul>
 * <li>state</li>
 * <li>functionality to be invoked</li>
 * </ul>
 * In this case, we'll assume simply state as represented by T
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 * @param <ValueType>
 */
public abstract class Node<ValueType> {

	protected ValueType value;

	protected int depth;

	protected Node<ValueType> parent;

	public Node(ValueType t) {
		this(null, t, 0);
	}

	public Node(Node<ValueType> parent, ValueType t) {
		this(parent, t, 0);
	}

	public Node(Node<ValueType> parent, ValueType t, int depth) {
		this.parent = parent;
		this.value = t;
		this.depth = depth;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public abstract double calculateCost();

	public Node<ValueType> getParent() {
		return parent;
	}

	public void setParent(Node<ValueType> parent) {
		this.parent = parent;
	}

	public ValueType getValue() {
		return value;
	}

	public void setValue(ValueType value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node<?>) {
			Node<ValueType> tmp = (Node<ValueType>) obj;
			return this.getValue().equals(tmp.getValue());
		} else {
			return false;
		}
	}
 
}
