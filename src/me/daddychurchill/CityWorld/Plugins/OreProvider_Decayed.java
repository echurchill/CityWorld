package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public class OreProvider_Decayed extends OreProvider_Normal {

	public OreProvider_Decayed(CityWorldGenerator generator) {
		super(generator);

		if (generator.settings.includeLavaFields) {
			fluidMaterial = Material.STATIONARY_LAVA;
			fluidFluidMaterial = Material.LAVA;
			fluidFrozenMaterial = Material.OBSIDIAN;
			fluidSubsurfaceMaterial = Material.LAVA;
			fluidSurfaceMaterial = Material.LAVA;
		}
		surfaceMaterial = Material.SAND;
		subsurfaceMaterial = Material.SANDSTONE;
	}
}
