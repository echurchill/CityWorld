package me.daddychurchill.CityWorld.Context.Maze;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Maze.MazeCoveredLot;
import me.daddychurchill.CityWorld.Plats.Maze.MazeInvisibleWalledLot;
import me.daddychurchill.CityWorld.Plats.Maze.MazeLavaWalledLot;
import me.daddychurchill.CityWorld.Plats.Maze.MazeWaterWalledLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class MazeNexusContext extends MazeConstructContext {

	public MazeNexusContext(CityWorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot generateSpecialOneLot(PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new MazeLavaWalledLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected PlatLot generateSpecialTooLot(PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new MazeWaterWalledLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected PlatLot generateNormalLot(PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		if (odds.flipCoin())
			return new MazeCoveredLot(platmap, chunkX, chunkZ);
		else
			return new MazeInvisibleWalledLot(platmap, chunkX, chunkZ);
	}

}
