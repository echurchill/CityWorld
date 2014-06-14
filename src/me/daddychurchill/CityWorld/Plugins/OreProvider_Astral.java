package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;

public class OreProvider_Astral extends OreProvider_Normal {

	public OreProvider_Astral(WorldGenerator generator) {
		super(generator);

		surfaceMaterial = Material.COBBLESTONE;
		subsurfaceMaterial = Material.STONE;
		stratumMaterial = Material.STONE;
		substratumMaterial = Material.BEDROCK;
		
		fluidMaterial = Material.ICE;
		fluidFluidMaterial = Material.ICE;
		fluidSurfaceMaterial = Material.COBBLESTONE;
		fluidSubsurfaceMaterial = Material.STONE;
		fluidFrozenMaterial = Material.ICE;
	}

	@Override
	public String getCollectionName() {
		return "Astral";
	}
}
