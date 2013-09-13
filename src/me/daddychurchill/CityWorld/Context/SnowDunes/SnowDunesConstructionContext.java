package me.daddychurchill.CityWorld.Context.SnowDunes;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ConstructionContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.SnowDunes.SnowDunesOfficeBuildingLot;
import me.daddychurchill.CityWorld.Plats.SnowDunes.SnowDunesParkLot;
import me.daddychurchill.CityWorld.Plats.SnowDunes.SnowDunesUnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SnowDunesConstructionContext extends ConstructionContext {

	public SnowDunesConstructionContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot getPark(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SnowDunesParkLot(platmap, chunkX, chunkZ, generator.connectedKeyForParks);
	}
	
	@Override
	protected PlatLot getUnfinishedBuilding(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SnowDunesUnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected PlatLot getBuilding(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SnowDunesOfficeBuildingLot(platmap, chunkX, chunkZ);
	}
}
