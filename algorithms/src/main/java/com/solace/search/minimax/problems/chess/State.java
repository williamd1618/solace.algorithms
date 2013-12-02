package com.solace.search.minimax.problems.chess;

import java.util.List;

public class State {

	private Board board;

	private Player player;

	private PieceCounter counter;

	private boolean isMax;

	private int depth;

	public State(Board board, Player player, boolean isMax, int depth) {
		super();
		this.board = board;
		this.player = player;
		this.counter = new PieceCounter(board);
		this.isMax = isMax;
		this.depth = depth;
	}

	public Board getBoard() {
		return board;
	}

	public boolean isMax() {
		return isMax;
	}

	public void setMax(boolean isMax) {
		this.isMax = isMax;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PieceCounter getCounter() {
		return counter;
	}

	public List<Piece> getActivePieces() {
		return getActivePieces(player);
	}

	public List<Piece> getActivePieces(Player p) {
		return board.getPieceLocations().get(p);
	}

	@Override
	public String toString() {
		String val = String.format("\n{} active: {}\n{} active: {}\n",
				Player.White, getActivePieces(Player.White), Player.Black,
				getActivePieces(Player.Black));
		return val;
	}
}
