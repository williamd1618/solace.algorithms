package com.solace.search.minimax.problems.chess.moves;

import java.util.List;

import com.solace.search.minimax.problems.chess.Board;
import com.solace.search.minimax.problems.chess.BoardLocation;
import com.solace.search.minimax.problems.chess.Piece;

public interface IValidMoveEvaluator {
	
	List<BoardLocation> evaluate(Board board, Piece piece);
	
}
