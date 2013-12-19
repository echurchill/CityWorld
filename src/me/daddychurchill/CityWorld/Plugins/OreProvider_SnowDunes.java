package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.BlackMagic;

public class OreProvider_SnowDunes extends OreProvider_Normal {

	public OreProvider_SnowDunes(WorldGenerator generator) {
		super(generator);

		fluidId = BlackMagic.iceId;
		fluidFluidId = BlackMagic.iceId;
	}

	@Override
	public String getCollectionName() {
		return "SnowDunes";
	}

}
