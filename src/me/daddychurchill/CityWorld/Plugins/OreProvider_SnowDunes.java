package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;

public class OreProvider_SnowDunes extends OreProvider_Normal {

	public OreProvider_SnowDunes(WorldGenerator generator) {
		super(generator);

		fluidId = iceId;
		fluidFluidId = iceId;
	}

	@Override
	public String getCollectionName() {
		return "SnowDunes";
	}

}
