package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Plugins.FoliageProvider.FloraType;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.util.noise.NoiseGenerator;

public class GroundProvider_Normal extends GroundProvider {

	public final static byte dirtId = (byte) Material.DIRT.getId();
	public final static byte grassId = (byte) Material.GRASS.getId();
	public final static byte sandId = (byte) Material.SAND.getId();
	public final static byte sandstoneId = (byte) Material.SANDSTONE.getId();
	public final static byte stillWaterId = (byte) Material.STATIONARY_WATER.getId();
	public final static byte stillLavaId = (byte) Material.STATIONARY_LAVA.getId();
	public final static byte snowId = (byte) Material.SNOW_BLOCK.getId();
	public final static byte bedrockId = (byte) Material.BEDROCK.getId();
	
	public GroundProvider_Normal(WorldGenerator generator, Random random) {
		super(generator, random);
		
		fluidId = stillWaterId;
		surfaceId = grassId;
		subsurfaceId = dirtId;
		substratumId = stoneId;

		// decayed?
		if (generator.settings.includeDecayedNature) {
			fluidId = stillLavaId;
			surfaceId = sandId;
			subsurfaceId = sandstoneId;
		}
	}
	
	@Override
	public Biome generateCrust(WorldGenerator generator, PlatLot lot, ByteChunk chunk, int x, int y, int z, boolean surfaceCaves) {
		Biome resultBiome = lot.getChunkBiome();
		
		// make the base
		chunk.setBlock(x, 0, z, bedrockId);
		chunk.setBlock(x, 1, z, substratumId);

		// buildable?
		if (lot.style == LotStyle.STRUCTURE || lot.style == LotStyle.ROUNDABOUT) {
			generateStratas(generator, lot, chunk, x, z, substratumId, generator.sidewalkLevel - 2, subsurfaceId, generator.sidewalkLevel, subsurfaceId, false);
			
		// possibly buildable?
		} else if (y == generator.sidewalkLevel) {
			generateStratas(generator, lot, chunk, x, z, substratumId, y - 3, subsurfaceId, y, surfaceId, generator.settings.includeDecayedNature);
		
		// won't likely have a building
		} else {

			// on the beach
			if (y == generator.seaLevel) {
				generateStratas(generator, lot, chunk, x, z, substratumId, y - 2, sandId, y, sandId, generator.settings.includeDecayedNature);
				resultBiome = Biome.BEACH;

			// we are in the water! ...or are we?
			} else if (y < generator.seaLevel) {
				resultBiome = Biome.DESERT;
				if (generator.settings.includeDecayedNature)
					if (generator.settings.includeAbovegroundFluids && y < generator.deepseaLevel)
						generateStratas(generator, lot, chunk, x, z, substratumId, y - 2, sandstoneId, y, sandId, generator.deepseaLevel, fluidId, false);
					else
						generateStratas(generator, lot, chunk, x, z, substratumId, y - 2, sandstoneId, y, sandId, true);
				else 
					if (generator.settings.includeAbovegroundFluids) {
						generateStratas(generator, lot, chunk, x, z, substratumId, y - 2, sandstoneId, y, sandId, generator.seaLevel, fluidId, false);
						if (fluidId == stillWaterId)
							resultBiome = Biome.OCEAN;
					} else
						generateStratas(generator, lot, chunk, x, z, substratumId, y - 2, sandstoneId, y, sandId, false);

			// we are in the mountains
			} else {

				// regular trees only
				if (y < generator.treeLevel) {
					generateStratas(generator, lot, chunk, x, z, substratumId, y - 3, subsurfaceId, y, surfaceId, generator.settings.includeDecayedNature);
					resultBiome = Biome.FOREST;

				// regular trees and some evergreen trees
				} else if (y < generator.evergreenLevel) {
					generateStratas(generator, lot, chunk, x, z, substratumId, y - 2, subsurfaceId, y, surfaceId, surfaceCaves);
					resultBiome = Biome.FOREST_HILLS;

				// evergreen and some of fallen snow
				} else if (y < generator.snowLevel) {
					generateStratas(generator, lot, chunk, x, z, substratumId, y - 1, subsurfaceId, y, surfaceId, surfaceCaves);
					resultBiome = Biome.TAIGA_HILLS;
					
				// only snow up here!
				} else {
					if (generator.settings.environment == Environment.NETHER)
						generateStratas(generator, lot, chunk, x, z, substratumId, y - 1, substratumId, y, surfaceId, surfaceCaves);
					else
						generateStratas(generator, lot, chunk, x, z, substratumId, y - 1, substratumId, y, snowId, surfaceCaves);
					resultBiome = Biome.ICE_MOUNTAINS;
				}
			}
		}
		return resultBiome;
	}
	
