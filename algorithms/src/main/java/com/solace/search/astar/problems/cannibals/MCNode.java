package com.solace.search.astar.problems.cannibals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.solace.algorithms.search.astar.AStarNode;
import com.solace.graph.Adjacency;
import com.solace.graph.Node;

/**
 * A representation of a node in relation to the classic Missionary and
 * Cannibals problem. Assumes 3 x 3.
 * 
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public class MCNode extends AStarNode<State, MCNode> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MCNode.class);

	public MCNode() {
		this(null);
	}

	public MCNode(State t) {
		this(null, t);
		// TODO Auto-generated constructor stub
	}

	public MCNode(Node<State> parent, State t) {
		this(parent, t, 0);
		// TODO Auto-generated constructor stub
	}

	public MCNode(Node<State> parent, State t, int depth) {
		super(parent, t, depth);
	}

	
	/**
	 * Will generate the adjacent/neighbor nodes to this based upon rules expressed
	 * in the problem.
	 */
	@Override
	public Adjacency<State, MCNode> generateAdjacency() {
		LOGGER.info("Determining states to move from ({})", getValue()
				.toString());

		BoatOrientation current = getValue().getBoatOrientation();

		BoatOrientation target = (current.equals(BoatOrientation.LeftBank) ? BoatOrientation.RightBank
				: BoatOrientation.LeftBank);

		Adjacency<State, MCNode> adjacency = new Adjacency<State, MCNode>(this);

		Set<MCNode> adjacencies = new HashSet<MCNode>();

		// a simple way to generate the varying steps
		// using the reflexive nature of the problem
		int[][] diffs = { { 2, 0, 2, 0 }, { 0, 2, 0, 2 }, { 1, 1, 1, 1 },
				{ 0, 1, 0, 1 }, { 1, 0, 1, 0 } };

		for (int[] vals : diffs) {
			if (current.equals(BoatOrientation.LeftBank)) {
				vals[0] *= -1;
				vals[1] *= -1;
			} else {
				vals[2] *= -1;
				vals[3] *= -1;
			}
		}

		for (int[] vals : diffs) {
			State potentialState = new State(target, getValue()
					.getLeftMissCount() + vals[0], getValue()
					.getLeftCannCount() + vals[1], getValue()
					.getRightMissCount() + vals[2], getValue()
					.getRightCannCount() + vals[3]);

			if (isValidState(getValue(), potentialState))
				adjacencies
						.add(new MCNode(this, potentialState, getDepth() + 1));
		}

		adjacency.setNeighbors(adjacencies);

		return adjacency;
	}

	/**
	 * Will return true if:
	 * <ul>
	 * <li>nodes are equal. this would imply that the node state looped on
	 * itself</li>
	 * <li>the counts of the missionaries are >= the count of connibals on both
	 * sides of the bank</li>
	 * </ul>
	 * Otherwise, we return false
	 * 
	 * @param now
	 * @param then
	 * @return
	 */
	private static boolean isValidState(State now, State then) {

		boolean result = true;

		if (now.equals(then))
			return false;

		if (now.getBoatOrientation().equals(then.getBoatOrientation()))
			return false;

		if (then.getLeftCannCount() >= 0 && then.getLeftMissCount() >= 0
				&& then.getRightCannCount() >= 0
				&& then.getRightMissCount() >= 0)
			if (((then.getLeftMissCount() >= then.getLeftCannCount()) || then
					.getLeftMissCount() == 0)
					&& (then.getRightMissCount() == 0 || (then
							.getRightMissCount() >= then.getRightCannCount())))
				result = true;
			else
				result = false;
		else
			result = false;

		if (result)
			LOGGER.info("valid state ==> ({})", then.toString());
		else
			LOGGER.debug("invalid state ==> ({})", then.toString());

		return result;
	}

	/**
	 * Our goal is to minimize the number of people on the left hand side of the
	 * bank, cannibals or missionaries.
	 */
	@Override
	public double calculateCost() {
		return calculateG() + calculateH();
	}

	/**
	 * The raw heeuristic value
	 * 
	 * @return
	 */
	public double calculateH() {
		return getValue().getLeftCannCount() + getValue().getLeftMissCount();
	}

	@Override
	public double calculateG() {
		return getDepth();
	}
}
