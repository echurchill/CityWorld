package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public class OreProvider_TheEnd extends OreProvider {
	
	public OreProvider_TheEnd(CityWorldGenerator generator) {
		super(generator);

		surfaceMaterial = Material.ENDER_STONE;
		subsurfaceMaterial = Material.ENDER_STONE;
		stratumMaterial = Material.ENDER_STONE;
		
		ore_types.add(generator.settings.materials.itemsMaterialListFor_TheEndOres.getNthMaterial(0, Material.WATER));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_TheEndOres.getNthMaterial(1, Material.LAVA));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_TheEndOres.getNthMaterial(2, Material.QUARTZ_BLOCK));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_TheEndOres.getNthMaterial(3, Material.GLOWSTONE));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_TheEndOres.getNthMaterial(4, Material.PRISMARINE));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_TheEndOres.getNthMaterial(5, Material.OBSIDIAN));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_TheEndOres.getNthMaterial(6, stratumMaterial));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_TheEndOres.getNthMaterial(7, stratumMaterial));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_TheEndOres.getNthMaterial(8, stratumMaterial));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_TheEndOres.getNthMaterial(9, stratumMaterial));
	}

	@Override
	public Biome remapBiome(Biome biome) {
		return Biome.SKY;
	}
}
