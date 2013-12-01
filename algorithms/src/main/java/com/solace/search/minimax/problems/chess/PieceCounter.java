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

	private void initializeCountMap(Board board) {
		for (int r = 0; r < RANK_COUNT; r++) {
			for (int f = 0; f < FILE_COUNT; f++) {
				final GamePiece piece = board.getPieces()[r][f].getPiece();
				final Player player = board.getPieces()[r][f].getPlayer();
				
				if ( piece == GamePiece.Empty)
					continue;

				if (pieces.containsKey(player)) {
					if (pieces.get(player).containsKey(piece)) {
						pieces.get(player).get(piece).incrementAndGet();
					} else {
						LOGGER.warn(
								"PieceCounter for {} {} does not exist, should not reach condition.",
								player, piece);
					}
				} else {
					pieces.put(player, new HashMap<GamePiece, AtomicInteger>() {
						{
							put(piece, new AtomicInteger(1));
						}
					});
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
