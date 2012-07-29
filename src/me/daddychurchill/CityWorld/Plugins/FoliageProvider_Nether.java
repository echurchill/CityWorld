package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.TreeType;

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
