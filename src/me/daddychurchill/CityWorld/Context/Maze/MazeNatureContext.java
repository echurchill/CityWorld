package me.daddychurchill.CityWorld.Context.Maze;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.NatureContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Maze.MazeNatureLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class MazeNatureContext extends NatureContext {

	public MazeNatureContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot createNaturalLot(WorldGenerator generator, PlatMap platmap, int x, int z) {
		return new MazeNatureLot(platmap, platmap.originX + x, platmap.originZ + z);
	}
	
}
