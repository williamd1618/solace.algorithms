package com.solace.search.minimax.problems.chess.moves;

import java.util.ArrayList;
import java.util.List;

import com.solace.search.minimax.problems.chess.Board;
import com.solace.search.minimax.problems.chess.BoardLocation;
import com.solace.search.minimax.problems.chess.GamePiece;
import com.solace.search.minimax.problems.chess.Piece;

/**
 * Will evaluate potential moves from a current location to be iterated over
 * while identifying adjacencies. Will not take into account pawn starting
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public class PawnMoveEvaluator implements IValidMoveEvaluator {

	public PawnMoveEvaluator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * A pawn can move forward and backward, but can only move at a 1 piece
	 * diagnol if the piece is occupied by an opponent
	 */
	@Override
	public List<BoardLocation> evaluate(Board board, Piece p) {

		GamePiece piece = p.getPiece();
		BoardLocation current = p.getLocation();

		List<BoardLocation> potentials = new ArrayList<BoardLocation>();

		if (!board.isOccupied(BoardLocation.find(current.getRank(),
				current.getFile() + 1)))
			potentials.add(BoardLocation.find(current.getRank(),
					current.getFile() + 1));
		else if (!board.isOccupied(BoardLocation.find(current.getRank(),
				current.getFile() - 1)))
			potentials.add(BoardLocation.find(current.getRank(),
					current.getFile() - 1));
		else if (board.isOccupiedByOpponent(BoardLocation.find(
				current.getRank() + 1, current.getFile() - 1), p.getPlayer()
				.getOpponent()))
			potentials.add(BoardLocation.find(current.getRank() + 1,
					current.getFile() - 1));
		else if (board.isOccupiedByOpponent(BoardLocation.find(
				current.getRank() + 1, current.getFile() + 1), p.getPlayer()
				.getOpponent()))
			potentials.add(BoardLocation.find(current.getRank() + 1,
					current.getFile() + 1));
		else if (board.isOccupiedByOpponent(BoardLocation.find(
				current.getRank() - 1, current.getFile() - 1), p.getPlayer()
				.getOpponent()))
			potentials.add(BoardLocation.find(current.getRank() - 1,
					current.getFile() - 1));
		else if (board.isOccupiedByOpponent(BoardLocation.find(
				current.getRank() - 1, current.getFile() + 1), p.getPlayer()
				.getOpponent()))
			potentials.add(BoardLocation.find(current.getRank() - 1,
					current.getFile() + 1));

		return potentials;
	}
}
