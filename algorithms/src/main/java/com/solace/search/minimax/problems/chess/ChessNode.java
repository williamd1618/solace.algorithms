package com.solace.search.minimax.problems.chess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solace.algorithms.search.minimax.MiniMaxNode;
import com.solace.graph.Adjacency;
import com.solace.graph.Node;

public class ChessNode extends MiniMaxNode<State, ChessNode> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChessNode.class);

	public ChessNode(Node<State> parent, State t) {
		super(parent, t);
		// TODO Auto-generated constructor stub
	}

	public ChessNode(Node<State> parent, State t, int depth) {
		super(parent, t, depth);
		// TODO Auto-generated constructor stub
	}

	public ChessNode(State t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Will generate the set of all possible moves ot be evalated given the
	 * board for the opponent
	 */
	public Adjacency<State, ChessNode> generateAdjacency() {

		Player opponent = getValue().getPlayer().getOpponent();

		Adjacency<State, ChessNode> adjacency = new Adjacency<State, ChessNode>(
				this);

		Set<ChessNode> nodes = new HashSet<ChessNode>();

		List<Piece> pieces = getValue().getActivePieces(opponent);

		for (Piece p : pieces) {

			for (BoardLocation loc : p.factoryMoveEvaluator().evaluate(
					getValue().getBoard(), p)) {
				Board newBoard = new Board(getValue().getBoard());

				newBoard.place(p, loc);

				ChessNode node = new ChessNode(this, new State(newBoard,
						opponent, !getValue().isMax(), getDepth() - 1));

				nodes.add(node);

				node.setParent(this);
			}
		}

		adjacency.setNeighbors(nodes);

		LOGGER.info("Generated {} possible moves for {}", nodes.size(),
				opponent);

		return adjacency;
	}

	/**
	 * the heuristic value for the board as a whole will be calculated as the
	 * piece that has the closest direct line value to the opponents King
	 */
	@Override
	public double calculateH() {

		double minValue = Double.MAX_VALUE;

		List<Piece> pieces = getValue().getActivePieces();

		BoardLocation king = getValue().getPlayer() == Player.Black ? getValue()
				.getBoard().getWhiteKingPlacement().getBoardLocation()
				: getValue().getBoard().getBlackKingPlacement()
						.getBoardLocation();

		for (Piece p : pieces) {
			double squared = Math.pow(
					Math.abs(king.getRank() - p.getLocation().getRank()), 2)
					+ Math.pow(Math.abs(king.getFile()
							- p.getLocation().getFile()), 2);

			double z = Math.sqrt(squared);

			if (z < minValue)
				minValue = z;
		}

		return minValue;
	}

	@Override
	public boolean containsWin() {
		// TODO Auto-generated method stub
		return false;
	}
}
