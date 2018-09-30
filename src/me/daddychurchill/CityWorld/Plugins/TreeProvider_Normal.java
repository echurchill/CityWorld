package me.daddychurchill.CityWorld.Plugins;

public class TreeProvider_Normal extends TreeProvider {

	public TreeProvider_Normal() {
		// TODO Auto-generated constructor stub
	}

//	@Override
//	protected boolean generateNormalTree(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, TreeType treeType, boolean includeLeaves) {
//		// most of the time we do it our way
//		if (odds.playOdds(Odds.oddsPrettyLikely)) {
//			return super.generateNormalTree(generator, chunk, x, y, z, treeType, includeLeaves);
//			
//		// some of the time we really actually try
//		} else {
//			boolean result = false;
//			
//			// how wide is the trunk?
//			int trunkWidth = 1;
//			int trunkHeight = odds.getRandomInt(3, 6);
//			switch (treeType) {
//			case DARK_OAK:
//			case JUNGLE:
//			case MEGA_REDWOOD:
//				trunkWidth = 2;
//				trunkHeight = trunkHeight * 2;
//	
//				// scoot the origin if needed
//				if (x == 15)
//					x = 14;
//				if (z == 15)
//					z = 14;
//				break;
//			default:
//				break;
//			}
//			
//			// what is under the trunk?
//			Material root = Material.DIRT;
//			switch (treeType) {
//			case BROWN_MUSHROOM:
//			case RED_MUSHROOM:
//				root = Material.MYCELIUM;
//				break;
//			default:
//				break;
//			}
//			
//			// let try and plant something, or at least give it a whirl
//			RememberedBlocks originalBlocks = new RememberedBlocks(chunk);
//			int rootAt = generateRootBall(chunk, originalBlocks, x, x + trunkWidth, y, z, z + trunkWidth, root);
//			
//			// if that didn't work, then never mind
//			if (rootAt == 0) {
//				originalBlocks.restoreBlocks();
//				return false;
//			}
//	
//			// try to make a tree
//			result = chunk.world.generateTree(chunk.getBlockLocation(x, rootAt, z), treeType);
//			
//			// if that worked then we are done
//			if (result) {
//				originalBlocks.forgetBlocks();
//				return result;
//			} 
//			
//			// if it didn't lets try it our way
//			// if it didn't lets try it our way
//			else {
//				originalBlocks.restoreBlocks();
//				
//				// one barren tree trunk please
//				if (odds.playOdds(Odds.oddsUnlikely))
//					return generateNormalTrunk(generator, chunk, x, y, z, treeType);
//				else
//					return super.generateNormalTree(generator, chunk, x, y, z, treeType, includeLeaves);
//			}
//		}
//	}
}
