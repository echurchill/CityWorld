package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;

public class OreProvider_SandDunes extends OreProvider_Normal {

	public OreProvider_SandDunes(WorldGenerator generator) {
		super(generator);

		fluidId = sandId;
		fluidFluidId = sandId;
		fluidSurfaceId = sandId;
		fluidSubsurfaceId = sandstoneId;
		fluidFrozenId = snowBlockId;
	}

	@Override
	public String getCollectionName() {
		return "SandDunes";
	}

}
