package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;

public class OreProvider_SandDunes extends OreProvider_Normal {

	public OreProvider_SandDunes(WorldGenerator generator) {
		super(generator);

		fluidMaterial = Material.SAND;
		fluidFluidMaterial = Material.SAND;
		fluidSurfaceMaterial = Material.SAND;
		fluidSubsurfaceMaterial = Material.SANDSTONE;
		fluidFrozenMaterial = Material.SNOW_BLOCK;
	}

	@Override
	public String getCollectionName() {
		return "SandDunes";
	}

}
