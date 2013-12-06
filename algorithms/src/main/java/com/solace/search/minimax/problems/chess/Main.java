package com.solace.search.minimax.problems.chess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	private static final int DEPTH = 5;
	
	private static final int REAL_PLIES = 10;
	
	private static final Player INITIAL_PLAYER = Player.White;

	public static void main(String... args) {
		Board initialBoard = new Board();
		initialBoard.clear();
		initialBoard.place(new Piece(GamePiece.Pawn, Player.White,
				BoardLocation.C6), BoardLocation.C6);
		initialBoard.place(new Piece(GamePiece.King, Player.White,
				BoardLocation.H8), BoardLocation.H8);
		initialBoard.place(new Piece(GamePiece.Pawn, Player.Black,
				BoardLocation.H5), BoardLocation.H5);
		initialBoard.place(new Piece(GamePiece.King, Player.Black,
				BoardLocation.A6), BoardLocation.A6);

		LOGGER.info(initialBoard.toString());
		
		int ply = 0;
		
		ChessNode initial = new ChessNode(new State(initialBoard, Player.White,
				true, DEPTH));
		
		ChessNode node = initial;
		
		Player player = INITIAL_PLAYER;
		
		do {			
			LOGGER.info("Real ply: {}", ply);
			
			ChessMiniMaxSearch search = new ChessMiniMaxSearch(node, DEPTH, true);
			
			ChessNode result = search.search();
			
			LOGGER.info(result.getValue().getBoard().toString());
			
			if ( result.isCheckMateFound() ) {
				LOGGER.info("checkmate found.");
				break;
			}				
			
			player = player.getOpponent();
			
			ply++;
		} while (ply < REAL_PLIES);
				
		
//		ChessNode initial = new ChessNode(new State(initialBoard, Player.White,
//				true, DEPTH));
//
//		LOGGER.info("First move made by {}.", Player.White);
//
//		ChessMiniMaxSearch search = new ChessMiniMaxSearch(initial, DEPTH, true);
//
//		ChessNode move = search.search();
//
//		LOGGER.info(move.toString());
	}
}
