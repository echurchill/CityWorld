package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;

public class OreProvider_Decayed extends OreProvider_Normal {

	public OreProvider_Decayed(WorldGenerator generator) {
		super(generator);

		if (generator.settings.includeLavaFields) {
			fluidMaterial = Material.STATIONARY_LAVA;
			fluidFluidMaterial = Material.LAVA;
		}
		surfaceMaterial = Material.SAND;
		subsurfaceMaterial = Material.SANDSTONE;
	}

	@Override
	public String getCollectionName() {
		return "Decayed";
	}

}