	@Override
	public void generateSurface(WorldGenerator generator, PlatLot lot, RealChunk chunk, int x, double perciseY, int z, boolean includeTrees) {
		int y = NoiseGenerator.floor(perciseY);
		
		// roll the dice
		double primary = random.nextDouble();
		double secondary = random.nextDouble();
		
		// top of the world?
		if (y >= generator.snowLevel) {
			y = chunk.findLastEmptyBelow(x, y + 1, z);
			if (chunk.isEmpty(x, y, z))
				chunk.setBlock(x, y, z, Material.SNOW, (byte) NoiseGenerator.floor((perciseY - Math.floor(perciseY)) * 8.0));
		
		// are on a plantable spot?
		} else if (generator.foliageProvider.isPlantable(generator, chunk, x, y, z)) {
			
			// below sea level and plantable.. then cactus?
			if (y <= generator.seaLevel) {
				
				// trees? but only if we are not too close to the edge
				if (!generator.settings.includeAbovegroundFluids && includeTrees && primary > 0.90 && x % 2 == 0 && z % 2 != 0) {
					if (chunk.isSurroundedByEmpty(x, y + 1, z))
						generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.CACTUS);
				}
				
			// regular trees, grass and flowers only
			} else if (y < generator.treeLevel) {

				// trees? but only if we are not too close to the edge
				if (includeTrees && primary > treeOdds && x > 0 && x < 15 && z > 0 && z < 15 && x % 2 == 0 && z % 2 != 0) {
					if (secondary > 0.90 && x > 5 && x < 11 && z > 5 && z < 11)
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.BIG_TREE);
					else if (secondary > 0.50)
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.BIRCH);
					else 
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.TREE);
				
				// foliage?
				} else if (primary < foliageOdds) {
					
					// what to pepper about
					if (secondary > 0.90)
						generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.FLOWER_RED);
					else if (secondary > 0.80)
						generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.FLOWER_YELLOW);
					else 
						generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.GRASS);
				}
				
			// regular trees, grass and some evergreen trees... no flowers
			} else if (y < generator.evergreenLevel) {

				// trees? but only if we are not too close to the edge
				if (includeTrees && primary > treeOdds && x % 2 == 0 && z % 2 != 0) {
					
					// range change?
					if (secondary > ((double) (y - generator.treeLevel) / (double) generator.deciduousRange))
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.TREE);
					else
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.REDWOOD);
				
				// foliage?
				} else if (primary < foliageOdds) {

					// range change?
					if (secondary > ((double) (y - generator.treeLevel) / (double) generator.deciduousRange))
						generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.GRASS);
					else
						generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.FERN);
				}
				
			// evergreen and some grass and fallen snow, no regular trees or flowers
			} else if (y < generator.snowLevel) {
				
				// trees? but only if we are not too close to the edge
				if (includeTrees && primary > treeOdds && x % 2 == 0 && z % 2 != 0) {
					if (secondary > 0.50)
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.REDWOOD);
					else
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.TALL_REDWOOD);
				
				// foliage?
				} else if (primary < foliageOdds) {
					
					// range change?
					if (secondary > ((double) (y - generator.evergreenLevel) / (double) generator.evergreenRange)) {
						if (random.nextDouble() < 0.40)
							generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.FERN);
					} else {
						if (!chunk.isEmpty(x, y, z))
							chunk.setBlock(x, y + 1, z, Material.SNOW);
					}
				}
			}
		
		// can't plant, maybe there is something else I can do
		} else {
			
			// regular trees, grass and flowers only
			if (y < generator.treeLevel) {

			// regular trees, grass and some evergreen trees... no flowers
			} else if (y < generator.evergreenLevel) {

			// evergreen and some grass and fallen snow, no regular trees or flowers
			} else if (y < generator.snowLevel) {
				
				if (primary < foliageOdds) {
					
					// range change?
					if (secondary > ((double) (y - generator.evergreenLevel) / (double) generator.evergreenRange)) {
						if (random.nextDouble() < 0.10 && generator.foliageProvider.isPlantable(generator, chunk, x, y, z))
							generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.FERN);
					} else {
						y = chunk.findLastEmptyBelow(x, y + 1, z);
						if (chunk.isEmpty(x, y, z))
							chunk.setBlock(x, y, z, Material.SNOW);
					}
				}
			}
		}
		
	}
	
}
