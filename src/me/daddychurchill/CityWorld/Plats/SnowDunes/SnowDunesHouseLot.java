package me.daddychurchill.CityWorld.Plats.SnowDunes;

import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Rural.HouseLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SnowDunesHouseLot extends HouseLot {

	public SnowDunesHouseLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}


	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new SnowDunesHouseLot(platmap, chunkX, chunkZ);
	}
}
