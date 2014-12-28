package me.daddychurchill.CityWorld.Context.SandDunes;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.LowriseContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.SandDunes.SandDunesOfficeBuildingLot;
import me.daddychurchill.CityWorld.Plats.SandDunes.SandDunesParkLot;
import me.daddychurchill.CityWorld.Plats.SandDunes.SandDunesUnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SandDunesLowriseContext extends LowriseContext {

	public SandDunesLowriseContext(CityWorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot getPark(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ, int waterDepth) {
		return new SandDunesParkLot(platmap, chunkX, chunkZ, generator.connectedKeyForParks, waterDepth);
	}
	
	@Override
	protected PlatLot getUnfinishedBuilding(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SandDunesUnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected PlatLot getBuilding(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SandDunesOfficeBuildingLot(platmap, chunkX, chunkZ);
	}
}
