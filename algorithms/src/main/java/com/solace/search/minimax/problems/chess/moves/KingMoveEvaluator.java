package com.solace.search.minimax.problems.chess.moves;

import java.util.ArrayList;
import java.util.List;

import com.solace.search.minimax.problems.chess.Board;
import com.solace.search.minimax.problems.chess.BoardLocation;
import com.solace.search.minimax.problems.chess.GamePiece;
import com.solace.search.minimax.problems.chess.Piece;
import com.solace.search.minimax.problems.chess.Player;

public class KingMoveEvaluator implements IValidMoveEvaluator {

	public KingMoveEvaluator() {
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public List<BoardLocation> evaluate(Board board, Piece gamePiece) {
		GamePiece piece = gamePiece.getPiece();
		BoardLocation current = gamePiece.getLocation();
		Player opponent = gamePiece.getPlayer().getOpponent();

		List<BoardLocation> potentials = new ArrayList<BoardLocation>();

		if (!board.isOccupied(BoardLocation.find(current.getRank(),
				current.getFile() + 1)))
			potentials.add(BoardLocation.find(current.getRank(),
					current.getFile() + 1));
		if (!board.isOccupied(BoardLocation.find(current.getRank(),
				current.getFile() - 1)))
			potentials.add(BoardLocation.find(current.getRank(),
					current.getFile() - 1));
		if (board.isOccupiedByOpponent(BoardLocation.find(
				current.getRank() + 1, current.getFile() - 1), opponent))
			potentials.add(BoardLocation.find(current.getRank() + 1,
					current.getFile() - 1));
		if (board.isOccupiedByOpponent(BoardLocation.find(
				current.getRank() + 1, current.getFile() + 1), opponent))
			potentials.add(BoardLocation.find(current.getRank() + 1,
					current.getFile() + 1));
		if (board.isOccupiedByOpponent(BoardLocation.find(
				current.getRank() - 1, current.getFile() - 1), opponent))
			potentials.add(BoardLocation.find(current.getRank() - 1,
					current.getFile() - 1));
		if (board.isOccupiedByOpponent(BoardLocation.find(
				current.getRank() - 1, current.getFile() + 1), opponent))
			potentials.add(BoardLocation.find(current.getRank() - 1,
					current.getFile() + 1));

		return potentials;
	}

}
