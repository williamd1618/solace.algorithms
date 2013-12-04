package com.solace.search.minimax.problems.chess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solace.algorithms.search.minimax.MiniMaxNode;
import com.solace.graph.Adjacency;
import com.solace.graph.Node;
import com.solace.search.minimax.problems.chess.moves.KingMove;
import com.solace.search.minimax.problems.chess.moves.Move;
import com.solace.search.minimax.problems.chess.moves.PawnMove;
import com.solace.search.minimax.problems.chess.moves.QueenMove;

public class ChessNode extends MiniMaxNode<State, ChessNode> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChessNode.class);

	boolean checkMateFound = false;

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
				
				if ( loc == null )
					continue;
				
				Board newBoard = new Board(getValue().getBoard());

				Move m = factoryMove(p, loc);

				LOGGER.debug("Instantiated a {} to move {} from {} to {}", m
						.getClass().getSimpleName(), p.getPiece(), p
						.getLocation(), loc);

				checkMateFound = m.execute(newBoard);
				
				LOGGER.debug("resulting board:\n{}", newBoard);

				// newBoard.place(p, loc);

				ChessNode node = new ChessNode(this, new State(newBoard,
						opponent, !getValue().isMax(), getDepth() - 1));

				if (checkMateFound) {
					LOGGER.info(
							"A check mate has been identified for player {} at {} .. clearing adjacency list",
							p.getPlayer(), loc);
					nodes.clear();
				}

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
	 * <p>
	 * Evaluates the positioning of the {@link Board} as a whole to give the
	 * highest value, but the actual values is based upon which piece is the
	 * closest to the opponent's {@link GamePiece#King}
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

			// should possibly multiple by -1 to make the lowest value, the
			// highest
			double z = Math.sqrt(squared);

			if (z < minValue)
				minValue = z;
		}

		// so that the closest pieces of all possible options
		// has the highest H value
		return -1 * minValue;
	}

	/**
	 * this implementation will be a bit redundant considering a similar
	 * strategy will be employed in the {@link #generateAdjacency()} invocation.
	 * In this structure what we are going to do is evaluated the current state
	 * of the board to see if we can capture the opponents
	 * {@link GamePiece#King}
	 */
	@Override
	public boolean containsWin() {
		// we want to get the active pieces for the current player who is
		// about to move to see if it is a win
		List<Piece> pieces = getValue().getActivePieces();

		boolean checkMateFound = false;

		for (Piece p : pieces) {
			for (BoardLocation loc : p.factoryMoveEvaluator().evaluate(
					getValue().getBoard(), p)) {
				Board newBoard = new Board(getValue().getBoard());

				Move m = factoryMove(p, loc);

				LOGGER.debug(
						"Evaluating current state of the board: move {} from {} to {}",
						p.getPiece(),
						p.getLocation(), loc);

				m.execute(newBoard);
				
				checkMateFound = m.isCheckmate();

				if (checkMateFound)
					break;
			}
		}

		return checkMateFound;
	}

	private Move factoryMove(Piece piece, BoardLocation loc) {
		if (piece.getPiece() == GamePiece.Pawn)
			return new PawnMove(piece,
					new Placement(piece, piece.getLocation()), new Placement(
							piece, loc));
		else if (piece.getPiece() == GamePiece.King)
			return new KingMove(piece,
					new Placement(piece, piece.getLocation()), new Placement(
							piece, loc));
		else if (piece.getPiece() == GamePiece.Queen)
			return new QueenMove(piece, new Placement(piece,
					piece.getLocation()), new Placement(piece, loc));
		else
			return null;
	}
}
