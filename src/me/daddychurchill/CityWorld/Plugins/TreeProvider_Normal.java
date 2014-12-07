package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
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
	public boolean generateNormalTree(WorldGenerator generator, SupportChunk chunk, int x, int y, int z, TreeType treeType) {
		boolean result = false;
		
		// where do we start?
		int bottomY = y;
		Block base = chunk.getActualBlock(x, bottomY - 1, z);
		Material baseMat = base.getType();
		short baseDat = BlackMagic.getMaterialData(base);
		
		// how wide is the trunk?
		int trunkWidth = 1;
		switch (treeType) {
		case DARK_OAK:
		case JUNGLE:
		case MEGA_REDWOOD:
			trunkWidth = 2;
			break;
		default:
			break;
		}
		
		// what is under the trunk?
		Material root = Material.DIRT;
		switch (treeType) {
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
			root = Material.MYCEL;
		default:
			break;
		}
		
		try {
			int tries = 0;
			
			// keep moving up till we get a tree
			while (tries < maxTries) {
				
				// a place to plant
				chunk.setBlocks(x, x + trunkWidth, y - 1, y, z, z + trunkWidth, root);
				
				// did we make a tree?
				result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType);
				
				// did it finally work?
				if (result) {
					
					// do we need to backfill?
					if (y != bottomY) {
						
						// what type of trunk
						Block trunk = chunk.getActualBlock(x, y, z);
						
						// copy the trunk down a bit
						BlackMagic.setBlocks(chunk, x, x + trunkWidth, bottomY, y, z, z + trunkWidth, trunk);
					}
					
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
			BlackMagic.setBlocks(chunk, x, x + trunkWidth, bottomY - 1, bottomY, z, z + trunkWidth, baseMat, baseDat);
		}
		return result;
	}
}
