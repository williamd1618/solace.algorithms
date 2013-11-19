package com.solace.algorithms.search;


import com.solace.graph.Node;

public abstract class Search<T, NodeType extends Node<T>> {

	public Search() {
		super();
	}

	/**
	 * A* search implementation based upon initial, goal state,
	 * {@link Node#generateAdjacency()}, and {@link Node#calculateCost()}
	 * 
	 * @return
	 */
	public abstract NodeType search();
}