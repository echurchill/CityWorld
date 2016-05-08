package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public class OreProvider_SnowDunes extends OreProvider_Normal {

	public OreProvider_SnowDunes(CityWorldGenerator generator) {
		super(generator);

		fluidMaterial = Material.FROSTED_ICE;
		fluidFluidMaterial = Material.SNOW_BLOCK;
		fluidSurfaceMaterial = Material.PACKED_ICE;
		fluidSubsurfaceMaterial = Material.PACKED_ICE;
		fluidFrozenMaterial = Material.PACKED_ICE;
	}
}
