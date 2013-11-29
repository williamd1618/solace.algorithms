package com.solace.search.minimax.problems.chess;

import java.util.*;

public class PieceCounter {

	private Board board;

	private Map<Player, Map<GamePiece, Integer>> pieces = new HashMap<Player, Map<GamePiece, Integer>>();

	public PieceCounter(Board board) {
		this.board = board;
	}

}
