package com.solace.algorithms.search.astar;

import com.solace.graph.Adjacency;
import com.solace.graph.AdjacencyGenerator;
import com.solace.graph.Node;
import com.solace.search.astar.problems.cannibals.MCNode;
import com.solace.search.astar.problems.cannibals.State;

public abstract class AStarNode<ValueType, NodeType extends Node<ValueType>>
		extends Node<ValueType> implements
		AdjacencyGenerator<ValueType, NodeType>,
		Comparable<AStarNode<ValueType, NodeType>> {

	public AStarNode(ValueType t) {
		this(null, t);
		// TODO Auto-generated constructor stub
	}

	public AStarNode(Node<ValueType> parent, ValueType t) {
		this(parent, t, 0);
		// TODO Auto-generated constructor stub
	}

	public AStarNode(Node<ValueType> parent, ValueType t, int depth) {
		super(parent, t, depth);
		// TODO Auto-generated constructor stub
	}

	public abstract double calculateH();

	public abstract double calculateG();

	public double calculateCost() {
		return calculateG() + calculateH();
	}
	
	/**
	 * An attempt at a comparator for ordering nodes.
	 * <p>
	 * initial consideration was taken to add the f(x) value to the sort but
	 * then realizing that {@link State} could provide a value that is
	 * commutatively equal to another value higher or lower in the graph Thought
	 * being:
	 * <ul>
	 * <li>if {@link #getDepth()} of this is less than compared to node, it is
	 * higher in the graph, therefore has a further route to the goal state.
	 * <li>if {@link #getDepth()} of this is greater than the compared to node,
	 * it is lower in the graph (more traversals)
	 * <li>if the f(x) values are not equal, and this has a lower f(x) then it
	 * should be sorted as a lower node.
	 * <li>converse to as above
	 */
	public int compareTo(AStarNode<ValueType, NodeType> o) {

		if (calculateH() == o.calculateH()) {

			if (calculateG() < o.calculateG())
				return 1;

			if (calculateG() > o.calculateG())
				return -1;

			return 0;
		} else {
			return (calculateH() < o.calculateH()) ? -1 : 1;
		}
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof AStarNode<?, ?>))
			return false;

		AStarNode tmp = (AStarNode) obj;

		return super.equals(obj) && calculateH() == tmp.calculateH(); 
	}
}
