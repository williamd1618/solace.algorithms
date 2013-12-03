package com.solace.search.minimax.problems.chess.moves;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solace.search.minimax.problems.chess.Board;
import com.solace.search.minimax.problems.chess.GamePiece;
import com.solace.search.minimax.problems.chess.Piece;
import com.solace.search.minimax.problems.chess.Placement;
import com.solace.search.minimax.problems.chess.Player;

public class KingMove extends Move {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KingMove.class);

	public KingMove(Piece piece, Placement from, Placement to) {
		super(piece, from, to);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Will update the location of the opposing King
	 */
	@Override
	public boolean doExecute(Board board) throws MoveException {
		
		boolean isMate = false;

		isMate = board.place(piece, to.getBoardLocation());

		if (piece.getPlayer() == Player.White)
			board.setWhiteKingPlacement(new Placement(Player.White,
					GamePiece.King, to.getBoardLocation()));
		else
			board.setBlackKingPlacement(new Placement(Player.Black,
					GamePiece.King, to.getBoardLocation()));
		
		return isMate;
	}

	@Override
	public Logger getLogger() {
		return LOGGER;
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
