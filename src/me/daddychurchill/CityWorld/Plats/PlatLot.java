package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Plugins.SpawnProvider;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public abstract class PlatLot {
	
	// extremes
	private boolean extremeComputed = false;
	protected int averageHeight;
	protected int minHeight = Integer.MAX_VALUE;
	protected int minHeightX = 0;
	protected int minHeightZ = 0;
	protected int maxHeight = Integer.MIN_VALUE;
	protected int maxHeightX = 0;
	protected int maxHeightZ = 0;
	protected int oreTopY = -1; // use generator.findBlockY()
	
	protected Random platmapRandom;
	protected Random chunkRandom;
	
	// styling!
	public enum LotStyle {NATURE, STRUCTURE, ROAD, ROUNDABOUT};
	public LotStyle style;
	
	public PlatLot(PlatMap platmap, int chunkX, int chunkZ) {
		super();
		
		initializeDice(platmap, chunkX, chunkZ);
		
		style = LotStyle.NATURE;
	}
	
	protected final static byte airId = (byte) Material.AIR.getId();
	protected final static byte snowId = (byte) Material.SNOW_BLOCK.getId();
	protected final static byte grassId = (byte) Material.GRASS.getId();
	protected final static byte dirtId = (byte) Material.DIRT.getId();
	protected final static byte stoneId = (byte) Material.STONE.getId();
	protected final static byte sandId = (byte) Material.SAND.getId();
	protected final static byte sandstoneId = (byte) Material.SANDSTONE.getId();
	protected final static byte bedrockId = (byte) Material.BEDROCK.getId();
	protected final static byte fenceId = (byte) Material.FENCE.getId();
	protected final static byte cobbleId = (byte) Material.COBBLESTONE.getId();
	protected final static byte stillWaterId = (byte) Material.STATIONARY_WATER.getId();
	protected final static byte stillLavaId = (byte) Material.STATIONARY_LAVA.getId();

	protected final static int snowMaterialId = Material.SNOW.getId();
	protected final static int grassMaterialId = Material.LONG_GRASS.getId();
	protected final static Material snowMaterial = Material.SNOW;
	protected final static Material airMaterial = Material.AIR;
	protected final static Material stoneMaterial = Material.STONE;
	protected final static Material dandelionMaterial = Material.YELLOW_FLOWER;
	protected final static Material roseMaterial = Material.RED_ROSE;
	protected final static Material rootMaterial = Material.GRASS;
	
	public abstract long getConnectedKey();
	public abstract boolean makeConnected(PlatLot relative);
	public abstract boolean isConnectable(PlatLot relative);
	public abstract boolean isConnected(PlatLot relative);
	
	protected abstract void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ);
	protected abstract void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ);

	protected Biome getChunkBiome() {
		return Biome.PLAINS;
	}
	
	private void initializeDice(PlatMap platmap, int chunkX, int chunkZ) {
		
		// reset and pick up the dice
		platmapRandom = platmap.getRandomGenerator();
		chunkRandom = platmap.getChunkRandomGenerator(chunkX, chunkZ);
	}
	
	public void generateChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		initializeDice(platmap, chunk.chunkX, chunk.chunkZ);
		
		// let there be dirt!
		generateCrust(generator, platmap, chunk, biomes, context, platX, platZ);
		
		// let the specialized platlot do it's thing
		generateActualChunk(generator, platmap, chunk, biomes, context, platX, platZ);
		
		// do we do it or not?
		if (generator.settings.includeMines)
			generateMines(generator, chunk, context);
	}
		
	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		initializeDice(platmap, chunk.chunkX, chunk.chunkZ);
		
		// let the specialized platlot do it's thing
		generateActualBlocks(generator, platmap, chunk, context, platX, platZ);
		
		// put ores in?
		generateOres(generator, chunk);

		// do we do it or not?
		if (generator.settings.includeMines)
			generateMines(generator, chunk, context);
	}
	
	protected void generateCrust(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {

		// biome for the chunk
		Biome chunkBiome = getChunkBiome();
		
		// total height
		int sumHeight = 0;
		
		// compute offset to start of chunk
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();
		
		// surface caves?
		boolean surfaceCaves = generator.isSurfaceCaveAt(chunk.chunkX, chunk.chunkZ);
		
		// shape the world
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {

				// how high are we?
				int y = generator.findBlockY(originX + x, originZ + z);
				
				// keep the tally going
				sumHeight += y;
				if (y < minHeight) {
					minHeight = y;
					minHeightX = x;
					minHeightZ = z;
				}
				if (y > maxHeight) {
					maxHeight = y;
					maxHeightX = x;
					maxHeightZ = z;
				}

				// make the base
				chunk.setBlock(x, 0, z, bedrockId);
				chunk.setBlock(x, 1, z, stoneId);

				// buildable?
				if (style == LotStyle.STRUCTURE || style == LotStyle.ROUNDABOUT) {
					generateStratas(generator, chunk, x, z, stoneId, generator.sidewalkLevel - 2, dirtId, generator.sidewalkLevel, dirtId, false);
					biomes.setBiome(x, z, chunkBiome);
					
				// possibly buildable?
				} else if (y == generator.sidewalkLevel) {
					generateStratas(generator, chunk, x, z, stoneId, y - 3, dirtId, y, grassId, false);
					biomes.setBiome(x, z, chunkBiome);
				
				// won't likely have a building
				} else {

					// on the beach
					if (y == generator.seaLevel) {
						generateStratas(generator, chunk, x, z, stoneId, y - 2, sandId, y, sandId, false);
						biomes.setBiome(x, z, Biome.BEACH);

						// we are in the water!
					} else if (y < generator.seaLevel) {
						generateStratas(generator, chunk, x, z, stoneId, y - 2, sandstoneId, y, sandId, generator.seaLevel, stillWaterId, false);
						biomes.setBiome(x, z, Biome.OCEAN);

						// we are in the mountains
					} else {

						// regular trees only
						if (y < generator.treeLevel) {
							generateStratas(generator, chunk, x, z, stoneId, y - 3, dirtId, y, grassId, false);
							biomes.setBiome(x, z, Biome.FOREST_HILLS);

						// regular trees and some evergreen trees
						} else if (y < generator.evergreenLevel) {
							generateStratas(generator, chunk, x, z, stoneId, y - 2, dirtId, y, grassId, surfaceCaves);
							biomes.setBiome(x, z, Biome.EXTREME_HILLS);

						// evergreen and some of fallen snow
						} else if (y < generator.snowLevel) {
							generateStratas(generator, chunk, x, z, stoneId, y - 1, dirtId, y, grassId, surfaceCaves);
							biomes.setBiome(x, z, Biome.ICE_MOUNTAINS);
							
						// only snow up here!
						} else {
							generateStratas(generator, chunk, x, z, stoneId, y - 1, stoneId, y, snowId, surfaceCaves);
							biomes.setBiome(x, z, Biome.ICE_PLAINS);
						}
					}
				}
			}
		}
		
		// what was the average height
		averageHeight = sumHeight / (chunk.width * chunk.width);
		extremeComputed = true;
	}
	
	private final static int lowestMineSegment = 16;
	
	protected void generateMines(WorldGenerator generator, ByteChunk chunk, ContextData context) {
		
		// make sure we have the facts
		precomputeExtremes(generator, chunk);
		
		// get shafted! (this builds down to keep the support poles happy)
		for (int y = (minHeight / 16 - 1) * 16; y >= lowestMineSegment; y -= 16) {
			if (isShaftableLevel(generator, context, y))
				generateHorizontalMineLevel(generator, chunk, context, y);
		}
	}
	
	protected int findHighestShaftableLevel(WorldGenerator generator, ContextData context, SupportChunk chunk) {

		// make sure we have the facts
		precomputeExtremes(generator, chunk);
		
		// keep going down until we find what we are looking for
		for (int y = (minHeight / 16 - 1) * 16; y >= lowestMineSegment; y -= 16) {
			if (isShaftableLevel(generator, context, y) && generator.getHorizontalWEShaft(chunk.chunkX, y, chunk.chunkZ))
				return y + 7;
		}
		
		// nothing found
		return 0;
	}
	
	protected boolean isShaftableLevel(WorldGenerator generator, ContextData context, int y) {
		return y >= lowestMineSegment && y < minHeight && minHeight > generator.seaLevel;
	}

	private void generateHorizontalMineLevel(WorldGenerator generator, ByteChunk chunk, ContextData context, int y) {
		int y1 = y + 6;
		int y2 = y1 + 1;
		
		// draw the shafts/walkways
		boolean pathFound = false;
		if (generator.getHorizontalNSShaft(chunk.chunkX, y, chunk.chunkZ)) {
			generateMineShaftSpace(chunk, 6, 10, y1, y1 + 4, 0, 6);
			generateMineNSSupport(chunk, 6, y2, 1);
			generateMineNSSupport(chunk, 6, y2, 4);
			generateMineShaftSpace(chunk, 6, 10, y1, y1 + 4, 10, 16);
			generateMineNSSupport(chunk, 6, y2, 11);
			generateMineNSSupport(chunk, 6, y2, 14);
			pathFound = true;
		}
		if (generator.getHorizontalWEShaft(chunk.chunkX, y, chunk.chunkZ)) {
			generateMineShaftSpace(chunk, 0, 6, y1, y1 + 4, 6, 10);
			generateMineWESupport(chunk, 1, y2, 6);
			generateMineWESupport(chunk, 4, y2, 6);
			generateMineShaftSpace(chunk, 10, 16, y1, y1 + 4, 6, 10);
			generateMineWESupport(chunk, 11, y2, 6);
			generateMineWESupport(chunk, 14, y2, 6);
			pathFound = true;
		}
		
		// draw the center bit
		if (pathFound)
			generateMineShaftSpace(chunk, 6, 10, y1, y1 + 4, 6, 10);
	}
	
	private final static byte shaftBridgeId = (byte) Material.WOOD.getId(); 
	private final static byte shaftSupportId = (byte) Material.FENCE.getId();
	private final static byte shaftBeamId = (byte) Material.WOOD.getId();

	private void generateMineShaftSpace(ByteChunk chunk, int x1, int x2, int y1, int y2, int z1, int z2) {
		chunk.setEmptyBlocks(x1, x2, y1, z1, z2, shaftBridgeId);
		chunk.setBlocks(x1, x2, y1 + 1, y2, z1, z2, airId);
	}
	
	private void generateMineNSSupport(ByteChunk chunk, int x, int y, int z) {
		
		// on a bridge
		if (chunk.getBlock(x, y - 1, z) == shaftBridgeId && 
			chunk.getBlock(x + 3, y - 1, z) == shaftBridgeId) {
			
			// place supports
			generateMineSupport(chunk, x, y - 1, z);
			generateMineSupport(chunk, x + 3, y - 1, z);
			
		// in a tunnel
		} else {
			chunk.setBlock(x, y, z, shaftSupportId);
			chunk.setBlock(x, y + 1, z, shaftSupportId);
			chunk.setBlock(x + 3, y, z, shaftSupportId);
			chunk.setBlock(x + 3, y + 1, z, shaftSupportId);
			chunk.setBlocks(x, x + 4, y + 2, z, z + 1, shaftBeamId);
		}
	}
	
	private void generateMineWESupport(ByteChunk chunk, int x, int y, int z) {
		// on a bridge
		if (chunk.getBlock(x, y - 1, z) == shaftBridgeId && 
			chunk.getBlock(x, y - 1, z + 3) == shaftBridgeId) {
			
			// place supports
			generateMineSupport(chunk, x, y - 1, z);
			generateMineSupport(chunk, x, y - 1, z + 3);
			
		// in a tunnel
		} else {
			chunk.setBlock(x, y, z, shaftSupportId);
			chunk.setBlock(x, y + 1, z, shaftSupportId);
			chunk.setBlock(x, y, z + 3, shaftSupportId);
			chunk.setBlock(x, y + 1, z + 3, shaftSupportId);
			chunk.setBlocks(x, x + 1, y + 2, z, z + 4, shaftBeamId);
		}
	}
	
	private void generateMineSupport(ByteChunk chunk, int x, int y, int z) {
		int aboveSupport = chunk.findLastEmptyAbove(x, y, z);
		if (aboveSupport < maxHeight)
			chunk.setBlocks(x, y + 1, aboveSupport + 1, z, shaftSupportId);
	}
	
	protected void precomputeExtremes(WorldGenerator generator, SupportChunk chunk) {

		// have we done this yet?
		if (!extremeComputed) {
			HeightInfo heights = HeightInfo.getHeights(generator, chunk.worldX, chunk.worldZ);
			averageHeight = heights.averageHeight;
			minHeight = heights.minHeight;
			minHeightX = heights.minHeightX;
			minHeightZ = heights.minHeightZ;
			maxHeight = heights.maxHeight;
			maxHeightX = heights.maxHeightX;
			maxHeightZ = heights.maxHeightZ;
			extremeComputed = true;
		}
	}
		
	protected void generateMines(WorldGenerator generator, RealChunk chunk, ContextData context) {
		
		// make sure we have the facts
		precomputeExtremes(generator, chunk);

		// get shafted!
		for (int y = 0; y + 16 < minHeight; y += 16) {
			if (isShaftableLevel(generator, context, y))
				generateVerticalMineLevel(generator, chunk, context, y);
		}
	}
	
	private void generateVerticalMineLevel(WorldGenerator generator, RealChunk chunk, ContextData context, int y) {
		int y1 = y + 6;
		boolean stairsFound = false;
		
		// going down?
		if (isShaftableLevel(generator, context, y - 16)) {
			if (generator.getHorizontalNSShaft(chunk.chunkX, y, chunk.chunkZ) &&
				generator.getHorizontalNSShaft(chunk.chunkX, y - 16, chunk.chunkZ)) {
				
				// draw the going down bit
				placeMineStairBase(chunk, 10, y1	, 15);
				placeMineStairStep(chunk, 10, y1    , 14, Stair.SOUTH);
				placeMineStairStep(chunk, 10, y1 - 1, 13, Stair.SOUTH);
				placeMineStairStep(chunk, 10, y1 - 2, 12, Stair.SOUTH);
				placeMineStairStep(chunk, 10, y1 - 3, 11, Stair.SOUTH);
				placeMineStairStep(chunk, 10, y1 - 4, 10, Stair.SOUTH);
				placeMineStairStep(chunk, 10, y1 - 5,  9, Stair.SOUTH);
				placeMineStairStep(chunk, 10, y1 - 6,  8, Stair.SOUTH);
				stairsFound = true;
			}
			
			if (!stairsFound &&
				generator.getHorizontalWEShaft(chunk.chunkX, y, chunk.chunkZ) &&
				generator.getHorizontalWEShaft(chunk.chunkX, y - 16, chunk.chunkZ)) {
				
				// draw the going down bit
				placeMineStairBase(chunk, 15, y1	, 10);
				placeMineStairStep(chunk, 14, y1    , 10, Stair.EAST);
				placeMineStairStep(chunk, 13, y1 - 1, 10, Stair.EAST);
				placeMineStairStep(chunk, 12, y1 - 2, 10, Stair.EAST);
				placeMineStairStep(chunk, 11, y1 - 3, 10, Stair.EAST);
				placeMineStairStep(chunk, 10, y1 - 4, 10, Stair.EAST);
				placeMineStairStep(chunk,  9, y1 - 5, 10, Stair.EAST);
				placeMineStairStep(chunk,  8, y1 - 6, 10, Stair.EAST);
			}
		}
		
		// reset the stairs flag
		stairsFound = false;
		
		// going up?
		if (isShaftableLevel(generator, context, y + 32)) {
			if (generator.getHorizontalNSShaft(chunk.chunkX, y, chunk.chunkZ) &&
				generator.getHorizontalNSShaft(chunk.chunkX, y + 16, chunk.chunkZ)) {
					
				// draw the going up bit
				placeMineStairBase(chunk,  5, y1	, 15);
				placeMineStairStep(chunk,  5, y1 + 1, 14, Stair.NORTH);
				placeMineStairStep(chunk,  5, y1 + 2, 13, Stair.NORTH);
				placeMineStairStep(chunk,  5, y1 + 3, 12, Stair.NORTH);
				placeMineStairStep(chunk,  5, y1 + 4, 11, Stair.NORTH);
				placeMineStairStep(chunk,  5, y1 + 5, 10, Stair.NORTH);
				placeMineStairStep(chunk,  5, y1 + 6,  9, Stair.NORTH);
				placeMineStairStep(chunk,  5, y1 + 7,  8, Stair.NORTH);
				placeMineStairStep(chunk,  5, y1 + 8,  7, Stair.NORTH);
				placeMineStairBase(chunk,  5, y1 + 8,  6);
				placeMineStairBase(chunk,  6, y1 + 8,  6);
				placeMineStairBase(chunk,  7, y1 + 8,  6);
				placeMineStairBase(chunk,  8, y1 + 8,  6);
				placeMineStairBase(chunk,  9, y1 + 8,  6);
				placeMineStairBase(chunk, 10, y1 + 8,  6);
				placeMineStairStep(chunk, 10, y1 + 9,  7, Stair.SOUTH);
				
				generateMineSupport(chunk, 6, y1 + 7, 7);
				generateMineSupport(chunk, 9, y1 + 7, 7);
				
				stairsFound = true;
			}
			
			if (!stairsFound &&
				generator.getHorizontalWEShaft(chunk.chunkX, y, chunk.chunkZ) &&
				generator.getHorizontalWEShaft(chunk.chunkX, y + 16, chunk.chunkZ)) {
				
				// draw the going up bit
				placeMineStairBase(chunk, 15, y1	,  5);
				placeMineStairStep(chunk, 14, y1 + 1,  5, Stair.WEST);
				placeMineStairStep(chunk, 13, y1 + 2,  5, Stair.WEST);
				placeMineStairStep(chunk, 12, y1 + 3,  5, Stair.WEST);
				placeMineStairStep(chunk, 11, y1 + 4,  5, Stair.WEST);
				placeMineStairStep(chunk, 10, y1 + 5,  5, Stair.WEST);
				placeMineStairStep(chunk,  9, y1 + 6,  5, Stair.WEST);
				placeMineStairStep(chunk,  8, y1 + 7,  5, Stair.WEST);
				placeMineStairStep(chunk,  7, y1 + 8,  5, Stair.WEST);
				placeMineStairBase(chunk,  6, y1 + 8,  5);
				placeMineStairBase(chunk,  6, y1 + 8,  6);
				placeMineStairBase(chunk,  6, y1 + 8,  7);
				placeMineStairBase(chunk,  6, y1 + 8,  8);
				placeMineStairBase(chunk,  6, y1 + 8,  9);
				placeMineStairBase(chunk,  6, y1 + 8, 10);
				placeMineStairStep(chunk,  7, y1 + 9, 10, Stair.EAST);
				
				generateMineSupport(chunk, 7, y1 + 7, 6);
				generateMineSupport(chunk, 7, y1 + 7, 9);
			}
		}
		
		// make the ceiling pretty
		boolean pathFound = false;
		if (generator.getHorizontalNSShaft(chunk.chunkX, y, chunk.chunkZ)) {
			generateMineShaftSpace(chunk, 6, 10, y1 + 3, 0, 6);
			generateMineShaftSpace(chunk, 6, 10, y1 + 3, 10, 16);
			pathFound = true;
		}
		if (generator.getHorizontalWEShaft(chunk.chunkX, y, chunk.chunkZ)) {
			generateMineShaftSpace(chunk, 0, 6, y1 + 3, 6, 10);
			generateMineShaftSpace(chunk, 10, 16, y1 + 3, 6, 10);
			pathFound = true;
		}
		
		// draw the center bit
		if (pathFound) {
			generateMineShaftSpace(chunk, 6, 10, y1 + 3, 6, 10);
			
			generateMineTreat(generator, context, chunk, 6, y1 + 1, 6);
			generateMineTrick(generator, context, chunk, 9, y1 + 1, 9);
		}
	}
	
	private void generateMineShaftSpace(RealChunk chunk, int x1, int x2, int y, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				if (chunkRandom.nextBoolean())
					if (!chunk.isEmpty(x, y + 1, z) && chunk.isEmpty(x, y, z))
						chunk.setStoneSlab(x, y, z, Direction.StoneSlab.COBBLESTONEFLIP);
			}
		}
	}
	
	private void generateMineSupport(RealChunk chunk, int x, int y, int z) {
		int aboveSupport = chunk.findLastEmptyAbove(x, y, z);
		if (aboveSupport < maxHeight)
			chunk.setBlocks(x, y + 1, aboveSupport + 1, z, Material.FENCE);
	}
	private void placeMineStairBase(RealChunk chunk, int x, int y, int z) {
		chunk.setBlocks(x, y + 1, y + 4, z, Material.AIR);
		chunk.setEmptyBlock(x, y, z, Material.WOOD);
	}
	
	private void placeMineStairStep(RealChunk chunk, int x, int y, int z, Stair direction) {
		chunk.setBlocks(x, y + 1, y + 4, z, Material.AIR);
		chunk.setStair(x, y, z, Material.WOOD_STAIRS, direction);
	}
	
	private void generateMineTreat(WorldGenerator generator, ContextData context, RealChunk chunk, int x, int y, int z) {

		// cool stuff?
		if (generator.settings.treasuresInMines && chunkRandom.nextDouble() <= context.oddsOfTreasureInMines) {
			 chunk.setChest(x, y, z, Direction.Chest.SOUTH, generator.getLootProvider().getItems(generator, LootProvider.chestInMines));
		}
	}

	private void generateMineTrick(WorldGenerator generator, ContextData context, RealChunk chunk, int x, int y, int z) {
		// not so cool stuff?
		if (generator.settings.spawnersInMines && chunkRandom.nextDouble() <= context.oddsOfSpawnerInMines) {
			chunk.setSpawner(x, y, z, generator.getSpawnProvider().getEntity(generator, SpawnProvider.spawnerInMines));
		}
	}
	
