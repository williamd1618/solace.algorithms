package com.solace.search.minimax.problems.tictactoe;

import java.util.HashSet;
import java.util.Set;

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
 * classes {@link #isWin()} functionality.
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public class TicTacToeNode extends MiniMaxNode<State, TicTacToeNode> {

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

		if (getValue().getPlayer() == Player.X)
			return x_poss_wins - o_poss_wins;
		else
			return o_poss_wins - x_poss_wins;
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
			total += analyzeIndices(p, piece, idx) ? 1 : 0;
		}

		for (int[] idx : Board.Indices.HORIZONTAL) {
			total += analyzeIndices(p, piece, idx) ? 1 : 0;
		}

		for (int[] idx : Board.Indices.VERTICAL) {
			total += analyzeIndices(p, piece, idx) ? 1 : 0;
		}

		return total;
	}

	/**
	 * Will check to see if if the node's {@link State}
	 */
	@Override
	public boolean isWin() {
		boolean isWin = false;

		int total = 0;

		Board b = getValue().getBoard();

		Player p = getValue().getPlayer();

		Piece piece = p == Player.X ? Piece.X : Piece.O;

		for (int[] idx : Board.Indices.DIAGNOAL) {
			isWin = analyzeIndices(p, piece, idx);
		}

		if (!isWin)
			for (int[] idx : Board.Indices.HORIZONTAL) {
				isWin = analyzeIndices(p, piece, idx);
			}
		else
			return isWin;

		if (!isWin)
			for (int[] idx : Board.Indices.VERTICAL) {
				isWin = analyzeIndices(p, piece, idx);
			}
		else
			return isWin;

		return isWin;
	}

	/**
	 * Will detect potential winning paths for a given {@link Player}
	 * 
	 * @param p
	 * @param idx
	 * @return
	 */
	private boolean analyzeIndices(Player p, Piece piece, int[] idx) {
		if (idx.length != 3)
			throw new RuntimeException(
					"inappropriate set of indices for board map!");

		boolean possWin = false;

		Board board = getValue().getBoard();

		for (int i = 0; i < idx.length; i++) {
			int index = idx[i];
			if (board.at(index) != piece && board.at(index) != Piece.Empty)
				continue;
			else if (index == idx.length - 1
					&& (board.at(index) == piece || board.at(index) == Piece.Empty))
				possWin = true;
		}

		return possWin;
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

		

		return adjacency;
	}
}
