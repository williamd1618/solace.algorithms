package com.solace.search.minimax.problems.chess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String... args) {
		Board initialBoard = new Board();
		initialBoard.clear();
		initialBoard.place(new Piece(GamePiece.Pawn, Player.White, BoardLocation.C6), BoardLocation.C6);
		initialBoard.place(new Piece(GamePiece.King, Player.White, BoardLocation.H8), BoardLocation.H8);
		initialBoard.place(new Piece(GamePiece.Pawn, Player.Black, BoardLocation.H5), BoardLocation.H5);
		initialBoard.place(new Piece(GamePiece.King, Player.Black, BoardLocation.A6), BoardLocation.A6);
		
		LOGGER.info(initialBoard.toString());
		
		ChessNode initial = new ChessNode(new State(initialBoard, Player.White, true, 3));
		
		ChessMiniMaxSearch search = new ChessMiniMaxSearch(initial, 3, true);
		
		ChessNode move = search.search();
		
		LOGGER.info(move.toString());
	}
}
