package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedHighriseContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedLowriseContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedMidriseContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedNatureContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedRoadContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class ShapeProvider_Flooded extends ShapeProvider_Normal {

	public ShapeProvider_Flooded(WorldGenerator generator, Odds odds) {
		super(generator, odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void allocateContexts(WorldGenerator generator) {
		if (!contextInitialized) {
			natureContext = new FloodedNatureContext(generator);
			roadContext = new FloodedRoadContext(generator);
			
//			parkContext = new FloodedParkContext(generator);
			highriseContext = new FloodedHighriseContext(generator);
//			constructionContext = new FloodedConstructionContext(generator);
			midriseContext = new FloodedMidriseContext(generator);
			lowriseContext = new FloodedLowriseContext(generator);
//			neighborhoodContext = new FloodedNeighborhoodContext(generator);
//			farmContext = new FloodedFarmContext(generator);
			
			contextInitialized = true;
		}
	}
	
	protected DataContext getContext(PlatMap platmap) {
		
		// how natural is this platmap?
		float nature = platmap.getNaturePercent();
		if (nature == 0.0)
			return highriseContext;
//		else if (nature < 0.15)
//			return constructionContext;
		else if (nature < 0.25)
			return midriseContext;
		else if (nature < 0.65)
			return lowriseContext;
//		else if (nature < 0.75)
//			return neighborhoodContext;
//		else if (nature < 0.90 && platmap.generator.settings.includeFarms)
//			return farmContext;
//		else if (nature < 1.0)
//			return neighborhoodContext;
		
		// otherwise just keep what we have
		else
			return natureContext;
	}

	@Override
	public String getCollectionName() {
		return "Flooded";
	}

}
