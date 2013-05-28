package me.daddychurchill.CityWorld.Context.SandDunes;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.LowriseContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.SandDunes.SandDunesOfficeBuildingLot;
import me.daddychurchill.CityWorld.Plats.SandDunes.SandDunesParkLot;
import me.daddychurchill.CityWorld.Plats.SandDunes.SandDunesUnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SandDunesLowriseContext extends LowriseContext {

	public SandDunesLowriseContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot getPark(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SandDunesParkLot(platmap, chunkX, chunkZ, generator.connectedKeyForParks);
	}
	
	@Override
	protected PlatLot getUnfinishedBuilding(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SandDunesUnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected PlatLot getBuilding(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SandDunesOfficeBuildingLot(platmap, chunkX, chunkZ);
	}
}
