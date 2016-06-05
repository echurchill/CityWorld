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
		fluidFluidMaterial = Material.LAVA;
		fluidFrozenMaterial = Material.OBSIDIAN;
		fluidSubsurfaceMaterial = Material.LAVA;
		fluidSurfaceMaterial = Material.LAVA;
		
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NetherOres.getNthMaterial(0, Material.LAVA));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NetherOres.getNthMaterial(1, Material.LAVA));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NetherOres.getNthMaterial(2, Material.SOUL_SAND));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NetherOres.getNthMaterial(3, Material.SOUL_SAND));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NetherOres.getNthMaterial(4, Material.GLOWSTONE));
		
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NetherOres.getNthMaterial(5, Material.GLOWSTONE));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NetherOres.getNthMaterial(6, Material.QUARTZ_ORE));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NetherOres.getNthMaterial(7, Material.SOUL_SAND));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NetherOres.getNthMaterial(8, Material.SOUL_SAND));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_NetherOres.getNthMaterial(9, Material.OBSIDIAN));
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
