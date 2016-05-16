package me.daddychurchill.CityWorld.Plats.Flooded;

import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedRoadLot extends RoadLot {

	public FloodedRoadLot(PlatMap platmap, int chunkX, int chunkZ,
			long globalconnectionkey, boolean roundaboutPart) {
		super(platmap, chunkX, chunkZ, globalconnectionkey, roundaboutPart);
		
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FloodedRoadLot(platmap, chunkX, chunkZ, connectedkey, roundaboutRoad);
	}
	
}
