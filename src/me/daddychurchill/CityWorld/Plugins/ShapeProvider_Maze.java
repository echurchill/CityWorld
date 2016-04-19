package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeCampgroundContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeCastleContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeFarmContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeMineContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeNatureContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeNeighborhoodContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeNexusContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeParkContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeRoadContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeTownContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeWoodworksContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class ShapeProvider_Maze extends ShapeProvider_Normal {

	public SimplexNoiseGenerator ecoShape;
	private MazeNexusContext nexusContext;
	
	public ShapeProvider_Maze(CityWorldGenerator generator, Odds odds) {
		super(generator, odds);
		
		long seed = generator.getWorldSeed();
		
		ecoShape = new SimplexNoiseGenerator(seed + 5);
	}

	@Override
	public void allocateContexts(CityWorldGenerator generator) {
		if (!contextInitialized) {
			natureContext = new MazeNatureContext(generator);
			roadContext = new MazeRoadContext(generator);

			nexusContext = new MazeNexusContext(generator);
			
			// I am just reusing a bunch of instance variables here
			highriseContext = new MazeTownContext(generator);
			constructionContext = new MazeWoodworksContext(generator);
			midriseContext = new MazeNeighborhoodContext(generator); 
			municipalContext = new MazeCastleContext(generator);
			lowriseContext = new MazeCampgroundContext(generator);
			industrialContext = new MazeMineContext(generator);
			parkContext = new MazeParkContext(generator);
			farmContext = new MazeFarmContext(generator);
			outlandContext = farmContext;
			
			contextInitialized = true;
		}
	}
	
	@Override
	public DataContext getContext(int originX, int originZ) {
//		CityWorld.log.info("X, Z = " + originX + ", " + originZ);
		if (originX == 0 && originZ == 0)
			return nexusContext;
		else {
			double rawValue = (Math.max(-0.9999, Math.min(0.9999, ecoShape.noise(originX, originZ) * 1.375)) + 1.0) / 2.0;
			switch (NoiseGenerator.floor(rawValue * 9)) { // the constant here should ALWAYS be one more than the biggest case statement constant!
			default: // always leave default at zero
			case 0:
				return natureContext;
			case 1:
				return highriseContext; // Town
			case 2:
				return constructionContext; // Treehouse
			case 3:
				return midriseContext; // Neighborhood
			case 4:
				return municipalContext; // Castle & Nature
			case 5:
				return lowriseContext; // Campground & Nature
			case 6:
				return industrialContext; // Mine
			case 7:
				return parkContext; // Park
			case 8:
				return farmContext; // Farm
			}
		}
	}
	
	@Override
	public DataContext getContext(PlatMap platmap) {
		return getContext(platmap.originX, platmap.originZ);
	}

}
