package me.daddychurchill.CityWorld.Support;

import java.util.logging.Logger;

import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatRoad;

public class SurroundingRoads {

	// debugging
	protected static Logger log = Logger.getLogger("Minecraft");

	private boolean[][] roads;

	public SurroundingRoads() {
		super();
		roads = new boolean[3][3];
	}
	
	public SurroundingRoads(PlatMap platmap, int platX, int platZ) {
		super();
		roads = new boolean[3][3];
		
		// calculate neighbors
		updateNeighbors(platmap, platX, platZ);
	}
	
	protected void updateNeighbors(PlatMap platmap, int platX, int platZ) {
		PlatLot platlot = platmap.platLots[platX][platZ];	
		
		// get a list of qualified neighbors
		PlatLot[][] neighborChunks = platlot.getNeighborPlatLots(platmap, platX, platZ, false);
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				PlatLot neighbor = neighborChunks[x][z];

				// beyond the edge
				if (neighbor == null) {
					roads[x][z] = platX == PlatRoad.PlatMapRoadInset - 1 ||
							platZ == PlatRoad.PlatMapRoadInset - 1 || 
							platX == PlatMap.Width - PlatRoad.PlatMapRoadInset ||
							platZ == PlatMap.Width - PlatRoad.PlatMapRoadInset; 

					// is connected in some way?
				} else {
					roads[x][z] = platlot.isConnected(neighbor);
				}
			}
		}
	}
	
	// adjacent roads?
	public boolean adjacentRoads() {
		return toNorth() || toSouth() || toWest() || toEast();
	}

	public boolean toNorthWest() {
		return true;
	}

	public boolean toNorth() {
		return roads[2][1];
	}

	public boolean toNorthEast() {
		return true;
	}
	
	public boolean toWest() {
		return roads[1][0];
	}
	
	public boolean toCenter() {
		return true;
	}
	
	public boolean toEast() {
		return roads[1][2];
	}

	public boolean toSouthWest() {
		return true;
	}

	public boolean toSouth() {
		return roads[0][1];
	}

	public boolean toSouthEast() {
		return true;
	}
}
