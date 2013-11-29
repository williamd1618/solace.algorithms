package com.solace.search.minimax.problems.chess;

public enum EPlayer {

	White("White"), Black("Black"), All("All");

	private String color;

	public String getColor() {
		return color;
	}

	private EPlayer(String color) {
		this.color = color;
	}

}
