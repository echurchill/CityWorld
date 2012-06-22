package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatFarm extends PlatConnected {

	//TODO Tree farm?
	//TODO Apple farm?
	//TODO Cocoa farm?
	//TODO Mushroom farm?
	
	private boolean directionNorthSouth;
	private Material cropType;

	public PlatFarm(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.STRUCTURE;
		
		directionNorthSouth = chunkRandom.nextBoolean();
		cropType = getCrop();
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
		// other bits
		if (result && relative instanceof PlatFarm) {
			PlatFarm relativeFarm = (PlatFarm) relative;
			
			directionNorthSouth = relativeFarm.directionNorthSouth;
			cropType = relativeFarm.cropType;
		}
		return result;
	}

	private final static Material matWater = Material.STATIONARY_WATER;
	private final static Material matSoil = Material.SOIL;
	private final static Material matSand = Material.SAND;
	private final static Material matDirt = Material.DIRT;
	private final static Material matAir = Material.AIR;
	private final static Material matTrellis = Material.WOOD;
	private final static Material matPole = Material.FENCE;

	private final static Material deadOnDirt = Material.LONG_GRASS;
	private final static Material deadOnSand = Material.DEAD_BUSH; 
	
	private final static Material cropFallow = Material.AIR;
	private final static Material cropNone = Material.STATIONARY_WATER; // we are actually using this as a flag
	private final static Material cropYellowFlower = Material.YELLOW_FLOWER;
	private final static Material cropRedFlower = Material.RED_ROSE;
	private final static Material cropWheat = Material.CROPS;
	private final static Material cropPumpkin = Material.PUMPKIN_STEM;
	private final static Material cropMelon = Material.MELON_STEM;
	private final static Material cropVine = Material.VINE;
	private final static Material cropSugarCane = Material.SUGAR_CANE_BLOCK;
	private final static Material cropGrass = Material.LONG_GRASS;
	
	@Override
	protected void generateRandomness() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		int croplevel = generator.sidewalkLevel + 1;
		
		// what type of crop do we plant?
		if (cropType == cropYellowFlower || cropType == cropRedFlower)
			plowField(chunk, chunkRandom, croplevel, matSoil, 8, matWater, cropType, 0, deadOnDirt, 1, 2, 1);
		else if (cropType == cropGrass)
			plowField(chunk, chunkRandom, croplevel, matSoil, 8, matWater, cropType, 1, deadOnDirt, 1, 2, 1);
		else if (cropType == cropWheat)
			plowField(chunk, chunkRandom, croplevel, matSoil, 8, matWater, cropType, chunkRandom.nextBoolean() ? 5 : 3, deadOnDirt, 1, 2, 1);
		else if (cropType == cropPumpkin || cropType == cropMelon)
			plowField(chunk, chunkRandom, croplevel, matSoil, 8, matWater, cropType, chunkRandom.nextBoolean() ? 5 : 3, deadOnDirt, 1, 3, 1);
		else if (cropType == cropSugarCane)
			plowField(chunk, chunkRandom, croplevel, matSand, 0, matWater, cropType, 0, deadOnSand, 1, 2, 3);
		else if (cropType == cropVine)
			buildVineyard(chunk, chunkRandom, croplevel);
		else if (cropType == cropNone)
			plowField(chunk, chunkRandom, croplevel, matSoil, 8, matWater, matAir, 0, matAir, 1, 2, 1);
		else // cropFallow
			plowField(chunk, chunkRandom, croplevel, matDirt, 0, matAir, cropType, 0, cropFallow, 1, 2, 1);
	}

	private void buildVineyard(RealChunk chunk, Random random, int cropLevel) {
		int stepCol = 3;
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepCol) {
				chunk.setBlocks(x, cropLevel, cropLevel + 4, 1, matPole);
				chunk.setBlocks(x, cropLevel, cropLevel + 4, 14, matPole);
				chunk.setBlocks(x, x + 1, cropLevel + 3, cropLevel + 4, 2, 14, matTrellis);
//				if (!noise.isDecrepit(random)) {
					for (int z = 2; z < 14; z++) {
//						if (!noise.isDecrepit(random)) {
							chunk.setBlocks(x - 1, x, cropLevel + 1 + random.nextInt(3), cropLevel + 4, z, z + 1, cropVine, (byte) 8);
							chunk.setBlocks(x + 1, x + 2, cropLevel + 1 + random.nextInt(3), cropLevel + 4, z, z + 1, cropVine, (byte) 2);
//						}
					}
//				}
			}
		} else {
			for (int z = 1; z < 15; z += stepCol) {
				chunk.setBlocks(1, cropLevel, cropLevel + 4, z, matPole);
				chunk.setBlocks(14, cropLevel, cropLevel + 4, z, matPole);
				chunk.setBlocks(2, 14, cropLevel + 3, cropLevel + 4, z, z + 1, matTrellis);
//				if (!noise.isDecrepit(random)) {
					for (int x = 2; x < 14; x++) {
//						if (!noise.isDecrepit(random)) {
							chunk.setBlocks(x, x + 1, cropLevel + 1 + random.nextInt(3), cropLevel + 4, z - 1, z, cropVine, (byte) 1);
							chunk.setBlocks(x, x + 1, cropLevel + 1 + random.nextInt(3), cropLevel + 4, z + 1, z + 2, cropVine, (byte) 4);
//						}
					}
//				}
			}
		}
	}
	
	private void plowField(RealChunk chunk, Random random, int croplevel, 
			Material matRidge, int datRidge, Material matFurrow, 
			Material matCrop, int datCrop, Material matDead,
			int stepRow, int stepCol, int maxHeight) {
		
		// convert the data types
		byte byteRidge = (byte) datRidge;
		byte byteFurrow = (byte) 0;
		byte byteCrop = (byte) datCrop;
		
		// do the deed
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepCol) {
				chunk.setBlocks(x, x + 1, croplevel - 1, croplevel, 1, 15, matRidge, byteRidge, false);
				if (stepCol > 1)
					chunk.setBlocks(x + 1, x + 2, croplevel - 1, croplevel, 1, 15, matFurrow, byteFurrow, false);
				for (int z = 1; z < 15; z += stepRow)
//					if (noise.isDecrepit(random))
//						chunk.setBlock(x, croplevel, z, matDead);
//					else
						chunk.setBlocks(x, croplevel, croplevel + random.nextInt(maxHeight) + 1, z, matCrop, byteCrop, false);
			}
		} else {
			for (int z = 1; z < 15; z += stepCol) {
				chunk.setBlocks(1, 15, croplevel - 1, croplevel, z, z + 1, matRidge, byteRidge, false);
				if (stepCol > 1)
					chunk.setBlocks(1, 15, croplevel - 1, croplevel, z + 1, z + 2, matFurrow, byteFurrow, false);
				for (int x = 1; x < 15; x += stepRow)
//					if (noise.isDecrepit(random))
//						chunk.setBlock(x, croplevel, z, matDead);
//					else
						chunk.setBlocks(x, croplevel, croplevel + random.nextInt(maxHeight) + 1, z, matCrop, byteCrop, false);
			}
		}
	}
	
	private Material getCrop() {
		switch (chunkRandom.nextInt(12)) {
		case 1:
			return cropYellowFlower;
		case 2:
			return cropRedFlower;
		case 3:
			return cropPumpkin;
		case 4:
			return cropMelon;
		case 5:
			return cropVine;
		case 6:
			return cropGrass;
		case 7:
			return cropSugarCane;
		case 8:
			return cropFallow;
		case 9:
			return cropNone;
		default:
			return cropWheat;
		}
	}

}
