package com.solace.algorithms.search.astar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solace.algorithms.search.Search;
import com.solace.graph.Node;


/**
 * A genericized implementation of the A* search algorithm.  
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 *
 * @param <T>
 * @param <NodeType>
 */
public class AStarSearch<T, NodeType extends AStarNode<T, NodeType>> extends Search<T, NodeType> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AStarSearch.class);

	private NodeType goal;

	private PriorityQueue<NodeType> open = new PriorityQueue<NodeType>();

	private Set<NodeType> closed = new HashSet<NodeType>();

	private List<String> traversal = new ArrayList<String>();

	public AStarSearch(NodeType initial, NodeType goal) {
		open = new PriorityQueue<NodeType>();

		open.add(initial);

		this.goal = goal;
	}

	/**
	 * A* search implementation based upon initial, goal state,
	 * {@link Node#generateAdjacency()}, and {@link Node#calculateCost()}
	 * 
	 * @return
	 */
	@Override
	public NodeType search() {

		NodeType current = null;

		NodeType result = null;

		while (!open.isEmpty() && result == null) {

			// we simply poll b/c we are leveraging a PrioritQueue
			// that weights based upon g and f
			current = open.poll();

			LOGGER.info("Current state: {}", current.getValue().toString());

			LOGGER.info("Evaluating state: {} with h(x): {}", current
					.getValue().toString(), current.calculateCost());

			for (NodeType neighbor : current.generateAdjacency().getNeighbors()) {

				if (goal.getValue().equals(neighbor.getValue())) {
					result = neighbor;
					continue;
				}

				if (!open.contains(neighbor) && !closed.contains(neighbor)) {
					LOGGER.debug(
							"{} does not exist in open or closed and has an f(x) of {}",
							neighbor.getValue(), neighbor.calculateCost());
					open.add(neighbor);
				}
			}

			closed.add(current);
		}

		return result;
	}

	
	/**
	 * Assumes the algorithm has tracked the Node's parents
	 * @param node
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Stack<NodeType> buildPathToGoal(NodeType node) {
		if ( node == null)
			return null;
		
		Stack<NodeType> steps = new Stack<NodeType>();

		NodeType current = node;;
		do {
			if ( current != null )
				steps.add(current);
			current = (NodeType) current.getParent();
			
		} while(current != null);
		
		return steps;
	}
}
