package com.solace.search.astar.problems.cannibals;

import static com.solace.search.astar.problems.cannibals.BoatOrientation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	private static State initialState = new State(LeftBank, 3, 3, 0, 0);
	
	private static State goalState = new State(RightBank, 0, 0, 3, 3);
	
	public static void main(String... args) {
		LOGGER.info("Starting A* search from Missionary and Cannibals...");
		
		State initial = new State(BoatOrientation.LeftBank, 3, 3, 0, 0);
		
		State goal = new State(BoatOrientation.RightBank, 0, 0, 3, 3);
		
		MandCAStarSearch search = new MandCAStarSearch(new MCNode(initial), new MCNode(goal));
		
		MCNode result = search.search();
		
		List<String> steps = new ArrayList<String>();
		
		if ( result != null ) {
			Stack<MCNode> path = search.buildPathToGoal(result);
			
			LOGGER.info("Path to result.");
			LOGGER.info("State space expressed as (left missionaries, left cannibals, bank, right missionaries, right cannibals)");
			
			while(!path.empty()) {				
				LOGGER.info(path.pop().getValue().toString());				
			}			
		} else {
			LOGGER.info("No path discovered.");
		}
	}
}
