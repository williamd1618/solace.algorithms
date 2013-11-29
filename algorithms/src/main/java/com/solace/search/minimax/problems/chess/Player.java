package com.solace.search.minimax.problems.chess;

public enum Player {

	White("White"), Black("Black"), All("All");

	private String color;

	public String getColor() {
		return color;
	}

	private Player(String color) {
		this.color = color;
	}

}
