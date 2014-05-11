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
}
