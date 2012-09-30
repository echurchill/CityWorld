package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.Tekkit.FoliageProvider_Tekkit;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;

public abstract class FoliageProvider extends Provider {
	
    public enum LigneousType {SHORT_OAK, SHORT_PINE, SHORT_BIRCH, OAK, PINE, BIRCH, TALL_OAK, TALL_PINE, TALL_BIRCH};
	public enum HerbaceousType {FLOWER_RED, FLOWER_YELLOW, GRASS, FERN, CACTUS, COVER};
	
	protected final static double oddsOfDarkFlora = DataContext.oddsLikely;
	
	public abstract boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType treeType);
	public abstract boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, HerbaceousType herbaceousType);
	
	protected Odds odds;
	
	public FoliageProvider(Odds odds) {
		super();
		this.odds = odds;
	}
	
	protected boolean likelyFlora(WorldGenerator generator, Odds odds) {
		return !generator.settings.darkEnvironment || odds.playOdds(oddsOfDarkFlora);
	}
	
	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static FoliageProvider loadProvider(WorldGenerator generator, Odds odds) {

		FoliageProvider provider = null;
		
//		// need something like PhatLoot but for foliage
//		provider = FoliageProvider_PhatFoliage.loadPhatFoliage();
		if (provider == null) {
			
			switch (generator.worldEnvironment) {
			case NETHER:
				provider = new FoliageProvider_Nether(odds);
				break;
			case THE_END:
				provider = new FoliageProvider_TheEnd(odds);
				break;
			default:
				if (generator.settings.includeDecayedNature)
					provider = new FoliageProvider_Decayed(odds);
				else if (generator.settings.includeTekkitMaterials)
					provider = new FoliageProvider_Tekkit(odds);
				else
					provider = new FoliageProvider_Normal(odds);
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

	protected boolean generateTree(RealChunk chunk, Odds odds, int x, int y, int z, LigneousType type, Material trunk, Material leaves1, Material leaves2) {
		switch (type) {
		case BIRCH:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.BIRCH, trunk, leaves1, leaves2);
		case PINE:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.REDWOOD, trunk, leaves1, leaves2);
		case OAK:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.TREE, trunk, leaves1, leaves2);
		case SHORT_BIRCH:
			return generateSmallTree(chunk, odds, x, y, z, TreeType.BIRCH, trunk, leaves1);
		case SHORT_PINE:
			return generateSmallTree(chunk, odds, x, y, z, TreeType.REDWOOD, trunk, leaves1);
		case SHORT_OAK:
			return generateSmallTree(chunk, odds, x, y, z, TreeType.TREE, trunk, leaves1);
		case TALL_BIRCH:
		case TALL_OAK:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.BIG_TREE, trunk, leaves1, leaves2);
		case TALL_PINE:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.TALL_REDWOOD, trunk, leaves1, leaves2);
		default:
			return false;
		}
	}
	
	protected boolean generateSmallTree(RealChunk chunk, Odds odds, int x, int y, int z, 
			TreeType treeType, Material trunk, Material leaves) {
		int treeHeight;
		byte treeData;
		switch (treeType) {
		case BIRCH:
			treeHeight = 3;
			treeData = 2;
			break;
		case REDWOOD:
			treeHeight = 4;
			treeData = 1;
			break;
		case TALL_REDWOOD:
			treeHeight = 5;
			treeData = 1;
			break;
		case BIG_TREE:
		default:
			treeHeight = 2;
			treeData = 0;
			break;
		}

		int trunkHeight = treeHeight - 1;
		chunk.setBlocks(x, y, y + treeHeight, z, trunk, treeData);
		chunk.setBlock(x - 1, y + trunkHeight, z, leaves, treeData);
		chunk.setBlock(x + 1, y + trunkHeight, z, leaves, treeData);
		chunk.setBlock(x, y + trunkHeight, z - 1, leaves, treeData);
		chunk.setBlock(x, y + trunkHeight, z + 1, leaves, treeData);
		chunk.setBlock(x, y + treeHeight, z, leaves, treeData);
		
		return true;
	}
	
	protected boolean generateNormalTree(RealChunk chunk, Odds odds, int x, int y, int z, 
			TreeType treeType, Material trunk, Material leaves1, Material leaves2) {
		boolean result = false;
		boolean customTree = trunk != log || leaves1 != leaves || leaves2 != leaves;
		
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
					result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType, 
							new TreeCustomDelegate(chunk, odds, trunk.getId(), leaves1.getId(), leaves2.getId()));
				else
					result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType, 
							new TreeVanillaDelegate(chunk));
				
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
	
	protected final static Material log = Material.LOG;
	protected final static int logId = log.getId();
	protected final static Material leaves = Material.LEAVES;
	protected final static int leavesId = leaves.getId();
	
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
		private Odds odds;
		private int trunkId;
		private int leavesId1;
		private int leavesId2;
		
		public TreeCustomDelegate(RealChunk chunk, Odds odds, int trunkId, int leavesId1, int leavesId2) {
			super(chunk);
			this.odds = odds;
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
				if (odds.flipCoin())
					return block.setTypeId(leavesId1, update);
				else
					return block.setTypeId(leavesId2, update);
			else
				return false;
		}
	}
}
