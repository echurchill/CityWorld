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

		fluidMaterial = Material.FROSTED_ICE;
		fluidFluidMaterial = Material.SNOW_BLOCK;
		fluidSurfaceMaterial = Material.PACKED_ICE;
		fluidSubsurfaceMaterial = Material.PACKED_ICE;
		fluidFrozenMaterial = Material.PACKED_ICE;
		
		ore_types.add(generator.materialProvider.itemsMaterialListFor_TheEndOres.getNthMaterial(0, Material.WATER));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_TheEndOres.getNthMaterial(1, Material.LAVA));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_TheEndOres.getNthMaterial(2, Material.GRAVEL));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_TheEndOres.getNthMaterial(3, Material.QUARTZ_BLOCK));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_TheEndOres.getNthMaterial(4, Material.GLOWSTONE));
		
		ore_types.add(generator.materialProvider.itemsMaterialListFor_TheEndOres.getNthMaterial(5, Material.PURPUR_BLOCK));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_TheEndOres.getNthMaterial(6, Material.GOLD_ORE));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_TheEndOres.getNthMaterial(7, Material.LAPIS_ORE));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_TheEndOres.getNthMaterial(8, Material.DIAMOND_ORE));
		ore_types.add(generator.materialProvider.itemsMaterialListFor_TheEndOres.getNthMaterial(9, Material.OBSIDIAN));
	}

	@Override
	public Biome remapBiome(Biome biome) {
		return Biome.SKY;
	}
}
