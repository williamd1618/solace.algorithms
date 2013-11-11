package com.solace.search.astar.problems.cannibals;

/**
 * The context of the problem!
 * 
 * @author <a href="mailto:daniel.williams@gmail.com">Daniel Williams</a>
 * 
 */
public final class State {

	public State(BoatOrientation boatOrientation, int leftMissCount,
			int leftCannCount, int rightMissCount, int rightCannCount) {
		super();

		assert leftMissCount + rightMissCount == leftCannCount + rightCannCount;

		this.boatOrientation = boatOrientation;
		this.leftMissCount = leftMissCount;
		this.rightMissCount = rightMissCount;
		this.leftCannCount = leftCannCount;
		this.rightCannCount = rightCannCount;
	}

	private int numMoves = 0;

	public int getNumMoves() {
		return numMoves;
	}

	private BoatOrientation boatOrientation;

	private int leftMissCount, rightMissCount, leftCannCount, rightCannCount;

	public BoatOrientation getBoatOrientation() {
		return boatOrientation;
	}

	public void setBoatOrientation(BoatOrientation boatOrientation) {
		this.boatOrientation = boatOrientation;
	}

	public int getLeftMissCount() {
		return leftMissCount;
	}

	public void setLeftMissCount(int leftMissCount) {
		this.leftMissCount = leftMissCount;
	}

	public int getRightMissCount() {
		return rightMissCount;
	}

	public void setRightMissCount(int rightMissCount) {
		this.rightMissCount = rightMissCount;
	}

	public int getLeftCannCount() {
		return leftCannCount;
	}

	public void setLeftCannCount(int leftCannCount) {
		this.leftCannCount = leftCannCount;
	}

	public int getRightCannCount() {
		return rightCannCount;
	}

	public void setRightCannCount(int rightCannCount) {
		this.rightCannCount = rightCannCount;
	}

	public void incrMove() {
		this.numMoves++;
	}

	@Override
	public String toString() {
		return String.format("%s", stateSpace());
	}

	public String stateSpace() {
		return String.format("(%d, %d, %s, %d, %d)", getLeftMissCount(),
				getLeftCannCount(), getBoatOrientation().name(),
				getRightMissCount(), getRightCannCount());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * Takes advantage of a well formed {@link #toString()} method
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof State))
			return false;
		else
			return ((State) obj).toString().equals(this.toString());
	}
}