package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.TreeType;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.SupportChunk;

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

	public static TreeProvider loadProvider(WorldGenerator generator) {

		TreeProvider provider = null;
		
		// get the right defaults
		switch (generator.settings.treeStyle) {
		case NORMAL:
			return new TreeProvider_Normal();
		case SPOOKY:
			return new TreeProvider_Spooky();
		case CRYSTAL:
			return new TreeProvider_Crystal();
		}
		
		return provider;
	}
	
	protected void generateLeavesBlock(SupportChunk chunk, int x, int y, int z, Material material, int data) {
		BlackMagic.setBlock(chunk, x, y, z, material, data);
	}
	
	protected void generateTrunkBlock(SupportChunk chunk, int x, int y, int z, int w, int h, Material material, int data) {
		BlackMagic.setBlocks(chunk, x, x + w, y, y + h, z, z + w, material, data);
	}
	
	public boolean generateMiniTrunk(SupportChunk chunk, int x, int y, int z, TreeType treeType) {
		return generateMiniTree(chunk, x, y, z, treeType, false);
	}
	
	public boolean generateMiniTree(SupportChunk chunk, int x, int y, int z, TreeType treeType) {
		return generateMiniTree(chunk, x, y, z, treeType, true);
	}
	
	protected boolean generateMiniTree(SupportChunk chunk, int x, int y, int z, TreeType treeType, Boolean includeLeaves) {
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

			// do the trunk
			generateTrunkBlock(chunk, x, y, z, 1, trunkHeight, trunkMaterial, trunkBlackMagicData);
	
			// and then do the leaves... maybe
			if (includeLeaves) {
				int leavesHeight = trunkHeight - 1;
				generateLeavesBlock(chunk, x - 1, y + leavesHeight, z, leavesMaterial, trunkBlackMagicData);
				generateLeavesBlock(chunk, x + 1, y + leavesHeight, z, leavesMaterial, trunkBlackMagicData);
				generateLeavesBlock(chunk, x, y + leavesHeight, z - 1, leavesMaterial, trunkBlackMagicData);
				generateLeavesBlock(chunk, x, y + leavesHeight, z + 1, leavesMaterial, trunkBlackMagicData);
				generateLeavesBlock(chunk, x, y + trunkHeight, z, leavesMaterial, trunkBlackMagicData);
			}
			
			return true;
		} else
			return false;
	}
	
	public boolean generateNormalTrunk(SupportChunk chunk, int x, int y, int z, TreeType treeType) {
		return generateNormalTree(chunk, x, y, z, treeType, false);
	}
	
	public boolean generateNormalTree(SupportChunk chunk, int x, int y, int z, TreeType treeType) {
		return generateNormalTree(chunk, x, y, z, treeType, true);
	}
	
	protected boolean generateNormalTree(SupportChunk chunk, int x, int y, int z, TreeType treeType, boolean includeLeaves) {
		Material trunkMaterial = Material.LOG;
		int trunkBlackMagicData = 0;
		int trunkHeight = 2;
		int trunkWidth = 1;
		
		// Figure out the height
		switch (treeType) {
		default:
		case TREE:
			trunkHeight = 4;
			break;
		case BIG_TREE:
			trunkHeight = 7;
			break;
		case DARK_OAK:
			trunkHeight = 10;
			trunkWidth = 2;
			break;

		case BIRCH:
			trunkHeight = 5;
			break;
		case TALL_BIRCH:
			trunkHeight = 7;
			break;
			
		case REDWOOD:
			trunkHeight = 5;
			break;
		case TALL_REDWOOD:
			trunkHeight = 10;
			break;
		case MEGA_REDWOOD:
			trunkHeight = 15;
			break;
			
		case JUNGLE_BUSH:
			trunkHeight = 2;
			break;
		case SMALL_JUNGLE:
			trunkHeight = 5;
			break;
		case JUNGLE:
			trunkHeight = 9;
			break;
			
		case ACACIA:
			trunkHeight = 6;
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
			trunkBlackMagicData = 0;
			break;
		case DARK_OAK:
			trunkMaterial = Material.LOG_2;
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

			// do the trunk
			generateTrunkBlock(chunk, x, y, z, trunkWidth, trunkHeight, trunkMaterial, trunkBlackMagicData);
	
//			// and then do the leaves... maybe
//			if (includeLeaves) {
//				int leavesHeight = trunkHeight - 1;
//				generateLeaves(chunk, x - 1, y + leavesHeight, z, leavesMaterial, trunkBlackMagicData);
//				generateLeaves(chunk, x + 1, y + leavesHeight, z, leavesMaterial, trunkBlackMagicData);
//				generateLeaves(chunk, x, y + leavesHeight, z - 1, leavesMaterial, trunkBlackMagicData);
//				generateLeaves(chunk, x, y + leavesHeight, z + 1, leavesMaterial, trunkBlackMagicData);
//				generateLeaves(chunk, x, y + trunkHeight, z, leavesMaterial, trunkBlackMagicData);
//			}
			
			return true;
		} else
			return false;
	}
	
}
