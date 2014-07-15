package me.daddychurchill.CityWorld.Support;

import org.bukkit.Material;
import org.bukkit.World;
import me.daddychurchill.CityWorld.WorldGenerator;

public abstract class AbstractChunk {

	public World world;
	public int width;
	public int height;
	public int chunkX;
	public int chunkZ;
	
	public final static int chunksBlockWidth = 16;
	
	public AbstractChunk(WorldGenerator generator) {
		super();
		
		this.width = chunksBlockWidth;
		this.world = generator.getWorld();
		this.height = generator.height;
	}

	public final static int getBlockX(int chunkX, int x) {
		return chunkX * chunksBlockWidth + x;
	}
	
	public final static int getBlockZ(int chunkZ, int z) {
		return chunkZ * chunksBlockWidth + z;
	}
	
	public final int getBlockX(int x) {
		return getOriginX() + x;
	}

	public final int getBlockZ(int z) {
		return getOriginZ() + z;
	}

	public final int getOriginX() {
		return chunkX * width;
	}

	public final int getOriginZ() {
		return chunkZ * width;
	}

	public abstract void setBlockIfAir(int x, int y, int z, Material material);
	public abstract void setBlock(int x, int y, int z, Material material);
	public abstract void setBlocks(int x, int y1, int y2, int z, Material material);
	public abstract void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material);
	public abstract void setBlocks(int x1, int x2, int y, int z1, int z2, Material material);
	public abstract void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material);
	
	public abstract int setLayer(int blocky, Material material);
	public abstract int setLayer(int blocky, int height, Material material);
	public abstract int setLayer(int blocky, int height, int inset, Material material);
	public abstract void setCircle(int cx, int cz, int r, int y, Material material);
	public abstract void setCircle(int cx, int cz, int r, int y, Material material, boolean fill);
	public abstract void setCircle(int cx, int cz, int r, int y1, int y2, Material material);
	public abstract void setCircle(int cx, int cz, int r, int y1, int y2, Material material, boolean fill);
	
	public abstract void clearBlock(int x, int y, int z);
	public abstract void clearBlocks(int x, int y1, int y2, int z);
	public abstract void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2);
	public abstract boolean setEmptyBlock(int x, int y, int z, Material material);
	public abstract void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, Material material);
	public abstract int findFirstEmpty(int x, int y, int z);
	public abstract int findFirstEmptyAbove(int x, int y, int z);
	public abstract int findLastEmptyAbove(int x, int y, int z);
	public abstract int findLastEmptyBelow(int x, int y, int z);

}
