package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeNatureContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeRoadContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class ShapeProvider_Maze extends ShapeProvider_Normal {

	public ShapeProvider_Maze(CityWorldGenerator generator, Odds odds) {
		super(generator, odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void allocateContexts(CityWorldGenerator generator) {
		if (!contextInitialized) {
			natureContext = new MazeNatureContext(generator);
			roadContext = new MazeRoadContext(generator);

//			highriseContext = new FloodedHighriseContext(generator);
//			constructionContext = new FloodedConstructionContext(generator);
//			midriseContext = new FloodedMidriseContext(generator);
//			municipalContext = midriseContext;
//			lowriseContext = new FloodedLowriseContext(generator);
//			industrialContext = lowriseContext;
//			parkContext = new MazeParkContext(generator);
//			farmContext = new MazeFarmContext(generator);
			
			contextInitialized = true;
		}
	}
	
	@Override
	public DataContext getContext(PlatMap platmap) {
			return natureContext;
	}
}
