package me.daddychurchill.CityWorld.Context.Flooded;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.ParkContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Flooded.FloodedOfficeBuildingLot;
import me.daddychurchill.CityWorld.Plats.Flooded.FloodedParkLot;
import me.daddychurchill.CityWorld.Plats.Flooded.FloodedUnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedParkContext extends ParkContext {

	public FloodedParkContext(CityWorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot getPark(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ, int waterDepth) {
		return new FloodedParkLot(platmap, chunkX, chunkZ, generator.connectedKeyForParks, waterDepth);
	}
	
	@Override
	protected PlatLot getUnfinishedBuilding(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new FloodedUnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected PlatLot getBuilding(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new FloodedOfficeBuildingLot(platmap, chunkX, chunkZ);
	}
}
