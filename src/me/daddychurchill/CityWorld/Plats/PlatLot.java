package me.daddychurchill.CityWorld.Plats;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.AbstractBlocks;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.BadMagic;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public abstract class PlatLot {
	
	// extremes
	protected int chunkX;
	protected int chunkZ;
	protected CachedYs blockYs;
	
//	protected Odds platmapOdds;
	protected Odds chunkOdds;
	
	// styling!
	public enum LotStyle {NATURE, STRUCTURE, ROAD, ROUNDABOUT};
	public LotStyle style;
	public boolean trulyIsolated;
	public boolean inACity;
	
	protected Material pavementSidewalk;
	protected Material dirtroadSidewalk;
	
	public PlatLot(PlatMap platmap, int chunkX, int chunkZ) {
		super();
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.style = LotStyle.NATURE;
		this.trulyIsolated = false;
		this.inACity = platmap.generator.settings.inCityRange(chunkX, chunkZ);

		// pavement is 0, read in RoadLot
		// lines is 1, read in RoadLot
		pavementSidewalk = platmap.generator.materialProvider.itemsMaterialListFor_Roads.getNthMaterial(2, Material.STEP);
		// dirt is 3, read in RoadLot
		dirtroadSidewalk = platmap.generator.materialProvider.itemsMaterialListFor_Roads.getNthMaterial(4, Material.DIRT);
		
		initializeDice(platmap, chunkX, chunkZ);

		// precalc the Ys
		blockYs = platmap.generator.shapeProvider.getCachedYs(platmap.generator, chunkX, chunkZ);
	}

	public abstract long getConnectedKey();
	public abstract boolean makeConnected(PlatLot relative);
	public abstract boolean isConnectable(PlatLot relative);
	public abstract boolean isConnected(PlatLot relative);
	
	public abstract PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ);

	protected abstract void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ);
	protected abstract void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ);

	public int getChunkX() {
		return chunkX;
	}
	
	public int getChunkZ() {
		return chunkZ;
	}
	
	protected void reportLocation(CityWorldGenerator generator, String title, AbstractBlocks chunk) {
		reportLocation(generator, title, chunk.getOriginX(), chunk.getOriginZ());
	}
	
	protected void reportLocation(CityWorldGenerator generator, String title, int originX, int originZ) {
		if (generator.settings.broadcastSpecialPlaces)
			generator.reportMessage(title + " placed at " + originX + ", " + originZ);
	}
	
	public Biome getChunkBiome() {
		return Biome.PLAINS;
	}
	
	public boolean isPlaceableAt(CityWorldGenerator generator, int chunkX, int chunkZ) {
		return generator.settings.inCityRange(chunkX, chunkZ);
	}
	
	public PlatLot validateLot(PlatMap platmap, int platX, int platZ) {
		return null; // assume that we don't do anything
	}
	
	public RoadLot repaveLot(CityWorldGenerator generator, PlatMap platmap) {
		return null; // same here
	}
	
	private void initializeDice(PlatMap platmap, int chunkX, int chunkZ) {
		
		// reset and pick up the dice
//		platmapOdds = platmap.getOddsGenerator();
		chunkOdds = platmap.getChunkOddsGenerator(chunkX, chunkZ);
	}
	
	protected int getSidewalkLevel(CityWorldGenerator generator) {
		int result = generator.streetLevel;
		if (inACity)
			return result + 1;
		else
			return result;
	}
	
	protected Material getSidewalkMaterial() {
		if (inACity)
			return pavementSidewalk;
		else
			return dirtroadSidewalk;
	}
	
	protected int getBlockY(int x, int z) {
		return blockYs == null ? 0 : blockYs.getBlockY(x, z);
	}
	
