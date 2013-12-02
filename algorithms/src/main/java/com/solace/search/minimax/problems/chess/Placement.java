package com.solace.search.minimax.problems.chess;


public class Placement {

	private Player player;
	private GamePiece piece;
	private BoardLocation location;
	
	public Placement(Piece piece, BoardLocation loc) {
		this(piece.getPlayer(), piece.getPiece(), loc);
	}

	public Placement(Player player, GamePiece piece, BoardLocation location) {
		super();
		this.player = player;
		this.piece = piece;
		this.location = location;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public GamePiece getGamePiece() {
		return piece;
	}

	public void setGamePiece(GamePiece piece) {
		this.piece = piece;
	}

	public BoardLocation getBoardLocation() {
		return location;
	}

	public void setBoardLocation(BoardLocation location) {
		this.location = location;
	}

}
