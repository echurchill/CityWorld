package me.daddychurchill.CityWorld.Clipboard;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.material.MaterialData;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealSection;
import me.daddychurchill.CityWorld.Support.SupportSection;

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
	public Material edgeType;
	public MaterialData edgeData;
	public int edgeRise;
	
	public Clipboard(CityWorldGenerator generator, File file) throws Exception {
		super();
		this.name = file.getName();
		
		// grab the data
		load(generator, file);
		
		// finish figuring things out
		blockCount = sizeX * sizeY * sizeZ;
		
		chunkX = (sizeX + SupportSection.sectionBlockWidth - 1) / SupportSection.sectionBlockWidth;
		chunkZ = (sizeZ + SupportSection.sectionBlockWidth - 1) / SupportSection.sectionBlockWidth;
		
		int leftoverX = chunkX * SupportSection.sectionBlockWidth - sizeX;
		int leftoverZ = chunkZ * SupportSection.sectionBlockWidth - sizeZ;
		
		insetWest = leftoverX / 2;
		insetEast = leftoverX - insetWest;
		insetNorth = leftoverZ / 2;
		insetSouth = leftoverZ - insetNorth;
	}
	
	protected abstract void load(CityWorldGenerator generator, File file) throws Exception;
	public abstract void paste(CityWorldGenerator generator, RealSection chunk, BlockFace facing, 
			int blockX, int blockY, int blockZ);
	public abstract void paste(CityWorldGenerator generator, RealSection chunk, BlockFace facing, 
			int blockX, int blockY, int blockZ,
			int x1, int x2, int y1, int y2, int z1, int z2);
}
