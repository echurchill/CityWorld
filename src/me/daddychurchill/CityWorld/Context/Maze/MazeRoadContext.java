package me.daddychurchill.CityWorld.Context.Maze;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.RoadContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class MazeRoadContext extends RoadContext {

	public MazeRoadContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot createRoadLot(WorldGenerator generator, PlatMap platmap, int x, int z, boolean roundaboutPart, PlatLot oldLot) {
		return null;
	}

	@Override
	public PlatLot createRoundaboutStatueLot(WorldGenerator generator, PlatMap platmap, int x, int z) {
		return null;
	}
}