//	public double getAverageY() {
//		return blockYs == null ? 0 : blockYs.averageHeight;
//	}
//	
//	protected double getPerciseY(int x, int z) {
//		return blockYs == null ? 0 : blockYs.getPerciseY(x, z);
//	}
//	
	protected int getSurfaceAtY(int x, int z) {
		return getSurfaceAtY(x, 15 - x, z, 15 - z);
	}
	
	protected int getSurfaceAtY(int x1, int x2, int z1, int z2) {
		int surfaceY = Math.min(getBlockY(x1, z1), getBlockY(x2, z1));
		surfaceY = Math.min(surfaceY, getBlockY(x1, z2));
		surfaceY = Math.min(surfaceY, getBlockY(x2, z2));
		return surfaceY;
	}
	
	public abstract int getBottomY(CityWorldGenerator generator);
	public abstract int getTopY(CityWorldGenerator generator);

	//TODO: It seems that Spigot is generating the real blocks twice (generateBlocks) for each time the blocks are initialized (generateChunk)
//	private static int totalNumberOfLotsOverGenerated = 0;
//	private static int totalNumberOfGeneratedChunks = 0;
	private int generateBlocksCallCountForThisLot = 0;
	
	protected void flattenLot(CityWorldGenerator generator, AbstractBlocks chunk, int maxLayersToDo) {
		if (blockYs.maxHeight > generator.streetLevel & blockYs.maxHeight <= generator.streetLevel + maxLayersToDo) {
			chunk.airoutLayer(generator, generator.streetLevel + 1, Math.min(blockYs.maxHeight - generator.streetLevel + 1, maxLayersToDo));
		}
	}
		
	public void generateChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
//		if (chunk.sectionX != chunkX || chunk.sectionZ != chunkZ)
//			generator.reportFormatted("!!!!!2! Wrong chunk [%d, %d] for Platlot [%d, %d]", chunk.sectionX, chunk.sectionZ, chunkX, chunkZ);
//		
//		burp(generator, 1, false);
		initializeDice(platmap, chunk.sectionX, chunk.sectionZ);
		
		// what do we need to first?
//		burp(generator, 2, false);
		generator.shapeProvider.preGenerateChunk(generator, this, chunk, biomes, blockYs);
		
		// let the specialized platlot do it's thing
//		burp(generator, 3, false);
		generateActualChunk(generator, platmap, chunk, biomes, context, platX, platZ);
		generateBlocksCallCountForThisLot = 0;
//		totalNumberOfGeneratedChunks++;
		
		// polish things off
//		burp(generator, 4, false);
		generator.shapeProvider.postGenerateChunk(generator, this, chunk, blockYs);
