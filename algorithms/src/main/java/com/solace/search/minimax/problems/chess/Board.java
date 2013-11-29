package com.solace.search.minimax.problems.chess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solace.search.minimax.problems.chess.moves.Move;

/**
 * Board structure is indexed by rank first Piece[rank:0-7][file:0-7]
 * 
 * [1][1] [0][1]
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public class Board {

	private static final Logger LOGGER = LoggerFactory.getLogger(Board.class);

	private Piece[][] board;

	private static Piece[][] DEFAULT_LAYOUT = new Piece[8][8];

	private static Piece[][] EMPTY_LAYOUT = new Piece[8][8];

	private Placement whiteKingPlacement, blackKingPlacement;

	static {
		// default it all to empty
		// to be able to detect stateful-ness
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				EMPTY_LAYOUT[i][j] = new Piece(GamePiece.Empty, Player.All);

		// default it all to empty
		// to be able to detect stateful-ness
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				DEFAULT_LAYOUT[i][j] = new Piece(GamePiece.Empty, Player.All);

		// setup pawns on opposing sides (row 2 and 7, 0 indexed)
		for (int i = 0; i < 8; i++) {
			DEFAULT_LAYOUT[1][i] = new Piece(GamePiece.Pawn, Player.White);
			DEFAULT_LAYOUT[6][i] = new Piece(GamePiece.Pawn, Player.Black);
		}

		// other white pieces
		DEFAULT_LAYOUT[0][0] = DEFAULT_LAYOUT[0][7] = new Piece(GamePiece.Rook,
				Player.White);
		DEFAULT_LAYOUT[0][1] = DEFAULT_LAYOUT[0][6] = new Piece(
				GamePiece.Knight, Player.White);
		DEFAULT_LAYOUT[0][2] = DEFAULT_LAYOUT[0][5] = new Piece(
				GamePiece.Bishop, Player.White);
		DEFAULT_LAYOUT[0][3] = new Piece(GamePiece.Queen, Player.White);
		DEFAULT_LAYOUT[0][4] = new Piece(GamePiece.King, Player.White);

		// other black pieces
		DEFAULT_LAYOUT[7][0] = DEFAULT_LAYOUT[7][7] = new Piece(GamePiece.Rook,
				Player.Black);
		DEFAULT_LAYOUT[7][1] = DEFAULT_LAYOUT[7][6] = new Piece(
				GamePiece.Knight, Player.Black);
		DEFAULT_LAYOUT[7][2] = DEFAULT_LAYOUT[7][5] = new Piece(
				GamePiece.Bishop, Player.Black);
		DEFAULT_LAYOUT[7][3] = new Piece(GamePiece.Queen, Player.Black);
		DEFAULT_LAYOUT[7][4] = new Piece(GamePiece.King, Player.Black);

	}

	/**
	 * Default constructor for default layout of board
	 */
	public Board() {
		this(DEFAULT_LAYOUT);
	}

	/**
	 * Copy constructor for pieces mapping
	 * 
	 * @param pieces
	 */
	public Board(Piece[][] pieces) {
		for (int i = 0; i < 8; i++)
			System.arraycopy(pieces, 0, this.board, 0, 8);

		LOGGER.debug("Board cloned to \n{}", toString());
	}

	public Board(Board board) {
		this(board.getPieces());
	}

	public Placement getWhiteKingPlacement() {
		return whiteKingPlacement;
	}

	public void setWhiteKingPlacement(Placement whiteKingPlacement) {
		this.whiteKingPlacement = whiteKingPlacement;
	}

	public Placement getBlackKingPlacement() {
		return blackKingPlacement;
	}

	public void setBlackKingPlacement(Placement blackKingPlacement) {
		this.blackKingPlacement = blackKingPlacement;
	}

	public Piece[][] getPieces() {
		return board;
	}

	public void place(Piece piece, String at) {
		place(piece, Enum.valueOf(BoardLocation.class, at));
	}

	/**
	 * Is largely responsible for the placing / capturing of {@link Move}s. When
	 * a {@link Move} is {@link Move#execute(Board)}d the implementing class is
	 * responsible for the implementation of the game rules of whether or not
	 * the rule is valid, and if the capture is successful. The increment and
	 * decrement of state variables, and if game has been won is handled by the
	 * place method.
	 * 
	 * @param piece
	 * @param placement
	 * @return will return true only if the move produces a checkmate
	 */
	public boolean place(Piece piece, BoardLocation placement) {
		boolean checkmate = false;

		if (placement != null) {
			Player opponent = piece.getPlayer() == Player.White ? Player.Black
					: Player.White;

			Piece target = board[placement.getRank()][placement.getFile()];

			if (target.getPiece() != GamePiece.Empty
					&& target.getPlayer() == opponent) {
				LOGGER.info("{} has capture a {}", piece.getPlayer(),
						board[placement.getRank()][placement.getFile()]
								.getPiece());

				if (target.getPiece() == GamePiece.King) {
					LOGGER.info("checkmate identified for {}",
							piece.getPlayer());
					checkmate = true;
				}

				board[placement.getRank()][placement.getFile()] = piece;
			} else {
				LOGGER.info("placing a {} for {} at {}", piece.getPiece(),
						piece.getPlayer(), placement);
			}
		} else {
			LOGGER.warn("placement was passed in as null");
		}

		return checkmate;
	}

	/**
	 * Contigent upon the orginal board being initialized to empty
	 * 
	 * @param placement
	 * @return
	 */
	public boolean isOccupied(BoardLocation placement) {
		return board[placement.getRank()][placement.getFile()].getPiece() != GamePiece.Empty;
	}

	public static Board factoryEmptyBoard() {
		return new Board(EMPTY_LAYOUT);
	}
}
