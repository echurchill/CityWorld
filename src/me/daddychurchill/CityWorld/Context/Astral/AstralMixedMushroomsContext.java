package me.daddychurchill.CityWorld.Context.Astral;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralBrownMushroomsLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralRedMushroomsLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class AstralMixedMushroomsContext extends AstralMushroomContext {

	public AstralMixedMushroomsContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot generateMushroomLot(PlatMap platmap, int chunkX, int chunkZ) {
		// random fluff
		Odds odds = platmap.getOddsGenerator();
		if (odds.flipCoin())
			return new AstralBrownMushroomsLot(platmap, chunkX, chunkZ);
		else
			return new AstralRedMushroomsLot(platmap, chunkX, chunkZ);
	}

}
