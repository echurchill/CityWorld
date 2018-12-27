package me.daddychurchill.CityWorld.Support;

public abstract class Surroundings {

	public abstract boolean toCenter();

	public abstract boolean toEast();

	public abstract boolean toNorth();

	public abstract boolean toSouth();

	public abstract boolean toWest();

	public abstract boolean toNorthEast();

	public abstract boolean toSouthEast();

	public abstract boolean toNorthWest();

	public abstract boolean toSouthWest();

	public int getNeighborCount() {
		int result = 0;
		if (toWest())
			result++;
		if (toEast())
			result++;
		if (toNorth())
			result++;
		if (toSouth())
			result++;
		return result;
	}

	public int getCompleteNeighborCount() {
		int result = getNeighborCount();
		if (toNorthWest())
			result++;
		if (toNorthEast())
			result++;
		if (toSouthWest())
			result++;
		if (toSouthEast())
			result++;
		return result;
	}

	public boolean adjacentNeighbors() {
		return toEast() || toWest() || toNorth() || toSouth();
	}

}
