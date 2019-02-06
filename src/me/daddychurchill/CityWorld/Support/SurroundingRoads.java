package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.RoadLot;

public final class SurroundingRoads extends Surroundings {

	private final boolean[][] roads;

	public SurroundingRoads(PlatMap platmap, int platX, int platZ) {
		super();
		roads = new boolean[3][3];

		// calculate neighbors
		updateNeighbors(platmap, platX, platZ);
	}

	private void updateNeighbors(PlatMap platmap, int platX, int platZ) {
		PlatLot platlot = platmap.getLot(platX, platZ);

		// get a list of qualified neighbors
		PlatLot[][] neighborChunks = platlot.getNeighborPlatLots(platmap, platX, platZ, false);
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				PlatLot neighbor = neighborChunks[x][z];

				// beyond the edge
				if (neighbor == null) {
					roads[x][z] = platX == RoadLot.PlatMapRoadInset - 1 || platZ == RoadLot.PlatMapRoadInset - 1
							|| platX == PlatMap.Width - RoadLot.PlatMapRoadInset
							|| platZ == PlatMap.Width - RoadLot.PlatMapRoadInset;

					// is connected in some way?
				} else {
					roads[x][z] = platlot.isConnected(neighbor);
				}
			}
		}
	}

	// adjacent roads?
	public boolean adjacentRoads() {
		return adjacentNeighbors();
	}

	@Override
	public boolean toCenter() {
		return true;
	}

	@Override
	public boolean toNorth() {
		return roads[1][0];
	}

	@Override
	public boolean toSouth() {
		return roads[1][2];
	}

	@Override
	public boolean toWest() {
		return roads[0][1];
	}

	@Override
	public boolean toEast() {
		return roads[2][1];
	}

	@Override
	public boolean toNorthWest() {
		return true;
	}

	@Override
	public boolean toNorthEast() {
		return true;
	}

	@Override
	public boolean toSouthWest() {
		return true;
	}

	@Override
	public boolean toSouthEast() {
		return true;
	}
}
