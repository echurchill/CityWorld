package me.daddychurchill.CityWorld.Context.SnowDunes;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.RoadContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.SnowDunes.SnowDunesRoadLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SnowDunesRoadContext extends RoadContext {

	public SnowDunesRoadContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public PlatLot createRoadLot(WorldGenerator generator, PlatMap platmap,
			int x, int z, boolean roundaboutPart) {
		return new SnowDunesRoadLot(platmap, platmap.originX + x, platmap.originZ + z, generator.connectedKeyForPavedRoads, roundaboutPart);
	}

}
