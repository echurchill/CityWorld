package me.daddychurchill.CityWorld.Plats.Flooded;

import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.ParkLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedParkLot extends ParkLot {

	public FloodedParkLot(PlatMap platmap, int chunkX, int chunkZ,
			long globalconnectionkey, int waterDepth) {
		super(platmap, chunkX, chunkZ, globalconnectionkey, waterDepth);

	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FloodedParkLot(platmap, chunkX, chunkZ, connectedkey, waterDepth);
	}
	
}
