package com.solace.search.minimax.problems.tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.solace.search.minimax.problems.tictactoe.Board.Piece;
import com.solace.search.minimax.problems.tictactoe.Board.Player;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String... args) {

		LOGGER.info("{} 2 plies, X is max {}", Strings.repeat("=", 15),
				Strings.repeat("=", 15));

		TicTacToeNode initial = new TicTacToeNode(new State(Player.X, true));

		TicTacToeMiniMaxSearch search = new TicTacToeMiniMaxSearch(initial, 2,
				true);

		TicTacToeNode result = search.search();

		LOGGER.info("======> Resulting move {}", result.toString());

		LOGGER.info("{}", Strings.repeat("=", 100));

		LOGGER.info("{} 3 plies, X is max {}", Strings.repeat("=", 15),
				Strings.repeat("=", 15));

		initial = new TicTacToeNode(new State(Player.X, true));

		search = new TicTacToeMiniMaxSearch(initial, 3, true);

		result = search.search();

		LOGGER.info("======> Resulting move {}", result.toString());

		LOGGER.info("{}", Strings.repeat("=", 100));

		LOGGER.info("{} 2 plies, X is max at position 3 {}",
				Strings.repeat("=", 15), Strings.repeat("=", 15));

		Board b = new Board();
		b.place(Piece.X, 3);
		initial = new TicTacToeNode(new State(b, Player.X, true, 0));

		search = new TicTacToeMiniMaxSearch(initial, 2, true);

		result = search.search();

		LOGGER.info("======> Resulting move {}", result.toString());

		LOGGER.info("{}", Strings.repeat("=", 100));

		LOGGER.info("{} 3 plies, X is max at position 3 {}",
				Strings.repeat("=", 15), Strings.repeat("=", 15));

		b = new Board();
		b.place(Piece.X, 3);
		initial = new TicTacToeNode(new State(b, Player.X, true, 0));

		search = new TicTacToeMiniMaxSearch(initial, 3, true);

		result = search.search();

		LOGGER.info("======> Resulting move {}", result.toString());
		
		LOGGER.info("{}", Strings.repeat("=", 100));

		LOGGER.info("{} 2 plies, X is max at position 8 {}",
				Strings.repeat("=", 15), Strings.repeat("=", 15));

		b = new Board();
		b.place(Piece.X, 8);
		initial = new TicTacToeNode(new State(b, Player.X, true, 0));

		search = new TicTacToeMiniMaxSearch(initial, 2, true);

		result = search.search();

		LOGGER.info("======> Resulting move {}", result.toString());

		LOGGER.info("{}", Strings.repeat("=", 100));

		LOGGER.info("{} 3 plies, X is max at position 8 {}",
				Strings.repeat("=", 15), Strings.repeat("=", 15));

		b = new Board();
		b.place(Piece.X, 8);
		initial = new TicTacToeNode(new State(b, Player.X, true, 0));

		search = new TicTacToeMiniMaxSearch(initial, 3, true);

		result = search.search();

		LOGGER.info("======> Resulting move {}", result.toString());
	}
}
