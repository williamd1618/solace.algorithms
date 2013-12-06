package com.solace.search.minimax.problems.chess;

import com.solace.algorithms.search.minimax.MiniMaxSearch;

public class ChessMiniMaxSearch extends MiniMaxSearch<State, ChessNode> {

	public ChessMiniMaxSearch(ChessNode state, int depth, boolean max) {
		super(state, depth, max);
	}
	
}
