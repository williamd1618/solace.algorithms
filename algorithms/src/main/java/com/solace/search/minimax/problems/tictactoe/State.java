package com.solace.search.minimax.problems.tictactoe;

import com.solace.search.minimax.problems.tictactoe.Board.Player;


/**
 * State for TicTacToe
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 *
 */
public class State {
	
	private Board board;
	
	private Player player;
	
	private boolean isMax;
	
	private int depth;
	
	
	/**
	 * Will default to {@link Board#IDEAL_START}
	 */
	public State() {
		this(Board.IDEAL_START);
	}
	
	/**
	 * Will default to player X
	 * @param board
	 */
	public State(Board board) {
		this(board, Player.X, true, 0);
	}
	
	/**
	 * Will default to {@link Board#IDEAL_START} and Max's move
	 * @param player
	 * @param isMax
	 */
	public State(Player player, boolean isMax) {
		this(Board.IDEAL_START, player, isMax, 0);
	}
	
	

	/**
	 * Allows all to be passed in
	 * @param board
	 * @param player
	 * @param isMax
	 */
	public State(Board board, Player player, boolean isMax, int depth) {
		this.board = board;
		this.player = player;
		this.isMax = isMax;
		this.depth = depth;
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
}
