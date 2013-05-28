package me.daddychurchill.CityWorld.Context.Flooded;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.FarmContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Flooded.FloodedFarmLot;
import me.daddychurchill.CityWorld.Plats.Flooded.FloodedHouseLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedFarmContext extends FarmContext {

	public FloodedFarmContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot getBackfillLot(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new FloodedFarmLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected PlatLot getHouseLot(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new FloodedHouseLot(platmap, chunkX, chunkZ);
	}
}
