package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class FoliageProvider_Normal extends FoliageProvider {

	public FoliageProvider_Normal(Random random) {
		super(random);
	}
	
	@Override
	public void generateSurfacePoint(WorldGenerator generator, PlatLot lot, RealChunk chunk, int x, double perciseY, int z, boolean includeTrees) {
		OreProvider ores = generator.oreProvider;
		int y = NoiseGenerator.floor(perciseY);
		
		// roll the dice
		double primary = random.nextDouble();
		double secondary = random.nextDouble();
		
		// top of the world?
		if (y >= generator.snowLevel) {
			ores.dropSnow(generator, chunk, x, y, z, (byte) NoiseGenerator.floor((perciseY - Math.floor(perciseY)) * 8.0));
		
		// are on a plantable spot?
		} else if (isPlantable(generator, chunk, x, y, z)) {
			
			// below sea level and plantable.. then cactus?
			if (y <= generator.seaLevel) {
				
				// trees? but only if we are not too close to the edge
				if (!generator.settings.includeAbovegroundFluids && includeTrees && primary > 0.95 && x % 2 == 0 && z % 2 != 0) {
					if (chunk.isSurroundedByEmpty(x, y + 1, z))
						generateFlora(generator, chunk, x, y + 1, z, FloraType.CACTUS);
				}
				
			// regular trees, grass and flowers only
			} else if (y < generator.treeLevel) {

				// trees? but only if we are not too close to the edge
				if (includeTrees && primary > treeOdds && x > 0 && x < 15 && z > 0 && z < 15 && x % 2 == 0 && z % 2 != 0) {
					if (secondary > 0.90 && x > 5 && x < 11 && z > 5 && z < 11)
						generateTree(generator, chunk, x, y + 1, z, TreeType.BIG_TREE);
					else if (secondary > 0.50)
						generateTree(generator, chunk, x, y + 1, z, TreeType.BIRCH);
					else 
						generateTree(generator, chunk, x, y + 1, z, TreeType.TREE);
				
				// foliage?
				} else if (primary < foliageOdds) {
					
					// what to pepper about
					if (secondary > 0.90)
						generateFlora(generator, chunk, x, y + 1, z, FloraType.FLOWER_RED);
					else if (secondary > 0.80)
						generateFlora(generator, chunk, x, y + 1, z, FloraType.FLOWER_YELLOW);
					else 
						generateFlora(generator, chunk, x, y + 1, z, FloraType.GRASS);
				}
				
			// regular trees, grass and some evergreen trees... no flowers
			} else if (y < generator.evergreenLevel) {

				// trees? but only if we are not too close to the edge
				if (includeTrees && primary > treeOdds && x % 2 == 0 && z % 2 != 0) {
					
					// range change?
					if (secondary > ((double) (y - generator.treeLevel) / (double) generator.deciduousRange))
						generateTree(generator, chunk, x, y + 1, z, TreeType.TREE);
					else
						generateTree(generator, chunk, x, y + 1, z, TreeType.REDWOOD);
				
				// foliage?
				} else if (primary < foliageOdds) {

					// range change?
					if (secondary > ((double) (y - generator.treeLevel) / (double) generator.deciduousRange))
						generateFlora(generator, chunk, x, y + 1, z, FloraType.GRASS);
					else
						generateFlora(generator, chunk, x, y + 1, z, FloraType.FERN);
				}
				
			// evergreen and some grass and fallen snow, no regular trees or flowers
			} else if (y < generator.snowLevel) {
				
				// trees? but only if we are not too close to the edge
				if (includeTrees && primary > treeOdds && x % 2 == 0 && z % 2 != 0) {
					if (secondary > 0.50)
						generateTree(generator, chunk, x, y + 1, z, TreeType.REDWOOD);
					else
						generateTree(generator, chunk, x, y + 1, z, TreeType.TALL_REDWOOD);
				
				// foliage?
				} else if (primary < foliageOdds) {
					
					// range change?
					if (secondary > ((double) (y - generator.evergreenLevel) / (double) generator.evergreenRange)) {
						if (random.nextDouble() < 0.40)
							generateFlora(generator, chunk, x, y + 1, z, FloraType.FERN);
					} else {
						ores.dropSnow(generator, chunk, x, y, z);
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
						if (random.nextDouble() < 0.10 && isPlantable(generator, chunk, x, y, z))
							generateFlora(generator, chunk, x, y + 1, z, FloraType.FERN);
					} else {
						ores.dropSnow(generator, chunk, x, y, z);
					}
				}
			}
		}
		
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, TreeType treeType) {
		if (likelyFlora(generator, random)) {
			return generateTree(chunk, random, x, y, z, treeType, logId, leavesId, leavesId);
		} else
			return false;
	}

	@Override
	public boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, FloraType floraType) {
		if (likelyFlora(generator, random)) {
			switch (floraType) {
			case FLOWER_RED:
				chunk.setBlock(x, y, z, Material.RED_ROSE);
				break;
			case FLOWER_YELLOW:
				chunk.setBlock(x, y, z, Material.YELLOW_FLOWER);
				break;
			case GRASS:
				chunk.setBlock(x, y, z, Material.LONG_GRASS, (byte) 1);
				break;
			case FERN:
				chunk.setBlock(x, y, z, Material.LONG_GRASS, (byte) 2);
				break;
			case CACTUS:
				chunk.setBlock(x, y - 1, z, Material.SAND);
				chunk.setBlocks(x, y, y + random.nextInt(3) + 2, z, Material.CACTUS);
				break;
			}
		}
		return true;
	}
}
