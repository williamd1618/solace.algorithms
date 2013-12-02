package com.solace.search.minimax.problems.chess.moves;

import java.util.List;

import com.solace.search.minimax.problems.chess.Board;
import com.solace.search.minimax.problems.chess.BoardLocation;
import com.solace.search.minimax.problems.chess.GamePiece;
import com.solace.search.minimax.problems.chess.Piece;
import com.solace.search.minimax.problems.chess.Placement;
import com.solace.search.minimax.problems.chess.Player;

public abstract class Move {

	Piece piece;
	Placement from, to;
	
	boolean isCheckmate = false;

	public Move(Piece piece, Placement from, Placement to) {
		this.piece = piece;
		this.from = from;
		this.to = to;

		if (from == null)
			throw new RuntimeException("from is not valid.");

		if (to == null)
			throw new RuntimeException("to is not valid");
	}

	public abstract boolean doExecute(Board board) throws MoveException;

	public boolean isCheckmate() {
		return isCheckmate;
	}

	public void execute(Board board) throws MoveException {
		isMoveInRange();

		isCheckmate = doExecute(board);
	}

	private void isMoveInRange() throws MoveException {
		if (!(to.getBoardLocation().getFile() >= 0
				&& to.getBoardLocation().getFile() < 8
				&& to.getBoardLocation().getRank() >= 0 && to
				.getBoardLocation().getRank() < 8))
			throw new MoveException("To placement value is not within range");
	}

	public abstract boolean isCheck(Board board);

	/**
	 * Will check to see if a given position holds a {@link GamePiece#King}.
	 * This will be used to evaluate if after a move to made, if a checkmate is
	 * viable given the capturing
	 * 
	 * @param board
	 * @param file
	 * @param rank
	 * @param p
	 * @return
	 */
	public static boolean isKing(Board board, int rank, int file, Player p) {
		Piece piece = board.getPieces()[rank][file];

		return piece.getPiece() == GamePiece.King && piece.getPlayer() == p;
	}
	
	public static List<BoardLocation> findValidMoves(Board board, Piece piece) {
		return null;
	}
}
