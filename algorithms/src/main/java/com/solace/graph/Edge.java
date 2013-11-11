package com.solace.graph;


/**
 * An edge represents the relationship between two {@link Node}s.  There
 * is a value between these nodes which is to be resolved by the abstrat 
 * {@link #weight()} method
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 *
 * @param <T>
 * @param <NodeType>
 */
public abstract class Edge<T, NodeType extends Node<T>> {
	
	private NodeType from, to;
	
	private double weight;
	
	private Graph<T, NodeType, Edge<T,NodeType>> graph;

	public NodeType getFrom() {
		return from;
	}

	public void setFrom(NodeType from) {
		this.from = from;
	}

	public NodeType getTo() {
		return to;
	}

	public void setTo(NodeType to) {
		this.to = to;
	}
	
	
	/**
	 * a generic weight/heuristic function that is merely based upon
	 * {@link #from} and {@link #to}
	 * @return
	 */
	public abstract double weight();
	
	
	/**
	 * By providing a {@link Graph} as an input parameter we provide the function
	 * the ability create a global weight/ heuristic value
	 * @param graph
	 * @param args
	 * @return
	 */
	public abstract double weight(Graph<T, NodeType, Edge<T,NodeType>> graph, Object... args);
}
