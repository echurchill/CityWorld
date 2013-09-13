package me.daddychurchill.CityWorld.Context.Flooded;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ConstructionContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Flooded.FloodedOfficeBuildingLot;
import me.daddychurchill.CityWorld.Plats.Flooded.FloodedParkLot;
import me.daddychurchill.CityWorld.Plats.Flooded.FloodedUnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedConstructionContext extends ConstructionContext {

	public FloodedConstructionContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot getPark(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new FloodedParkLot(platmap, chunkX, chunkZ, generator.connectedKeyForParks);
	}
	
	@Override
	protected PlatLot getUnfinishedBuilding(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new FloodedUnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected PlatLot getBuilding(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new FloodedOfficeBuildingLot(platmap, chunkX, chunkZ);
	}
}
