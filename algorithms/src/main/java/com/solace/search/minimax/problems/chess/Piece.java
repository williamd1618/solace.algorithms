package com.solace.search.minimax.problems.chess;

public class Piece {

	private GamePiece piece;

	private Player player;

	public Piece(GamePiece piece, Player player) {
		super();
		this.piece = piece;
		this.player = player;
	}

	public GamePiece getPiece() {
		return piece;
	}

	public void setPiece(GamePiece piece) {
		this.piece = piece;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
