package com.solace.search.minimax.problems.chess.moves;

import com.solace.search.minimax.problems.chess.Board;
import com.solace.search.minimax.problems.chess.GamePiece;
import com.solace.search.minimax.problems.chess.Piece;
import com.solace.search.minimax.problems.chess.Placement;
import com.solace.search.minimax.problems.chess.Player;

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

	/**
	 * Will evaluate moves forward, attacks, and Queening
	 * <p>
	 * Note: may need to take into account index boundaries of 0 and 7 for edges
	 * on potential attacks
	 */
	@Override
	public boolean doExecute(Board board) throws MoveException {
		
		boolean isMate = false;

		if (from.getBoardLocation().getFile() == to.getBoardLocation()
				.getFile()) {
			if (Math.abs(from.getBoardLocation().getRank()
					- to.getBoardLocation().getRank()) == 1
					&& !board.isOccupied(to.getBoardLocation())) {
				isMate = board.place(piece, to.getBoardLocation());
			}
		} else if (Math.abs(from.getBoardLocation().getRank()
				- to.getBoardLocation().getRank()) == 1
				&& Math.abs(from.getBoardLocation().getFile()
						- to.getBoardLocation().getFile()) == 1
				&& board.isOccupied(to.getBoardLocation())) {

			isMate = board.place(piece, to.getBoardLocation());

			// if this player has make it to the opponents first rank
			// then the pawn can be promoted to a queen.
			Player opponent = piece.getPlayer().getOpponent();

			if (opponent.getFirstRankIndex() == to.getBoardLocation().getRank())
				isMate = board.place(new Piece(GamePiece.Queen, piece.getPlayer()),
						to.getBoardLocation());
		}
		
		return isMate;
	}

	@Override
	public boolean isCheck(Board board) {
		boolean isCheck = false;

		int rank = to.getBoardLocation().getRank();
		int file = to.getBoardLocation().getFile();

		// if the player is black
		if (piece.getPlayer() == Player.Black) {
			isCheck = isKing(board, rank - 1, file - 1, piece.getPlayer()
					.getOpponent())
					|| isKing(board, rank + 1, file - 1, piece.getPlayer()
							.getOpponent());
		} else if (piece.getPlayer() == Player.White) {
			isCheck = isKing(board, rank - 1, file - 1, piece.getPlayer()
					.getOpponent())
					|| isKing(board, rank + 1, file - 1, piece.getPlayer()
							.getOpponent());
		} 

		return isCheck;
	}
}
