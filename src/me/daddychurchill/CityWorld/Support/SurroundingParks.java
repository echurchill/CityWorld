package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;

public class SurroundingParks {

	public boolean[][] neighbors;
	
	public SurroundingParks(PlatMap platmap, int platX, int platZ) {
		super();
		neighbors = new boolean[3][3];
		
		// get a list of qualified neighbors
		PlatLot platlot = platmap.getLot(platX, platZ);
		PlatLot[][] neighborChunks = platlot.getNeighborPlatLots(platmap, platX, platZ, true);
		for (int x = 0; x < 3; x++)
			for (int z = 0; z < 3; z++)
				neighbors[x][z] = neighborChunks[x][z] != null;
	}
	
	public int getNeighborCount() {
		int result = 0;
		if (neighbors[0][1])
			result++;
		if (neighbors[1][0])
			result++;
		if (neighbors[2][1])
			result++;
		if (neighbors[1][2])
			result++;
		return result;
	}
	
	public boolean toCenter() {
		return true;
	}
	
	public boolean toEast() {
		return neighbors[2][1];
	}

	public boolean toNorth() {
		return neighbors[1][0];
	}
	
	public boolean toSouth() {
		return neighbors[1][2];
	}

	public boolean toWest() {
		return neighbors[0][1];
	}

	public boolean toNorthEast() {
		return neighbors[2][0] && toEast() && toNorth();
	}

	public boolean toSouthEast() {
		return neighbors[2][2] && toEast() && toSouth();
	}
	
	public boolean toNorthWest() {
		return neighbors[0][0] && toWest() && toNorth();
	}

	public boolean toSouthWest() {
		return neighbors[0][2] && toWest() && toSouth();
	}
}
