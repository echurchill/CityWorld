package me.daddychurchill.CityWorld.Plats.Flooded;

import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.UnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedUnfinishedBuildingLot extends UnfinishedBuildingLot {
	
	public FloodedUnfinishedBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FloodedUnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}
	
}
