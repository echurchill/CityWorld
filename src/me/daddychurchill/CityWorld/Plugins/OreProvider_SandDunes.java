package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.BlackMagic;

public class OreProvider_SandDunes extends OreProvider_Normal {

	public OreProvider_SandDunes(WorldGenerator generator) {
		super(generator);

		fluidId = BlackMagic.sandId;
		fluidFluidId = BlackMagic.sandId;
		fluidSurfaceId = BlackMagic.sandId;
		fluidSubsurfaceId = BlackMagic.sandstoneId;
		fluidFrozenId = BlackMagic.snowBlockId;
	}

	@Override
	public String getCollectionName() {
		return "SandDunes";
	}

}
