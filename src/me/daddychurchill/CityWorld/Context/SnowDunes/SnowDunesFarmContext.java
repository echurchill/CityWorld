package me.daddychurchill.CityWorld.Context.SnowDunes;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.FarmContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.SnowDunes.SnowDunesFarmLot;
import me.daddychurchill.CityWorld.Plats.SnowDunes.SnowDunesHouseLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SnowDunesFarmContext extends FarmContext {

	public SnowDunesFarmContext(CityWorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot getFarmLot(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SnowDunesFarmLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected PlatLot getHouseLot(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SnowDunesHouseLot(platmap, chunkX, chunkZ);
	}
}
