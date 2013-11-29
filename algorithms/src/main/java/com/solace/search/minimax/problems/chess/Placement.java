package com.solace.search.minimax.problems.chess;


public class Placement {

	private EPlayer player;
	private EPiece piece;
	private EPlacement location;

	public Placement(EPlayer player, EPiece piece, EPlacement location) {
		super();
		this.player = player;
		this.piece = piece;
		this.location = location;
	}

	public EPlayer getPlayer() {
		return player;
	}

	public void setPlayer(EPlayer player) {
		this.player = player;
	}

	public EPiece getPiece() {
		return piece;
	}

	public void setPiece(EPiece piece) {
		this.piece = piece;
	}

	public EPlacement getLocation() {
		return location;
	}

	public void setLocation(EPlacement location) {
		this.location = location;
	}

}
