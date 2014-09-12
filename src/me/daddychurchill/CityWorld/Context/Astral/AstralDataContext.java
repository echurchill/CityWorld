package me.daddychurchill.CityWorld.Context.Astral;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.Odds;

public abstract class AstralDataContext extends DataContext {

	public AstralDataContext(WorldGenerator generator) {
		super(generator);
		
		oddsOfIsolatedConstructs = Odds.oddsSomewhatUnlikely;
	}

}
