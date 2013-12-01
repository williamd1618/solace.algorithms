package com.solace.search.minimax.problems.chess;

public class Piece {

	private GamePiece piece;

	private Player player;
	
	private boolean isCaptured = false;
	
	BoardLocation location;
	
	public Piece(GamePiece piece, Player player) {
		this(piece, player, null);
	}

	public Piece(GamePiece piece, Player player, BoardLocation location) {
		super();
		this.piece = piece;
		this.player = player;
		this.location = location;
	}

	public BoardLocation getLocation() {
		return location;
	}

	public void setLocation(BoardLocation location) {
		this.location = location;
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
	
	public void capture() {
		isCaptured = true;
	}
	
	public boolean isCaptured() {
		return isCaptured;
	}
}
