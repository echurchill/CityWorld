package me.daddychurchill.CityWorld.Clipboard;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.RealChunk;

public abstract class Clipboard {

	public String name;
	public int sizeX;
	public int sizeY;
	public int sizeZ;
	
	public Clipboard(WorldGenerator generator, String name) {
		super();
		this.name = name;
	}
	
	public abstract void paste(WorldGenerator generator, RealChunk chunk, Direction.Facing facing, 
			int blockX, int blockY, int blockZ);
	public abstract void paste(WorldGenerator generator, RealChunk chunk, Direction.Facing facing, 
			int blockX, int blockY, int blockZ,
			int x1, int x2, int y1, int y2, int z1, int z2);
}
