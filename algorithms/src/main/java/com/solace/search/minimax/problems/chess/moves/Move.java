package com.solace.search.minimax.problems.chess.moves;

import com.solace.search.minimax.problems.chess.Board;
import com.solace.search.minimax.problems.chess.Piece;
import com.solace.search.minimax.problems.chess.Placement;

public abstract class Move {
	
	Piece piece;
	Placement from, to;
	
	public Move(Piece piece, Placement from, Placement to) {
		this.piece = piece;
		this.from = from;
		this.to = to;
		
		if ( from == null )
			throw new RuntimeException("from is not valid.");
		
		if ( to == null )
			throw new RuntimeException("to is not valid");
	}
	
	public abstract void execute(Board board) throws MoveException;
	
	public abstract boolean isCheck();
}
