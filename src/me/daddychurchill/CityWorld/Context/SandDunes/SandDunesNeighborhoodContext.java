package me.daddychurchill.CityWorld.Context.SandDunes;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.NeighborhoodContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.SandDunes.SandDunesHouseLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SandDunesNeighborhoodContext extends NeighborhoodContext {

	public SandDunesNeighborhoodContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot getHouseLot(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new SandDunesHouseLot(platmap, chunkX, chunkZ);
	}
}
