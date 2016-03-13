package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RememberedBlocks;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.BlockFace;

public class TreeProvider_Normal extends TreeProvider {

	public TreeProvider_Normal() {
		// TODO Auto-generated constructor stub
	}

	private static int maxDepth = 5;
	private int generateRootBall(SupportBlocks chunk, RememberedBlocks originalBlocks, int x1, int x2, int y1, int z1, int z2, Material root) {
		
		// set things up
		int y = y1;
		boolean foundBase = false;
		while (!foundBase && y > y1 - maxDepth) {

			// assume success
			boolean partialLevel = false;
			for (int x = x1; x < x2; x++) {
				for (int z = z1; z < z2; z++) {
					if (chunk.isEmpty(x, y, z)) {
						partialLevel = true;
					}
					if (partialLevel)
						break;
				}
				if (partialLevel)
					break;
			}
			
			// failed? if so move down
			if (partialLevel) {
				
				// clear this level but remember what was there
				originalBlocks.clearBlocks(x1, x2, y, z1, z2);
				y--;
			} else
				foundBase = true;
		}
		
		// add the root base
		if (foundBase) {
			originalBlocks.setBlocks(x1, x2, y, z1, z2, root);
			
			// clear a bit of room out above it
			originalBlocks.clearBlocks(x1, x2, y + 1, y + 3, z1, z2);

			// return just above the root ball
			return y + 1;
		} else {
			return 0;
		}
	}
	
	private static int maxTries = 3;
	@Override
	public boolean generateNormalTree(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, TreeType treeType) {
		boolean result = false;
		
		// how wide is the trunk?
		int trunkWidth = 1;
		int trunkHeight = odds.getRandomInt(3, 6);
		switch (treeType) {
		case DARK_OAK:
		case JUNGLE:
		case MEGA_REDWOOD:
			trunkWidth = 2;
			trunkHeight = trunkHeight * 2;

			// scoot the origin if needed
			if (x == 15)
				x = 14;
			if (z == 15)
				z = 14;
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
			break;
		default:
			break;
		}
		
		// let try and plant something, or at least give it a whirl
		RememberedBlocks originalBlocks = new RememberedBlocks(chunk);
		int rootAt = generateRootBall(chunk, originalBlocks, x, x + trunkWidth, y, z, z + trunkWidth, root);
		if (rootAt == 0) {
			originalBlocks.restoreBlocks();
		} else {
			
			// keep moving up till we get a tree
			int tries = 0;
			while (tries < maxTries) {
				
				// did we make a tree?
				result = chunk.world.generateTree(chunk.getBlockLocation(x, rootAt, z), treeType);
				
				// did it finally work?
				if (result) 
					break;
				
				// and again?
				tries++;
			}
				
			// if a tree then there isn't really anything else to do
			if (result) {
				originalBlocks.forgetBlocks();

			} else { // fine... lets see if a tree trunk will do instead
				
				// one barren tree trunk please
				if (odds.playOdds(Odds.oddsUnlikely)) {
					
					// create the trunk
					chunk.setLogs(x, x + trunkWidth, rootAt, rootAt + trunkHeight, z, z + trunkWidth, Material.LOG, getTreeSpecies(treeType), BlockFace.UP);
					
					// roughen up the top bit
					if (trunkWidth > 1)
						chunk.clearBlocks(x, x + trunkWidth, rootAt + trunkHeight - 1, z, z + trunkWidth, odds);
				} else
					originalBlocks.restoreBlocks();
			}
		}
		return result;
	}
}
