package com.solace.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * An abstract class that represents a graph as a set of {@link Node}s and
 * {@link Edge}s
 * <p>
 * This class is abstracted to allow for the concept of a directed graph, or an
 * undirected graph.
 * <p>
 * A directed graph only allows for edge traversal between {@link Node}s to be
 * unidirectional, not bidirectional
 * <p>
 * A good graph implementation and higher ordinal traversal algorithm could be used for something
 * such as an abstract FSA.
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 * @param <T>
 * @param <NodeType>
 * @param <EdgeType>
 */
public abstract class Graph<T, NodeType extends Node<T>, EdgeType extends Edge<T, NodeType>> {

	/**
	 * Hashset leveraged here to guarantee uniqueness
	 */
	private Set<NodeType> nodes = new HashSet<NodeType>();

	private Set<EdgeType> edges = new HashSet<EdgeType>();

}
