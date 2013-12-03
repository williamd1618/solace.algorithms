package com.solace.search.minimax.problems.chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solace.search.minimax.problems.chess.moves.IValidMoveEvaluator;
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

	private Map<Player, List<Piece>> pieceLocations = new HashMap<Player, List<Piece>>();

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
			DEFAULT_LAYOUT[1][i] = new Piece(GamePiece.Pawn, Player.White,
					BoardLocation.find(1, i));
			DEFAULT_LAYOUT[6][i] = new Piece(GamePiece.Pawn, Player.Black,
					BoardLocation.find(6, i));
		}

		// other white pieces
		DEFAULT_LAYOUT[0][0] = new Piece(GamePiece.Rook, Player.White,
				BoardLocation.find(0, 0));
		DEFAULT_LAYOUT[0][7] = new Piece(GamePiece.Rook, Player.White,
				BoardLocation.find(0, 7));
		DEFAULT_LAYOUT[0][1] = new Piece(GamePiece.Rook, Player.White,
				BoardLocation.find(0, 1));
		DEFAULT_LAYOUT[0][6] = new Piece(GamePiece.Knight, Player.White,
				BoardLocation.find(0, 6));
		DEFAULT_LAYOUT[0][2] = new Piece(GamePiece.Rook, Player.White,
				BoardLocation.find(0, 2));
		DEFAULT_LAYOUT[0][5] = new Piece(GamePiece.Bishop, Player.White,
				BoardLocation.find(0, 5));
		DEFAULT_LAYOUT[0][3] = new Piece(GamePiece.Queen, Player.White,
				BoardLocation.find(0, 3));
		DEFAULT_LAYOUT[0][4] = new Piece(GamePiece.King, Player.White,
				BoardLocation.find(0, 4));

		// other black pieces
		DEFAULT_LAYOUT[7][0] = new Piece(GamePiece.Rook, Player.Black,
				BoardLocation.find(7, 0));
		DEFAULT_LAYOUT[7][7] = new Piece(GamePiece.Rook, Player.Black,
				BoardLocation.find(7, 7));
		DEFAULT_LAYOUT[7][1] = new Piece(GamePiece.Knight, Player.Black,
				BoardLocation.find(7, 1));
		DEFAULT_LAYOUT[7][6] = new Piece(GamePiece.Knight, Player.Black,
				BoardLocation.find(7, 6));
		DEFAULT_LAYOUT[7][2] = new Piece(GamePiece.Knight, Player.Black,
				BoardLocation.find(7, 2));
		DEFAULT_LAYOUT[7][5] = new Piece(GamePiece.Bishop, Player.Black,
				BoardLocation.find(7, 5));
		DEFAULT_LAYOUT[7][3] = new Piece(GamePiece.Knight, Player.Black,
				BoardLocation.find(7, 3));
		DEFAULT_LAYOUT[7][4] = new Piece(GamePiece.King, Player.Black,
				BoardLocation.find(7, 4));

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
		LOGGER.debug(pieces.toString());

		board = new Piece[8][8];

		for (int i = 0; i < 8; i++)
			System.arraycopy(pieces[i], 0, this.board[i], 0, 8);

		pieceLocations.put(Player.Black, new ArrayList<Piece>());
		pieceLocations.put(Player.White, new ArrayList<Piece>());

		// load the map of locations ot be easily accessed for when
		// generated adjacency lists
		for (int r = 0, f = 0; r < ChessConstants.RANK_COUNT
				&& f < ChessConstants.FILE_COUNT; r++, f++)
			if (pieces[r][f].getPiece() != GamePiece.Empty)
				pieceLocations.get(pieces[r][r].getPlayer()).add(pieces[r][f]);

		LOGGER.debug("Board cloned to \n{}", toString());
	}

	/**
	 * Will clone the pieces map and re-initialize the cloned pieceLocations map
	 * 
	 * @param board
	 */
	public Board(Board board) {
		this(board.getPieces());
	}

	public Placement getWhiteKingPlacement() {
		return whiteKingPlacement;
	}

	public void setWhiteKingPlacement(Placement whiteKingPlacement) {
		this.whiteKingPlacement = whiteKingPlacement;
	}

	public Map<Player, List<Piece>> getPieceLocations() {
		return pieceLocations;
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

				LOGGER.info(
						"Removing {} at {} from the board and location map.",
						target.getPiece(), target.getLocation());

				pieceLocations.get(opponent).remove(target);

				if (target.getPiece() == GamePiece.King) {
					LOGGER.info("checkmate identified for {}",
							piece.getPlayer());
					checkmate = true;
				}

				board[placement.getRank()][placement.getFile()] = piece;

				piece.setLocation(BoardLocation.find(placement.getRank(),
						placement.getFile()));

				pieceLocations.get(piece.getPlayer()).add(piece);

				if (piece.getPiece() == GamePiece.King)
					updateKingLoc(piece);

			} else {
				LOGGER.info("placing a {} for {} at {}", piece.getPiece(),
						piece.getPlayer(), placement);

				board[placement.getRank()][placement.getFile()] = piece;

				piece.setLocation(BoardLocation.find(placement.getRank(),
						placement.getFile()));

				pieceLocations.get(piece.getPlayer()).add(piece);

				if (piece.getPiece() == GamePiece.King)
					updateKingLoc(piece);
			}
		} else {
			LOGGER.warn("placement was passed in as null");
		}

		return checkmate;
	}

	/**
	 * 
	 * @param piece
	 */
	private void updateKingLoc(Piece piece) {
		if (piece.getPiece() != GamePiece.King)
			throw new RuntimeException("not a King");

		if (piece.getPlayer() == Player.White)
			setWhiteKingPlacement(new Placement(piece, piece.getLocation()));
		else
			setBlackKingPlacement(new Placement(piece, piece.getLocation()));
	}

	/**
	 * Will evaluate whether or not a board location has an element in it. Due
	 * to the nature of the {@link IValidMoveEvaluator}s a BoardLocation could
	 * be passed in as null if if the rank and file passed to
	 * {@link BoardLocation#find(int, int)} is not a valid position, in which
	 * case we return false.
	 * 
	 * @param placement
	 * @return
	 */
	public boolean isOccupied(BoardLocation placement) {
		if (placement == null)
			return false;

		if (board[placement.getRank()][placement.getFile()] == null)
			return false;

		return board[placement.getRank()][placement.getFile()].getPiece() != GamePiece.Empty;
	}

	public boolean isOccupiedByOpponent(BoardLocation placement, Player opponent) {
		return board[placement.getRank()][placement.getFile()].getPiece() != GamePiece.Empty
				&& board[placement.getRank()][placement.getFile()].getPlayer() == opponent;
	}

	public static Board factoryEmptyBoard() {
		return new Board(EMPTY_LAYOUT);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int r = 7; r >= 0; r--) {
			sb.append("\n");
			for (int f = 0; f < 8; f++)
				sb.append("[").append(board[r][f]).append("]");
		}

		return sb.toString();
	}

	/**
	 * will clear out the {@link #board} and the {@link #pieceLocations}
	 */
	public void clear() {
		for (int r = 0; r < 8; r++) {
			for (int f = 0; f < 8; f++)
				board[r][f] = new Piece(GamePiece.Empty, Player.All);
		}

		// if we're clearing the table, clear out the piece location metamap
		for (Entry<Player, List<Piece>> pos : this.getPieceLocations()
				.entrySet()) {
			pos.getValue().clear();
		}
	}
}
