package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;

public abstract class FoliageProvider {
	
	public enum FloraType {FLOWER_RED, FLOWER_YELLOW, GRASS, FERN, CACTUS};
	
	public abstract boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, TreeType treeType);
	public abstract boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, FloraType floraType);
	
	protected Random random;
	
	public FoliageProvider(Random random) {
		this.random = random;
	}
	
	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static FoliageProvider loadProvider(WorldGenerator generator, Random random) {

		FoliageProvider provider = null;
		
//		// need something like PhatLoot but for foliage
//		provider = FoliageProvider_PhatFoliage.loadPhatFoliage();
		
		// default to stock OreProvider
		if (provider == null) {
			
			if (generator.settings.includeTekkitMaterials)
				provider = new FoliageProvider_Tekkit(random);
			else if (generator.settings.environment == Environment.NETHER)
				provider = new FoliageProvider_Nether(random);
			else if (generator.settings.environment == Environment.THE_END)
				provider = new FoliageProvider_TheEnd(random);
			else
				provider = new FoliageProvider_Default(random);
		}
	
		return provider;
	}
	
	public boolean isPlantable(WorldGenerator generator, RealChunk chunk, int x, int y, int z) {
		
		// only if the spot above is empty
		if (!chunk.isEmpty(x, y + 1, z))
			return false;
		
		// depends on the block's type and what the world is like
		if (generator.settings.includeDecayedNature)
			return !chunk.isEmpty(x, y, z);
		else if (!generator.settings.includeAbovegroundFluids && y <= generator.seaLevel)
			return chunk.getBlock(x, y, z) == Material.SAND;
		else
			return chunk.isPlantable(x, y, z);
	}
	
	protected boolean likelyFlora(WorldGenerator generator, Random random) {
		return !generator.settings.darkEnvironment ||
				random.nextDouble() < 0.30;
	}

	private int maxTries = 3;

	protected boolean generateTree(RealChunk chunk, Random random, int x, int y, int z, TreeType treeType, int trunkId, int leavesId1, int leavesId2) {
		boolean result = false;
		boolean customTree = trunkId != logId || leavesId1 != leavesId || leavesId2 != leavesId;
		
		// where do we start?
		int bottomY = y;
		Block base = chunk.getActualBlock(x, y - 1, z);
		int baseTypeId = base.getTypeId();
		byte baseData = base.getData();
		try {
			int tries = 0;
			
			// keep moving up till we get a tree
			while (tries < maxTries) {
				
				// a place to plant
				chunk.setBlock(x, y - 1, z, Material.DIRT);
				
				// did we make a tree?
				if (customTree)
					result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType, new TreeDelegate(chunk, random, trunkId, leavesId1, leavesId2));
				else
					result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType);
				
				// did it finally work?
				if (result) {
					
					// copy the trunk down a bit
					Block root = chunk.getActualBlock(x, y, z);
					chunk.setBlocks(x, bottomY, y, z, root.getType(), root.getData());
					
					// all done
					break;
					
				// on failure move a bit more
				} else {
					y++;
				}
				
				// and again?
				tries++;
			}
		} finally {
			
			// if we actually failed remove all that dirt we made
			if (!result)
				chunk.setBlocks(x, bottomY, y, z, Material.AIR);
 			
			// set the base back to what it was originally
			chunk.setBlock(x, bottomY - 1, z, baseTypeId, baseData, false);
		}
		return result;
	}
	
	protected final static int logId = Material.LOG.getId();
	protected final static int leavesId = Material.LEAVES.getId();
	
	private class TreeDelegate implements BlockChangeDelegate {
		private RealChunk chunk;
		private Random random;
		private int trunkId;
		private int leavesId1;
		private int leavesId2;
		
		public TreeDelegate(RealChunk chunk, Random random, int trunkId, int leavesId1, int leavesId2) {
			this.chunk = chunk;
			this.random = random;
			this.trunkId = trunkId;
			this.leavesId1 = leavesId1;
			this.leavesId2 = leavesId2;
		}

		@Override
		public int getHeight() {
			return chunk.height;
		}

		@Override
		public int getTypeId(int x, int y, int z) {
			return chunk.world.getBlockAt(x, y, z).getTypeId();
		}

		@Override
		public boolean isEmpty(int x, int y, int z) {
			return chunk.world.getBlockAt(x, y, z).isEmpty();
		}

		@Override
		public boolean setRawTypeId(int x, int y, int z, int id) {
			return setTypeIdAndData(x, y, z, id, 0, false);
		}

		@Override
		public boolean setRawTypeIdAndData(int x, int y, int z, int id, int data) {
			return setTypeIdAndData(x, y, z, id, data, false);
		}

		@Override
		public boolean setTypeId(int x, int y, int z, int id) {
			return setTypeIdAndData(x, y, z, id, 0, true);
		}

		@Override
		public boolean setTypeIdAndData(int x, int y, int z, int id, int data) {
			return setTypeIdAndData(x, y, z, id, data, true);
		}
		
		private boolean setTypeIdAndData(int x, int y, int z, int id, int data, boolean update) {
			Block block = chunk.world.getBlockAt(x, y, z);
			if (id == logId)
				if (trunkId == logId)
					return block.setTypeIdAndData(logId, (byte) data, update);
				else
					return block.setTypeId(trunkId, update);
			
			else if (id == leavesId)
				if (random.nextBoolean())
					return block.setTypeId(leavesId1, update);
				else
					return block.setTypeId(leavesId2, update);
			else
				return false;
		}
	}
}
