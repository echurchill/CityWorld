package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ConstructionContext;
import me.daddychurchill.CityWorld.Context.FarmContext;
import me.daddychurchill.CityWorld.Context.HighriseContext;
import me.daddychurchill.CityWorld.Context.IndustrialContext;
import me.daddychurchill.CityWorld.Context.LowriseContext;
import me.daddychurchill.CityWorld.Context.MidriseContext;
import me.daddychurchill.CityWorld.Context.MunicipalContext;
import me.daddychurchill.CityWorld.Context.NatureContext;
import me.daddychurchill.CityWorld.Context.NeighborhoodContext;
import me.daddychurchill.CityWorld.Context.ParkContext;
import me.daddychurchill.CityWorld.Context.RoadContext;
import me.daddychurchill.CityWorld.Support.Odds;

public class ShapeProvider_Flooded extends ShapeProvider_Normal {

	public ShapeProvider_Flooded(WorldGenerator generator, Odds odds) {
		super(generator, odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void allocateContexts(WorldGenerator generator) {
		natureContext = new NatureContext(generator);
		roadContext = new RoadContext(generator);
		
		parkContext = new ParkContext(generator);
		highriseContext = new HighriseContext(generator);
		constructionContext = new ConstructionContext(generator);
		midriseContext = new MidriseContext(generator);
		municipalContext = new MunicipalContext(generator);
		industrialContext = new IndustrialContext(generator);
		lowriseContext = new LowriseContext(generator);
		neighborhoodContext = new NeighborhoodContext(generator);
		farmContext = new FarmContext(generator);
	}
}
