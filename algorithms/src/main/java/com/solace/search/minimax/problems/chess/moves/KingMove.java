package com.solace.search.minimax.problems.chess.moves;

import com.solace.search.minimax.problems.chess.Board;
import com.solace.search.minimax.problems.chess.GamePiece;
import com.solace.search.minimax.problems.chess.Piece;
import com.solace.search.minimax.problems.chess.Placement;
import com.solace.search.minimax.problems.chess.Player;

public class KingMove extends Move {

	public KingMove(Piece piece, Placement from, Placement to) {
		super(piece, from, to);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doExecute(Board board) throws MoveException {

		board.place(piece, to.getBoardLocation());
	}

	/**
	 * Will address all surrounding permutations of the to location
	 */
	@Override
	public boolean isCheck(Board board) {
		int rank = to.getBoardLocation().getRank();
		int file = to.getBoardLocation().getFile();

		Player opp = piece.getPlayer().getOpponent();

		return isKing(board, rank - 1, file - 1, opp)
				|| isKing(board, rank - 1, file, opp)
				|| isKing(board, rank, file - 1, opp)
				|| isKing(board, rank + 1, file + 1, opp)
				|| isKing(board, rank + 1, file, opp)
				|| isKing(board, rank, file + 1, opp)
				|| isKing(board, rank - 1, file + 1, opp)
				|| isKing(board, rank + 1, file - 1, opp);
	}
}
