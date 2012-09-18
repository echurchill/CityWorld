package me.daddychurchill.CityWorld.Clipboard;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public abstract class Clipboard {

	public String name;
	public double oddsOfAppearance = 1.0;//TODO 0.10;
	public int groundLevelY = 0;

	public int sizeX;
	public int sizeY;
	public int sizeZ;
	public int blockCount;
	
	public int chunkX;
	public int chunkZ;
	
	public int insetNorth;
	public int insetSouth;
	public int insetWest;
	public int insetEast;
	
	public Clipboard(WorldGenerator generator, String name) {
		super();
		this.name = name;
	}
	
	public abstract void paste(WorldGenerator generator, RealChunk chunk, Direction.Facing facing, 
			int blockX, int blockY, int blockZ);
	public abstract void paste(WorldGenerator generator, RealChunk chunk, Direction.Facing facing, 
			int blockX, int blockY, int blockZ,
			int x1, int x2, int y1, int y2, int z1, int z2);
	
	protected void setSizes(int x, int y, int z) {
		sizeX = x;
		sizeY = y;
		sizeZ = z;
		blockCount = sizeX * sizeY * sizeZ;
		
		chunkX = (sizeX + SupportChunk.chunksBlockWidth) / SupportChunk.chunksBlockWidth;
		chunkZ = (sizeZ + SupportChunk.chunksBlockWidth) / SupportChunk.chunksBlockWidth;
		CityWorld.reportMessage("Loaded: name = " + name + " chunk = " + chunkX + ", " + chunkZ + ", size = " + sizeX + ", " + sizeZ);
		
		int leftoverX = chunkX * SupportChunk.chunksBlockWidth - sizeX;
		int leftoverZ = chunkZ * SupportChunk.chunksBlockWidth - sizeZ;
		
		insetWest = leftoverX / 2;
		insetEast = leftoverX - insetWest;
		insetNorth = leftoverZ / 2;
		insetSouth = leftoverZ - insetNorth;
		
//		// where to paste to?
//		int blockX = (originX + placeX) * SupportChunk.chunksBlockWidth + insetNorth;
//		int blockZ = (originZ + placeZ) * SupportChunk.chunksBlockWidth + insetWest;
//		int blockY = generator.structureLevel - clip.groundLevelY;
//		CityWorld.reportMessage("PlatMap: " + clip.name + 
//				" block = " + blockX + ", " + blockY + ", " + blockZ +
//				" chunk = " + chunksX + ", " + chunksZ);
//		
//		// y wize?
//		int y1 = 0;
//		int y2 = clip.sizeY;
//		
//		// calculate the various template plats
//		for (int x = 0; x < chunksX; x++) {
//			for (int z = 0; z < chunksZ; z++) {
//				
//				// were to paste from
//				int x1, x2, z1, z2;
//				
//				// north edge
//				if (z == 0) {
//					z1 = 0;
//					z2 = SupportChunk.chunksBlockWidth - insetNorth;
//
//				// south edge
//				} else if (z == chunksZ - 1) {
//					z1 = clip.sizeZ - insetSouth;
//					z2 = clip.sizeZ;
//				
//				// the reset of the z bits
//				} else {
//					z1 = (z - 1) * SupportChunk.chunksBlockWidth + insetNorth;
//					z2 = z1 + SupportChunk.chunksBlockWidth;
//				}
//				
//				// west edge
//				if (x == 0) {
//					x1 = 0;
//					x2 = SupportChunk.chunksBlockWidth - insetWest;
//
//				// east edge
//				} else if (x == chunksX - 1) {
//					x1 = clip.sizeX - insetEast;
//					x2 = clip.sizeX;
//					
//				// the reset of the x bits
//				} else {
//					x1 = (x - 1) * SupportChunk.chunksBlockWidth + insetWest;
//					x2 = x1 + SupportChunk.chunksBlockWidth;
//				}
//				
//				// create and place the template plat
//				setLot(x, z, new ClipboardLot(this, originX + placeX + x, originZ + placeZ + z,
//						clip, facing, blockX, blockY, blockZ, 
//						x1, x2, y1, y2, z1, z2));
//			}
//		}
//	}
//}
		
	}
	
	public Direction.Facing randomFacing(Random random) {
		switch (random.nextInt(4)) {
		case 0:
			return Direction.Facing.SOUTH;
		case 1:
			return Direction.Facing.WEST;
		case 2:
			return Direction.Facing.NORTH;
		default:
			return Direction.Facing.EAST;
		}
	}
}
