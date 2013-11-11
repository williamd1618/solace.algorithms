package com.solace.graph;

import java.util.HashSet;
import java.util.Set;


/**
 * Representation of an adjacency set in a graph to a particular node
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 *
 * @param <ValueType>
 * @param <NodeType>
 */
public class Adjacency<ValueType, NodeType extends Node<ValueType>> {
	
	protected NodeType root;
	
	protected Set<NodeType> neighbors;
	
	public Adjacency(NodeType root) {
		this(root, null);
	}
	
	public Adjacency(NodeType root, Set<NodeType> neighbors) {
		this.root = root;
		this.neighbors = neighbors;
	}
		
	public NodeType getRoot() {
		return root;
	}

	public void setRoot(NodeType root) {
		this.root = root;
	}
	
	/**
	 * Will guarantee not null
	 * @return
	 */
	public Set<NodeType> getNeighbors() {
		if ( neighbors == null )
			neighbors = new HashSet<NodeType>();
		
		return neighbors;
	}

	public void setNeighbors(Set<NodeType> neighbors) {
		this.neighbors = neighbors;
	}
}
