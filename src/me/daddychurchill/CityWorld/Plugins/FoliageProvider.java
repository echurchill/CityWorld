package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.Tekkit.FoliageProvider_Tekkit;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.TreeCustomDelegate;
import me.daddychurchill.CityWorld.Support.TreeVanillaDelegate;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;

public abstract class FoliageProvider extends Provider {
	
	public enum LigneousType {SHORT_OAK, SHORT_PINE, SHORT_BIRCH, SHORT_JUNGLE,
							  OAK, PINE, BIRCH, JUNGLE, SWAMP,
							  TALL_OAK, TALL_PINE, TALL_BIRCH, TALL_JUNGLE};
	public enum HerbaceousType {FLOWER_RED, FLOWER_YELLOW, GRASS, FERN, CACTUS, REED, COVER};
	
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
			
			switch (generator.worldStyle) {
			case FLOODED:
				provider = new FoliageProvider_Flooded(odds);
				break;
			default:
				if (generator.settings.includeTekkitMaterials) {
					generator.reportMessage("[FoliageProvider] Found ForgeTekkit, enabling its foliage");
	
					//TODO provide nether, theend and decayed variants of Tekkit
					provider = new FoliageProvider_Tekkit(odds);
				} else {
					
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
						else
							provider = new FoliageProvider_Normal(odds);
						break;
					}
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

	protected boolean generateTree(RealChunk chunk, Odds odds, int x, int y, int z, LigneousType type, Material trunk, Material leaves1, Material leaves2) {
		switch (type) {
		case BIRCH:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.BIRCH, trunk, leaves1, leaves2);
		case PINE:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.REDWOOD, trunk, leaves1, leaves2);
		case OAK:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.TREE, trunk, leaves1, leaves2);
		case JUNGLE:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.SMALL_JUNGLE, trunk, leaves1, leaves2);
		case SWAMP:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.SWAMP, trunk, leaves1, leaves2);
		case SHORT_BIRCH:
			return generateSmallTree(chunk, odds, x, y, z, TreeType.BIRCH, trunk, leaves1);
		case SHORT_PINE:
			return generateSmallTree(chunk, odds, x, y, z, TreeType.REDWOOD, trunk, leaves1);
		case SHORT_OAK:
			return generateSmallTree(chunk, odds, x, y, z, TreeType.TREE, trunk, leaves1);
		case SHORT_JUNGLE:
			return generateSmallTree(chunk, odds, x, y, z, TreeType.JUNGLE_BUSH, trunk, leaves1);
		case TALL_BIRCH:
		case TALL_OAK:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.BIG_TREE, trunk, leaves1, leaves2);
		case TALL_PINE:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.TALL_REDWOOD, trunk, leaves1, leaves2);
		case TALL_JUNGLE:
			return generateNormalTree(chunk, odds, x, y, z, TreeType.JUNGLE, trunk, leaves1, leaves2);
		default:
			return false;
		}
	}
	
	protected boolean generateTrunk(RealChunk chunk, Odds odds, int x, int y, int z, 
			LigneousType type) {
		int treeHeight;
		byte treeData;
		switch (type) {
		case SHORT_OAK:
			treeHeight = 3;
			treeData = 0;
			break;
		case SHORT_PINE:
			treeHeight = 3;
			treeData = 1;
			break;
		case SHORT_BIRCH:
			treeHeight = 3;
			treeData = 2;
			break;
		case SHORT_JUNGLE:
			treeHeight = 3;
			treeData = 3;
			break;
		case OAK:
			treeHeight = 5;
			treeData = 0;
			break;
		case PINE:
			treeHeight = 5;
			treeData = 1;
			break;
		case BIRCH: 
			treeHeight = 5;
			treeData = 2;
			break;
		case JUNGLE: 
			treeHeight = 5;
			treeData = 3;
			break;
		case SWAMP:
			treeHeight = 2;
			treeData = 3;
			break;
		case TALL_OAK:
			treeHeight = 7;
			treeData = 0;
			break;
		case TALL_PINE:
			treeHeight = 9;
			treeData = 1;
			break;
		case TALL_BIRCH:
			treeHeight = 7;
			treeData = 2;
			break;
		case TALL_JUNGLE:
		default:
			treeHeight = 9;
			treeData = 3;
			break;
		}
		chunk.setBlocks(x, y, y + treeHeight, z, Material.LOG, treeData);
		
		return true;
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
		byte baseTypeId = BlackMagic.getMaterialId(base);
		byte baseData = BlackMagic.getMaterialData(base);
		try {
			int tries = 0;
			
			// keep moving up till we get a tree
			while (tries < maxTries) {
				
				// a place to plant
				chunk.setBlock(x, y - 1, z, Material.DIRT);
				
				// did we make a tree?
				if (customTree)
					result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType, 
							getCustomTreeDelegate(chunk, odds, trunk, leaves1, leaves2));
				else
					result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType, 
							getVanillaTreeDelegate(chunk));
				
				// did it finally work?
				if (result) {
					
					// copy the trunk down a bit
					Block root = chunk.getActualBlock(x, y, z);
					chunk.setBlocks(x, bottomY, y, z, 
							BlackMagic.getMaterialId(root), BlackMagic.getMaterialData(root));
					
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
	
	public final static Material log = Material.LOG;
	public final static byte logId = BlackMagic.getMaterialId(log);
	public final static Material leaves = Material.LEAVES;
	public final static byte leavesId = BlackMagic.getMaterialId(leaves);
	
	protected BlockChangeDelegate getCustomTreeDelegate(RealChunk chunk, Odds odds, 
			Material trunk, Material leaves1, Material leaves2) {
		return new TreeCustomDelegate(chunk, odds, trunk, leaves1, leaves2);
	}
	
	protected BlockChangeDelegate getVanillaTreeDelegate(RealChunk chunk) {
		return new TreeVanillaDelegate(chunk);
	}
}
