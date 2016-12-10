package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.TreeType;
import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RelativeBlocks;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public abstract class TreeProvider {
	
	public enum TreeStyle {NORMAL, SPOOKY, CRYSTAL};
	
	public static TreeStyle toTreeStyle(String value, TreeStyle defaultValue) {
		try {
			return TreeStyle.valueOf(value);
		} catch(Exception e) {
			return defaultValue;
		}
	}
	
	public TreeProvider() {
		// TODO Auto-generated constructor stub
	}
	
	protected Odds odds;

	public static TreeProvider loadProvider(CityWorldGenerator generator, Odds odds) {

		TreeProvider provider = null;
		
		// get the right defaults
		switch (generator.settings.treeStyle) {
		case SPOOKY:
			provider = new TreeProvider_Spooky();
			break;
		case CRYSTAL:
			provider = new TreeProvider_Crystal();
			break;
		case NORMAL:
		default:
			provider = new TreeProvider_Normal();
			break;
		}
		
		provider.odds = odds;
		
		return provider;
	}
	
	public static TreeSpecies getTreeSpecies(TreeType treeType) {
		switch (treeType) {
		case ACACIA:
			return TreeSpecies.ACACIA;
		case DARK_OAK:
			return TreeSpecies.DARK_OAK;
		case JUNGLE:
		case JUNGLE_BUSH:
		case SMALL_JUNGLE:
		case COCOA_TREE:
			return TreeSpecies.JUNGLE;
		case TALL_REDWOOD:
		case MEGA_REDWOOD:
		case REDWOOD:
			return TreeSpecies.REDWOOD;
		case BIRCH:
		case TALL_BIRCH:
			return TreeSpecies.BIRCH;
		case SWAMP:
		case BIG_TREE:
		case TREE:
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
		default:
			return TreeSpecies.GENERIC;
		}
	}
	
	public static TreeType getTreeType(TreeSpecies treeSpecies) {
		switch (treeSpecies) {
		case ACACIA:
			return TreeType.ACACIA;
		case BIRCH:
			return TreeType.BIRCH;
		case DARK_OAK:
			return TreeType.DARK_OAK;
		case JUNGLE:
			return TreeType.JUNGLE;
		case REDWOOD:
			return TreeType.REDWOOD;
		case GENERIC:
		default:
			return TreeType.TREE;
		}
	}
	
	protected void generateLeavesBlock(SupportBlocks chunk, int x, int y, int z, Material material, int data, DyeColor specialColor) {
		// this variant does nothing with the special color
		if (chunk.isEmpty(x, y, z))
			BlackMagic.setBlock(chunk, x, y, z, material, data);
	}
	
	protected void generateTrunkBlock(SupportBlocks chunk, int x, int y, int z, int w, int h, Material material, int data) {
//		if (chunk.isEmpty(x, y, z))
		BlackMagic.setBlocks(chunk, x, x + w, y, y + h, z, z + w, material, data);
	}
	
	public boolean generateMiniTrunk(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, TreeType treeType) {
		return generateMiniTree(generator, chunk, x, y, z, treeType, false);
	}
	
	public boolean generateMiniTree(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, TreeType treeType) {
		return generateMiniTree(generator, chunk, x, y, z, treeType, true);
	}
	
	protected boolean generateMiniTree(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, TreeType treeType, Boolean includeLeaves) {
		Material trunkMaterial = Material.LOG;
		Material leavesMaterial = Material.LEAVES;
		int trunkHeight = 2;
		int trunkBlackMagicData = 0;
		
		// Figure out the height
		switch (treeType) {
		default:
		case TREE:
		case REDWOOD:
		case BIRCH:
		case JUNGLE_BUSH:
			trunkHeight = 2;
			break;

		case BIG_TREE:
		case TALL_REDWOOD:
		case TALL_BIRCH:
		case SMALL_JUNGLE:
			trunkHeight = 3;
			break;
		
		case JUNGLE:
		case ACACIA:
		case SWAMP:
			trunkHeight = 4;
			break;
			
		case DARK_OAK:
		case MEGA_REDWOOD:
			trunkHeight = 6;
			break;
			
		case BROWN_MUSHROOM: //TODO: We don't do these yet
		case RED_MUSHROOM:
			trunkHeight = 0;
			break;
		}

		// Figure out the material data
		switch (treeType) {
		default:
		case TREE:
		case BIG_TREE:
		case SWAMP:
			trunkBlackMagicData = 0;
			break;
			
		case REDWOOD:
		case TALL_REDWOOD:
		case MEGA_REDWOOD:
			trunkBlackMagicData = 1;
			break;
			
		case BIRCH:
		case TALL_BIRCH:
			trunkBlackMagicData = 2;
			break;
			
		case JUNGLE_BUSH:
		case SMALL_JUNGLE:
		case JUNGLE:
			trunkBlackMagicData = 3;
			break;
			
		case ACACIA:
			trunkMaterial = Material.LOG_2;
			leavesMaterial = Material.LEAVES_2;
			trunkBlackMagicData = 0;
			break;
		case DARK_OAK:
			trunkMaterial = Material.LOG_2;
			leavesMaterial = Material.LEAVES_2;
			trunkBlackMagicData = 1;
			break;
			
		case BROWN_MUSHROOM: //TODO: We don't do these yet
		case RED_MUSHROOM:
			trunkHeight = 0;
			break;
		}
		
		// something to do?
		if (trunkHeight > 0) {
			
			// a place to work
			RelativeBlocks blocks = new RelativeBlocks(generator, chunk);

			// do the trunk
			generateTrunkBlock(blocks, x, y, z, 1, trunkHeight, trunkMaterial, trunkBlackMagicData);
	
			// for that special case
			DyeColor leafColor = odds.getRandomColor();
			
			// and then do the leaves... maybe
			if (includeLeaves) {
				int leavesHeight = trunkHeight - 1;
				generateLeavesBlock(blocks, x - 1, y + leavesHeight, z, leavesMaterial, trunkBlackMagicData, leafColor);
				generateLeavesBlock(blocks, x + 1, y + leavesHeight, z, leavesMaterial, trunkBlackMagicData, leafColor);
				generateLeavesBlock(blocks, x, y + leavesHeight, z - 1, leavesMaterial, trunkBlackMagicData, leafColor);
				generateLeavesBlock(blocks, x, y + leavesHeight, z + 1, leavesMaterial, trunkBlackMagicData, leafColor);
				generateLeavesBlock(blocks, x, y + trunkHeight, z, leavesMaterial, trunkBlackMagicData, leafColor);
			}
			
			return true;
		} else
			return false;
	}
	
	public boolean generateNormalTrunk(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, TreeType treeType) {
		return generateNormalTree(generator, chunk, x, y, z, treeType, false);
	}
	
	public boolean generateNormalTree(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, TreeType treeType) {
		return generateNormalTree(generator, chunk, x, y, z, treeType, true);
	}
	
	protected boolean generateNormalTree(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, TreeType treeType, boolean includeLeaves) {
		Material trunkMaterial = Material.LOG;
		Material leavesMaterial = Material.LEAVES;
		int trunkBlackMagicData = 0;
		int trunkHeight = 2;
		int trunkWidth = 1;
		
		boolean leaves1exist = false;
		int leaves1start = 1;
		int leaves1end = 3;
		double leaves1width = 2;
		double leaves1delta = 0;
		
		boolean leaves2exist = false;
		int leaves2start = 1;
		int leaves2end = 3;
		double leaves2width = 2;
		double leaves2delta = 0;
		
		// Figure out the height
		switch (treeType) {
		default:
		case TREE:
			trunkHeight = 4;
			
			leaves1exist = true;
			leaves1start = -2;
			leaves1end = 2;
			leaves1width = 2;
			leaves1delta = 0;
			break;
		case BIG_TREE:
			trunkHeight = 7;
			
			leaves1exist = true;
			leaves1start = -3;
			leaves1end = 2;
			leaves1width = 3;
			leaves1delta = 0;
			break;
		case DARK_OAK:
			trunkHeight = 10;
			trunkWidth = 2;
			
			leaves1exist = true;
			leaves1start = -4;
			leaves1end = 2;
			leaves1width = 3;
			leaves1delta = 0;
			break;

		case BIRCH:
			trunkHeight = 5;
			
			leaves1exist = true;
			leaves1start = -2;
			leaves1end = 2;
			leaves1width = 2;
			leaves1delta = 0;
			break;
		case TALL_BIRCH:
			trunkHeight = 7;
			
			leaves1exist = true;
			leaves1start = -3;
			leaves1end = 2;
			leaves1width = 3;
			leaves1delta = 0;
			break;
			
		case REDWOOD:
			trunkHeight = 5;

			leaves1exist = true;
			leaves1start = -2;
			leaves1end = 2;
			leaves1width = 2;
			leaves1delta = 0.5;
			break;
		case TALL_REDWOOD:
			trunkHeight = 10;

			leaves1exist = true;
			leaves1start = -4;
			leaves1end = 2;
			leaves1width = 3;
			leaves1delta = 0.5;
			break;
		case MEGA_REDWOOD:
			trunkHeight = 15;

			leaves1exist = true;
			leaves1start = -8;
			leaves1end = -2;
			leaves1width = 3;
			leaves1delta = 0.5;

			leaves2exist = true;
			leaves2start = -2;
			leaves2end = 2;
			leaves2width = 2;
			leaves2delta = 0.5;
			break;
			
		case JUNGLE_BUSH:
			trunkHeight = 2;

			leaves1exist = true;
			leaves1start = -2;
			leaves1end = 2;
			leaves1width = 2;
			leaves1delta = 0;
			break;
		case SMALL_JUNGLE:
			trunkHeight = 5;

			leaves1exist = true;
			leaves1start = -2;
			leaves1end = 2;
			leaves1width = 2;
			leaves1delta = 0;
			break;
		case JUNGLE:
			trunkHeight = 9;

			leaves1exist = true;
			leaves1start = -3;
			leaves1end = 2;
			leaves1width = 3;
			leaves1delta = 0;
			break;
			
		case ACACIA:
			trunkHeight = 6;

			leaves1exist = true;
			leaves1start = -3;
			leaves1end = 3;
			leaves1width = 3;
			leaves1delta = 0.25;
			break;
			
		case BROWN_MUSHROOM: //TODO: We don't do these yet
		case RED_MUSHROOM:
		case SWAMP:
			trunkHeight = 0;
			break;
		}

		// Figure out the material data
		switch (treeType) {
		default:
		case TREE:
		case BIG_TREE:
			trunkBlackMagicData = 0;
			break;
			
		case REDWOOD:
		case TALL_REDWOOD:
		case MEGA_REDWOOD:
			trunkBlackMagicData = 1;
			break;
			
		case BIRCH:
		case TALL_BIRCH:
			trunkBlackMagicData = 2;
			break;
			
		case JUNGLE_BUSH:
		case SMALL_JUNGLE:
		case JUNGLE:
			trunkBlackMagicData = 3;
			break;
			
		case ACACIA:
			trunkMaterial = Material.LOG_2;
			leavesMaterial = Material.LEAVES_2;
			trunkBlackMagicData = 4;
			break;
		case DARK_OAK:
			trunkMaterial = Material.LOG_2;
			leavesMaterial = Material.LEAVES_2;
			trunkBlackMagicData = 1;
			break;
			
		case BROWN_MUSHROOM: //TODO: We don't do these yet
		case RED_MUSHROOM:
		case SWAMP:
			trunkHeight = 0;
			break;
		}

		// something to do?
		if (trunkHeight > 0) {

			// a place to work
			RelativeBlocks blocks = new RelativeBlocks(generator, chunk);

			// do the trunk
			generateTrunkBlock(blocks, x, y, z, trunkWidth, trunkHeight, trunkMaterial, trunkBlackMagicData);
	
			// and then do the leaves... maybe
			if (includeLeaves) {
				if (leaves1exist) {
					addLeaves(blocks, x, y, z, leavesMaterial, trunkBlackMagicData, trunkWidth, trunkHeight,
							leaves1start, leaves1end, leaves1width, leaves1delta);
					
					if (leaves2exist) 
						addLeaves(blocks, x, y, z, leavesMaterial, trunkBlackMagicData, trunkWidth, trunkHeight,
								leaves2start, leaves2end, leaves2width, leaves2delta);
				}
			}
			
			return true;
		} else
			return false;
	}
	
	private final static double edgeOdds = 0.00; // Not chance of edge bits
	
	private void addLeaves(SupportBlocks chunk, int trunkX, int trunkY, int trunkZ, 
			Material leavesMaterial, int leavesData, int trunkWidth, int trunkHeight, 
			int start, int end, double width, double delta) {
		
		// for that special case
		DyeColor leafColor = odds.getRandomColor();
		boolean randomColor = odds.playOdds(Odds.oddsPrettyUnlikely);
		
		// from the bottom up
		double widthAt = width;
		int minY = trunkY + trunkHeight + start;
		int maxY = trunkY + trunkHeight + end;
		for (int y = minY; y < maxY; y++) {
			
			// calculate the current extremes
			int widthInt = NoiseGenerator.floor(widthAt);
			int minX = trunkX - widthInt;
			int maxX = trunkX + widthInt + trunkWidth;
			int minZ = trunkZ - widthInt;
			int maxZ = trunkZ + widthInt + trunkWidth;
			
			for (int x = minX; x < maxX; x++) {
				for (int z = minZ; z < maxZ; z++) {
					
					// odds of leaves
					double leavesOdds = Odds.oddsExceedinglyLikely;
					
					// extremes
					if (x == minX || x == maxX - 1) {
						if (z == minZ || z == maxZ - 1)
							leavesOdds = edgeOdds;
						else if (y == minY || y == maxY - 1)
							leavesOdds = edgeOdds;
					} else if (z == minZ || z == maxZ - 1) {
						if (x == minX || x == maxX - 1)
							leavesOdds = edgeOdds;
						else if (y == minY || y == maxY - 1)
							leavesOdds = edgeOdds;
					} else if (y == minY || y == maxY - 1) {
						if (x == minX || x == maxX - 1)
							leavesOdds = edgeOdds;
						else if (z == minZ || z == maxZ - 1)
							leavesOdds = edgeOdds;
					}
					
					// worth doing?
					if (leavesOdds > 0.00 && odds.playOdds(leavesOdds)) {
						generateLeavesBlock(chunk, x, y, z, leavesMaterial, leavesData, leafColor);
						if (randomColor)
							leafColor = odds.getRandomColor();
					}
				}
			}
			
			// make it smaller as we go higher
			widthAt = widthAt - delta;
		}
	}
	
}
