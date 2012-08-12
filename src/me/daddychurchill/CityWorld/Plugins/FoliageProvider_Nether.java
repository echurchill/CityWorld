package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.util.noise.NoiseGenerator;

public class FoliageProvider_Nether extends FoliageProvider {

	public FoliageProvider_Nether(Random random) {
		super(random);
	}
	
	@Override
	protected boolean likelyFlora(WorldGenerator generator, Random random) {
		return random.nextDouble() < oddsOfDarkFlora;
	}
	
	private final static int airId = Material.AIR.getId();
	private final static int glowId = Material.GLOWSTONE.getId();
	private final static int glassId = Material.GLASS.getId();
	private final static int paneId = Material.THIN_GLASS.getId();
	private final static int obsidianId = Material.OBSIDIAN.getId();
	private final static int clayId = Material.CLAY.getId();
	private final static int ironId = Material.IRON_FENCE.getId();
	
	@Override
	public void generateSurface(WorldGenerator generator, PlatLot lot, RealChunk chunk, int x, double perciseY, int z, boolean includeTrees) {
		int y = NoiseGenerator.floor(perciseY);
		
		// roll the dice
		double primary = random.nextDouble();
		double secondary = random.nextDouble();
		
		// top of the world?
		if (y >= generator.snowLevel) {
			if (random.nextDouble() < 0.20)
				setFire(chunk, x, y, z);
		
		// are on a plantable spot?
		} else if (isPlantable(generator, chunk, x, y, z)) {
			
			// regular trees, grass and flowers only
			if (y < generator.treeLevel) {

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
						setFire(chunk, x, y, z);
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
						setFire(chunk, x, y, z);
					}
				}
			}
		}	
	}
	
	private void setFire(RealChunk chunk, int x, int y, int z) {
		y = chunk.findLastEmptyBelow(x, y + 1, z);
		if (chunk.isEmpty(x, y, z) && chunk.isType(x, y - 1, z, Material.NETHERRACK))
			chunk.setBlock(x, y, z, Material.FIRE);
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, TreeType treeType) {
		if (likelyFlora(generator, random)) {
			int trunkId = logId;
			int leavesId1 = airId;
			int leavesId2 = airId;
			switch (treeType) {
			case TREE:
				//leave trunkId alone
				if (!generator.settings.includeDecayedNature && random.nextDouble() < 0.10) {
					leavesId1 = ironId;
					leavesId2 = ironId;
					if (random.nextDouble() < 0.10) {
						trunkId = glowId;
					}
				} 
				break;
			case BIRCH:
				trunkId = clayId;
				if (!generator.settings.includeDecayedNature && random.nextDouble() < 0.20) {
					leavesId1 = ironId;
					leavesId2 = ironId;
					if (random.nextDouble() < 0.10) {
						trunkId = glowId;
					}
				} 
				break;
			case BIG_TREE:
				trunkId = obsidianId;
				if (!generator.settings.includeDecayedNature && random.nextDouble() < 0.20) {
					leavesId1 = ironId;
					leavesId2 = ironId;
					if (random.nextDouble() < 0.10) {
						trunkId = glowId;
					}
				} 
				break;
			case TALL_REDWOOD:
			case REDWOOD:
				trunkId = obsidianId;
				if (!generator.settings.includeDecayedNature && random.nextDouble() < 0.10) {
					leavesId1 = paneId;
					if (random.nextDouble() < 0.10)
						leavesId2 = glowId;
					else
						leavesId2 = glassId;
				}
				break;
			default:
				break;
			}
			return generateTree(chunk, random, x, y, z, treeType, trunkId, leavesId1, leavesId2);
		} else
			return false;
	}

	@Override
	public boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, FloraType floraType) {
		if (likelyFlora(generator, random)) {
				
			// icky things in the nether
			switch (floraType) {
			case FLOWER_RED:
				chunk.setBlock(x, y, z, Material.RED_MUSHROOM);
				break;
			case FLOWER_YELLOW:
				chunk.setBlock(x, y, z, Material.BROWN_MUSHROOM);
				break;
			case GRASS:
			case FERN:
				if (random.nextDouble() < 0.05) {
					chunk.setBlock(x, y - 1, z, Material.NETHERRACK);
					chunk.setBlock(x, y, z, Material.FIRE);
				} else {
					chunk.setBlock(x, y - 1, z, Material.SOUL_SAND);
					if (random.nextDouble() < 0.05)
						chunk.setBlock(x, y, z, Material.NETHER_WARTS, (byte) random.nextInt(4));
				}
				break;
			case CACTUS:
			default:
				break;
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isPlantable(WorldGenerator generator, RealChunk chunk, int x, int y, int z) {
		
		// only if the spot above is empty
		if (!chunk.isEmpty(x, y + 1, z))
			return false;
		
		// depends on the block's type and what the world is like
		return !chunk.isEmpty(x, y, z);
	}
}
