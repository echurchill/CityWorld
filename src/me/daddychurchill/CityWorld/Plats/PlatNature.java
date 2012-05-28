package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatNature extends PlatIsolated {

	protected final static Material snowMaterial= Material.SNOW;
	protected final static Material grassMaterial= Material.LONG_GRASS;
	protected final static Material dandelionMaterial= Material.YELLOW_FLOWER;
	protected final static Material roseMaterial= Material.RED_ROSE;
	protected final static Material rootMaterial= Material.GRASS;
	
	protected final static byte snowId = (byte) snowMaterial.getId();
	protected final static byte grassId = (byte) grassMaterial.getId();
	
	public PlatNature(Random random, PlatMap platmap, int chunkX, int chunkZ) {
		super(random, platmap, chunkX, chunkZ);
		style = LotStyle.NATURE;
	}

	@Override
	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		// TODO add foliage and underground fixtures
		
		// compute offset to start of chunk
		int blockX = chunk.chunkX * chunk.width;
		int blockZ = chunk.chunkZ * chunk.width;
		
		// precalculate 
		int deciduousRange = chunk.evergreenlevel - chunk.treelevel;
		int evergreenRange = chunk.snowlevel - chunk.evergreenlevel;
		
		// plant grass or snow
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				double perciseY = generator.findPerciseY(blockX + x, blockZ + z);
				int y = NoiseGenerator.floor(perciseY);
				
				// top of the world?
				if (y >= chunk.snowlevel) {
					if (!chunk.isEmpty(x, y, z))
						chunk.setBlock(x, y + 1, z, snowId, (byte) NoiseGenerator.floor((perciseY - Math.floor(perciseY)) * 8.0));
				
				// are on a plantable spot?
				} else if (chunk.isPlantable(x, y, z)) {
					
					// roll the dice
					double primary = chunk.random.nextDouble();
					double secondary = chunk.random.nextDouble();
					
					// regular trees, grass and flowers only
					if (y < chunk.treelevel) {
	
						// trees? but only if we are not too close to the edge
						if (primary > 0.97 && x > 2 && x < 14 && z > 2 && z < 14) {
							if (secondary > 0.90 && x > 5 && x < 11 && z > 5 && z < 11)
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.BIG_TREE);
							else if (secondary > 0.50)
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.BIRCH);
							else 
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.TREE);
						
						// foliage?
						} else if (primary > 0.75) {
							
							// what to pepper about
							if (secondary > 0.90)
								chunk.setBlock(x, y + 1, z, roseMaterial);
							else if (secondary > 0.80)
								chunk.setBlock(x, y + 1, z, dandelionMaterial);
							else
								chunk.setBlock(x, y + 1, z, grassId, (byte) 1);
						}
						
					// regular trees, grass and some evergreen trees... no flowers
					} else if (y < chunk.evergreenlevel) {
	
						// trees? but only if we are not too close to the edge
						if (primary > 0.90 && x > 2 && x < 14 && z > 2 && z < 14) {
							
							// range change?
							if (secondary > ((double) (y - chunk.treelevel) / (double) deciduousRange))
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.TREE);
							else
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.REDWOOD);
						
						// foliage?
						} else if (primary > 0.75) {

							// range change?
							if (secondary > ((double) (y - chunk.treelevel) / (double) deciduousRange))
								chunk.setBlock(x, y + 1, z, grassId, (byte) 1);
							else
								chunk.setBlock(x, y + 1, z, grassId, (byte) 2);
						}
						
					// evergreen and some grass and fallen snow, no regular trees or flowers
					} else if (y < chunk.snowlevel) {
						
						// trees? but only if we are not too close to the edge
						if (primary > 0.90 && x > 2 && x < 14 && z > 2 && z < 14) {
							if (secondary > 0.50)
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.REDWOOD);
							else
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.TALL_REDWOOD);
						
						// foliage?
						} else if (primary > 0.40) {
							
							// range change?
							if (secondary > ((double) (y - chunk.evergreenlevel) / (double) evergreenRange)) {
								if (chunk.random.nextBoolean())
									chunk.setBlock(x, y + 1, z, grassId, (byte) 2);
							} else {
								chunk.setBlock(x, y + 1, z, snowMaterial);
							}
						}
					}
				}
			}
		}
	}
	
//	@Override
//	public void generateChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, PlatMapContext context, int platX, int platZ) {
//		//TODO turn on nature again!
//		chunk.setLayer(0, bedrockId);
//	}
}
