package com.solace.search.minimax.problems.chess;

public enum Player {

	White("White", 0, "Black"), Black("Black", 7, "White"), All("All", -1);

	private String color;
	
	private int firstRankIndex;
	
	private String opponent;

	public String getColor() {
		return color;
	}

	public int getFirstRankIndex() {
		return firstRankIndex;
	}

	private Player(String color, int index) {
		this.color = color;
		this.firstRankIndex = index;
	}
	
	private Player(String color, int index, String opponent) {
		this.color = color;
		this.firstRankIndex = index;
		this.opponent = opponent;
	}

	
	/**
	 * evaluates lazy due to first order compilation of enums
	 * @return
	 */
	public Player getOpponent() {
		return Player.valueOf(opponent);
	}
}
