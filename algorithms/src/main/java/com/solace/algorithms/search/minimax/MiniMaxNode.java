package com.solace.algorithms.search.minimax;

import com.solace.graph.Adjacency;
import com.solace.graph.AdjacencyGenerator;
import com.solace.graph.Node;
import com.solace.search.minimax.problems.tictactoe.Board.Piece;

public abstract class MiniMaxNode<ValueType, NodeType extends Node<ValueType>> extends
		Node<ValueType> implements AdjacencyGenerator<ValueType, NodeType>,
		Comparable<MiniMaxNode<ValueType, NodeType>> {
	
	boolean isMax = false;
	
	double alpha = 0.0d;

	public MiniMaxNode(ValueType t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	public MiniMaxNode(Node<ValueType> parent, ValueType t, int depth) {
		super(parent, t, depth);
		// TODO Auto-generated constructor stub
	}

	public MiniMaxNode(Node<ValueType> parent, ValueType t) {
		super(parent, t);
		// TODO Auto-generated constructor stub
	}

	public int compareTo(MiniMaxNode<ValueType, NodeType> o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public abstract double calculateH();

	@Override
	public double calculateCost() {
		return calculateH();
	}
		
	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	
	public abstract boolean containsWin();
}
