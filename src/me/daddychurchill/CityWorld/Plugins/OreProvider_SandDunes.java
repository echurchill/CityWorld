package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class OreProvider_SandDunes extends OreProvider_Normal {

	public OreProvider_SandDunes(WorldGenerator generator) {
		super(generator);

		fluidId = SupportChunk.sandId;
		fluidFluidId = SupportChunk.sandId;
		fluidSurfaceId = SupportChunk.sandId;
		fluidSubsurfaceId = SupportChunk.sandstoneId;
		fluidFrozenId = SupportChunk.snowBlockId;
	}

	@Override
	public String getCollectionName() {
		return "SandDunes";
	}

}
