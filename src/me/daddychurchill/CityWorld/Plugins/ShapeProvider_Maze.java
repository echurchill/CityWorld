package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeFarmContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeNatureContext;
import me.daddychurchill.CityWorld.Context.Maze.MazeRoadContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class ShapeProvider_Maze extends ShapeProvider_Normal {

	public ShapeProvider_Maze(WorldGenerator generator, Odds odds) {
		super(generator, odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void allocateContexts(WorldGenerator generator) {
		if (!contextInitialized) {
			natureContext = new MazeNatureContext(generator);
			roadContext = new MazeRoadContext(generator);

//			parkContext = new FloodedParkContext(generator);
//			highriseContext = new FloodedHighriseContext(generator);
//			constructionContext = new FloodedConstructionContext(generator);
//			midriseContext = new FloodedMidriseContext(generator);
//			municipalContext = midriseContext;
//			lowriseContext = new FloodedLowriseContext(generator);
//			industrialContext = lowriseContext;
//			neighborhoodContext = new MazeNeighborhoodContext(generator);
//			farmContext = new MazeFarmContext(generator);
			
			contextInitialized = true;
		}
	}
	
	@Override
	public DataContext getContext(PlatMap platmap) {
		platmap.generator.reportMessage("getContext");
//		// how natural is this platmap?
//		float nature = platmap.getNaturePercent();
//		if (nature <= 0.25) {
//			switch (platmap.getOddsGenerator().getRandomInt(3)) {
////			case 1:
////				return industrialContext;
////			case 2:
////				return neighborhoodContext;
//			default:
//				return farmContext;
//			}
//		}
//		
//		// otherwise just keep what we have
//		else
			return natureContext;
	}
}
