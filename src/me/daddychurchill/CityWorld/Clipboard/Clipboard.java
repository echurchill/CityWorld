package me.daddychurchill.CityWorld.Clipboard;

import java.io.File;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public abstract class Clipboard {

	public String name;
	public double oddsOfAppearance = DataContext.oddsSomewhatUnlikely;
	public int groundLevelY = 0;
	public boolean broadcastLocation = false;
	public boolean decayable = true;

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
	
	public Material edgeType = Material.AIR;
	public byte edgeData = 0;
	public int edgeRise = 0;
	
	public Clipboard(WorldGenerator generator, File file) throws Exception {
		super();
		this.name = file.getName();
		
		// grab the data
		load(generator, file);
		
		// finish figuring things out
		blockCount = sizeX * sizeY * sizeZ;
		
		chunkX = (sizeX + SupportChunk.chunksBlockWidth - 1) / SupportChunk.chunksBlockWidth;
		chunkZ = (sizeZ + SupportChunk.chunksBlockWidth - 1) / SupportChunk.chunksBlockWidth;
		
		int leftoverX = chunkX * SupportChunk.chunksBlockWidth - sizeX;
		int leftoverZ = chunkZ * SupportChunk.chunksBlockWidth - sizeZ;
		
		insetWest = leftoverX / 2;
		insetEast = leftoverX - insetWest;
		insetNorth = leftoverZ / 2;
		insetSouth = leftoverZ - insetNorth;
	}
	
	protected abstract void load(WorldGenerator generator, File file) throws Exception;
	public abstract void paste(WorldGenerator generator, RealChunk chunk, Direction.Facing facing, 
			int blockX, int blockY, int blockZ);
	public abstract void paste(WorldGenerator generator, RealChunk chunk, Direction.Facing facing, 
			int blockX, int blockY, int blockZ,
			int x1, int x2, int y1, int y2, int z1, int z2);
}
