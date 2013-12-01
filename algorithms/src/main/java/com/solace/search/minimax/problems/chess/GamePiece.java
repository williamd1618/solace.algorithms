package com.solace.search.minimax.problems.chess;

/**
 * An enum representation of pieces and counts
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public enum GamePiece {

	King("King", 1), Queen("Queen", 1), Rook("Rook", 2), Bishop("Bishop", 2), Knight(
			"Knight", 2), Pawn("Pawn", 8), Empty("Empty", 64);

	private String name;
	private int count;

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	private GamePiece(String name, int count) {
		this.name = name;
		this.count = count;
	}
}