//	private void generateStratas(WorldGenerator generator, ByteChunk byteChunk, int x, int z, byte baseId,
//			int baseY, byte substrateId, int substrateY, byte surfaceId,
//			boolean surfaceCaves) {
//
//		// compute the world block coordinates
//		int blockX = byteChunk.chunkX * byteChunk.width + x;
//		int blockZ = byteChunk.chunkZ * byteChunk.width + z;
//		
//		// stony bits
//		for (int y = 2; y < baseY; y++)
//			if (generator.notACave(blockX, y, blockZ)) {
//				byte oreId = generator.getOre(byteChunk, blockX, y, blockZ, baseId);
//				
//				// transmute to fluid?
//				if (generator.settings.includeUndergroundFluids && oreId != baseId && generator.anyStrataFluid(blockX, y, blockZ))
//					oreId = getStrataFluid(generator, y);
//				
//				// place it
//				byteChunk.setBlock(x, y, z, oreId);
//		
//			// Loosely based on http://www.minecraftwiki.net/wiki/Lava
//			} else if (y <= 10)
//				byteChunk.setBlock(x, y, z, lavaId);
//
//		// aggregate bits
//		for (int y = baseY; y < substrateY - 1; y++)
//			if (!surfaceCaves || generator.notACave(blockX, y, blockZ))
//				byteChunk.setBlock(x, y, z, substrateId);
//
//		// icing for the cake
//		if (!surfaceCaves || generator.notACave(blockX, substrateY, blockZ)) {
//			byteChunk.setBlock(x, substrateY - 1, z, substrateId);
//			byteChunk.setBlock(x, substrateY, z, surfaceId);
//		}
//	}
//
//	private void generateStratas(WorldGenerator generator, ByteChunk chunk, int x, int z, byte baseId,
//			int baseY, byte substrateId, int substrateY, byte surfaceId,
//			int coverY, byte coverId, boolean surfaceCaves) {
//
//		// a little crust please?
//		generateStratas(generator, chunk, x, z, baseId, baseY, substrateId, substrateY, surfaceId, surfaceCaves);
//
//		// cover it up
//		for (int y = substrateY + 1; y <= coverY; y++)
//			chunk.setBlock(x, y, z, coverId);
//	}

	private void generateStratas(WorldGenerator generator, ByteChunk chunk, int x, int z, byte baseId,
			int baseY, byte substrateId, int substrateY, byte surfaceId,
			boolean surfaceCaves) {

		// compute the world block coordinates
		int blockX = chunk.chunkX * chunk.width + x;
		int blockZ = chunk.chunkZ * chunk.width + z;
		
		// stony bits
		for (int y = 2; y < baseY; y++)
			if (isValidStrataY(generator, blockX, y, blockZ))
				chunk.setBlock(x, y, z, baseId);

		// aggregate bits
		for (int y = baseY; y < substrateY - 1; y++)
			if (!surfaceCaves || generator.notACave(blockX, y, blockZ))
				chunk.setBlock(x, y, z, substrateId);

		// icing for the cake
		if (!surfaceCaves || generator.notACave(blockX, substrateY, blockZ)) {
			chunk.setBlock(x, substrateY - 1, z, substrateId);
			chunk.setBlock(x, substrateY, z, surfaceId);
		}
	}

	private void generateStratas(WorldGenerator generator, ByteChunk chunk, int x, int z, byte baseId,
			int baseY, byte substrateId, int substrateY, byte surfaceId,
			int coverY, byte coverId, boolean surfaceCaves) {

		// a little crust please?
		generateStratas(generator, chunk, x, z, baseId, baseY, substrateId, substrateY, surfaceId, surfaceCaves);

		// cover it up
		for (int y = substrateY + 1; y <= coverY; y++)
			chunk.setBlock(x, y, z, coverId);
	}
	
	protected boolean isValidStrataY(WorldGenerator generator, int blockX, int blockY, int blockZ) {
		return generator.notACave(blockX, blockY, blockZ);
	}
	
	protected int getTopStrataY(WorldGenerator generator, int blockX, int blockZ) {
		return generator.findBlockY(blockX, blockZ);
	}
	
	private void generateOres(WorldGenerator generator, RealChunk chunk) {
		
		// lava the bottom a bit
		if (generator.settings.includeLavaFields) {
			for (int x = 0; x < chunk.width; x++) {
				for (int z = 0; z < chunk.width; z++) {
			
					// stony bits
					for (int y = 2; y < 8; y++) {
						Block block = chunk.getActualBlock(x, y, z);
						if (block.isEmpty())
							block.setTypeId(SupportChunk.stillLavaId, false);
					}
				}
			}
		}
		
		// shape the world
		if (generator.settings.includeOres || generator.settings.includeUndergroundFluids)
			sprinkleOres(generator, chunk, chunkRandom);
	}

	/**
	 * Populates the world with ores.
	 *
	 * @author Nightgunner5
	 * @author Markus Persson
	 * modified by simplex
	 * wildly modified by daddychurchill
	 */
	
	private static final int[] ore_types = new int[] {Material.WATER.getId(),
		  											  Material.GRAVEL.getId(), 
													  Material.COAL_ORE.getId(),
													  Material.IRON_ORE.getId(), 
													  Material.GOLD_ORE.getId(), 
													  Material.LAPIS_ORE.getId(),
													  Material.REDSTONE_ORE.getId(),
													  Material.DIAMOND_ORE.getId()}; 
	//                                                         WATER   GRAV   COAL   IRON   GOLD  LAPIS  REDST   DIAM  
	private static final int[] ore_iterations = new int[]    {     4,    40,    35,    12,     4,     3,     6,     2};
	private static final int[] ore_amountToDo = new int[]    {     1,     8,     8,     8,     3,     2,     4,     2};
	private static final int[] ore_maxY = new int[]          {   128,    96,   128,    68,    34,    30,    17,    16};
	private static final int[] ore_minY = new int[]          {     8,    40,    16,    16,     5,     5,     8,     1};
	private static final boolean[] ore_upper = new boolean[] {  true, false,  true,  true,  true,  true,  true, false};
	
	private static final int[] ore_types_tekkit = new int[] {Material.WATER.getId(),
													  Material.GRAVEL.getId(), 
													  Material.COAL_ORE.getId(),
													  Material.IRON_ORE.getId(), 
													  Material.GOLD_ORE.getId(), 
													  Material.LAPIS_ORE.getId(),
													  Material.REDSTONE_ORE.getId(),
													  Material.DIAMOND_ORE.getId(),
													  140, //ruby
													  1401, // emerald
													  1402, //sapphire
													  1403, //silver
													  1404, //tin
													  1405, //copper
													  1406, //tungsten
													  1407, //nikolite
													  142, //marble
													  1421, //basalt
													  247 //uranium
													  };
	//                                                   		      WATER   GRAV   COAL   IRON   GOLD  LAPIS  REDST   DIAM   RUBY   EMER   SAPP   SILV    TIN  COPPR   TUNG   NIKO   MARB   BASA   URAN  
	private static final int[] ore_iterations_tekkit = new int[]    {     4,    20,    15,     6,     2,     2,     4,     1,     1,     1,     1,     3,     6,     6,     2,     2,    30,    20,     1};
	private static final int[] ore_amountToDo_tekkit = new int[]    {     1,     6,     8,     6,     3,     2,     4,     2,     2,     2,     2,     4,     6,     6,     3,     8,    20,     8,     1};
	private static final int[] ore_maxY_tekkit = new int[]          {   128,    96,   128,    68,    34,    30,    17,    16,    16,    16,    16,    34,    68,    96,    34,    68,   128,    10,    16};
	private static final int[] ore_minY_tekkit = new int[]          {     8,    40,    16,    16,     5,     5,     8,     1,     2,     2,     2,     5,    16,    16,     2,     2,    40,     1,     2};
	private static final boolean[] ore_upper_tekkit = new boolean[] {  true, false,  true,  true,  true,  true,  true, false, false, false, false,  true,  true,  true,  true,  true,  true, false, false};
	
	public void sprinkleOres(WorldGenerator generator, RealChunk chunk, Random random) {
		if (generator.settings.tekkitServer) {
			for (int typeNdx = 0; typeNdx < ore_types_tekkit.length; typeNdx++) {
				int range = ore_maxY_tekkit[typeNdx] - ore_minY_tekkit[typeNdx];
				for (int iter = 0; iter < ore_iterations_tekkit[typeNdx]; iter++) {
					sprinkleOres_iterate(generator, chunk, random, random.nextInt(16), random.nextInt(range) + ore_minY_tekkit[typeNdx], random.nextInt(16), ore_amountToDo_tekkit[typeNdx], ore_types_tekkit[typeNdx]);
					if (ore_upper_tekkit[typeNdx])
						sprinkleOres_iterate(generator, chunk, random, random.nextInt(16), 
								(generator.seaLevel + generator.landRange) - ore_minY_tekkit[typeNdx] - random.nextInt(range), random.nextInt(16), ore_amountToDo_tekkit[typeNdx], ore_types_tekkit[typeNdx]);
				}
			}
		} else {
			for (int typeNdx = 0; typeNdx < ore_types.length; typeNdx++) {
				int range = ore_maxY[typeNdx] - ore_minY[typeNdx];
				for (int iter = 0; iter < ore_iterations[typeNdx]; iter++) {
					sprinkleOres_iterate(generator, chunk, random, random.nextInt(16), random.nextInt(range) + ore_minY[typeNdx], random.nextInt(16), ore_amountToDo[typeNdx], ore_types[typeNdx]);
					if (ore_upper[typeNdx])
						sprinkleOres_iterate(generator, chunk, random, random.nextInt(16), 
								(generator.seaLevel + generator.landRange) - ore_minY[typeNdx] - random.nextInt(range), random.nextInt(16), ore_amountToDo[typeNdx], ore_types[typeNdx]);
				}
			}
		}
	}

	private void sprinkleOres_iterate(WorldGenerator generator, RealChunk chunk, Random random, int originX, int originY, int originZ, int amountToDo, int typeId) {
		int trysLeft = amountToDo * 2;
		int oresDone = 0;
		if (generator.findBlockY(chunk.getBlockX(originX), chunk.getBlockZ(originZ)) > originY + amountToDo / 4) {
			while (oresDone < amountToDo && trysLeft > 0) {
				
				// ore or not?
				if (typeId == waterTypeId) {
					if (generator.settings.includeUndergroundFluids)
						oresDone += sprinkleOres_placeFluid(generator, chunk, random, originX, originY, originZ);

				} else {
					
					// shimmy
					int x = originX + random.nextInt(Math.max(1, amountToDo / 2)) - amountToDo / 4;
					int y = originY + random.nextInt(Math.max(1, amountToDo / 4)) - amountToDo / 8;
					int z = originZ + random.nextInt(Math.max(1, amountToDo / 2)) - amountToDo / 4;
					
					// ore it is
					oresDone += sprinkleOres_placeOre(generator, chunk, random, x, y, z, amountToDo - oresDone, typeId);
				}
				
				// one less try to try
				trysLeft--;
			}
		}
	}
	
	private int sprinkleOres_placeOre(WorldGenerator generator, RealChunk chunk, Random random, int centerX, int centerY, int centerZ, int oresToDo, int typeId) {
		int count = 0;
		if (centerY > 0 && centerY < chunk.height) {
			if (sprinkleOres_placeThing(chunk, random, centerX, centerY, centerZ, typeId, false)) {
				count++;
				if (count < oresToDo && centerX < 15 && sprinkleOres_placeThing(chunk, random, centerX + 1, centerY, centerZ, typeId, false))
					count++;
				if (count < oresToDo && centerX > 0 && sprinkleOres_placeThing(chunk, random, centerX - 1, centerY, centerZ, typeId, false))
					count++;
				if (count < oresToDo && centerZ < 15 && sprinkleOres_placeThing(chunk, random, centerX, centerY, centerZ + 1, typeId, false))
					count++;
				if (count < oresToDo && centerZ > 0 && sprinkleOres_placeThing(chunk, random, centerX, centerY, centerZ - 1, typeId, false))
					count++;
			}
		}
		return count;
	}
	
	private static final int iceTypeId = Material.ICE.getId();
	private static final int waterTypeId = Material.WATER.getId();
	private static final int lavaTypeId = Material.LAVA.getId();
	
	private int sprinkleOres_placeFluid(WorldGenerator generator, RealChunk chunk, Random random, int centerX, int centerY, int centerZ) {
		int count = 0;
		if (centerY > 0 && centerY < chunk.height) {

			// what type of fluid are we talking about?
			int fluidId;
			if (centerY < 24)
				fluidId = lavaTypeId;
			else if (centerY > generator.snowLevel)
				fluidId = iceTypeId;
			else
				fluidId = waterTypeId;
			
			// odds are?
			if (sprinkleOres_placeThing(chunk, random, centerX, centerY, centerZ, fluidId, true))
				count++;
		}
		return count;
	}
	
	private boolean sprinkleOres_placeThing(RealChunk chunk, Random random, int x, int y, int z, int typeId, boolean physics) {
		if (random.nextDouble() < 0.35) {
			Block block = chunk.getActualBlock(x, y, z);
			if (block.getTypeId() == stoneId) {
				if (typeId > 1000) {
					int blockId = typeId/10;
					byte dataVal = (byte) (typeId%10);
					block.setTypeIdAndData(blockId, dataVal, physics);
				} else {
					block.setTypeId(typeId, physics);
				}
				return true;
			}
		}
		return false;
	}
	
	//TODO move this logic to SurroundingLots, add to it the ability to produce SurroundingHeights and SurroundingDepths
	public PlatLot[][] getNeighborPlatLots(PlatMap platmap, int platX, int platZ, boolean onlyConnectedNeighbors) {
		PlatLot[][] miniPlatMap = new PlatLot[3][3];
		
		// populate the results
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				
				// which platchunk are we looking at?
				int atX = platX + x - 1;
				int atZ = platZ + z - 1;

				// is it in bounds?
				if (!(atX < 0 || atX > PlatMap.Width - 1 || atZ < 0 || atZ > PlatMap.Width - 1)) {
					PlatLot relative = platmap.getLot(atX, atZ);
					
					if (!onlyConnectedNeighbors || isConnected(relative)) {
						miniPlatMap[x][z] = relative;
					}
				}
			}
		}
		
		return miniPlatMap;
	}

	protected void generateSurface(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ, boolean includeTrees) {
		
		// compute offset to start of chunk
		int blockX = chunk.chunkX * chunk.width;
		int blockZ = chunk.chunkZ * chunk.width;
		
		// precalculate 
		int deciduousRange = generator.evergreenLevel - generator.treeLevel;
		int evergreenRange = generator.snowLevel - generator.evergreenLevel;
		
		// plant grass or snow
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				double perciseY = generator.findPerciseY(blockX + x, blockZ + z);
				int y = NoiseGenerator.floor(perciseY);
				
				// roll the dice
				double primary = chunkRandom.nextDouble();
				double secondary = chunkRandom.nextDouble();
				
				// top of the world?
				if (y >= generator.snowLevel) {
					y = chunk.findLastEmptyBelow(x, y + 1, z);
					if (chunk.isEmpty(x, y, z))
						chunk.setBlock(x, y, z, snowMaterialId, (byte) NoiseGenerator.floor((perciseY - Math.floor(perciseY)) * 8.0));
				
				// are on a plantable spot?
				} else if (chunk.isPlantable(x, y, z)) {
					
					// regular trees, grass and flowers only
					if (y < generator.treeLevel) {
	
						// trees? but only if we are not too close to the edge
						if (includeTrees && primary > 0.97 && x > 2 && x < 14 && z > 2 && z < 14) {
							if (secondary > 0.90 && x > 5 && x < 11 && z > 5 && z < 11)
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.BIG_TREE);
							else if (secondary > 0.70 && generator.settings.tekkitServer)
								chunk.setBlock(x, y + 1, z, 241,(byte) 0);
							else if (secondary > 0.50)
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.BIRCH);
							else 
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.TREE);
						
						// foliage?
						} else if (primary > 0.75) {
							
							// what to pepper about
							if (secondary > 0.90)
								chunk.setBlock(x, y + 1, z, roseMaterial);
							else if (secondary > 0.80)
								chunk.setBlock(x, y + 1, z, dandelionMaterial);
							else
								chunk.setBlock(x, y + 1, z, grassMaterialId, (byte) 1);
						}
						
					// regular trees, grass and some evergreen trees... no flowers
					} else if (y < generator.evergreenLevel) {
	
						// trees? but only if we are not too close to the edge
						if (includeTrees && primary > 0.90 && x > 2 && x < 14 && z > 2 && z < 14) {
							
							// range change?
							if (secondary > ((double) (y - generator.treeLevel) / (double) deciduousRange))
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.TREE);
							else
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.REDWOOD);
						
						// foliage?
						} else if (primary > 0.75) {

							// range change?
							if (secondary > ((double) (y - generator.treeLevel) / (double) deciduousRange))
								chunk.setBlock(x, y + 1, z, grassMaterialId, (byte) 1);
							else
								chunk.setBlock(x, y + 1, z, grassMaterialId, (byte) 2);
						}
						
					// evergreen and some grass and fallen snow, no regular trees or flowers
					} else if (y < generator.snowLevel) {
						
						// trees? but only if we are not too close to the edge
						if (includeTrees && primary > 0.90 && x > 2 && x < 14 && z > 2 && z < 14) {
							if (secondary > 0.50)
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.REDWOOD);
							else
								chunk.world.generateTree(chunk.getBlockLocation(x, y + 1, z), TreeType.TALL_REDWOOD);
						
						// foliage?
						} else if (primary > 0.40) {
							
							// range change?
							if (secondary > ((double) (y - generator.evergreenLevel) / (double) evergreenRange)) {
								if (chunkRandom.nextBoolean())
									chunk.setBlock(x, y + 1, z, grassMaterialId, (byte) 2);
							} else {
								chunk.setBlock(x, y + 1, z, snowMaterial);
							}
						}
					}
				
				// can't plant, maybe there is something else I can do
				} else {
					
					// regular trees, grass and flowers only
					if (y < generator.treeLevel) {
	
					// regular trees, grass and some evergreen trees... no flowers
					} else if (y < generator.evergreenLevel) {
	
					// evergreen and some grass and fallen snow, no regular trees or flowers
					} else if (y < generator.snowLevel) {
						
						if (primary > 0.40) {
							
							// range change?
							if (secondary > ((double) (y - generator.evergreenLevel) / (double) evergreenRange)) {
//								if (chunkRandom.nextBoolean())
//									chunk.setBlock(x, y + 1, z, grassId, (byte) 2);
							} else {
								y = chunk.findLastEmptyBelow(x, y + 1, z);
								if (chunk.isEmpty(x, y, z))
									chunk.setBlock(x, y, z, snowMaterial);
							}
						}
					}
				}
			}
		}
	}
}
