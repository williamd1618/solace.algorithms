package com.solace.search.minimax.problems.tictactoe;

/**
 * makes the assumption of the following positions [7][8][9] [4][5][6] [1][2][3]
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public class Board {

	Piece[][] board = { { Piece.Empty, Piece.Empty, Piece.Empty },
			{ Piece.Empty, Piece.Empty, Piece.Empty },
			{ Piece.Empty, Piece.Empty, Piece.Empty } };

	public static final Board IDEAL_START = new Board(new Piece[][] {
			{ Piece.Empty, Piece.Empty, Piece.Empty },
			{ Piece.Empty, Piece.X, Piece.Empty },
			{ Piece.Empty, Piece.Empty, Piece.Empty } });

	public static class Indices {
		public static final int[][] DIAGNOAL = { { 1, 5, 9 }, { 7, 5, 2 } };
		public static final int[][] HORIZONTAL = { { 1, 2, 3 }, { 4, 5, 6 },
				{ 7, 8, 9 } };
		public static final int[][] VERTICAL = { { 1, 4, 7 }, { 2, 5, 8 },
				{ 3, 6, 9 } };
	}

	private static final int MIN_POS = 1;
	private static final int MAX_POS = 9;

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
	 * @param toClone
	 */
	public Board(Board toClone) {		
		System.arraycopy(toClone.board[0], 0, board[0], 0, 3);
		System.arraycopy(toClone.board[1], 0, board[1], 0, 3);
		System.arraycopy(toClone.board[2], 0, board[2], 0, 3);
	}

	public void place(Piece piece, int location) {
		if (location < MIN_POS || location > MAX_POS)
			return;

		if (board[location / 3][location % 3] == Piece.Empty)
			board[location / 3][location % 3] = piece;
	}

	public Piece at(int location) {
		return board[location / 3][location % 3];
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
}
