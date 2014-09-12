package me.daddychurchill.CityWorld.Context.Astral;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralRedMushroomsLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class AstralRedMushroomsContext extends AstralMushroomContext {

	public AstralRedMushroomsContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlatLot generateMushroomLot(PlatMap platmap, int chunkX, int chunkZ) {
		return new AstralRedMushroomsLot(platmap, chunkX, chunkZ);
	}

}
