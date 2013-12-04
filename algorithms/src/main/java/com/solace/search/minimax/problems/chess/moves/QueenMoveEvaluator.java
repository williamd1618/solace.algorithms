package com.solace.search.minimax.problems.chess.moves;

import java.util.ArrayList;
import java.util.List;

import com.solace.graph.AdjacencyGenerator;
import com.solace.search.minimax.problems.chess.Board;
import com.solace.search.minimax.problems.chess.BoardLocation;
import com.solace.search.minimax.problems.chess.ChessConstants;
import com.solace.search.minimax.problems.chess.GamePiece;
import com.solace.search.minimax.problems.chess.Piece;
import com.solace.search.minimax.problems.chess.Player;

public class QueenMoveEvaluator implements IValidMoveEvaluator {

	public QueenMoveEvaluator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @todo: update algorithm for validating the {@link AdjacencyGenerator} set
	 */
	@Override
	public List<BoardLocation> evaluate(Board board, Piece piece) {

		List<BoardLocation> potentials = new ArrayList<BoardLocation>();

		List<BoardLocation> win = new ArrayList<BoardLocation>();

		Player opponent = piece.getPlayer().getOpponent();
		BoardLocation current = piece.getLocation();

		boolean isCheck = false;

		BoardLocation king;

		if (opponent == Player.Black)
			king = board.getBlackKingPlacement().getBoardLocation();
		else
			king = board.getWhiteKingPlacement().getBoardLocation();

		win.add(king);

		// is the king in the rank of file for where we are
		if (king.getRank() == current.getRank()) {
			if (king.getFile() > current.getFile()) {
				for (int i = current.getFile(); i <= king.getFile(); i++) {
					boolean isKing = Move.isKing(board, king.getRank(), i,
							piece.getPlayer().getOpponent());

					if (!isKing
							&& board.getPieces()[king.getRank()][i].getPiece() != GamePiece.Empty)
						break;

					isCheck = isKing;
				}

				if (isCheck)
					return win;

				for (int i = king.getFile(); i >= current.getFile(); i--) {
					boolean isKing = Move.isKing(board, i, king.getRank(),
							piece.getPlayer().getOpponent());

					if (!isKing
							&& board.getPieces()[king.getRank()][i].getPiece() != GamePiece.Empty)
						break;

					isCheck = isKing;
				}

				if (isCheck)
					return win;
			}
		}

		// is the king in the line of file for where we are
		if (king.getFile() == current.getFile()) {
			if (king.getRank() > current.getRank()) {
				for (int i = current.getRank(); i <= king.getRank(); i++) {
					boolean isKing = Move.isKing(board, i, king.getFile(),
							piece.getPlayer().getOpponent());

					if (!isKing
							&& board.getPieces()[i][king.getFile()].getPiece() != GamePiece.Empty)
						break;

					isCheck = isKing;
				}

				if (isCheck)
					return win;

				for (int i = king.getRank(); i >= current.getRank(); i--) {
					boolean isKing = Move.isKing(board, i, king.getFile(),
							piece.getPlayer().getOpponent());

					if (!isKing
							&& board.getPieces()[i][king.getFile()].getPiece() != GamePiece.Empty)
						break;

					isCheck = isKing;
				}

				if (isCheck)
					return win;
			}
		}

		if (isCheck)
			return win;

		for (int i = current.getFile(), j = current.getRank(); i >= 0; i--, j++) {
			boolean isKing = Move.isKing(board, j, i, piece.getPlayer()
					.getOpponent());

			if (!isKing
					&& board.getPieces()[j][i].getPiece() != GamePiece.Empty)
				break;

			isCheck = isKing;
		}

		if (isCheck)
			return win;

		for (int i = current.getRank(), j = current.getFile(); i < ChessConstants.FILE_COUNT; i++, j++) {
			boolean isKing = Move.isKing(board, i, j, piece.getPlayer()
					.getOpponent());

			if (!isKing
					&& board.getPieces()[i][j].getPiece() != GamePiece.Empty)
				break;

			isCheck = isKing;
		}

		if (isCheck)
			return win;

		for (int i = current.getRank(), j = current.getFile(); i >= 0; i--, j--) {
			boolean isKing = Move.isKing(board, i, j, piece.getPlayer()
					.getOpponent());

			if (!isKing
					&& board.getPieces()[i][j].getPiece() != GamePiece.Empty)
				break;

			isCheck = isKing;
		}

		if (isCheck)
			return win;

		for (int i = current.getRank(), j = current.getFile(); i <= ChessConstants.FILE_COUNT; i--, j++) {
			boolean isKing = Move.isKing(board, i, j, piece.getPlayer()
					.getOpponent());

			if (!isKing
					&& board.getPieces()[i][j].getPiece() != GamePiece.Empty)
				break;

			isCheck = isKing;
		}

		if (isCheck)
			return win;
		else
			return potentials;

	}
}
