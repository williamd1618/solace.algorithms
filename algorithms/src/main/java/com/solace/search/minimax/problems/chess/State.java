package com.solace.search.minimax.problems.chess;

public class State {
	
	private Board board;
	
	private Player player;
	
	private PieceCounter counter;

	public State(Board board, Player player) {
		super();
		this.board = board;
		this.player = player;
		this.counter = new PieceCounter(board);
	}

	public Board getBoard() {
		return board;
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
}
