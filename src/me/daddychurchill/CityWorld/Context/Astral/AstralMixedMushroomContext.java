package me.daddychurchill.CityWorld.Context.Astral;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralBrownMushroomLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralRedMushroomLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class AstralMixedMushroomContext extends AstralMushroomContext {

	public AstralMixedMushroomContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot generateMushroomLot(PlatMap platmap, int chunkX, int chunkZ) {
		// random fluff
		Odds odds = platmap.getOddsGenerator();
		if (odds.flipCoin())
			return new AstralBrownMushroomLot(platmap, chunkX, chunkZ);
		else
			return new AstralRedMushroomLot(platmap, chunkX, chunkZ);
	}

}
