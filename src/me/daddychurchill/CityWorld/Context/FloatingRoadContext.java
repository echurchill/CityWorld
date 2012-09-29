package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.FloatingRoadLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;

public class FloatingRoadContext extends RoadContext {

	public FloatingRoadContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot createRoadLot(WorldGenerator generator, PlatMap platmap, int x, int z, boolean roundaboutPart) {
		return new FloatingRoadLot(platmap, platmap.originX + x, platmap.originZ + z, generator.connectedKeyForPavedRoads, roundaboutPart);
	}

}
