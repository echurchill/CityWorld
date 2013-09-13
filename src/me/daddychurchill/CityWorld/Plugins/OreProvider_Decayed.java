package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;

public class OreProvider_Decayed extends OreProvider_Normal {

	public OreProvider_Decayed(WorldGenerator generator) {
		super(generator);

		if (generator.settings.includeLavaFields) {
			fluidId = stillLavaId;
			fluidFluidId = fluidLavaId;
		}
		surfaceId = sandId;
		subsurfaceId = sandstoneId;
	}

	@Override
	public String getCollectionName() {
		return "Decayed";
	}

}
