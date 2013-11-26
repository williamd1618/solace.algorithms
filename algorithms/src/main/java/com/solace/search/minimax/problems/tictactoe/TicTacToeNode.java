package com.solace.search.minimax.problems.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.solace.algorithms.search.minimax.MiniMaxNode;
import com.solace.graph.Adjacency;
import com.solace.graph.Node;
import com.solace.search.minimax.problems.tictactoe.Board.Indices;
import com.solace.search.minimax.problems.tictactoe.Board.Piece;
import com.solace.search.minimax.problems.tictactoe.Board.Player;

/**
 * Represents a node in a graph of a TicTacToe move. In this, the node itself,
 * has a State. This state represents the state at that point in the graph. The
 * game logic for detecting a win, or not win for that matter, is help in this
 * classes {@link #containsWin()} functionality.
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public class TicTacToeNode extends MiniMaxNode<State, TicTacToeNode> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TicTacToeNode.class);

	public TicTacToeNode(Node<State> parent, State t) {
		super(parent, t);
		// TODO Auto-generated constructor stub
	}

	public TicTacToeNode(Node<State> parent, State t, int depth) {
		super(parent, t, depth);
		// TODO Auto-generated constructor stub
	}

	public TicTacToeNode(State t) {
		super(t);

		t.setDepth(0);
	}

	@Override
	public double calculateH() {

		int x_poss_wins = calculatePossibleWins(Player.X);

		int o_poss_wins = calculatePossibleWins(Player.O);

		double h = 0.0d;

		if (getValue().getPlayer() == Player.X)
			h = x_poss_wins - o_poss_wins;
		else
			h = o_poss_wins - x_poss_wins;

		LOGGER.info("h(x) for \n{} calculated as {}", getValue().getBoard()
				.toString(), h);

		return h;
	}

	/**
	 * Reliant upon the map of indices in {@link Indices}
	 * 
	 * @param p
	 * @return
	 */
	private int calculatePossibleWins(Player p) {

		int total = 0;

		Piece piece = p == Player.X ? Piece.X : Piece.O;

		Board b = getValue().getBoard();

		for (int[] idx : Board.Indices.DIAGNOAL) {
			total += analyzeForWins(p, piece, idx) ? 1 : 0;
		}

		for (int[] idx : Board.Indices.HORIZONTAL) {
			total += analyzeForWins(p, piece, idx) ? 1 : 0;
		}

		for (int[] idx : Board.Indices.VERTICAL) {
			total += analyzeForWins(p, piece, idx) ? 1 : 0;
		}

		LOGGER.info("Player: {} calculated wins: {}", p.name(), total);

		return total;
	}

	/**
	 * Will check to see if if the node's {@link State}
	 */
	@Override
	public boolean containsWin() {
		boolean isWin = false;

		int total = 0;

		Board b = getValue().getBoard();

		Player p = getValue().getPlayer();

		Piece piece = p == Player.X ? Piece.X : Piece.O;

		for (int[] idx : Board.Indices.DIAGNOAL) {
			isWin = isWin(p, piece, idx);
		}

		if (!isWin)
			for (int[] idx : Board.Indices.HORIZONTAL) {
				isWin = isWin(p, piece, idx);
			}
		else
			return isWin;

		if (!isWin)
			for (int[] idx : Board.Indices.VERTICAL) {
				isWin = isWin(p, piece, idx);
			}
		else
			return isWin;

		LOGGER.info("\n{} {} as a win for {}", getValue().getBoard().toString(),
				isWin ? "identified" : "not identified", p.name());

		return isWin;
	}
	
	
	private boolean isWin(Player p, final Piece piece, int[] line) {
		if (line.length != 3)
			throw new RuntimeException(
					"inappropriate set of indices for board map!");

		boolean win = false;

		Board board = getValue().getBoard();
		
		List<Piece> pieces = new ArrayList<Piece>();
		
		for(int idx : line) 
			pieces.add(board.at(idx));
		
		Piece result = Iterables.find(pieces, new Predicate<Piece>() {

			public boolean apply(Piece arg0) {
				return piece != arg0;
			}			
		});
		
		// if p == null then we have a win.
		return result == null;
	}

	/**
	 * Will detect potential winning paths for a given {@link Player}
	 * 
	 * @param p
	 * @param idx
	 * @return true if the line of indices does not contain an opposing piece
	 */
	private boolean analyzeForWins(Player p, Piece piece, int[] line) {
		if (line.length != 3)
			throw new RuntimeException(
					"inappropriate set of indices for board map!");

		boolean possWin = false;

		Board board = getValue().getBoard();
		
		Piece opponent = piece == Piece.X ? Piece.O : Piece.X;
		
		List<Piece> placements = new ArrayList<Piece>();
		
		for(int idx : line) {
			placements.add(board.at(idx));
		}
		
		return !placements.contains(opponent);
	}

	/**
	 * Will identify a set of {@link TicTacToeNode}s that are adjacent to this
	 * that meet all potential moves for the other player. In this we'll take
	 * into account symmetry concerns so as to not repeat {@link State}
	 * <p>
	 * Initial implementation will brute force all adjacency options so as to
	 * test the rest of the algorithm. Once that is complete we will implement a
	 * form of a symmetry analysis structure
	 */
	public Adjacency<State, TicTacToeNode> generateAdjacency() {

		Player toGenerate = getValue().getPlayer() == Player.X ? Player.O
				: Player.X;

		Piece pieceToPlay = toGenerate == Player.X ? Piece.X : Piece.O;

		Adjacency<State, TicTacToeNode> adjacency = new Adjacency<State, TicTacToeNode>(
				this);

		Set<TicTacToeNode> nodes = new HashSet<TicTacToeNode>();

		Board board = getValue().getBoard();

		for (int i = Board.MIN_POS; i <= Board.MAX_POS; i++) {
			Board b = new Board(board);

			if (b.place(pieceToPlay, i)) {
				TicTacToeNode node = new TicTacToeNode(this, new State(b,
						toGenerate, !getValue().isMax(), getDepth() - 1));
				nodes.add(node);
				
				node.setParent(this);
			}
		}

		adjacency.setNeighbors(nodes);
		
		LOGGER.info("Generated {} possible moves for {}", nodes.size(), toGenerate.name());

		return adjacency;
	}
}
