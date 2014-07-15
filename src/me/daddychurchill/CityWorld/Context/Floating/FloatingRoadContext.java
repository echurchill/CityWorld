package me.daddychurchill.CityWorld.Context.Floating;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.RoadContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Floating.FloatingRoadLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloatingRoadContext extends RoadContext {

	public FloatingRoadContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot createRoadLot(WorldGenerator generator, PlatMap platmap, int x, int z, boolean roundaboutPart, PlatLot oldLot) {
		return new FloatingRoadLot(platmap, platmap.originX + x, platmap.originZ + z, generator.connectedKeyForPavedRoads, roundaboutPart);
	}

}
