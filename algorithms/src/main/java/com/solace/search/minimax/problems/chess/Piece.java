package com.solace.search.minimax.problems.chess;

public class Piece {

	private EPiece piece;

	private EPlayer player;

	public Piece(EPiece piece, EPlayer player) {
		super();
		this.piece = piece;
		this.player = player;
	}

	public EPiece getPiece() {
		return piece;
	}

	public void setPiece(EPiece piece) {
		this.piece = piece;
	}

	public EPlayer getPlayer() {
		return player;
	}

	public void setPlayer(EPlayer player) {
		this.player = player;
	}

}
