package com.solace.algorithms.search.minimax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.solace.algorithms.search.Search;

/**
 * Designed to return a single MiniMaxNode based upon depth to search for an
 * appropriate move. If multiple moves called, multiple instances of the
 * {@link Search} algorithm with have to be invoked.
 * 
 * Perhaps not optimal, but will suffice for this example.
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 * @param <T>
 * @param <NodeType>
 */
public class MiniMaxSearch<T, NodeType extends MiniMaxNode<T, NodeType>>
		extends Search<T, NodeType> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MiniMaxSearch.class);

	private NodeType startState;
	private int depth;
	private boolean isMax;

	public MiniMaxSearch(NodeType state, int depth, boolean max) {
		this.startState = state;
		this.depth = depth;
		this.isMax = max;
	}

	@Override
	public NodeType search() {
		return minimax(startState, depth, true);
	}

	private NodeType minimax(NodeType node, int depth, boolean maximizing) {
		LOGGER.info("{}Initiating minimax search as {} with a depth of {}{}",
				Strings.repeat("=", 10), maximizing ? "MAX" : "MIN", depth,
				Strings.repeat("=", 10));

		NodeType result = node;

		if (depth == 0 || node.containsWin())
			return node;

		if (maximizing) {
			int best = -Integer.MAX_VALUE;

			for (NodeType child : node.generateAdjacency().getNeighbors()) {
				NodeType val = minimax(child, depth - 1, false);

				result = child;

				best = (int) (best > val.calculateCost() ? best : val
						.calculateCost());

				if (best <= val.getAlpha()) {
					LOGGER.info("beta cutoff identified for \n{}", val
							.getValue().toString());
					break;
				}
			}

			node.setAlpha(best);
		} else {
			int best = Integer.MAX_VALUE;

			for (NodeType child : node.generateAdjacency().getNeighbors()) {
				NodeType val = minimax(child, depth - 1, true);

				result = child;

				best = (int) (best < val.calculateCost() ? best : val
						.calculateCost());

				if (best <= val.getAlpha()) {
					LOGGER.info("beta cutoff identified for {}", val);
					break;
				}
			}

			node.setAlpha(best);
		}

		LOGGER.info("A result was identified with a value of {}.",
				node.getAlpha());

		return result;
	}
}
