package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Plugins.OreProvider.OreLocation;
import me.daddychurchill.CityWorld.Plugins.SpawnProvider.SpawnerLocation;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;
import me.daddychurchill.CityWorld.Support.WorldBlocks;

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
	protected final static byte stoneId = (byte) Material.STONE.getId();
	protected final static byte dirtId = (byte) Material.DIRT.getId();
	protected final static byte grassId = (byte) Material.GRASS.getId();
	protected final static byte snowId = (byte) Material.SNOW_BLOCK.getId();
	protected final static byte sandId = (byte) Material.SAND.getId();
	protected final static byte sandstoneId = (byte) Material.SANDSTONE.getId();
	protected final static byte bedrockId = (byte) Material.BEDROCK.getId();
	protected final static byte fenceId = (byte) Material.FENCE.getId();
	protected final static byte cobbleId = (byte) Material.COBBLESTONE.getId();
	protected final static byte stillWaterId = (byte) Material.STATIONARY_WATER.getId();
	protected final static byte stillLavaId = (byte) Material.STATIONARY_LAVA.getId();
	protected final static byte waterId = (byte) Material.WATER.getId();
	protected final static byte lavaId = (byte) Material.LAVA.getId();
	protected final static byte leavesId = (byte) Material.LEAVES.getId();
	protected final static byte glassId = (byte) Material.GLASS.getId();
	protected final static byte paneId = (byte) Material.THIN_GLASS.getId();
	protected final static byte logId = (byte) Material.LOG.getId();
	protected final static byte glowId = (byte) Material.GLOWSTONE.getId();
	protected final static byte stepId = (byte) Material.STEP.getId();
	protected final static byte clayId = (byte) Material.CLAY.getId();
	protected final static byte ironFenceId = (byte) Material.IRON_FENCE.getId();
	protected final static byte endId = (byte) Material.ENDER_STONE.getId();
	protected final static byte netherrackId = (byte) Material.NETHERRACK.getId();
	protected final static byte soulsandId = (byte) Material.SOUL_SAND.getId();

	protected final static int snowMaterialId = Material.SNOW.getId();
	protected final static Material snowMaterial = Material.SNOW;
	protected final static Material airMaterial = Material.AIR;
	protected final static Material stoneMaterial = Material.STONE;
	protected final static Material rootMaterial = Material.GRASS;
	
	public abstract long getConnectedKey();
	public abstract boolean makeConnected(PlatLot relative);
	public abstract boolean isConnectable(PlatLot relative);
	public abstract boolean isConnected(PlatLot relative);
	
	protected abstract void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ);
	protected abstract void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ);

	public Biome getChunkBiome() {
		return Biome.PLAINS;
	}
	
	public boolean isPlaceableAt(WorldGenerator generator, int chunkX, int chunkZ) {
		return generator.settings.inCityRange(chunkX, chunkZ);
	}
	
	private void initializeDice(PlatMap platmap, int chunkX, int chunkZ) {
		
		// reset and pick up the dice
		platmapRandom = platmap.getRandomGenerator();
		chunkRandom = platmap.getChunkRandomGenerator(chunkX, chunkZ);
	}
	
	public void generateChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		initializeDice(platmap, chunk.chunkX, chunk.chunkZ);
		
		// let there be dirt!
		generateCrust(generator, platmap, chunk, biomes, context, platX, platZ);
		
		// let the specialized platlot do it's thing
		generateActualChunk(generator, platmap, chunk, biomes, context, platX, platZ);
		
		// do we do it or not?
		if (generator.settings.includeMines)
			generateMines(generator, chunk, context);
	}
		
	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		initializeDice(platmap, chunk.chunkX, chunk.chunkZ);
		
		// let the specialized platlot do it's thing
		generateActualBlocks(generator, platmap, chunk, context, platX, platZ);
		
		// put ores in?
		generateOres(generator, chunk);

		// do we do it or not?
		if (generator.settings.includeMines)
			generateMines(generator, chunk, context);
	}
	
	protected void generateCrust(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {

		// total height
		int sumHeight = 0;
		
		// compute offset to start of chunk
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();
		
		// surface caves?
		boolean surfaceCaves = generator.isSurfaceCaveAt(chunk.chunkX, chunk.chunkZ);
		
		// calculate the Ys for this chunk
		int[][] blocksY= new int[16][16];
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {

				// how high are we?
				blocksY[x][z] = generator.findBlockY(originX + x, originZ + z);
				
				// keep the tally going
				sumHeight += blocksY[x][z];
				if (blocksY[x][z] < minHeight) {
					minHeight = blocksY[x][z];
					minHeightX = x;
					minHeightZ = z;
				}
				if (blocksY[x][z] > maxHeight) {
					maxHeight = blocksY[x][z];
					maxHeightX = x;
					maxHeightZ = z;
				}
			}
		}
		
		// shape the world
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				biomes.setBiome(x, z, generator.shapeProvider.generateCrust(generator, this, chunk, x, blocksY[x][z], z, surfaceCaves));
			}
		}
		
		// what was the average height
		averageHeight = sumHeight / (chunk.width * chunk.width);
		extremeComputed = true;
	}
	
	private final static int lowestMineSegment = 16;
	
	protected void generateMines(WorldGenerator generator, ByteChunk chunk, DataContext context) {
		
		// make sure we have the facts
		precomputeExtremes(generator, chunk);
		
		// get shafted! (this builds down to keep the support poles happy)
		for (int y = (minHeight / 16 - 1) * 16; y >= lowestMineSegment; y -= 16) {
			if (isShaftableLevel(generator, context, y))
				generateHorizontalMineLevel(generator, chunk, context, y);
		}
	}
	
	protected int findHighestShaftableLevel(WorldGenerator generator, DataContext context, SupportChunk chunk) {

		// make sure we have the facts
		precomputeExtremes(generator, chunk);
		
		// keep going down until we find what we are looking for
		for (int y = (minHeight / 16 - 1) * 16; y >= lowestMineSegment; y -= 16) {
			if (isShaftableLevel(generator, context, y) && generator.shapeProvider.isHorizontalWEShaft(chunk.chunkX, y, chunk.chunkZ))
				return y + 7;
		}
		
		// nothing found
		return 0;
	}
	
	protected boolean isShaftableLevel(WorldGenerator generator, DataContext context, int y) {
		return y >= lowestMineSegment && y < minHeight && minHeight > generator.seaLevel;
	}

	private void generateHorizontalMineLevel(WorldGenerator generator, ByteChunk chunk, DataContext context, int y) {
		int y1 = y + 6;
		int y2 = y1 + 1;
		
		// draw the shafts/walkways
		boolean pathFound = false;
		if (generator.shapeProvider.isHorizontalNSShaft(chunk.chunkX, y, chunk.chunkZ)) {
			generateMineShaftSpace(chunk, 6, 10, y1, y1 + 4, 0, 6);
			generateMineNSSupport(chunk, 6, y2, 1);
			generateMineNSSupport(chunk, 6, y2, 4);
			generateMineShaftSpace(chunk, 6, 10, y1, y1 + 4, 10, 16);
			generateMineNSSupport(chunk, 6, y2, 11);
			generateMineNSSupport(chunk, 6, y2, 14);
			pathFound = true;
		}
		if (generator.shapeProvider.isHorizontalWEShaft(chunk.chunkX, y, chunk.chunkZ)) {
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
		
	protected void generateMines(WorldGenerator generator, RealChunk chunk, DataContext context) {
		
		// make sure we have the facts
		precomputeExtremes(generator, chunk);

		// get shafted!
		for (int y = 0; y + 16 < minHeight; y += 16) {
			if (isShaftableLevel(generator, context, y))
				generateVerticalMineLevel(generator, chunk, context, y);
		}
	}
	
	private void generateVerticalMineLevel(WorldGenerator generator, RealChunk chunk, DataContext context, int y) {
		int y1 = y + 6;
		boolean stairsFound = false;
		
		// going down?
		if (isShaftableLevel(generator, context, y - 16)) {
			if (generator.shapeProvider.isHorizontalNSShaft(chunk.chunkX, y, chunk.chunkZ) &&
				generator.shapeProvider.isHorizontalNSShaft(chunk.chunkX, y - 16, chunk.chunkZ)) {
				
				// draw the going down bit
				placeMineStairBase(chunk, 10, y1	, 15);
				placeMineStairStep(chunk, 10, y1    , 14, Stair.SOUTH, Stair.NORTHFLIP);
				placeMineStairStep(chunk, 10, y1 - 1, 13, Stair.SOUTH, Stair.NORTHFLIP);
				placeMineStairStep(chunk, 10, y1 - 2, 12, Stair.SOUTH, Stair.NORTHFLIP);
				placeMineStairStep(chunk, 10, y1 - 3, 11, Stair.SOUTH, Stair.NORTHFLIP);
				placeMineStairStep(chunk, 10, y1 - 4, 10, Stair.SOUTH, Stair.NORTHFLIP);
				placeMineStairStep(chunk, 10, y1 - 5,  9, Stair.SOUTH, Stair.NORTHFLIP);
				placeMineStairStep(chunk, 10, y1 - 6,  8, Stair.SOUTH, Stair.NORTHFLIP);
				stairsFound = true;
			}
			
			if (!stairsFound &&
				generator.shapeProvider.isHorizontalWEShaft(chunk.chunkX, y, chunk.chunkZ) &&
				generator.shapeProvider.isHorizontalWEShaft(chunk.chunkX, y - 16, chunk.chunkZ)) {
				
				// draw the going down bit
				placeMineStairBase(chunk, 15, y1	, 10);
				placeMineStairStep(chunk, 14, y1    , 10, Stair.EAST, Stair.WESTFLIP);
				placeMineStairStep(chunk, 13, y1 - 1, 10, Stair.EAST, Stair.WESTFLIP);
				placeMineStairStep(chunk, 12, y1 - 2, 10, Stair.EAST, Stair.WESTFLIP);
				placeMineStairStep(chunk, 11, y1 - 3, 10, Stair.EAST, Stair.WESTFLIP);
				placeMineStairStep(chunk, 10, y1 - 4, 10, Stair.EAST, Stair.WESTFLIP);
				placeMineStairStep(chunk,  9, y1 - 5, 10, Stair.EAST, Stair.WESTFLIP);
				placeMineStairStep(chunk,  8, y1 - 6, 10, Stair.EAST, Stair.WESTFLIP);
			}
		}
		
		// reset the stairs flag
		stairsFound = false;
		
		// going up?
		if (isShaftableLevel(generator, context, y + 32)) {
			if (generator.shapeProvider.isHorizontalNSShaft(chunk.chunkX, y, chunk.chunkZ) &&
				generator.shapeProvider.isHorizontalNSShaft(chunk.chunkX, y + 16, chunk.chunkZ)) {
					
				// draw the going up bit
				placeMineStairBase(chunk,  5, y1	, 15);
				placeMineStairStep(chunk,  5, y1 + 1, 14, Stair.NORTH, Stair.SOUTHFLIP);
				placeMineStairStep(chunk,  5, y1 + 2, 13, Stair.NORTH, Stair.SOUTHFLIP);
				placeMineStairStep(chunk,  5, y1 + 3, 12, Stair.NORTH, Stair.SOUTHFLIP);
				placeMineStairStep(chunk,  5, y1 + 4, 11, Stair.NORTH, Stair.SOUTHFLIP);
				placeMineStairStep(chunk,  5, y1 + 5, 10, Stair.NORTH, Stair.SOUTHFLIP);
				placeMineStairStep(chunk,  5, y1 + 6,  9, Stair.NORTH, Stair.SOUTHFLIP);
				placeMineStairStep(chunk,  5, y1 + 7,  8, Stair.NORTH, Stair.SOUTHFLIP);
				placeMineStairStep(chunk,  5, y1 + 8,  7, Stair.NORTH, Stair.SOUTHFLIP);
				placeMineStairBase(chunk,  5, y1 + 8,  6);
				placeMineStairBase(chunk,  6, y1 + 8,  6);
				placeMineStairBase(chunk,  7, y1 + 8,  6);
				placeMineStairBase(chunk,  8, y1 + 8,  6);
				placeMineStairBase(chunk,  9, y1 + 8,  6);
				placeMineStairBase(chunk, 10, y1 + 8,  6);
				placeMineStairStep(chunk, 10, y1 + 9,  7, Stair.SOUTH, Stair.NORTHFLIP);
				
				generateMineSupport(chunk, 6, y1 + 7, 7);
				generateMineSupport(chunk, 9, y1 + 7, 7);
				
				stairsFound = true;
			}
			
			if (!stairsFound &&
				generator.shapeProvider.isHorizontalWEShaft(chunk.chunkX, y, chunk.chunkZ) &&
				generator.shapeProvider.isHorizontalWEShaft(chunk.chunkX, y + 16, chunk.chunkZ)) {
				
				// draw the going up bit
				placeMineStairBase(chunk, 15, y1	,  5);
				placeMineStairStep(chunk, 14, y1 + 1,  5, Stair.WEST, Stair.EASTFLIP);
				placeMineStairStep(chunk, 13, y1 + 2,  5, Stair.WEST, Stair.EASTFLIP);
				placeMineStairStep(chunk, 12, y1 + 3,  5, Stair.WEST, Stair.EASTFLIP);
				placeMineStairStep(chunk, 11, y1 + 4,  5, Stair.WEST, Stair.EASTFLIP);
				placeMineStairStep(chunk, 10, y1 + 5,  5, Stair.WEST, Stair.EASTFLIP);
				placeMineStairStep(chunk,  9, y1 + 6,  5, Stair.WEST, Stair.EASTFLIP);
				placeMineStairStep(chunk,  8, y1 + 7,  5, Stair.WEST, Stair.EASTFLIP);
				placeMineStairStep(chunk,  7, y1 + 8,  5, Stair.WEST, Stair.EASTFLIP);
				placeMineStairBase(chunk,  6, y1 + 8,  5);
				placeMineStairBase(chunk,  6, y1 + 8,  6);
				placeMineStairBase(chunk,  6, y1 + 8,  7);
				placeMineStairBase(chunk,  6, y1 + 8,  8);
				placeMineStairBase(chunk,  6, y1 + 8,  9);
				placeMineStairBase(chunk,  6, y1 + 8, 10);
				placeMineStairStep(chunk,  7, y1 + 9, 10, Stair.EAST, Stair.WESTFLIP);
				
				generateMineSupport(chunk, 7, y1 + 7, 6);
				generateMineSupport(chunk, 7, y1 + 7, 9);
			}
		}
		
		// make the ceiling pretty
		boolean pathFound = false;
		if (generator.shapeProvider.isHorizontalNSShaft(chunk.chunkX, y, chunk.chunkZ)) {
			generateMineCeiling(chunk, 6, 10, y1 + 3, 0, 6);
			generateMineCeiling(chunk, 6, 10, y1 + 3, 10, 16);
			
			generateMineAlcove(generator, context, chunk, 4, y1, 2, 4, 2);
			generateMineAlcove(generator, context, chunk, 10, y1, 2, 11, 3);

			pathFound = true;
		}
		if (generator.shapeProvider.isHorizontalWEShaft(chunk.chunkX, y, chunk.chunkZ)) {
			generateMineCeiling(chunk, 0, 6, y1 + 3, 6, 10);
			generateMineCeiling(chunk, 10, 16, y1 + 3, 6, 10);

			generateMineAlcove(generator, context, chunk, 2, y1, 4, 2, 4);
			generateMineAlcove(generator, context, chunk, 2, y1, 10, 3, 11);
			
			pathFound = true;
		}
		
		// draw the center bit
		if (pathFound)
			generateMineCeiling(chunk, 6, 10, y1 + 3, 6, 10);
	}
	
	private void generateMineAlcove(WorldGenerator generator, DataContext context, RealChunk chunk, int x, int y, int z, int prizeX, int prizeZ) {
		if (chunkRandom.nextDouble() < 0.66) {
			if (!chunk.isEmpty(x, y, z) &&
				!chunk.isEmpty(x + 1, y, z) &&
				!chunk.isEmpty(x, y, z + 1) &&
				!chunk.isEmpty(x + 1, y, z + 1)) {
				chunk.setBlocks(x, x + 2, y + 1, y + 4, z, z + 2, Material.AIR);
				generateMineCeiling(chunk, x, x + 2, y + 3, z, z + 2);
				if (chunkRandom.nextDouble() < 0.66) {
					if (chunkRandom.nextDouble() < 0.33)
						generateMineTreat(generator, context, chunk, prizeX, y + 1, prizeZ);
					else
						generateMineTrick(generator, context, chunk, prizeX, y + 1, prizeZ);
				}
			}
		}
	}
	
	private void generateMineCeiling(RealChunk chunk, int x1, int x2, int y, int z1, int z2) {
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
	
	private void placeMineStairStep(RealChunk chunk, int x, int y, int z, Stair direction, Stair flipDirection) {
		chunk.setBlocks(x, y + 1, y + 4, z, Material.AIR);
		chunk.setStair(x, y, z, Material.WOOD_STAIRS, direction);
		if (chunk.isEmpty(x, y - 1, z))
			chunk.setStair(x, y - 1, z, Material.WOOD_STAIRS, flipDirection);
	}
	
	private void generateMineTreat(WorldGenerator generator, DataContext context, RealChunk chunk, int x, int y, int z) {

		// cool stuff?
		if (generator.settings.treasuresInMines && chunkRandom.nextDouble() <= context.oddsOfTreasureInMines) {
			 chunk.setChest(x, y, z, Direction.Chest.SOUTH, generator.lootProvider.getItems(generator, chunkRandom, LootLocation.MINE));
		}
	}

	private void generateMineTrick(WorldGenerator generator, DataContext context, RealChunk chunk, int x, int y, int z) {
		// not so cool stuff?
		if (generator.settings.spawnersInMines && chunkRandom.nextDouble() <= context.oddsOfSpawnerInMines) {
			chunk.setSpawner(x, y, z, generator.spawnProvider.getEntity(generator, chunkRandom, SpawnerLocation.MINE));
		}
	}

	public boolean isValidStrataY(WorldGenerator generator, int blockX, int blockY, int blockZ) {
		return true;
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
			generator.oreProvider.sprinkleOres(generator, chunk, chunkRandom, OreLocation.CRUST);
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
	
	protected void generateSurface(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ, boolean includeTrees) {
		
		// compute offset to start of chunk
		int blockX = chunk.chunkX * chunk.width;
		int blockZ = chunk.chunkZ * chunk.width;
		
		// plant grass or snow
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				generator.foliageProvider.generateSurface(generator, this, chunk, x, generator.findPerciseY(blockX + x, blockZ + z), z, includeTrees);
			}
		}
	}
	
	protected void destroyBuilding(WorldGenerator generator, RealChunk chunk, int y, int floors) {
		destroyLot(generator, chunk, y, y + DataContext.FloorHeight * (floors + 1));
	}
	
	protected void destroyLot(WorldGenerator generator, RealChunk chunk, int y1, int y2) {
		destroyWithin(generator, chunk, 0, chunk.width, y1, y2, 0, chunk.width);
	}
	
	protected void destroyWithin(WorldGenerator generator, RealChunk chunk, int x1, int x2, int y1, int y2, int z1, int z2) {
		int count = Math.max(1, (y2 - y1) / DataContext.FloorHeight);
		
		// world centric 
		WorldBlocks blocks = new WorldBlocks(generator);
	
		// now destroy it
		while (count > 0) {
			
			// find a place
			int cx = chunk.getBlockX(chunkRandom.nextInt(x2 - x1) + x1);
			int cz = chunk.getBlockZ(chunkRandom.nextInt(z2 - z1) + z1);
			int cy = chunkRandom.nextInt(Math.max(1, y2 - y1)) + y1;
			int radius = chunkRandom.nextInt(3) + 3;
			
			// make it go away
			blocks.desperseArea(chunkRandom, cx, cy, cz, radius);
			
			// done with this round
			count--;
		}
	}
	
}
