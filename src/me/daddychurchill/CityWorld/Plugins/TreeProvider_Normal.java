package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.SupportChunk;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;

public class TreeProvider_Normal extends TreeProvider {

	public TreeProvider_Normal() {
		// TODO Auto-generated constructor stub
	}

	private int maxTries = 3;

	@Override
	public boolean generateMiniTrunk(SupportChunk chunk, int x, int y, int z, TreeType treeType) {
		return generateMiniTree(chunk, x, y, z, treeType, false);
	}

	@Override
	public boolean generateNormalTrunk(SupportChunk chunk, int x, int y, int z, TreeType treeType) {
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
			return false;
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
			return false;
		}

		BlackMagic.setBlocks(chunk, x, x + trunkWidth, y, y + trunkHeight, z, z + trunkWidth, trunkMaterial, trunkBlackMagicData);
		return true;
	}

	@Override
	public boolean generateMiniTree(SupportChunk chunk, int x, int y, int z, TreeType treeType) {
		return generateMiniTree(chunk, x, y, z, treeType, true);
	}

	@Override
	public boolean generateNormalTree(SupportChunk chunk, int x, int y, int z, TreeType treeType) {
		boolean result = false;
		
		// where do we start?
		int bottomY = y;
		int trunkWidth = 1;
		Block base = chunk.getActualBlock(x, y - 1, z);
		Material baseMaterial = base.getType();
		byte baseData = BlackMagic.getMaterialData(base);
		if (treeType == TreeType.DARK_OAK)
			trunkWidth = 2;
		
		try {
			int tries = 0;
			
			// keep moving up till we get a tree
			while (tries < maxTries) {
				
				// a place to plant
				chunk.setBlocks(x, x + trunkWidth, y - 1, z, z + trunkWidth, Material.DIRT);
				
				// did we make a tree?
				result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType);
				
				// did it finally work?
				if (result) {
					
					// copy the trunk down a bit
					Block root = chunk.getActualBlock(x, y, z);
					BlackMagic.setBlocks(chunk, x, x + trunkWidth, bottomY, y, z, z + trunkWidth, 
							root.getType(), BlackMagic.getMaterialData(root));
					
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
				chunk.setBlocks(x, x + trunkWidth, bottomY, y, z, z + trunkWidth, Material.AIR);
 			
			// set the base back to what it was originally
			BlackMagic.setBlocks(chunk, x, x + trunkWidth, bottomY - 1, z, z + trunkWidth, baseMaterial, baseData);
		}
		return result;
	}
	
	private boolean generateMiniTree(SupportChunk chunk, int x, int y, int z, TreeType treeType, boolean includeLeaves) {
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
			return false;
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
			return false;
		}

		// do the trunk
		BlackMagic.setBlocks(chunk, x, y, y + trunkHeight, z, trunkMaterial, trunkBlackMagicData);
		
		// and then do the leaves... maybe
		if (includeLeaves) {
			int leavesHeight = trunkHeight - 1;
			BlackMagic.setBlock(chunk, x - 1, y + leavesHeight, z, leavesMaterial, trunkBlackMagicData);
			BlackMagic.setBlock(chunk, x + 1, y + leavesHeight, z, leavesMaterial, trunkBlackMagicData);
			BlackMagic.setBlock(chunk, x, y + leavesHeight, z - 1, leavesMaterial, trunkBlackMagicData);
			BlackMagic.setBlock(chunk, x, y + leavesHeight, z + 1, leavesMaterial, trunkBlackMagicData);
			BlackMagic.setBlock(chunk, x, y + trunkHeight, z, leavesMaterial, trunkBlackMagicData);
		}
		
		return true;
	}
}
