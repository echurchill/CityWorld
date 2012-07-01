package me.daddychurchill.CityWorld.Support;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.Material;
import org.bukkit.World;

public abstract class SupportChunk {
	
	public World world;
	public int chunkX;
	public int chunkZ;
	public int worldX;
	public int worldZ;
	public int width;
	public int height;
	public Random random;
	
//	private byte[] ores;
	
	public static final int chunksBlockWidth = 16;
	public static final int sectionsPerChunk = 16;
	
	public static final byte airId = (byte) Material.AIR.getId();
	public static final byte stoneId = (byte) Material.STONE.getId();
	public static final byte ironId = (byte) Material.IRON_ORE.getId();
	public static final byte goldId = (byte) Material.GOLD_ORE.getId();
	public static final byte lapisId = (byte) Material.LAPIS_ORE.getId();
	public static final byte redstoneId = (byte) Material.REDSTONE_ORE.getId();
	public static final byte diamondId = (byte) Material.DIAMOND_ORE.getId();
	public static final byte coalId = (byte) Material.COAL_ORE.getId();
	public static final byte dirtId = (byte) Material.DIRT.getId();
	public static final byte grassId = (byte) Material.GRASS.getId();
	public static final byte iceId = (byte) Material.ICE.getId(); // the fluid type
	public static final byte waterId = (byte) Material.WATER.getId(); // the fluid type
	public static final byte lavaId = (byte) Material.LAVA.getId(); // the fluid type
	public static final byte stillLavaId = (byte) Material.STATIONARY_LAVA.getId(); // the fluid type
	
	public SupportChunk(WorldGenerator generator, Random aRandom) {
		super();
		
		world = generator.getWorld();
		random = aRandom;
		
		width = chunksBlockWidth;
		height = generator.height;
	}
	
	public int getBlockX(int x) {
		return getOriginX() + x;
	}

	public int getBlockZ(int z) {
		return getOriginZ() + z;
	}

	public int getOriginX() {
		return chunkX * width;
	}

	public int getOriginZ() {
		return chunkZ * width;
	}

//	public byte getOre(int y) {
//		// a VERY VERY rough approximation of http://www.minecraftwiki.net/wiki/Ore
//		
//		// haven't been here before
//		if (ores == null)
//			ores = new byte[sectionsPerChunk];
//		
//		// what is the random ore for this section?
//		int section = y >> 4;
//		if (ores[section] == 0) {
//			if (inRange(y, 14, 200)) //      diamond, red(more), lapis, gold, iron, coal, fluid(air)
//				ores[section] = pickRandomMineral(14);
//			else if (inRange(y, 16, 192)) // red, lapis, gold, iron, coal, fluid(air)
//				ores[section] = pickRandomMineral(12);
//			else if (inRange(y, 30, 137)) // lapis, gold(more), iron, coal, fluid(air)
//				ores[section] = pickRandomMineral(11);  
//			else if (inRange(y, 32, 129)) // gold, iron, coal, fluid(air)
//				ores[section] = pickRandomMineral(9); 
//			else if (inRange(y, 66, 100)) // iron, coal, fluid(air)
//				ores[section] = pickRandomMineral(8);  
//			else //                          coal
//				ores[section] = pickRandomMineral(4); 
//		}
//		
//		// return it
//		return ores[section];
//	}
//	
//	private byte pickRandomMineral(int max) {
//		switch (random.nextInt(max)) {
//		default:
//		case 1:
//		case 2:
//		case 3: return coalId;
//		case 4:
//		case 5:
//		case 6: return ironId;
//		case 7: return waterId;
//		case 8:
//		case 9: return goldId;
//		case 10: return lapisId;
//		case 11:
//		case 12: return redstoneId;
//		case 13: return diamondId;
//		}
//	}
//	
//	private boolean inRange(int blockY, int lower, int upper) {
//		return blockY <= lower || blockY >= upper;
//	}

	protected abstract void setBlock(int x, int y, int z, byte materialId);
	protected abstract void setBlocks(int x1, int x2, int y, int z1, int z2, byte materialId);
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y, byte materialId) {
		// Ref: Notes/BCircle.PDF
//		setBlock(cx + x, y, cz + z, coalId); // point in octant 1
//		setBlock(cx + z, y, cz + x, ironId); // point in octant 2
//		setBlock(cx - z - 1, y, cz + x, goldId); // point in octant 3
//		setBlock(cx - x - 1, y, cz + z, lapisId); // point in octant 4
//		setBlock(cx - x - 1, y, cz - z - 1, redstoneId); // point in octant 5
//		setBlock(cx - z - 1, y, cz - x - 1, diamondId); // point in octant 6
//		setBlock(cx + z, y, cz - x - 1, stoneId); // point in octant 7
//		setBlock(cx + x, y, cz - z - 1, grassId); // point in octant 8
		setBlock(cx + x, y, cz + z, materialId); // point in octant 1
		setBlock(cx + z, y, cz + x, materialId); // point in octant 2
		setBlock(cx - z - 1, y, cz + x, materialId); // point in octant 3
		setBlock(cx - x - 1, y, cz + z, materialId); // point in octant 4
		setBlock(cx - x - 1, y, cz - z - 1, materialId); // point in octant 5
		setBlock(cx - z - 1, y, cz - x - 1, materialId); // point in octant 6
		setBlock(cx + z, y, cz - x - 1, materialId); // point in octant 7
		setBlock(cx + x, y, cz - z - 1, materialId); // point in octant 8
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y, byte materialId) {
		// Ref: Notes/BCircle.PDF
		setBlocks(cx - x - 1, cx - x, y, cz - z - 1, cz + z + 1, materialId); // point in octant 5
		setBlocks(cx - z - 1, cx - z, y, cz - x - 1, cz + x + 1, materialId); // point in octant 6
		setBlocks(cx + z, cx + z + 1, y, cz - x - 1, cz + x + 1, materialId); // point in octant 7
		setBlocks(cx + x, cx + x + 1, y, cz - z - 1, cz + z + 1, materialId); // point in octant 8
	}
	
	public void setCircle(int cx, int cz, int r, int y, byte materialId, boolean fill) {
		// Ref: Notes/BCircle.PDF
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			if (fill)
				fillCircleBlocks(cx, cz, x, z, y, materialId);
			else
				drawCircleBlocks(cx, cz, x, z, y, materialId);
			z++;
			rError += zChange;
			zChange += 2;
			if (2 * rError + xChange > 0) {
				x--;
				rError += xChange;
				xChange += 2;
			}
		}
	}
	
}
