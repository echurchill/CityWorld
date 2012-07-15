package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SurroundingFarms;

public class PlatFarm extends PlatConnected {

	//TODO Tree farm?
	//TODO Apple farm?
	//TODO Cocoa farm?
	
	private boolean directionNorthSouth;
	private Material cropType;

	public PlatFarm(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.STRUCTURE;
		
		directionNorthSouth = chunkRandom.nextBoolean();
		
		// crop type please
		if (platmap.generator.settings.environment == Environment.NETHER)
			cropType = getNetherCrop();
		else
			cropType = getNormalCrop();
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

	@Override
	protected int getTopStrataY(WorldGenerator generator, int blockX, int blockZ) {
		return generator.sidewalkLevel - 1;
	}

	private final static byte isolationId = (byte) Material.LOG.getId();
	private final static byte isolationAltId = (byte) Material.NETHER_BRICK.getId();
	
	private final static Material matWater = Material.STATIONARY_WATER;
	private final static Material matSoil = Material.SOIL;
	private final static Material matSand = Material.SAND;
	private final static Material matDirt = Material.DIRT;
	private final static Material matSoul = Material.SOUL_SAND;
	private final static Material matAir = Material.AIR;
	private final static Material matTrellis = Material.WOOD;
	private final static Material matPole = Material.FENCE;

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
	private final static Material cropCactus = Material.CACTUS;
	private final static Material cropBrownMushroom = Material.BROWN_MUSHROOM;
	private final static Material cropRedMushroom = Material.RED_MUSHROOM;
	private final static Material cropNetherwart = Material.NETHER_WARTS;
	private final static Material cropDeadBush = Material.DEAD_BUSH; 
	
	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		// look around
		SurroundingFarms farms = new SurroundingFarms(platmap, platX, platZ);
		
		// what type of ground do we have
		byte groundId = dirtId;
		if (cropType == cropSugarCane || cropType == cropCactus)
			groundId = sandId;
		chunk.setLayer(generator.sidewalkLevel, groundId);
		
		// inbetween bits bits
		byte dividerId = isolationId;
		byte cornerId = grassId;
		if (generator.settings.environment == Environment.NETHER) {
			dividerId = isolationAltId;
			cornerId = sandId;
		}
		
		// draw the isolation blocks
		if (!farms.toNorth()) {
			chunk.setBlocks(1, 15, generator.sidewalkLevel, 0, 1, dividerId);
			if (farms.toWest())
				chunk.setBlock(0, generator.sidewalkLevel, 0, dividerId);
			else
				chunk.setBlock(0, generator.sidewalkLevel, 0, cornerId);
			if (farms.toEast())
				chunk.setBlock(15, generator.sidewalkLevel, 0, dividerId);
			else
				chunk.setBlock(15, generator.sidewalkLevel, 0, cornerId);
		}
		if (!farms.toSouth()) {
			chunk.setBlocks(1, 15, generator.sidewalkLevel, 15, 16, dividerId);
			if (farms.toWest())
				chunk.setBlock(0, generator.sidewalkLevel, 15, dividerId);
			else
				chunk.setBlock(0, generator.sidewalkLevel, 15, cornerId);
			if (farms.toEast())
				chunk.setBlock(15, generator.sidewalkLevel, 15, dividerId);
			else
				chunk.setBlock(15, generator.sidewalkLevel, 15, cornerId);
		}
		if (!farms.toWest()) {
			chunk.setBlocks(0, 1, generator.sidewalkLevel, 1, 15, dividerId);
			if (farms.toNorth())
				chunk.setBlock(0, generator.sidewalkLevel, 0, dividerId);
			else
				chunk.setBlock(0, generator.sidewalkLevel, 0, cornerId);
			if (farms.toSouth())
				chunk.setBlock(0, generator.sidewalkLevel, 15, dividerId);
			else
				chunk.setBlock(0, generator.sidewalkLevel, 15, cornerId);
		}
		if (!farms.toEast()) {
			chunk.setBlocks(15, 16, generator.sidewalkLevel, 1, 15, dividerId);
			if (farms.toNorth())
				chunk.setBlock(15, generator.sidewalkLevel, 0, dividerId);
			else
				chunk.setBlock(15, generator.sidewalkLevel, 0, cornerId);
			if (farms.toSouth())
				chunk.setBlock(15, generator.sidewalkLevel, 15, dividerId);
			else
				chunk.setBlock(15, generator.sidewalkLevel, 15, cornerId);
		}
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		int croplevel = generator.sidewalkLevel + 1;
		
		// where are we again?
		if (generator.settings.environment == Environment.NETHER) {
			
			// what type of crop do we plant?
			if (cropType == cropGrass)
				plowField(generator, chunk, chunkRandom, croplevel, matDirt, 0, matAir, cropType, 0, 1, 2, 1);
			else if (cropType == cropBrownMushroom)
				plowField(generator, chunk, chunkRandom, croplevel, matDirt, 0, matAir, cropType, 0, 1, 2, 1);
			else if (cropType == cropRedMushroom)
				plowField(generator, chunk, chunkRandom, croplevel, matDirt, 0, matAir, cropType, 0, 1, 2, 1);
			else if (cropType == cropNetherwart)
				plowField(generator, chunk, chunkRandom, croplevel, matSoul, 0, matAir, cropType, chunkRandom.nextInt(4), 1, 2, 1);
			else if (cropType == cropPumpkin)
				plowField(generator, chunk, chunkRandom, croplevel, matDirt, 0, matAir, cropType, chunkRandom.nextInt(6) + 2, 1, 3, 1);
//			else if (cropType == cropVine)
//				buildVineyard(generator, chunk, chunkRandom, croplevel, matAir);
			else if (cropType == cropDeadBush)
				plowField(generator, chunk, chunkRandom, croplevel, matDirt, 0, matAir, cropType, 0, 1, 2, 1);
			else if (cropType == cropNone)
				plowField(generator, chunk, chunkRandom, croplevel, matDirt, 0, matAir, matAir, 0, 1, 2, 1);
			else // cropFallow
				plowField(generator, chunk, chunkRandom, croplevel, matDirt, 0, matAir, cropType, 0, 1, 2, 1);
			
		} else {
			
			// what type of crop do we plant?
			if (cropType == cropYellowFlower || cropType == cropRedFlower)
				plowField(generator, chunk, chunkRandom, croplevel, matSoil, 8, matWater, cropType, 0, 1, 2, 1);
			else if (cropType == cropGrass)
				plowField(generator, chunk, chunkRandom, croplevel, matSoil, 8, matWater, cropType, 1, 1, 2, 1);
			else if (cropType == cropWheat)
				plowField(generator, chunk, chunkRandom, croplevel, matSoil, 8, matWater, cropType, chunkRandom.nextInt(6) + 2, 1, 2, 1);
			else if (cropType == cropPumpkin || cropType == cropMelon)
				plowField(generator, chunk, chunkRandom, croplevel, matSoil, 8, matWater, cropType, chunkRandom.nextInt(6) + 2, 1, 3, 1);
			else if (cropType == cropSugarCane)
				plowField(generator, chunk, chunkRandom, croplevel, matSand, 0, matWater, cropType, 0, 1, 2, 3);
			else if (cropType == cropCactus)
				plowField(generator, chunk, chunkRandom, croplevel, matSand, 0, matSand, cropType, 0, 2, 2, 3);
			else if (cropType == cropVine)
				buildVineyard(generator, chunk, chunkRandom, croplevel, cropType);
			else if (cropType == cropNone)
				plowField(generator, chunk, chunkRandom, croplevel, matSoil, 8, matWater, matAir, 0, 1, 2, 1);
			else // cropFallow
				plowField(generator, chunk, chunkRandom, croplevel, matDirt, 0, matAir, cropType, 0, 1, 2, 1);
		}
	}

