package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

import org.bukkit.Material;
import org.bukkit.TreeType;

public class TreeProvider_Normal extends TreeProvider {

	public TreeProvider_Normal() {
		// TODO Auto-generated constructor stub
	}

//	private int maxTries = 3;

	private boolean generateRoot(SupportBlocks chunk, int x1, int y1, int z1, int width, Material root) {
		boolean rootMake = true;
		for (int x = x1; x < x1 + width; x++)
			for (int z = z1; z < z1 + width; z++)
				if (chunk.isEmpty(x, y1, z)) {
					rootMake = false;
				}
		if (rootMake)
			chunk.setBlocks(x1, x1 + width, y1, z1, z1 + width, root);
		return rootMake;
	}
	
	@Override
	public boolean generateNormalTree(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, TreeType treeType) {
		boolean result = false;
		
		// where do we start?
//		int bottomY = y;
//		Block base = chunk.getActualBlock(x, bottomY - 1, z);
//		Material baseMat = base.getType();
//		short baseDat = BlackMagic.getMaterialData(base);
		
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
		
		// let try and plant something, or at least give it a whirl
		boolean rootMade = generateRoot(chunk, x, y - 1, z, trunkWidth, root);
		if (!rootMade) {
			y--;
			rootMade = generateRoot(chunk, x, y - 1, z, trunkWidth, root);
		}
		if (rootMade) {
			chunk.clearBlocks(x, x + trunkWidth, y, y + 2, z, z + trunkWidth);
//			try {
//				int tries = 0;
//				
//				// keep moving up till we get a tree
//				while (tries < maxTries) {
//					
//					// a place to plant
//					chunk.setBlocks(x, x + trunkWidth, y - 1, y, z, z + trunkWidth, root);
					
					// did we make a tree?
					result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType);
					
//					// did it finally work?
//					if (result) {
//						
//						// do we need to backfill?
//						if (y != bottomY) {
//							
//							// what type of trunk
//							Block trunk = chunk.getActualBlock(x, y, z);
//							
//							// copy the trunk down a bit
//							BlackMagic.setBlocks(chunk, x, x + trunkWidth, bottomY, y, z, z + trunkWidth, trunk);
//						}
//						
//						//TODO If the trunkWidth is > 1 we might need to copy the dirt down so the dirt doesn't look like it is floating
//						
//						// all done
//						break;
//						
//					// on failure move a bit more
//					} else {
//						y++;
//					}
//					
//					// and again?
//					tries++;
//				}
//			} finally {
//				
//				// if we actually failed remove all that dirt we made
//				if (!result)
//					chunk.setBlocks(x, x + trunkWidth, bottomY - 2, y + 1, z, z + trunkWidth, Material.GLOWSTONE);
//	 			
//				// set the base back to what it was originally
//				BlackMagic.setBlocks(chunk, x, x + trunkWidth, bottomY - 1, bottomY, z, z + trunkWidth, baseMat, baseDat);
//			}
		}
		return result;
	}
}
