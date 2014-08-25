package me.daddychurchill.CityWorld.Context.Astral;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralBrownMushroomLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class AstralBrownMushroomContext extends AstralMushroomContext {

	public AstralBrownMushroomContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot generateMushroomLot(PlatMap platmap, int chunkX, int chunkZ) {
		return new AstralBrownMushroomLot(platmap, chunkX, chunkZ);
	}

}