	private void plowField(WorldGenerator generator, RealChunk chunk, Random random, int croplevel, 
			Material matRidge, int datRidge, Material matFurrow, 
			Material matCrop, int datCrop,
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
					if (chunkRandom.nextDouble() < 0.80)
						chunk.setBlocks(x, croplevel, croplevel + random.nextInt(maxHeight) + 1, z, matCrop, byteCrop, false);
			}
		} else {
			for (int z = 1; z < 15; z += stepCol) {
				chunk.setBlocks(1, 15, croplevel - 1, croplevel, z, z + 1, matRidge, byteRidge, false);
				if (stepCol > 1)
					chunk.setBlocks(1, 15, croplevel - 1, croplevel, z + 1, z + 2, matFurrow, byteFurrow, false);
				for (int x = 1; x < 15; x += stepRow)
					if (chunkRandom.nextDouble() < 0.80)
						chunk.setBlocks(x, croplevel, croplevel + random.nextInt(maxHeight) + 1, z, matCrop, byteCrop, false);
			}
		}
	}
	
	private void buildVineyard(WorldGenerator generator, RealChunk chunk, Random random, int cropLevel, Material matCrop) {
		int stepCol = 3;
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepCol) {
				chunk.setBlocks(x, cropLevel, cropLevel + 4, 1, matPole);
				chunk.setBlocks(x, cropLevel, cropLevel + 4, 14, matPole);
				chunk.setBlocks(x, x + 1, cropLevel + 3, cropLevel + 4, 2, 14, matTrellis);
				if (chunkRandom.nextDouble() < 0.80) {
					for (int z = 2; z < 14; z++) {
						chunk.setBlocks(x - 1, x, cropLevel + 1 + random.nextInt(3), cropLevel + 4, z, z + 1, matCrop, (byte) 8);
						chunk.setBlocks(x + 1, x + 2, cropLevel + 1 + random.nextInt(3), cropLevel + 4, z, z + 1, matCrop, (byte) 2);
					}
				}
			}
		} else {
			for (int z = 1; z < 15; z += stepCol) {
				chunk.setBlocks(1, cropLevel, cropLevel + 4, z, matPole);
				chunk.setBlocks(14, cropLevel, cropLevel + 4, z, matPole);
				chunk.setBlocks(2, 14, cropLevel + 3, cropLevel + 4, z, z + 1, matTrellis);
				if (chunkRandom.nextDouble() < 0.80) {
					for (int x = 2; x < 14; x++) {
						chunk.setBlocks(x, x + 1, cropLevel + 1 + random.nextInt(3), cropLevel + 4, z - 1, z, matCrop, (byte) 1);
						chunk.setBlocks(x, x + 1, cropLevel + 1 + random.nextInt(3), cropLevel + 4, z + 1, z + 2, matCrop, (byte) 4);
					}
				}
			}
		}
	}
	
	private Material getNormalCrop() {
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
			return cropCactus;
		case 9:
			return cropFallow;
		case 10:
			return cropNone;
		default:
			return cropWheat;
		}
	}

	private Material getNetherCrop() {
		switch (chunkRandom.nextInt(10)) {
		case 1:
			return cropBrownMushroom;
		case 2:
			return cropRedMushroom;
		case 3:
			return cropNetherwart;
		case 4:
			return cropPumpkin;
		case 5:
			return cropGrass;
//		case 6:
//			return cropVine;
		case 7:
			return cropDeadBush;
		case 8:
			return cropNone;
		default:
			return cropFallow;
		}
	}
}
