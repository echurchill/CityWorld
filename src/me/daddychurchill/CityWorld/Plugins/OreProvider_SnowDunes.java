package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class OreProvider_SnowDunes extends OreProvider_Normal {

	public OreProvider_SnowDunes(WorldGenerator generator) {
		super(generator);

		fluidId = SupportChunk.iceId;
		fluidFluidId = SupportChunk.iceId;
	}

	@Override
	public String getCollectionName() {
		return "SnowDunes";
	}

}
