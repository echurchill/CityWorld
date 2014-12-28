package me.daddychurchill.CityWorld.Context.SnowDunes;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.LowriseContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.SnowDunes.SnowDunesOfficeBuildingLot;
import me.daddychurchill.CityWorld.Plats.SnowDunes.SnowDunesParkLot;
import me.daddychurchill.CityWorld.Plats.SnowDunes.SnowDunesUnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SnowDunesLowriseContext extends LowriseContext {

	public SnowDunesLowriseContext(CityWorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot getPark(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ, int waterDepth) {
		return new SnowDunesParkLot(platmap, chunkX, chunkZ, generator.connectedKeyForParks, waterDepth);
	}
	
	@Override
	protected PlatLot getUnfinishedBuilding(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SnowDunesUnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected PlatLot getBuilding(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SnowDunesOfficeBuildingLot(platmap, chunkX, chunkZ);
	}
}