//		burp(generator, 5, false);
	}
		
	public void generateBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {
		
		//TODO: This code makes sure that there is a single generateBlocks for each generateChunk... and occasionally reports how often the problem occurred.
		generateBlocksCallCountForThisLot++;
		if (generateBlocksCallCountForThisLot > 1) {
//			totalNumberOfLotsOverGenerated++;
//			if (totalNumberOfLotsOverGenerated % 100 == 0)
//				generator.reportMessage(String.format("OVERGEN: At least %3.1f percentage of the lots have been over generated", 
//						((double)totalNumberOfLotsOverGenerated / (double)totalNumberOfGeneratedChunks) * 100));
			return;
		}
		
		initializeDice(platmap, chunk.sectionX, chunk.sectionZ);
		
		// what do we need to first?
		generator.shapeProvider.preGenerateBlocks(generator, this, chunk, blockYs);
		
		// let the specialized platlot do it's thing
		generateActualBlocks(generator, platmap, chunk, context, platX, platZ);
		
		// polish things off
		generator.shapeProvider.postGenerateBlocks(generator, this, chunk, blockYs);
	}
	
	protected void destroyLot(CityWorldGenerator generator, int y1, int y2) {
		int x1 = chunkX * SupportBlocks.sectionBlockWidth;
		int z1 = chunkZ * SupportBlocks.sectionBlockWidth;
		generator.decayBlocks.destroyWithin(x1, x1 + SupportBlocks.sectionBlockWidth, y1, y2, z1, z1 + SupportBlocks.sectionBlockWidth);
	}
	
	public void destroyBuilding(CityWorldGenerator generator, int y, int floors) {
		destroyLot(generator, y, y + DataContext.FloorHeight * (floors + 1));
	}
	
	private final static int lowestMineSegment = 16;
	
	public void generateMines(CityWorldGenerator generator, InitialBlocks chunk) {
		
		// get shafted! (this builds down to keep the support poles happy)
		if (generator.settings.includeMines)
			for (int y = (blockYs.minHeight / 16 - 1) * 16; y >= lowestMineSegment; y -= 16) {
				if (isShaftableLevel(generator, y))
					generateHorizontalMineLevel(generator, chunk, y);
			}
	}
	
	protected int findHighestShaftableLevel(CityWorldGenerator generator, DataContext context, SupportBlocks chunk) {

		// keep going down until we find what we are looking for
		for (int y = (blockYs.minHeight / 16 - 1) * 16; y >= lowestMineSegment; y -= 16) {
			if (isShaftableLevel(generator, y) && generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y, chunk.sectionZ))
				return y + 7;
		}
		
		// nothing found
		return 0;
	}
	
	protected boolean isShaftableLevel(CityWorldGenerator generator, int blockY) {
		return blockY >= lowestMineSegment && blockY < blockYs.minHeight && blockYs.minHeight > generator.seaLevel;
	}

	private void generateHorizontalMineLevel(CityWorldGenerator generator, InitialBlocks chunk, int y) {
		int y1 = y + 6;
		int y2 = y1 + 1;
		
		// draw the shafts/walkways
		boolean pathFound = false;
		if (generator.shapeProvider.isHorizontalNSShaft(chunk.sectionX, y, chunk.sectionZ)) {
			generateMineShaftSpace(generator, chunk, 6, 10, y1, y1 + 4, 0, 6);
			generateMineNSSupport(chunk, 6, y2, 1);
			generateMineNSSupport(chunk, 6, y2, 4);
			generateMineShaftSpace(generator, chunk, 6, 10, y1, y1 + 4, 10, 16);
			generateMineNSSupport(chunk, 6, y2, 11);
			generateMineNSSupport(chunk, 6, y2, 14);
			pathFound = true;
		}
		if (generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y, chunk.sectionZ)) {
			generateMineShaftSpace(generator, chunk, 0, 6, y1, y1 + 4, 6, 10);
			generateMineWESupport(chunk, 1, y2, 6);
			generateMineWESupport(chunk, 4, y2, 6);
			generateMineShaftSpace(generator, chunk, 10, 16, y1, y1 + 4, 6, 10);
			generateMineWESupport(chunk, 11, y2, 6);
			generateMineWESupport(chunk, 14, y2, 6);
			pathFound = true;
		}
		
		// draw the center bit
		if (pathFound)
			generateMineShaftSpace(generator, chunk, 6, 10, y1, y1 + 4, 6, 10);
	}
	
	private final static Material shaftBridge = Material.WOOD; 
	private final static Material shaftSupport = Material.FENCE;
	private final static Material shaftBeam = Material.WOOD;

	private void generateMineShaftSpace(CityWorldGenerator generator, InitialBlocks chunk, int x1, int x2, int y1, int y2, int z1, int z2) {
		chunk.setEmptyBlocks(x1, x2, y1, z1, z2, shaftBridge);
		chunk.airoutBlocks(generator, x1, x2, y1 + 1, y2, z1, z2);
	}
	
	private void generateMineNSSupport(InitialBlocks chunk, int x, int y, int z) {
		
		// on a bridge
		if (chunk.isType(x, y - 1, z, shaftBridge) && 
			chunk.isType(x + 3, y - 1, z, shaftBridge)) {
			
			// place supports
			generateMineSupport(chunk, x, y - 1, z);
			generateMineSupport(chunk, x + 3, y - 1, z);
			
		// in a tunnel
		} else {
			chunk.setBlock(x, y, z, shaftSupport);
			chunk.setBlock(x, y + 1, z, shaftSupport);
			chunk.setBlock(x + 3, y, z, shaftSupport);
			chunk.setBlock(x + 3, y + 1, z, shaftSupport);
			chunk.setBlocks(x, x + 4, y + 2, z, z + 1, shaftBeam);
		}
	}
	
	private void generateMineWESupport(InitialBlocks chunk, int x, int y, int z) {
		// on a bridge
		if (chunk.isType(x, y - 1, z, shaftBridge) && 
			chunk.isType(x, y - 1, z + 3, shaftBridge)) {
			
			// place supports
			generateMineSupport(chunk, x, y - 1, z);
			generateMineSupport(chunk, x, y - 1, z + 3);
			
		// in a tunnel
		} else {
			chunk.setBlock(x, y, z, shaftSupport);
			chunk.setBlock(x, y + 1, z, shaftSupport);
			chunk.setBlock(x, y, z + 3, shaftSupport);
			chunk.setBlock(x, y + 1, z + 3, shaftSupport);
			chunk.setBlocks(x, x + 1, y + 2, z, z + 4, shaftBeam);
		}
	}
	
	private void generateMineSupport(InitialBlocks chunk, int x, int y, int z) {
		int aboveSupport = chunk.findLastEmptyAbove(x, y, z, blockYs.maxHeight);
		if (aboveSupport < blockYs.maxHeight)
			chunk.setBlocks(x, y + 1, aboveSupport + 1, z, shaftSupport);
	}
	
	public void generateMines(CityWorldGenerator generator, RealBlocks chunk) {
		
		// get shafted!
		if (generator.settings.includeMines)
			for (int y = 0; y + 16 < blockYs.minHeight; y += 16) {
				if (isShaftableLevel(generator, y))
					generateVerticalMineLevel(generator, chunk, y);
			}
	}
	
	private void generateVerticalMineLevel(CityWorldGenerator generator, RealBlocks chunk, int y) {
		int y1 = y + 6;
		boolean stairsFound = false;
		
		// going down?
		if (isShaftableLevel(generator, y - 16)) {
			if (generator.shapeProvider.isHorizontalNSShaft(chunk.sectionX, y, chunk.sectionZ) &&
				generator.shapeProvider.isHorizontalNSShaft(chunk.sectionX, y - 16, chunk.sectionZ)) {
				
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
				generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y, chunk.sectionZ) &&
				generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y - 16, chunk.sectionZ)) {
				
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
		if (isShaftableLevel(generator, y + 32)) {
			if (generator.shapeProvider.isHorizontalNSShaft(chunk.sectionX, y, chunk.sectionZ) &&
				generator.shapeProvider.isHorizontalNSShaft(chunk.sectionX, y + 16, chunk.sectionZ)) {
					
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
				generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y, chunk.sectionZ) &&
				generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y + 16, chunk.sectionZ)) {
				
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
		if (generator.shapeProvider.isHorizontalNSShaft(chunk.sectionX, y, chunk.sectionZ)) {
			generateMineCeiling(chunk, 6, 10, y1 + 3, 0, 6);
			generateMineCeiling(chunk, 6, 10, y1 + 3, 10, 16);
			
			generateMineAlcove(generator, chunk, 4, y1, 2, 4, 2);
			generateMineAlcove(generator, chunk, 10, y1, 2, 11, 3);

			pathFound = true;
		}
		if (generator.shapeProvider.isHorizontalWEShaft(chunk.sectionX, y, chunk.sectionZ)) {
			generateMineCeiling(chunk, 0, 6, y1 + 3, 6, 10);
			generateMineCeiling(chunk, 10, 16, y1 + 3, 6, 10);

			generateMineAlcove(generator, chunk, 2, y1, 4, 2, 4);
			generateMineAlcove(generator, chunk, 2, y1, 10, 3, 11);
			
			pathFound = true;
		}
		
		// draw the center bit
		if (pathFound)
			generateMineCeiling(chunk, 6, 10, y1 + 3, 6, 10);
	}
	
	
	private void generateMineAlcove(CityWorldGenerator generator, RealBlocks chunk, int x, int y, int z, int prizeX, int prizeZ) {
		if (chunkOdds.playOdds(generator.settings.oddsOfAlcoveInMines)) {
			if (!chunk.isEmpty(x, y, z) &&
				!chunk.isEmpty(x + 1, y, z) &&
				!chunk.isEmpty(x, y, z + 1) &&
				!chunk.isEmpty(x + 1, y, z + 1)) {
				chunk.setBlocks(x, x + 2, y + 1, y + 4, z, z + 2, Material.AIR);
				generateMineCeiling(chunk, x, x + 2, y + 3, z, z + 2);
				if (chunkOdds.flipCoin())
					generateMineTrick(generator, chunk, prizeX, y + 1, prizeZ);
				else
					generateMineTreat(generator, chunk, prizeX, y + 1, prizeZ);
			}
		}
	}
	
	private void generateMineCeiling(RealBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				if (chunkOdds.flipCoin())
					if (!chunk.isEmpty(x, y + 1, z) && chunk.isEmpty(x, y, z))
						chunk.setStoneSlab(x, y, z, BadMagic.StoneSlab.COBBLESTONEFLIP);
			}
		}
	}
	
	private void generateMineSupport(RealBlocks chunk, int x, int y, int z) {
		int aboveSupport = chunk.findLastEmptyAbove(x, y, z, blockYs.maxHeight);
		if (aboveSupport < blockYs.maxHeight)
			chunk.setBlocks(x, y + 1, aboveSupport + 1, z, Material.FENCE);
	}
	private void placeMineStairBase(RealBlocks chunk, int x, int y, int z) {
		chunk.setBlocks(x, y + 1, y + 4, z, Material.AIR);
		chunk.setEmptyBlock(x, y, z, Material.WOOD);
	}
	
	private void placeMineStairStep(RealBlocks chunk, int x, int y, int z, Stair direction, Stair flipDirection) {
		chunk.setBlocks(x, y + 1, y + 4, z, Material.AIR);
		chunk.setStair(x, y, z, Material.WOOD_STAIRS, direction);
		if (chunk.isEmpty(x, y - 1, z))
			chunk.setStair(x, y - 1, z, Material.WOOD_STAIRS, flipDirection);
	}
	
	private void generateMineTreat(CityWorldGenerator generator, RealBlocks chunk, int x, int y, int z) {

		// cool stuff?
		if (generator.settings.treasuresInMines && chunkOdds.playOdds(generator.settings.oddsOfTreasureInMines)) {
			 chunk.setChest(generator, x, y, z, BadMagic.General.SOUTH, chunkOdds, generator.lootProvider, LootLocation.MINE);
		}
	}

	private void generateMineTrick(CityWorldGenerator generator, RealBlocks chunk, int x, int y, int z) {
		// not so cool stuff?
		generator.spawnProvider.setSpawnOrSpawner(generator, chunk, chunkOdds, x, y, z, 
				generator.settings.spawnersInMines, generator.spawnProvider.itemsEntities_Mine);
	}

	public boolean isValidStrataY(CityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return true;
	}
	
	public void generateBones(CityWorldGenerator generator, RealBlocks chunk) {

		// fossils?
		if (generator.settings.includeBones && chunkOdds.playOdds(Odds.oddsExceedinglyUnlikely))
			generator.thingProvider.generateBones(generator, this, chunk, blockYs, chunkOdds);
	}
	
	public void generateOres(CityWorldGenerator generator, RealBlocks chunk) {
		
		// shape the world
		if (generator.settings.includeOres || generator.settings.includeUndergroundFluids)
			generator.oreProvider.sprinkleOres(generator, this, chunk, blockYs, chunkOdds);
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
	
	public void generateSurface(CityWorldGenerator generator, SupportBlocks chunk, boolean includeTrees) {
		generateSurface(generator, chunk, 0, includeTrees);
	}
	
	public void generateSurface(CityWorldGenerator generator, SupportBlocks chunk, int addTo, boolean includeTrees) {
		
		// plant grass or snow... sometimes we want the sprinker to start a little higher
		generator.surfaceProvider.generateSurface(generator, this, chunk, blockYs, addTo, includeTrees);
	}
	
	protected boolean clearAir(CityWorldGenerator generator) {
		return generator.shapeProvider.clearAtmosphere(generator);
	}
	
//	protected Material getAirMaterial(CityWorldGenerator generator, int y) {
//		if (getTopY(generator) <= y)
//			return Material.AIR;
//		else
//			return generator.shapeProvider.findAtmosphereMaterialAt(generator, y);
//	}
}
