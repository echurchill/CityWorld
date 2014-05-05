package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.TreeType;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public abstract class TreeProvider {
	
	public TreeProvider() {
		// TODO Auto-generated constructor stub
	}

	public static TreeProvider loadProvider(WorldGenerator generator) {

		TreeProvider provider = null;
		
		// get the right defaults
		switch (generator.worldEnvironment) {
		case NORMAL:
			return new TreeProvider_Normal();
		case NETHER:
			return new TreeProvider_Custom();
		case THE_END:
			return new TreeProvider_Custom();
		}
		
		return provider;
	}
	
	public abstract boolean generateMiniTrunk(SupportChunk chunk, int x, int y, int z, TreeType treeType);
	public abstract boolean generateNormalTrunk(SupportChunk chunk, int x, int y, int z, TreeType treeType);
	public abstract boolean generateMiniTree(SupportChunk chunk, int x, int y, int z, TreeType treeType);
	public abstract boolean generateNormalTree(SupportChunk chunk, int x, int y, int z, TreeType treeType);
	
//	public boolean generateTrunk(SupportChunk chunk, int x, int y, int z, TreeStyle height, TreeType type) {
//		switch (type) {
//		case MINI_BIRCH:
//			return generateMiniTrunk(chunk, x, y, z, TreeType.BIRCH);
//		case SHORT_BIRCH:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.BIRCH); // BUKKIT: there isn't a smaller than normal Birch tree
//		case BIRCH:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.BIRCH);
//		case TALL_BIRCH:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.TALL_BIRCH);
//
//		case MINI_PINE:
//			return generateMiniTrunk(chunk, x, y, z, TreeType.REDWOOD);
//		case SHORT_PINE:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.REDWOOD);
//		case PINE:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.TALL_REDWOOD);
//		case TALL_PINE:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.MEGA_REDWOOD);
//
//		case MINI_OAK:
//			return generateMiniTrunk(chunk, x, y, z, TreeType.TREE);
//		case SHORT_OAK:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.TREE);
//		case OAK:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.BIG_TREE);
//		case TALL_OAK:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.DARK_OAK);
//
//		case MINI_JUNGLE:
//			return generateMiniTrunk(chunk, x, y, z, TreeType.JUNGLE);
//		case SHORT_JUNGLE:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.JUNGLE_BUSH);
//		case JUNGLE:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.SMALL_JUNGLE);
//		case TALL_JUNGLE:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.JUNGLE);
//
//		case SWAMP:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.SWAMP);
//		case ACACIA:
//			return generateNormalTrunk(chunk, x, y, z, TreeType.ACACIA);
////		case BROWN_MUSHROOM:
////			return generateNormalTrunk(chunk, x, y, z, TreeType.BROWN_MUSHROOM, trunk, leaves1, leaves2);
////		case RED_MUSHROOM:
////			return generateNormalTrunk(chunk, x, y, z, TreeType.RED_MUSHROOM, trunk, leaves1, leaves2);
//		default:
//			return false;
//		}
//	}
	
//	public boolean generateTree(SupportChunk chunk, int x, int y, int z, TreeHeight height, TreeType type) {
//		switch (type) {
//		case MINI_BIRCH:
//			return generateMiniTree(chunk, x, y, z, TreeType.BIRCH);
//		case SHORT_BIRCH:
//			return generateNormalTree(chunk, x, y, z, TreeType.BIRCH); // BUKKIT: there isn't a smaller than normal Birch tree
//		case BIRCH:
//			return generateNormalTree(chunk, x, y, z, TreeType.BIRCH);
//		case TALL_BIRCH:
//			return generateNormalTree(chunk, x, y, z, TreeType.TALL_BIRCH);
//
//		case MINI_PINE:
//			return generateMiniTree(chunk, x, y, z, TreeType.REDWOOD);
//		case SHORT_PINE:
//			return generateNormalTree(chunk, x, y, z, TreeType.REDWOOD);
//		case PINE:
//			return generateNormalTree(chunk, x, y, z, TreeType.TALL_REDWOOD);
//		case TALL_PINE:
//			return generateNormalTree(chunk, x, y, z, TreeType.MEGA_REDWOOD);
//
//		case MINI_OAK:
//			return generateMiniTree(chunk, x, y, z, TreeType.TREE);
//		case SHORT_OAK:
//			return generateNormalTree(chunk, x, y, z, TreeType.TREE);
//		case OAK:
//			return generateNormalTree(chunk, x, y, z, TreeType.BIG_TREE);
//		case TALL_OAK:
//			return generateNormalTree(chunk, x, y, z, TreeType.DARK_OAK);
//
//		case MINI_JUNGLE:
//			return generateMiniTree(chunk, x, y, z, TreeType.JUNGLE);
//		case SHORT_JUNGLE:
//			return generateNormalTree(chunk, x, y, z, TreeType.JUNGLE_BUSH);
//		case JUNGLE:
//			return generateNormalTree(chunk, x, y, z, TreeType.SMALL_JUNGLE);
//		case TALL_JUNGLE:
//			return generateNormalTree(chunk, x, y, z, TreeType.JUNGLE);
//
//		case SWAMP:
//			return generateNormalTree(chunk, x, y, z, TreeType.SWAMP);
//		case ACACIA:
//			return generateNormalTree(chunk, x, y, z, TreeType.ACACIA);
////		case BROWN_MUSHROOM:
////			return generateNormalTree(chunk, x, y, z, TreeType.BROWN_MUSHROOM, trunk, leaves1, leaves2);
////		case RED_MUSHROOM:
////			return generateNormalTree(chunk, x, y, z, TreeType.RED_MUSHROOM, trunk, leaves1, leaves2);
//		default:
//			return false;
//		}
//	}
}
