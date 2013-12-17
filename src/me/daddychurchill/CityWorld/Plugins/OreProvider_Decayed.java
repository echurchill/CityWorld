package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class OreProvider_Decayed extends OreProvider_Normal {

	public OreProvider_Decayed(WorldGenerator generator) {
		super(generator);

		if (generator.settings.includeLavaFields) {
			fluidId = SupportChunk.stillLavaId;
			fluidFluidId = SupportChunk.fluidLavaId;
		}
		surfaceId = SupportChunk.sandId;
		subsurfaceId = SupportChunk.sandstoneId;
	}

	@Override
	public String getCollectionName() {
		return "Decayed";
	}

}
