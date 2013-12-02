package com.solace.search.minimax.problems.chess.moves;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solace.search.minimax.problems.chess.Board;
import com.solace.search.minimax.problems.chess.BoardLocation;
import com.solace.search.minimax.problems.chess.ChessConstants;
import com.solace.search.minimax.problems.chess.GamePiece;
import com.solace.search.minimax.problems.chess.Piece;
import com.solace.search.minimax.problems.chess.Placement;
import com.solace.search.minimax.problems.chess.Player;

/**
 * Will be used if a {@link GamePiece#Pawn} is ever promoted to a
 * {@link GamePiece#Queen}
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public class QueenMove extends Move {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueenMove.class);

	public QueenMove(Piece piece, Placement from, Placement to) {
		super(piece, from, to);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean doExecute(Board board) throws MoveException {
		
		GamePiece target = GamePiece.Empty;
		
		if ((target = validateAvailable(board, from.getBoardLocation(),
				to.getBoardLocation())) != GamePiece.King)
			LOGGER.info("Checkmate");

		return board.place(piece, to.getBoardLocation());
	}

	/**
	 * Will validate that the board locations are available and that all are
	 * empty. if the last holds an opposing player
	 * 
	 * @param from
	 * @param to
	 * @return a not-null {@link GamePiece}. if captured the piece, if otherwise
	 *         {@link GamePiece#Empty}
	 * @throws MoveException
	 */
	private GamePiece validateAvailable(Board board, BoardLocation from,
			BoardLocation to) throws MoveException {
		GamePiece target = GamePiece.Empty;

		Piece[] values;

		// if ranks are similar we still want to populate
		// our arrays in a fashion that will allow us to iterate and validate
		// consistently
		if (from.getRank() == to.getRank()) {
			values = new Piece[Math.abs(from.getFile() - to.getFile())];

			if (from.getFile() < to.getFile())
				System.arraycopy(board.getPieces()[from.getRank()],
						from.getFile(), values, 0,
						Math.abs(from.getFile() - to.getFile()));
			else
				for (int i = to.getFile(), j = 0; i > from.getFile(); i--, j++)
					values[j] = board.getPieces()[from.getRank()][i];

			for (int i = 0; i < values.length - 1; i++) {
				if (values[i].getPiece() != GamePiece.Empty)
					throw new MoveException(
							String.format(
									"GamePiece at [%s] is not empty and is not the target position.",
									BoardLocation.find(
											from.getRank(),
											from.getFile() > to.getFile() ? to
													.getFile() + i : from
													.getFile() + i)));
			}

			// will have to be handled programmatically b/c of the
			// need to access the values
		} else if (from.getFile() == to.getFile()) {
			values = new Piece[Math.abs(from.getRank() - to.getRank())];

			if (from.getRank() < to.getRank())
				for (int i = from.getRank(); i <= to.getRank(); i++)
					values[i] = board.getPieces()[i][from.getFile()];
			else
				for (int i = to.getRank(); i <= from.getRank(); i--)
					values[i] = board.getPieces()[i][from.getFile()];
			// diagnols are going to get nice and complicated
		} else {
			values = new Piece[Math.abs(from.getRank() - to.getRank())];

			if (to.getFile() > from.getFile()) {
				if (to.getRank() > from.getRank()) {
					for (int i = from.getRank(), j = from.getFile(); i <= to
							.getRank() && j <= to.getFile(); i++, j++)
						values[i] = board.getPieces()[i][j];
				} else {
					for (int i = from.getRank(), j = from.getFile(); i <= to
							.getRank() && j >= to.getFile(); i++, j--)
						values[i] = board.getPieces()[i][j];
				}
			} else {
				if (to.getRank() > from.getRank()) {
					for (int i = from.getRank(), j = from.getFile(); i >= to
							.getRank() && j >= to.getFile(); i--, j--)
						values[i] = board.getPieces()[i][j];
				} else {
					for (int i = from.getRank(), j = from.getFile(); i >= to
							.getRank() && j <= to.getFile(); i--, j++)
						values[i] = board.getPieces()[i][j];
				}
			}
		}

		return values[values.length].getPiece();
	}

	/**
	 * Iterates and checks horizontals, verticals, and diagnols
	 */
	@Override
	public boolean isCheck(Board board) {

		boolean isCheck = false;

		Player opponent = piece.getPlayer().getOpponent();

		BoardLocation king;

		if (opponent == Player.Black)
			king = board.getBlackKingPlacement().getBoardLocation();
		else
			king = board.getWhiteKingPlacement().getBoardLocation();

		// is the king in the rank of file for where we are
		if (king.getRank() == to.getBoardLocation().getRank()) {
			if (king.getFile() > to.getBoardLocation().getFile()) {
				for (int i = to.getBoardLocation().getFile(); i <= king
						.getFile(); i++) {
					boolean isKing = isKing(board, king.getRank(), i, piece
							.getPlayer().getOpponent());

					if (!isKing
							&& board.getPieces()[king.getRank()][i].getPiece() != GamePiece.Empty)
						break;

					isCheck = isKing;
				}

				if (isCheck)
					return isCheck;

				for (int i = king.getFile(); i >= to.getBoardLocation()
						.getFile(); i--) {
					boolean isKing = isKing(board, i, king.getRank(), piece
							.getPlayer().getOpponent());

					if (!isKing
							&& board.getPieces()[king.getRank()][i].getPiece() != GamePiece.Empty)
						break;

					isCheck = isKing;
				}

				if (isCheck)
					return isCheck;
			}
		}

		// is the king in the line of file for where we are
		if (king.getFile() == to.getBoardLocation().getFile()) {
			if (king.getRank() > to.getBoardLocation().getRank()) {
				for (int i = to.getBoardLocation().getRank(); i <= king
						.getRank(); i++) {
					boolean isKing = isKing(board, i, king.getFile(), piece
							.getPlayer().getOpponent());

					if (!isKing
							&& board.getPieces()[i][king.getFile()].getPiece() != GamePiece.Empty)
						break;

					isCheck = isKing;
				}

				if (isCheck)
					return isCheck;

				for (int i = king.getRank(); i >= to.getBoardLocation()
						.getRank(); i--) {
					boolean isKing = isKing(board, i, king.getFile(), piece
							.getPlayer().getOpponent());

					if (!isKing
							&& board.getPieces()[i][king.getFile()].getPiece() != GamePiece.Empty)
						break;

					isCheck = isKing;
				}

				if (isCheck)
					return isCheck;
			}
		}

		if (isCheck)
			return isCheck;

		for (int i = to.getBoardLocation().getFile(), j = to.getBoardLocation()
				.getRank(); i >= 0; i--, j++) {
			boolean isKing = isKing(board, j, i, piece.getPlayer()
					.getOpponent());

			if (!isKing
					&& board.getPieces()[j][i].getPiece() != GamePiece.Empty)
				break;

			isCheck = isKing;
		}

		if (isCheck)
			return isCheck;

		for (int i = to.getBoardLocation().getRank(), j = to.getBoardLocation()
				.getFile(); i < ChessConstants.FILE_COUNT; i++, j++) {
			boolean isKing = isKing(board, i, j, piece.getPlayer()
					.getOpponent());

			if (!isKing
					&& board.getPieces()[i][j].getPiece() != GamePiece.Empty)
				break;

			isCheck = isKing;
		}

		if (isCheck)
			return isCheck;

		for (int i = to.getBoardLocation().getRank(), j = to.getBoardLocation()
				.getFile(); i >= 0; i--, j--) {
			boolean isKing = isKing(board, i, j, piece.getPlayer()
					.getOpponent());

			if (!isKing
					&& board.getPieces()[i][j].getPiece() != GamePiece.Empty)
				break;

			isCheck = isKing;
		}

		if (isCheck)
			return isCheck;

		for (int i = to.getBoardLocation().getRank(), j = to.getBoardLocation()
				.getFile(); i <= ChessConstants.FILE_COUNT; i--, j++) {
			boolean isKing = isKing(board, i, j, piece.getPlayer()
					.getOpponent());

			if (!isKing
					&& board.getPieces()[i][j].getPiece() != GamePiece.Empty)
				break;

			isCheck = isKing;
		}

		return isCheck;
	}
}
