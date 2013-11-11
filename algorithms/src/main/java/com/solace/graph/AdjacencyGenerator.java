package com.solace.graph;

public interface AdjacencyGenerator<ValueType, NodeType extends Node<ValueType>> {
	
	public abstract Adjacency<ValueType, NodeType> generateAdjacency();

}
