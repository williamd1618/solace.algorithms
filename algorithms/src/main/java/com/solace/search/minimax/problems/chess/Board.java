package com.solace.search.minimax.problems.chess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solace.search.minimax.problems.chess.moves.Move;

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
				EMPTY_LAYOUT[i][j] = new Piece(EPiece.Empty, EPlayer.All);

		// default it all to empty
		// to be able to detect stateful-ness
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				DEFAULT_LAYOUT[i][j] = new Piece(EPiece.Empty, EPlayer.All);

		// setup pawns on opposing sides (row 2 and 7 0 indexed)
		for (int i = 0; i < 8; i++) {
			DEFAULT_LAYOUT[i][1] = new Piece(EPiece.Pawn, EPlayer.White);
			DEFAULT_LAYOUT[i][6] = new Piece(EPiece.Pawn, EPlayer.Black);
		}

		// other white pieces
		DEFAULT_LAYOUT[0][0] = DEFAULT_LAYOUT[7][0] = new Piece(EPiece.Rook,
				EPlayer.White);
		DEFAULT_LAYOUT[1][0] = DEFAULT_LAYOUT[6][0] = new Piece(EPiece.Knight,
				EPlayer.White);
		DEFAULT_LAYOUT[2][0] = DEFAULT_LAYOUT[5][0] = new Piece(EPiece.Bishop,
				EPlayer.White);
		DEFAULT_LAYOUT[3][0] = new Piece(EPiece.Queen, EPlayer.White);
		DEFAULT_LAYOUT[4][0] = new Piece(EPiece.King, EPlayer.White);

		// other black pieces
		DEFAULT_LAYOUT[0][7] = DEFAULT_LAYOUT[7][7] = new Piece(EPiece.Rook,
				EPlayer.Black);
		DEFAULT_LAYOUT[1][7] = DEFAULT_LAYOUT[6][7] = new Piece(EPiece.Knight,
				EPlayer.Black);
		DEFAULT_LAYOUT[2][7] = DEFAULT_LAYOUT[5][7] = new Piece(EPiece.Bishop,
				EPlayer.Black);
		DEFAULT_LAYOUT[3][7] = new Piece(EPiece.Queen, EPlayer.Black);
		DEFAULT_LAYOUT[4][7] = new Piece(EPiece.King, EPlayer.Black);

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
		place(piece, Enum.valueOf(EPlacement.class, at));
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
	 */
	public void place(Piece piece, EPlacement placement) {
		if (placement != null) {
			if (board[placement.getX()][placement.getY()].getPiece() != EPiece.Empty) {
				LOGGER.info("{} has capture a {}", piece.getPlayer(),
						board[placement.getX()][placement.getY()].getPiece());

				board[placement.getX()][placement.getY()] = piece;
			} else {
				LOGGER.info("placing a {} for {} at {}", piece.getPiece(),
						piece.getPlayer(), placement);
			}
		} else {
			LOGGER.warn("placement was passed in as null");
		}
	}

	/**
	 * Contigent upon the orginal board being initialized to empty
	 * 
	 * @param placement
	 * @return
	 */
	public boolean isOccupied(EPlacement placement) {
		return board[placement.getX()][placement.getY()].getPiece() != EPiece.Empty;
	}

	public static Board factoryEmptyBoard() {
		return new Board(EMPTY_LAYOUT);
	}
}
