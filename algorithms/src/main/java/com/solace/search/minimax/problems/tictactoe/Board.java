package com.solace.search.minimax.problems.tictactoe;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * makes the assumption of the following positions [7][8][9] [4][5][6] [1][2][3]
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public class Board {

	private static final Logger LOGGER = LoggerFactory.getLogger(Board.class);

	Piece[][] board = { { Piece.Empty, Piece.Empty, Piece.Empty },
			{ Piece.Empty, Piece.Empty, Piece.Empty },
			{ Piece.Empty, Piece.Empty, Piece.Empty } };

	public static final Board IDEAL_START = new Board();

	static {
		IDEAL_START.place(Piece.X, 5);
	}

	public static class Indices {
		public static final int[][] DIAGNOAL = { { 1, 5, 9 }, { 7, 5, 2 } };
		public static final int[][] HORIZONTAL = { { 1, 2, 3 }, { 4, 5, 6 },
				{ 7, 8, 9 } };
		public static final int[][] VERTICAL = { { 1, 4, 7 }, { 2, 5, 8 },
				{ 3, 6, 9 } };
	}

	public static final int MIN_POS = 1;
	public static final int MAX_POS = 9;

	public static enum Player {
		X('X'), O('O');

		private char value;

		private Player(char v) {
			this.value = v;
		}

		@Override
		public String toString() {
			return new String(new char[] { value });
		}
	}

	/**
	 * representation of a game piece
	 * 
	 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
	 * 
	 */
	public static enum Piece {
		X('X'), O('O'), Empty(' ');

		private char value;

		private Piece(char v) {
			this.value = v;
		}

		@Override
		public String toString() {
			return new String(new char[] { value });
		}
	}

	public Board() {

	}

	/**
	 * Will do a deep clone of the boad pieces
	 * 
	 * @param toClone
	 */
	public Board(Board toClone) {

		for (int i = 0; i < 3; i++)
			System.arraycopy(toClone.board[i], 0, board[i], 0, 3);
		
		LOGGER.debug("Board cloned to \n{}", toString());
	}

	public boolean place(Piece piece, int location) {
		boolean result = false;
		
		Coordinate c = position(location);

		if (at(location) == Piece.Empty) {
			board[c.getFirst()][c.getSecond()] = piece;
			result = true;
		} else {
			result = false;
		}

		LOGGER.debug("{} placed {} at {}", result ? "successfully"
				: "unsuccuessfully", piece, location);

		return result;
	}

	public Piece at(int location) {
		return at(board, location);
	}

	private Piece at(Piece[][] board, int location) {
		
		Coordinate c = position(location);

		LOGGER.debug("Attempting to get position at {}, or [{}][{}]", location,
				c.getFirst(), c.getSecond());

		return board[c.getFirst()][c.getSecond()];
	}
	
	private Coordinate position(int loc) {
		if ( loc < MIN_POS || loc > MAX_POS)
			throw new RuntimeException("location must be between 1 and 9");
		
		int first = (int)Math.floor((loc - 1) / 3);
		int second = (loc - 1) % 3;

		return new Coordinate(first, second);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		Piece[] arr = board[2];
		sb.append("[").append(arr[0]).append("][").append(arr[1]).append("][")
				.append(arr[2]).append("]").append("\n");
		arr = board[1];
		sb.append("[").append(arr[0]).append("][").append(arr[1]).append("][")
				.append(arr[2]).append("]").append("\n");

		arr = board[0];
		sb.append("[").append(arr[0]).append("][").append(arr[1]).append("][")
				.append(arr[2]).append("]").append("\n");

		return sb.toString();
	}
	
	
	/**
	 * An internal representation of the 3x3 matrix coordinate structure
	 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
	 *
	 */
	private static class Coordinate {
		private int first, second;

		public Coordinate(int first, int second) {
			super();
			this.first = first;
			this.second = second;
		}

		public int getFirst() {
			return first;
		}

		public int getSecond() {
			return second;
		}
	}
}
