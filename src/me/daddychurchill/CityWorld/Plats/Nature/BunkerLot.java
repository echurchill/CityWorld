package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConnectedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Plugins.SpawnProvider.SpawnerLocation;
import me.daddychurchill.CityWorld.Support.AbstractChunk;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class BunkerLot extends ConnectedLot {

	private final static int FloorHeight = DataContext.FloorHeight;
	
	public BunkerLot(PlatMap platmap, int chunkX, int chunkZ, boolean firstOne) {
		super(platmap, chunkX, chunkZ);
		
		this.firstOne = firstOne;
	}
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new BunkerLot(platmap, chunkX, chunkZ, false);
	}

	// these MUST be given in chunk segment units (currently 16) 
	private final static int bunkerSegment = 16;
	private final static int bunkerBuffer = bunkerSegment;
	private final static int bunkerBelowStreet = bunkerSegment;
	private final static int bunkerMinHeight = bunkerSegment * 2;
	private final static int bunkerMaxHeight = bunkerSegment * 8;

	protected boolean firstOne = false;
	protected int bottomOfBunker;
	protected int topOfBunker;
	
	protected enum BilgeType {EMPTY, WATER, LAVA, ICE};
	protected enum BunkerType {ENTRY, PYRAMID, TANK, QUAD, RECALL, BALLSY, FLOORED, GROWING}; // FARM, MISSILE, VENT

	protected BilgeType bilgeType;
	protected BunkerType buildingType;
	
	@Override
	protected void initializeContext(WorldGenerator generator, AbstractChunk chunk) {
		super.initializeContext(generator, chunk);
		
		bottomOfBunker = calcSegmentOrigin(generator.streetLevel) - bunkerBelowStreet;
		topOfBunker = calcBunkerCeiling(generator);
		
		// initial rolls
		bilgeType =    getRandomBilgeType();
		buildingType = getRandomBunkerType();
	}
	
	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);

		// other bits
		if (result && relative instanceof BunkerLot) {
			BunkerLot bunker = (BunkerLot)relative;
		
			bilgeType = bunker.bilgeType;
		}
		return result;
	}
	
	@Override
	public long getConnectedKey() {
		return connectedkey = 135792468; // all bunkers share this key
	}
	
	@Override
	public boolean isValidStrataY(WorldGenerator generator, int blockX, int blockY, int blockZ) {
		return blockY < bottomOfBunker || blockY >= topOfBunker;
	}

	@Override
	protected boolean isShaftableLevel(WorldGenerator generator, int blockY) {
		return (blockY < bottomOfBunker - bunkerBuffer || blockY > topOfBunker - bunkerSegment - bunkerBuffer) &&
				super.isShaftableLevel(generator, blockY);
//		
//		return (y < calcSegmentOrigin(generator.sidewalkLevel) - bunkerMinHeight - bunkerBelowStreet || y > calcBunkerCeiling(generator) - bunkerMinHeight) &&
//				super.isShaftableLevel(generator, context, y);	
	}
	
	private final static Material supportMaterial = Material.QUARTZ_BLOCK;
	private final static Material platformMaterial = Material.QUARTZ_BLOCK;
	private final static Material crosswalkMaterial = Material.QUARTZ_BLOCK;
	private final static Material railingMaterial = Material.IRON_FENCE;
	private final static Material buildingMaterial = Material.STAINED_CLAY;
	private final static Material windowMaterial = Material.GLASS;
	
	private static int calcSegmentOrigin(int y) {
		return y / bunkerSegment * bunkerSegment;
	}
	
	public static int calcBunkerMinHeight(WorldGenerator generator) {
		return calcSegmentOrigin(generator.streetLevel) + bunkerMinHeight - bunkerBelowStreet + bunkerBuffer;
	}
	
	public static int calcBunkerMaxHeight(WorldGenerator generator) {
		return calcSegmentOrigin(generator.streetLevel) + bunkerMaxHeight - bunkerBelowStreet + bunkerBuffer;
	}
	
	private int calcBunkerCeiling(WorldGenerator generator) {
		return Math.min(calcBunkerMaxHeight(generator), calcSegmentOrigin(minHeight) - bunkerBuffer);
	}

	@Override
	public int getBottomY(WorldGenerator generator) {
		return bottomOfBunker;
	}
	
	@Override
	public int getTopY(WorldGenerator generator) {
		return topOfBunker;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		
		// precalculate
		int yBottom = bottomOfBunker;//calcSegmentOrigin(generator.sidewalkLevel) - bunkerBelowStreet;
		int yTop4 = topOfBunker;//calcBunkerCeiling(generator);
		int yTop3 = yTop4 - 2;
		int yTop2 = yTop4 - bunkerSegment; 
		int yTop1 = yTop2 - 2;
		int yPlatform = calcSegmentOrigin(yBottom) + 6;
		
		// bottom
		chunk.setLayer(yBottom, supportMaterial);
		
		// clear out stuff?
		switch (bilgeType) {
		case WATER:
			chunk.setLayer(yBottom + 1, Material.STATIONARY_WATER);
			break;
		case LAVA:
			chunk.setLayer(yBottom + 1, Material.STATIONARY_LAVA);
			break;
		case ICE:
			chunk.setLayer(yBottom + 1, Material.ICE);
			break;
		case EMPTY:
			break;
		}
		
		// hold up buildings
		generateSupport(chunk, context, 3, yBottom + 1, 3);
		generateSupport(chunk, context, 3, yBottom + 1, 10);
		generateSupport(chunk, context, 10, yBottom + 1, 3);
		generateSupport(chunk, context, 10, yBottom + 1, 10);
		
		// vertical beams
		BlackMagic.setBlocks(chunk, 0, 2, yBottom + 1, yTop3, 0, 1, supportMaterial, 2);
		BlackMagic.setBlocks(chunk, 0, yBottom + 1, yTop3, 1, supportMaterial, 2);
		BlackMagic.setBlocks(chunk, 0, 2, yBottom + 1, yTop3, 15, 16, supportMaterial, 2);
		BlackMagic.setBlocks(chunk, 0, yBottom + 1, yTop3, 14, supportMaterial, 2);
		BlackMagic.setBlocks(chunk, 14, 16, yBottom + 1, yTop3, 0, 1, supportMaterial, 2);
		BlackMagic.setBlocks(chunk, 15, yBottom + 1, yTop3, 1, supportMaterial, 2);
		BlackMagic.setBlocks(chunk, 14, 16, yBottom + 1, yTop3, 15, 16, supportMaterial, 2);
		BlackMagic.setBlocks(chunk, 15, yBottom + 1, yTop3, 14, supportMaterial, 2);
		
		// near top cross beams
		chunk.setBlocks(0, 16, yTop1, yTop2, 0, 2, supportMaterial);
		chunk.setBlocks(0, 16, yTop1, yTop2, 14, 16, supportMaterial);
		chunk.setBlocks(0, 2, yTop1, yTop2, 2, 14, supportMaterial);
		chunk.setBlocks(14, 16, yTop1, yTop2, 2, 14, supportMaterial);
		
		// top cross beams
		chunk.setBlocks(0, 16, yTop3, yTop4, 0, 2, supportMaterial);
		chunk.setBlocks(0, 16, yTop3, yTop4, 14, 16, supportMaterial);
		chunk.setBlocks(0, 2, yTop3, yTop4, 2, 14, supportMaterial);
		chunk.setBlocks(14, 16, yTop3, yTop4, 2, 14, supportMaterial);
		
//		// clear out space between the top cross beams
//		chunk.setBlocks(2, 14, yTop3, yTop4, 2, 14, airId);
		
		// draw platform
		BlackMagic.setBlocks(chunk, 2, 14, yPlatform, 2, 14, platformMaterial, 1);
		
		// draw crosswalks
		chunk.setBlocks(7, 9, yPlatform, 0, 2, crosswalkMaterial);
		chunk.setBlocks(0, 2, yPlatform, 7, 9, crosswalkMaterial);
		chunk.setBlocks(7, 9, yPlatform, 14, 16, crosswalkMaterial);
		chunk.setBlocks(14, 16, yPlatform, 7, 9, crosswalkMaterial);
		
		// draw railing
		chunk.setBlocks(2, 7, yPlatform + 1, 2, 3, railingMaterial);
		chunk.setBlocks(9, 14, yPlatform + 1, 2, 3, railingMaterial);
		chunk.setBlocks(2, 7, yPlatform + 1, 13, 14, railingMaterial);
		chunk.setBlocks(9, 14, yPlatform + 1, 13, 14, railingMaterial);
		
		chunk.setBlocks(2, 3, yPlatform + 1, 3, 7, railingMaterial);
		chunk.setBlocks(13, 14, yPlatform + 1, 3, 7, railingMaterial);
		chunk.setBlocks(2, 3, yPlatform + 1, 9, 13, railingMaterial);
		chunk.setBlocks(13, 14, yPlatform + 1, 9, 13, railingMaterial);
		
		chunk.setBlocks(6, 7, yPlatform, yPlatform + 2, 0, 2, railingMaterial);
		chunk.setBlocks(9, 10, yPlatform, yPlatform + 2, 0, 2, railingMaterial);
		chunk.setBlocks(6, 7, yPlatform, yPlatform + 2, 14, 16, railingMaterial);
		chunk.setBlocks(9, 10, yPlatform, yPlatform + 2, 14, 16, railingMaterial);
		
		chunk.setBlocks(0, 2, yPlatform, yPlatform + 2, 6, 7, railingMaterial);
		chunk.setBlocks(0, 2, yPlatform, yPlatform + 2, 9, 10, railingMaterial);
		chunk.setBlocks(14, 16, yPlatform, yPlatform + 2, 6, 7, railingMaterial);
		chunk.setBlocks(14, 16, yPlatform, yPlatform + 2, 9, 10, railingMaterial);
		
		// build a bunker
		switch (buildingType) {
		case BALLSY:
			generateBallsyBunker(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case ENTRY:
			generateEntryBunker(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case FLOORED:
			generateFlooredBunker(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case GROWING:
			generateGrowingBunker(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case PYRAMID:
			generatePyramidBunker(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case QUAD:
			generateQuadBunker(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case RECALL:
			generateRecallBunker(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case TANK:
			generateTankBunker(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		}
		
		// add some surface
		generateSurface(generator, chunk, true);
	}

	private void generateEntryBunker(WorldGenerator generator, DataContext context, SupportChunk chunk, int y1, int y2) {
		reportLocation(generator, "Bunker", chunk.getOriginX(), chunk.getOriginZ());
		
		// where is the surface?
		int surfaceY = Math.min(getBlockY(6, 6), getBlockY(9, 6));
		surfaceY = Math.min(surfaceY, getBlockY(6, 9));
		surfaceY = Math.min(surfaceY, getBlockY(9, 9));
		
		// walls
		chunk.setBlocks(5, 11, y1, surfaceY + 4, 5, 11, buildingMaterial);
		
		// do it!
		MineEntranceLot.generateStairWell(generator, chunk, chunkOdds, 6, 6, y1, minHeight, surfaceY,
				Material.QUARTZ_STAIRS, Material.QUARTZ_BLOCK, Material.QUARTZ_BLOCK); // Make the last one air if you want an easy way down
		
		// roof!
		chunk.setBlocks(6, 10, surfaceY + 4, surfaceY + 5, 6, 10, buildingMaterial);
		chunk.setBlocks(7, 9, surfaceY + 5, surfaceY + 6, 7, 9, buildingMaterial);
		
		// camo
		chunk.camoClay(5, 11, surfaceY - 2, surfaceY + 6, 5, 11, chunkOdds);
		
		// bottom doors
		chunk.setBlocks(7, 9, y1, y1 + 2, 5, 6, Material.AIR);
		chunk.setBlocks(7, 9, y1, y1 + 2, 10, 11, Material.AIR);
		chunk.setBlocks(5, 6, y1, y1 + 2, 7, 9, Material.AIR);
		chunk.setBlocks(10, 11, y1, y1 + 2, 7, 9, Material.AIR);
		
		// top doors
		chunk.setBlocks(7, 9, surfaceY + 1, surfaceY + 3, 5, 6, railingMaterial);
		chunk.setBlocks(7, 9, surfaceY + 1, surfaceY + 3, 10, 11, railingMaterial);
		chunk.setBlocks(5, 6, surfaceY + 1, surfaceY + 3, 7, 9, railingMaterial);
		chunk.setBlocks(10, 11, surfaceY + 1, surfaceY + 3, 7, 9, railingMaterial);
		
		// put in some windows
		for (int y = y1 + 3; y < y2; y = y + 3) {
			if (chunkOdds.flipCoin())
				chunk.setBlocks(7, 8 + chunkOdds.getRandomInt(2), y, y + 2, 5, 6, windowMaterial);
			if (chunkOdds.flipCoin())
				chunk.setBlocks(8 - chunkOdds.getRandomInt(2), 9, y, y + 2, 10, 11, windowMaterial);
			if (chunkOdds.flipCoin())
				chunk.setBlocks(5, 6, y, y + 2, 7, 8 + chunkOdds.getRandomInt(2), windowMaterial);
			if (chunkOdds.flipCoin())
				chunk.setBlocks(10, 11, y, y + 2, 8 - chunkOdds.getRandomInt(2), 9, windowMaterial);
		}
		
		// place snow
		blockYs.lift(7);
		generateSurface(generator, chunk, false);
	}
	
	private void generateGrowingBunker(WorldGenerator generator, DataContext context, SupportChunk chunk, int y1, int y2) {
		int x1 = 4;
		int x2 = x1 + 8;
		int y = y1;
		int z1 = 4;
		int z2 = z1 + 8;
		int Height = FloorHeight;

		Material emptyMaterial = getAirMaterial(generator, y1);
		DyeColor coreColor = chunkOdds.getRandomColor();
		DyeColor detailColor = chunkOdds.getRandomColor();
		boolean firstFloor = true;
		
		while (y + Height < y2) {
			
			// walls please
			chunk.setClayWalls(x1, x2, y, y + Height - 1, z1, z2, coreColor);
			
			// doors
			if (firstFloor) {
				chunk.setBlocks(x1 + 3, x2 - 3, y, y + 2, z1    , z1 + 1, emptyMaterial);
				chunk.setBlocks(x1 + 3, x2 - 3, y, y + 2, z2 - 1, z2    , emptyMaterial);
				chunk.setBlocks(x1    , x1 + 1, y, y + 2, z1 + 3, z2 - 3, emptyMaterial);
				chunk.setBlocks(x2 - 1, x2    , y, y + 2, z1 + 3, z2 - 3, emptyMaterial);
				firstFloor = false;
			}
			
			// interspace
			chunk.setClay(x1 + 1, x2 - 1, y + Height - 1, y + Height, z1 + 1, z2 - 1, detailColor);
			
			// make things bigger
			y += Height;
			Height += FloorHeight;
		}
	}

	private void generateFlooredBunker(WorldGenerator generator, DataContext context, SupportChunk chunk, int y1, int y2) {
		int x1 = 4;
		int x2 = x1 + 8;
		int z1 = 4;
		int z2 = z1 + 8;
		int y3 = y2 - 2;
		
		Material emptyMaterial = getAirMaterial(generator, y1);
		DyeColor coreColor = chunkOdds.getRandomColor();
		DyeColor detailColor = chunkOdds.getRandomColor();
		boolean firstFloor = true;
		
		for (int y = y1; y < y3; y += FloorHeight) {
			
			// walls please
			chunk.setClayWalls(x1, x2, y, y + FloorHeight - 1, z1, z2, coreColor);
			
			// windows in the wall
			chunk.setBlocks(x1 + 2, x2 - 2, y + 1, y + 2, z1, z1 + 1, windowMaterial);
			chunk.setBlocks(x1 + 2, x2 - 2, y + 1, y + 2, z2 - 1, z2, windowMaterial);
			chunk.setBlocks(x1, x1 + 1, y + 1, y + 2, z1 + 2, z2 - 2, windowMaterial);
			chunk.setBlocks(x2 - 1, x2, y + 1, y + 2, z1 + 2, z2 - 2, windowMaterial);
			
			// doors
			if (firstFloor) {
				chunk.setBlocks(x1 + 3, x2 - 3, y, y + 2, z1    , z1 + 1, emptyMaterial);
				chunk.setBlocks(x1 + 3, x2 - 3, y, y + 2, z2 - 1, z2    , emptyMaterial);
				chunk.setBlocks(x1    , x1 + 1, y, y + 2, z1 + 3, z2 - 3, emptyMaterial);
				chunk.setBlocks(x2 - 1, x2    , y, y + 2, z1 + 3, z2 - 3, emptyMaterial);
				firstFloor = false;
			}
			
			// interspace
			chunk.setClay(x1 + 1, x2 - 1, y + FloorHeight - 1, y + FloorHeight, z1 + 1, z2 - 1, detailColor);
		}
	}

	private void generateRecallBunker(WorldGenerator generator, DataContext context, SupportChunk chunk, int y1, int y2) {
		int buildingWidth = 10;
		int x1 = (chunk.width - buildingWidth) / 2;
		int x2 = x1 + buildingWidth;
		int z1 = x1;
		int z2 = z1 + buildingWidth;
		
		Material emptyMaterial = getAirMaterial(generator, y1);
		DyeColor coreColor = chunkOdds.getRandomColor();
		DyeColor detailColor = chunkOdds.getRandomColor();
		
		// lower bit
		chunk.setClayWalls(x1 + 1, x2 - 1, y1, y1 + 1, z1 + 1, z2 - 1, coreColor);
		chunk.setClayWalls(x1 + 1, x2 - 1, y1 + 1, y1 + 2, z1 + 1, z2 - 1, coreColor);
		
		// make it so we can walk into the 
		chunk.setBlocks(x1 + 4, x2 - 4, y1, y1 + 2, z1 + 1, z1 + 2, emptyMaterial);
		chunk.setBlocks(x1 + 4, x2 - 4, y1, y1 + 2, z2 - 2, z2 - 1, emptyMaterial);
		chunk.setBlocks(x1 + 1, x1 + 2, y1, y1 + 2, z1 + 4, z2 - 4, emptyMaterial);
		chunk.setBlocks(x2 - 2, x2 - 1, y1, y1 + 2, z1 + 4, z2 - 4, emptyMaterial);
		
		int y = y1 + 2;
		int Height = FloorHeight;
		while (y + Height < y2) {
			int yTop = y + Height - 1;
			
			// texture
			for (int i = 1; i < buildingWidth; i += 2) {
				chunk.setClay(x1 + i, y, yTop, z1, detailColor);
				chunk.setClay(x1 + i - 1, y, yTop, z2 - 1, detailColor);
				chunk.setClay(x1, y, yTop, z1 + i, detailColor);
				chunk.setClay(x2 - 1, y, yTop, z1 + i - 1, detailColor);
			}
			
			// inner wall
			chunk.setClayWalls(x1 + 1, x2 - 1, y, yTop, z1 + 1, z2 - 1, coreColor);
			
			// cap it off
			chunk.setClay(x1 + 1, x2 - 1, yTop, yTop + 1, z1 + 1, z2 - 1, detailColor);
			
			// make things bigger
			y += Height;
			Height += FloorHeight;
		}
		
		generateTreat(generator, chunk, 5, y1, 5);
		generateTreat(generator, chunk, 10, y1, 10);
		
		generateTrick(generator, chunk, 10, y1, 5);
		generateTrick(generator, chunk, 5, y1, 10);
	}

	private void generateBallsyBunker(WorldGenerator generator, DataContext context, SupportChunk chunk, int y1, int y2) {
		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		int y3 = y2 - 5;

		DyeColor coreColor = chunkOdds.getRandomColor();
		
		// initial pylon
		chunk.setClay(x1 + 4, x2 - 4, y1, y1 + 2, z1 + 4, z2 - 4, coreColor);
		
		// rest of the pylon and balls
		for (int y = y1 + 2; y < y3; y += 6) {
			
			// center pylon
			chunk.setClay(x1 + 4, x2 - 4, y, y + 6, z1 + 4, z2 - 4, coreColor);
			
			// balls baby!
			generateBallsyBuildingBall(chunk, x1, y, z1);
			generateBallsyBuildingBall(chunk, x1, y, z2 - 5);
			generateBallsyBuildingBall(chunk, x2 - 5, y, z1);
			generateBallsyBuildingBall(chunk, x2 - 5, y, z2 - 5);
		}
	}
	
	private void generateBallsyBuildingBall(SupportChunk chunk, int x, int y, int z) {
		if (chunkOdds.flipCoin()) {
			
			DyeColor ballColor = chunkOdds.getRandomColor();
			
			// bottom
			chunk.setClay(x + 1, x + 4, y, y + 1, z + 1, z + 4, ballColor);
			
			// sides
			chunk.setGlassWalls(x, x + 5, y + 1, y + 4, z, z + 5, ballColor);
			
			// top
			chunk.setClay(x + 1, x + 4, y + 4, y + 5, z + 1, z + 4, ballColor);
		}
	}

	private void generateQuadBunker(WorldGenerator generator, DataContext context, SupportChunk chunk, int y1, int y2) {
		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		int ySegment = Math.max(1, (y2 - y1) / 5);
		int yRange = ySegment * 3;
		int yBase = y1 + ySegment;
		
		DyeColor coreColor = chunkOdds.getRandomColor();
		DyeColor detailColor = chunkOdds.getRandomColor();
		
		int colY1 = yBase + chunkOdds.getRandomInt(yRange);
		int colY2 = yBase + chunkOdds.getRandomInt(yRange);
		int colY3 = yBase + chunkOdds.getRandomInt(yRange);
		int colY4 = yBase + chunkOdds.getRandomInt(yRange);
		
		// four towers
		generateQuadTowers(chunk, x1, x1 + 5, y1, colY1, z1, z1 + 5, coreColor, detailColor);
		generateQuadTowers(chunk, x1, x1 + 5, y1, colY2, z2 - 5, z2, coreColor, detailColor);
		generateQuadTowers(chunk, x2 - 5, x2, y1, colY3, z1, z1 + 5, coreColor, detailColor);
		generateQuadTowers(chunk, x2 - 5, x2, y1, colY4, z2 - 5, z2, coreColor, detailColor);
		
		// now randomly place connectors
		generateQuadConnectors(chunk, x1 + 1, x1 + 4, y1 + 5, Math.min(colY1, colY2) - 3, z1 + 5, z1 + 7);
		generateQuadConnectors(chunk, x1 + 5, x1 + 7, y1 + 5, Math.min(colY1, colY3) - 3, z1 + 1, z1 + 4);
		generateQuadConnectors(chunk, x1 + 8, x1 + 11, y1 + 5, Math.min(colY3, colY4) - 3, z1 + 5, z1 + 7);
		generateQuadConnectors(chunk, x1 + 5, x1 + 7, y1 + 5, Math.min(colY2, colY4) - 3, z1 + 8, z1 + 11);
		
		//TODO make them hollow
		//TODO vertical windows
		//TODO horizontal connections from time to time, place treasures here
		//TODO spiral staircase up the middle
	}
	
	private void generateQuadTowers(SupportChunk chunk, int x1, int x2, int y1, int y2, int z1, int z2, DyeColor coreColor, DyeColor detailColor) {
		chunk.setClay(x1 + 1, x2 - 1, y1, y1 + 1, z1 + 1, z2 - 1, detailColor);
		chunk.setClayWalls(x1, x2, y1 + 1, y2 - 1, z1, z2, coreColor);
		chunk.setClay(x1 + 1, x2 - 1, y2 - 1, y2, z1 + 1, z2 - 1, detailColor);
	}
	
	private void generateQuadConnectors(SupportChunk chunk, int x1, int x2, int y1, int y2, int z1, int z2) {
		for (int y = y1; y < y2; y = y + 3)
			if (chunkOdds.flipCoin())
				chunk.setGlass(x1, x2, y, y + 2, z1, z2, chunkOdds.getRandomColor());
	}
	
	private void generateTankBunker(WorldGenerator generator, DataContext context, SupportChunk chunk, int y1, int y2) {
		int x1 = 4;
		int x2 = x1 + 8;
		int z1 = 4;
		int z2 = z1 + 8;
		int yBottom = y1 + 4;
		int yTop = y2;
		
		DyeColor coreColor = chunkOdds.getRandomColor();
		DyeColor detailColor = chunkOdds.getRandomColor();
		
		// supports
		chunk.setClay(x1 + 1, x1 + 3, y1, yBottom, z1 + 1, z1 + 3, detailColor);
		chunk.setClay(x1 + 1, x1 + 3, y1, yBottom, z2 - 3, z2 - 1, detailColor);
		chunk.setClay(x2 - 3, x2 - 1, y1, yBottom, z1 + 1, z1 + 3, detailColor);
		chunk.setClay(x2 - 3, x2 - 1, y1, yBottom, z2 - 3, z2 - 1, detailColor);
		
		// bottom bit
		chunk.setClay(x1, x2, yBottom, yBottom + 1, z1, z2, coreColor);
		
		// walls
		chunk.setClay(x1, x2, yBottom + 1, yTop, z1 - 1, z1, coreColor);
		chunk.setClay(x1, x2, yBottom + 1, yTop, z2    , z2 + 1, coreColor);
		chunk.setClay(x1 - 1, x1, yBottom + 1, yTop, z1, z2, coreColor);
		chunk.setClay(x2    , x2 + 1, yBottom + 1, yTop, z1, z2, coreColor);
		
		// make it so we can see in a bit
		chunk.setBlocks(x1 + 3, x2 - 3, yBottom + 1, yTop, z1 - 1, z1, windowMaterial);
		chunk.setBlocks(x1 + 3, x2 - 3, yBottom + 1, yTop, z2    , z2 + 1, windowMaterial);
		chunk.setBlocks(x1 - 1, x1, yBottom + 1, yTop, z1 + 3, z2 - 3, windowMaterial);
		chunk.setBlocks(x2    , x2 + 1, yBottom + 1, yTop, z1 + 3, z2 - 3, windowMaterial);
		
		// put a top on it
		chunk.setClay(x1, x2, yTop, yTop + 1, z1, z2, coreColor);
		
		chunk.setBlocks(x1, x2, yBottom + 1, yBottom + ((yTop - yBottom) / 3) * 2, z1, z2, getFillMaterial());
	}
	
	private Material getFillMaterial() {
		switch (chunkOdds.getRandomInt(10)) {
		case 1:
			return Material.STATIONARY_LAVA;
		case 2:
			return Material.PACKED_ICE;
		case 3:
			return Material.SNOW_BLOCK;
		case 4:
			return Material.SPONGE;
		case 5:
			return Material.REDSTONE_BLOCK;
		case 6:
			return Material.COAL_BLOCK;
		case 7:
			return Material.HARD_CLAY;
		case 8:
			return Material.ENDER_STONE;
		case 9:
			return Material.EMERALD_BLOCK;
		default:
			return Material.STATIONARY_WATER;
		}
	}

	private void generatePyramidBunker(WorldGenerator generator, DataContext context, SupportChunk chunk, int y1, int y2) {
		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		
		DyeColor coreColor = chunkOdds.getRandomColor();
		DyeColor detailColor = chunkOdds.getRandomColor();
		
		Material emptyMaterial = getAirMaterial(generator, y1);
		for (int i = 0; i < 7; i++) {
			int y = y1 + i * 2;
			chunk.setClayWalls(x1 + i, x2 - i, y, y + 2, z1 + i, z2 - i, coreColor);
		}

		// make it so we can walk through the pyramid
		chunk.setBlocks(x1 + 5, x2 - 5, y1, y1 + 2, z1    , z1 + 1, emptyMaterial);
		chunk.setBlocks(x1 + 5, x2 - 5, y1, y1 + 2, z2 - 1, z2    , emptyMaterial);
		chunk.setBlocks(x1    , x1 + 1, y1, y1 + 2, z1 + 5, z2 - 5, emptyMaterial);
		chunk.setBlocks(x2 - 1, x2    , y1, y1 + 2, z1 + 5, z2 - 5, emptyMaterial);
		
		// top off the entry ways
		chunk.setClay(x1 + 4, x2 - 4, y1 + 2, y1 + 3, z1    , z1 + 1, detailColor);
		chunk.setClay(x1 + 4, x2 - 4, y1 + 2, y1 + 3, z2 - 1, z2    , detailColor);
		chunk.setClay(x1    , x1 + 1, y1 + 2, y1 + 3, z1 + 4, z2 - 4, detailColor);
		chunk.setClay(x2 - 1, x2    , y1 + 2, y1 + 3, z1 + 4, z2 - 4, detailColor);

		generateTreat(generator, chunk, 3, y1, 3);
		generateTreat(generator, chunk, 12, y1, 12);
		
		generateTrick(generator, chunk, 12, y1, 3);
		generateTrick(generator, chunk, 3, y1, 12);
	}

	private final static Material springMat = Material.QUARTZ_STAIRS;
	private final static Material springBaseMat = Material.QUARTZ_BLOCK;
	private final static Material springCoreMat = Material.GLOWSTONE;
	
	private void generateSupport(SupportChunk chunk, DataContext context, int x, int y, int z) {
		BlackMagic.setBlocks(chunk, x, x + 3, y, z, z + 3, springBaseMat, 2);
		
		generateSpringBit(chunk, x    , y + 1, z    , Stair.EAST, Stair.SOUTHFLIP, Stair.EAST);
		generateSpringBit(chunk, x + 1, y + 1, z    , Stair.WESTFLIP, Stair.EAST, Stair.WESTFLIP);
		generateSpringBit(chunk, x + 2, y + 1, z    , Stair.SOUTH, Stair.WESTFLIP, Stair.SOUTH);
		generateSpringBit(chunk, x + 2, y + 1, z + 1, Stair.NORTHFLIP, Stair.SOUTH, Stair.NORTHFLIP);
		generateSpringBit(chunk, x + 2, y + 1, z + 2, Stair.WEST, Stair.NORTHFLIP, Stair.WEST);
		generateSpringBit(chunk, x + 1, y + 1, z + 2, Stair.EASTFLIP, Stair.WEST, Stair.EASTFLIP);
		generateSpringBit(chunk, x    , y + 1, z + 2, Stair.NORTH, Stair.EASTFLIP, Stair.NORTH);
		generateSpringBit(chunk, x    , y + 1, z + 1, Stair.SOUTHFLIP, Stair.NORTH, Stair.SOUTHFLIP);

		chunk.setBlocks(x + 1, y + 1, y + 5, z + 1, springCoreMat);
		
		BlackMagic.setBlocks(chunk, x, x + 3, y + 4, z, z + 3, springBaseMat, 2);
	}
	
	private void generateSpringBit(SupportChunk chunk, int x, int y, int z, Stair data1, Stair data2, Stair data3) {
		chunk.setStair(x, y    , z, springMat, data1);
		chunk.setStair(x, y + 1, z, springMat, data2);
		chunk.setStair(x, y + 2, z, springMat, data3);
	}

	private void generateTreat(WorldGenerator generator, SupportChunk chunk, int x, int y, int z) {
		
		// cool stuff?
		if (generator.settings.treasuresInBunkers && chunkOdds.playOdds(generator.settings.oddsOfTreasureInBunkers)) {
			 chunk.setChest(x, y, z, Direction.General.NORTH, chunkOdds, generator.lootProvider, LootLocation.BUNKER);
		}
	}

	private void generateTrick(WorldGenerator generator, SupportChunk chunk, int x, int y, int z) {

		// not so cool stuff?
		if (generator.settings.spawnersInBunkers && chunkOdds.playOdds(generator.settings.oddsOfSpawnerInBunkers)) {
			chunk.setSpawner(x, y, z, generator.spawnProvider.getEntity(generator, chunkOdds, SpawnerLocation.BUNKER));
		}
	}
	
	public BilgeType getRandomBilgeType() {
		switch (platmapOdds.getRandomInt(5)) {
		case 1:
			return BilgeType.WATER; // 1 out of 5
		case 2:
			return BilgeType.LAVA;  // 1 out of 5
		case 3:
			return BilgeType.ICE;   // 1 out of 5
		default:
			return BilgeType.EMPTY; // 2 out of 5
		}
	}
	
	public BunkerType getRandomBunkerType() {
		if (firstOne)
			return BunkerType.ENTRY;
		else 
			switch (chunkOdds.getRandomInt(7)) {
			case 1:
				return BunkerType.BALLSY;  // 1 out of 7
			case 2:
				return BunkerType.FLOORED; // 1 out of 7
			case 3:
				return BunkerType.GROWING; // 1 out of 7
			case 4:
				return BunkerType.PYRAMID; // 1 out of 7
			case 5:
				return BunkerType.QUAD;    // 1 out of 7
			case 6:
				return BunkerType.RECALL;  // 1 out of 7
			default:
				return BunkerType.TANK;    // 1 out of 7
			}
	}
	
}
