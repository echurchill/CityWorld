package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class OreProvider_Nether extends OreProvider {
	
	public OreProvider_Nether(CityWorldGenerator generator) {
		super(generator);
		
		surfaceMaterial = Material.NETHERRACK;
		subsurfaceMaterial = Material.NETHERRACK;
		stratumMaterial = Material.NETHERRACK;
		
		fluidMaterial = Material.STATIONARY_LAVA;
		fluidSubsurfaceMaterial = Material.NETHERRACK;
		fluidSurfaceMaterial = Material.NETHERRACK;

		ore_types.add(generator.settings.materials.itemsMaterialListFor_NetherOres.getNthMaterial(0, Material.LAVA));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_NetherOres.getNthMaterial(1, Material.GRAVEL));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_NetherOres.getNthMaterial(2, Material.SOUL_SAND));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_NetherOres.getNthMaterial(3, Material.GLOWSTONE));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_NetherOres.getNthMaterial(4, Material.QUARTZ_ORE));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_NetherOres.getNthMaterial(5, Material.OBSIDIAN));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_NetherOres.getNthMaterial(6, stratumMaterial));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_NetherOres.getNthMaterial(7, stratumMaterial));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_NetherOres.getNthMaterial(8, stratumMaterial));
		ore_types.add(generator.settings.materials.itemsMaterialListFor_NetherOres.getNthMaterial(9, stratumMaterial));
	}

	@Override
	public Biome remapBiome(Biome biome) {
		return Biome.HELL;
	}

	@Override
	public void dropSnow(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, int level) {
		
		// do nothing
	}
}
