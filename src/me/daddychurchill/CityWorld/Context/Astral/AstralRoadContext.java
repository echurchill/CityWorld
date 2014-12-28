package me.daddychurchill.CityWorld.Context.Astral;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.RoadContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralRoadLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class AstralRoadContext extends RoadContext {

	public AstralRoadContext(CityWorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot createRoadLot(CityWorldGenerator generator, PlatMap platmap, int x, int z, boolean roundaboutPart, PlatLot oldLot) {
		return new AstralRoadLot(platmap, platmap.originX + x, platmap.originZ + z, generator.connectedKeyForPavedRoads, roundaboutPart);
	}

}
