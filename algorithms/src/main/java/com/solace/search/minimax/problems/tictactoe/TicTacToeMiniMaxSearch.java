package com.solace.search.minimax.problems.tictactoe;

import com.solace.algorithms.search.minimax.MiniMaxSearch;

/**
 * An implementation of the TicTacToe {@link MiniMaxSearch}. Largely based upon
 * the rules put in place in {@link TicTacToeNode}.
 * <p>
 * See
 * <ul>
 * <li>{@link TicTacToeNode#calculateCost()} for heuristic function
 * <li>{@link TicTacToeNode#generateAdjacency()} for how next possible moves are
 * computed
 * </ul>
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public class TicTacToeMiniMaxSearch extends MiniMaxSearch<State, TicTacToeNode> {

	public TicTacToeMiniMaxSearch(TicTacToeNode state, int depth, boolean max) {
		super(state, depth, max);
		// TODO Auto-generated constructor stub
	}

}
