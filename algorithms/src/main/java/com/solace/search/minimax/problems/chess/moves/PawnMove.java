package com.solace.search.minimax.problems.chess.moves;

import com.solace.search.minimax.problems.chess.Board;
import com.solace.search.minimax.problems.chess.Piece;
import com.solace.search.minimax.problems.chess.Placement;

/**
 * This move implementation is based upon the problem set given as extra credit
 * in lecture 5 where the original move of two spaces does not exist as the game
 * has begun and no pawns are in their originating position
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public class PawnMove extends Move {

	public PawnMove(Piece piece, Placement from, Placement to) {
		super(piece, from, to);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Board board) throws MoveException {
		
		if ( from.getBoardLocation().getX() == to.getBoardLocation().getX() ) {
			if ( Math.abs(from.getBoardLocation().getX() - to.getBoardLocation().getX()) == 1 && !board.isOccupied(to.getBoardLocation())) {
				board.place(piece, to.getBoardLocation());
			}
		} else if ( Math.abs(from.getBoardLocation().getX() - to.getBoardLocation().getX()) == 1 &&
				Math.abs(from.getBoardLocation().getY() - to.getBoardLocation().getY()) == 1 &&
				board.isOccupied(to.getBoardLocation())) {
			
			board.place(piece, to.getBoardLocation());
		}
	}

	@Override
	public boolean isCheck() {
		// TODO Auto-generated method stub
		return false;
	}
}
