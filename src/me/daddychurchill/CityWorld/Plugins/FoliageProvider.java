package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;

public abstract class FoliageProvider {
	
//	public enum LigneousType {SHRUB, SMALL_TREE, TREE};
//	public enum HerbaceousType {FLOWER_RED, FLOWER_YELLOW, GRASS, FERN};
	public enum FloraType {FLOWER_RED, FLOWER_YELLOW, GRASS, FERN, CACTUS};
	
	protected final static double oddsOfDarkFlora = 0.50;
	protected final static double treeOdds = 0.90;
	protected final static double foliageOdds = 0.40;
	
	public abstract void generateSurfacePoint(WorldGenerator generator, PlatLot lot, RealChunk chunk, int x, double perciseY, int z, boolean includeTrees);
	public abstract boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, TreeType treeType);
	public abstract boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, FloraType floraType);
	
	protected Random random;
	
	public FoliageProvider(Random random) {
		this.random = random;
	}
	
	public void generateSurface(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs, boolean includeTrees) {
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				generateSurfacePoint(generator, lot, chunk, x, blockYs.getPerciseY(x, z), z, includeTrees);
			}
		}
	}
	
	protected boolean likelyFlora(WorldGenerator generator, Random random) {
		return !generator.settings.darkEnvironment || random.nextDouble() < oddsOfDarkFlora;
	}
	
	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static FoliageProvider loadProvider(WorldGenerator generator, Random random) {

		FoliageProvider provider = null;
		
//		// need something like PhatLoot but for foliage
//		provider = FoliageProvider_PhatFoliage.loadPhatFoliage();
		if (provider == null) {
			
			switch (generator.settings.mapStyle) {
			case FLOATING:
				switch (generator.settings.environmentStyle) {
//				case NETHER:
//					provider = new FoliageProvider_FloatingNether(random, );
//					break;
				default:
//					if (generator.settings.includeDecayedNature)
//						provider = new FoliageProvider_FloatingDecayed(random);
//					else
						provider = new FoliageProvider_Floating(random);
//					break;
				}
				break;
			default:
				switch (generator.settings.environmentStyle) {
				case NETHER:
					provider = new FoliageProvider_Nether(random);
					break;
				case THE_END:
					provider = new FoliageProvider_TheEnd(random);
					break;
				default:
					if (generator.settings.includeDecayedNature)
						provider = new FoliageProvider_Decayed(random);
					else if (generator.settings.includeTekkitMaterials)
						provider = new FoliageProvider_Tekkit(random);
					else
						provider = new FoliageProvider_Normal(random);
					break;
				}
				break;
			}
		}
	
		return provider;
	}
	
	public boolean isPlantable(WorldGenerator generator, RealChunk chunk, int x, int y, int z) {
		
		// only if the spot above is empty
		if (!chunk.isEmpty(x, y + 1, z))
			return false;
		
		// depends on the block's type and what the world is like
		if (!generator.settings.includeAbovegroundFluids && y <= generator.seaLevel)
			return chunk.getBlock(x, y, z) == Material.SAND;
		else
			return chunk.isPlantable(x, y, z);
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
					result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType, new TreeCustomDelegate(chunk, random, trunkId, leavesId1, leavesId2));
				else
					result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType, new TreeVanillaDelegate(chunk));
				
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
	
	private class TreeVanillaDelegate implements BlockChangeDelegate {
		protected World world;
		protected RealChunk chunk;
		
		public TreeVanillaDelegate(RealChunk chunk) {
			this.chunk = chunk;
			this.world = chunk.world;
		}

		@Override
		public int getHeight() {
			return chunk.height;
		}

		@Override
		public int getTypeId(int x, int y, int z) {
			return world.getBlockAt(x, y, z).getTypeId();
		}

		@Override
		public boolean isEmpty(int x, int y, int z) {
			return world.getBlockAt(x, y, z).isEmpty();
		}

		@Override
		public boolean setRawTypeId(int x, int y, int z, int id) {
			return world.getBlockAt(x, y, z).setTypeIdAndData(id, (byte) 0, false);
		}

		@Override
		public boolean setRawTypeIdAndData(int x, int y, int z, int id, int data) {
			return world.getBlockAt(x, y, z).setTypeIdAndData(id, (byte) data, false);
		}

		@Override
		public boolean setTypeId(int x, int y, int z, int id) {
			return world.getBlockAt(x, y, z).setTypeIdAndData(id, (byte) 0, false);
		}

		@Override
		public boolean setTypeIdAndData(int x, int y, int z, int id, int data) {
			return world.getBlockAt(x, y, z).setTypeIdAndData(id, (byte) data, false);
		}
	}
	
	private class TreeCustomDelegate extends TreeVanillaDelegate {
		private Random random;
		private int trunkId;
		private int leavesId1;
		private int leavesId2;
		
		public TreeCustomDelegate(RealChunk chunk, Random random, int trunkId, int leavesId1, int leavesId2) {
			super(chunk);
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
			return world.getBlockAt(x, y, z).getTypeId();
		}

		@Override
		public boolean isEmpty(int x, int y, int z) {
			return world.getBlockAt(x, y, z).isEmpty();
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
			return setTypeIdAndData(x, y, z, id, 0, false);
		}

		@Override
		public boolean setTypeIdAndData(int x, int y, int z, int id, int data) {
			return setTypeIdAndData(x, y, z, id, data, false);
		}
		
		private boolean setTypeIdAndData(int x, int y, int z, int id, int data, boolean update) {
			Block block = world.getBlockAt(x, y, z);
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
