package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.TreeType;

public class FoliageProvider_Nether extends FoliageProvider {
	
	public FoliageProvider_Nether(Random random) {
		super(random);
	}
	
	private final static int airId = Material.AIR.getId();
	private final static int glowId = Material.GLOWSTONE.getId();
	private final static int glassId = Material.GLASS.getId();
	private final static int paneId = Material.THIN_GLASS.getId();
	private final static int obsidianId = Material.OBSIDIAN.getId();
	private final static int clayId = Material.CLAY.getId();
	private final static int ironId = Material.IRON_FENCE.getId();
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, TreeType treeType) {
		int trunkId = logId;
		int leavesId1 = airId;
		int leavesId2 = airId;
		switch (treeType) {
		case BIRCH:
			trunkId = clayId;
		case TREE:
			if (random.nextDouble() < 0.10) {
				leavesId1 = ironId;
				leavesId2 = ironId;
				if (random.nextDouble() < 0.10) {
					trunkId = glowId;
				}
			} 
			break;
		case BIG_TREE:
			trunkId = obsidianId;
			if (random.nextDouble() < 0.20) {
				leavesId1 = ironId;
				leavesId2 = ironId;
				if (random.nextDouble() < 0.10) {
					trunkId = glowId;
				}
			} 
			break;
		case TALL_REDWOOD:
		case REDWOOD:
			trunkId = obsidianId;
			if (random.nextDouble() < 0.10) {
				leavesId1 = paneId;
				if (random.nextDouble() < 0.10)
					leavesId2 = glowId;
				else
					leavesId2 = glassId;
			}
			break;
		default:
			break;
		}
		return generateTree(chunk, random, x, y, z, treeType, trunkId, leavesId1, leavesId2);
	}

	@Override
	public boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, FloraType floraType) {
		if (likelyFlora(generator, random)) {
			switch (floraType) {
			case FLOWER_RED:
				chunk.setBlock(x, y - 1, z, Material.MYCEL);
				chunk.setBlock(x, y, z, Material.RED_MUSHROOM);
				break;
			case FLOWER_YELLOW:
				chunk.setBlock(x, y - 1, z, Material.MYCEL);
				chunk.setBlock(x, y, z, Material.BROWN_MUSHROOM);
				break;
			case GRASS:
			case FERN:
				chunk.setBlock(x, y, z, Material.DEAD_BUSH);
				break;
			}
			return true;
		}
		return false;
	}
	
//
//	protected void generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, TreeType treeType) {
//		generateTree(generator, chunk, x, y, z, treeType, false);
//	}
//	
//	protected void generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, TreeType treeType, boolean forceLeaves) {
//		if (generator.settings.includeDecayedNature) {
//			chunk.setBlock(x, y - 1, z, Material.DIRT, true);
//			if (forceLeaves)
//				chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType, new TreeDelegate(chunk, TreeDelegateStyle.METAL));
//			else
//				chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType, new TreeDelegate(chunk, TreeDelegateStyle.TRUNKONLY));
//			chunk.setBlock(x, y - 1, z, Material.SAND, false);
//		} else {
//			chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType);
//		}
////		case THE_END:
////			chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType, new TreeDelegate(chunk, TreeDelegateStyle.CRYSTAL));
////			break;
//	}
//	
//	protected enum TreeDelegateStyle {NORMAL, TRUNKONLY, CRYSTAL, METAL};
//	
//	private class TreeDelegate implements BlockChangeDelegate {
//		private RealChunk chunk;
//		private TreeDelegateStyle style;
//		
//		public TreeDelegate(RealChunk chunk, TreeDelegateStyle style) {
//			this.chunk = chunk;
//			this.style = style;
//		}
//
//		@Override
//		public int getHeight() {
//			return chunk.height;
//		}
//
//		@Override
//		public int getTypeId(int x, int y, int z) {
//			return chunk.world.getBlockAt(x, y, z).getTypeId();
//		}
//
//		@Override
//		public boolean isEmpty(int x, int y, int z) {
//			return chunk.world.getBlockAt(x, y, z).isEmpty();
//		}
//
//		@Override
//		public boolean setRawTypeId(int x, int y, int z, int id) {
//			return setTypeIdAndData(x, y, z, id, 0, false);
//		}
//
//		@Override
//		public boolean setRawTypeIdAndData(int x, int y, int z, int id, int data) {
//			return setTypeIdAndData(x, y, z, id, data, false);
//		}
//
//		@Override
//		public boolean setTypeId(int x, int y, int z, int id) {
//			return setTypeIdAndData(x, y, z, id, 0, true);
//		}
//
//		@Override
//		public boolean setTypeIdAndData(int x, int y, int z, int id, int data) {
//			return setTypeIdAndData(x, y, z, id, data, true);
//		}
//		
//		private boolean setTypeIdAndData(int x, int y, int z, int id, int data, boolean update) {
//			Block block = chunk.world.getBlockAt(x, y, z);
//			switch (style) {
//			case NORMAL:
//				return block.setTypeIdAndData(id, (byte) data, update);
//			case TRUNKONLY:
//				if (id == logId)
//					return block.setTypeIdAndData(id, (byte) data, update);
//				else
//					return false;
//			case CRYSTAL:
//				if (id == logId)
//					return block.setTypeId(glowId, update);
//				else if (id == leavesId)
//					if (chunkRandom.nextBoolean())
//						return block.setTypeId(glassId, update);
//					else
//						return block.setTypeId(paneId, update);
//				else
//					return false;
//			case METAL:
//				if (id == logId)
//					return block.setTypeId(clayId, update);
//				else if (id == leavesId)
//					return block.setTypeId(ironFenceId, update);
//				else
//					return false;
//			default:
//				return false;
//			}
//		}
//	}
}
