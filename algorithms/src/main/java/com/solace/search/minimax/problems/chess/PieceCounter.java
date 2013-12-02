package com.solace.search.minimax.problems.chess;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.solace.search.minimax.problems.chess.ChessConstants.*;

/**
 * A piece counter that is initialized by the board.
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public class PieceCounter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PieceCounter.class);

	private Board board;
	
	private Map<Player, Map<GamePiece, AtomicInteger>> pieces = new HashMap<Player, Map<GamePiece, AtomicInteger>>();

	public PieceCounter(Board board) {
		this.board = board;

		initializeCountMap(board);
	}

	@SuppressWarnings("serial")
	private void initializeCountMap(Board board) {
		for (int r = 0; r < RANK_COUNT; r++) {
			for (int f = 0; f < FILE_COUNT; f++) {
				final GamePiece piece = board.getPieces()[r][f].getPiece();
				final Player player = board.getPieces()[r][f].getPlayer();

				if (piece == GamePiece.Empty)
					continue;

				if (pieces.containsKey(player)) {
					if (pieces.get(player).containsKey(piece)) {
						int val = pieces.get(player).get(piece).incrementAndGet();
						LOGGER.info(
								"PieceCounter for {} {} incremented to {}",
								player, piece, val);
					} else {
						pieces.get(player).put(piece, new AtomicInteger(1));
						LOGGER.info(
								"PieceCounter for {} {} initialized with a counter of 1",
								player, piece);
					}
				} else {
					pieces.put(player, new HashMap<GamePiece, AtomicInteger>());
				}
			}
		}
	}

	public void incr(Player player, Piece p) {
		pieces.get(player).get(p).decrementAndGet();
	}

	public void decr(Player player, Piece p) {
		pieces.get(player).get(p).decrementAndGet();
	}
}
