package me.daddychurchill.CityWorld.Context.Astral;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public abstract class AstralDataContext extends DataContext {

	public AstralDataContext(CityWorldGenerator generator) {
		super(generator);
		
		oddsOfIsolatedConstructs = Odds.oddsSomewhatUnlikely;
	}
	
	protected double getPopulationOdds(int x, int z) {
		double result = 1.0;
		result = result / getPopulationOdds(x);
		result = result / getPopulationOdds(z);
		return result;
	}
	
	private double getPopulationOdds(int i) {
		double result = 1.0;
		if (i == 0 || i == PlatMap.Width - 1)
			result = result / 0.33;
		else if (i == 1 || i == PlatMap.Width - 2)
			result = result / 0.66;
		return result;
	}
}
