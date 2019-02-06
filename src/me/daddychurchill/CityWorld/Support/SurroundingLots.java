package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.Plats.PlatLot;

public final class SurroundingLots extends Surroundings {

	private final boolean[][] neighbors;

	public SurroundingLots(PlatMap platmap, int platX, int platZ) {
		neighbors = new boolean[3][3];

		// get a list of qualified neighbors
		PlatLot platlot = platmap.getLot(platX, platZ);
		PlatLot[][] neighborChunks = platlot.getNeighborPlatLots(platmap, platX, platZ, true);
		for (int x = 0; x < 3; x++)
			for (int z = 0; z < 3; z++)
				neighbors[x][z] = neighborChunks[x][z] != null;
	}

	@Override
	public boolean toCenter() {
		return true;
	}

	@Override
	public boolean toEast() {
		return neighbors[2][1];
	}

	@Override
	public boolean toNorth() {
		return neighbors[1][0];
	}

	@Override
	public boolean toSouth() {
		return neighbors[1][2];
	}

	@Override
	public boolean toWest() {
		return neighbors[0][1];
	}

	@Override
	public boolean toNorthEast() {
		return neighbors[2][0] && toEast() && toNorth();
	}

	@Override
	public boolean toSouthEast() {
		return neighbors[2][2] && toEast() && toSouth();
	}

	@Override
	public boolean toNorthWest() {
		return neighbors[0][0] && toWest() && toNorth();
	}

	@Override
	public boolean toSouthWest() {
		return neighbors[0][2] && toWest() && toSouth();
	}
}
