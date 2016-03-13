package me.daddychurchill.CityWorld.Clipboard;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public abstract class Clipboard {

	public String name;
	public double oddsOfAppearance = Odds.oddsSomewhatUnlikely;
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
	
//	public Material edgeType = Material.AIR;
//	public MaterialData edgeData = new MaterialData(edgeType);
	public Material edgeMaterial;
//	public int edgeData; // TODO: one of these days I need to get this working again
	public int edgeRise;
	
	public Clipboard(CityWorldGenerator generator, File file) throws Exception {
		super();
		this.name = file.getName();
		
		// grab the data
		load(generator, file);
		
		// finish figuring things out
		blockCount = sizeX * sizeY * sizeZ;
		
		chunkX = (sizeX + SupportBlocks.sectionBlockWidth - 1) / SupportBlocks.sectionBlockWidth;
		chunkZ = (sizeZ + SupportBlocks.sectionBlockWidth - 1) / SupportBlocks.sectionBlockWidth;
		
		int leftoverX = chunkX * SupportBlocks.sectionBlockWidth - sizeX;
		int leftoverZ = chunkZ * SupportBlocks.sectionBlockWidth - sizeZ;
		
		insetWest = leftoverX / 2;
		insetEast = leftoverX - insetWest;
		insetNorth = leftoverZ / 2;
		insetSouth = leftoverZ - insetNorth;
	}
	
	protected abstract void load(CityWorldGenerator generator, File file) throws Exception;
	public abstract void paste(CityWorldGenerator generator, RealBlocks chunk, BlockFace facing, 
			int blockX, int blockY, int blockZ);
	public abstract void paste(CityWorldGenerator generator, RealBlocks chunk, BlockFace facing, 
			int blockX, int blockY, int blockZ,
			int x1, int x2, int y1, int y2, int z1, int z2);
}
