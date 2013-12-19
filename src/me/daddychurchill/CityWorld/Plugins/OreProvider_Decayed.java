package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.BlackMagic;

public class OreProvider_Decayed extends OreProvider_Normal {

	public OreProvider_Decayed(WorldGenerator generator) {
		super(generator);

		if (generator.settings.includeLavaFields) {
			fluidId = BlackMagic.stillLavaId;
			fluidFluidId = BlackMagic.fluidLavaId;
		}
		surfaceId = BlackMagic.sandId;
		subsurfaceId = BlackMagic.sandstoneId;
	}

	@Override
	public String getCollectionName() {
		return "Decayed";
	}

}
