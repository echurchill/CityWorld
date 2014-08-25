package me.daddychurchill.CityWorld.Context.Astral;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralRedMushroomLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class AstralRedMushroomContext extends AstralMushroomContext {

	public AstralRedMushroomContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot generateMushroomLot(PlatMap platmap, int chunkX, int chunkZ) {
		return new AstralRedMushroomLot(platmap, chunkX, chunkZ);
	}

}
