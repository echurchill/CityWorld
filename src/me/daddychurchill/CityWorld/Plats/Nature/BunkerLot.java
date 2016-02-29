package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConnectedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Plugins.SpawnProvider.SpawnerLocation;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.BadMagic;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class BunkerLot extends ConnectedLot {

	private final static int FloorHeight = DataContext.FloorHeight;
	
	// these MUST be given in chunk segment units (currently 16) 
	private final static int bunkerSegment = 16;
	private final static int bunkerBuffer = bunkerSegment;
	private final static int bunkerBelowStreet = bunkerSegment;
	private final static int bunkerMinHeight = bunkerSegment * 2;
	private final static int bunkerMaxHeight = bunkerSegment * 6;

	protected boolean firstOne = false;
	protected int bottomOfBunker;
	protected int topOfBunker;
	
	public enum BunkerType {ENTRY, PYRAMID, TANK, QUAD, RECALL, BALLSY, FLOORED, GROWING, SAUCER, ROAD}; // MISSILE, FARM, VENT

	protected int bilgeType;
	protected BunkerType buildingType;
	
	public BunkerLot(PlatMap platmap, int chunkX, int chunkZ, boolean firstOne) {
		super(platmap, chunkX, chunkZ);
		
		this.firstOne = firstOne;
		
		// been here?
		bottomOfBunker = calcSegmentOrigin(platmap.generator.streetLevel) - bunkerBelowStreet;
		topOfBunker = calcBunkerCeiling(platmap.generator);
		
//		platmap.generator.reportMessage("minHeight = " + minHeight + 
//			" calcSegmentOrigin(minHeight) = " + calcSegmentOrigin(minHeight) +
//			" calcSegmentOrigin(minHeight) - bunkerBuffer = " + (calcSegmentOrigin(minHeight) - bunkerBuffer) +
//			" -> topOfBunker = " + topOfBunker);
		
		// initial rolls, using two different odds engines
		buildingType = getRandomBunkerType(chunkOdds, firstOne);
	}
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new BunkerLot(platmap, chunkX, chunkZ, false);
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
	public boolean isValidStrataY(CityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return bunkerIsValidStrataY(generator, blockX, blockY, blockZ, bottomOfBunker, topOfBunker);
	}
	
	@Override
	protected boolean isShaftableLevel(CityWorldGenerator generator, int blockY) {
		return isBunkerShaftable() &&
			   bunkerIsShaftableLevel(generator, blockY, bottomOfBunker, topOfBunker)&&
			   super.isShaftableLevel(generator, blockY);
	}
	
	private boolean isBunkerShaftable() {
		return buildingType != BunkerType.ENTRY && buildingType != BunkerType.SAUCER;
	}
	
	public static boolean bunkerIsValidStrataY(CityWorldGenerator generator, int blockX, int blockY, int blockZ, 
			int bottomOfBunker, int topOfBunker) {
		return blockY < bottomOfBunker || blockY >= topOfBunker;
	}
	
	public static boolean bunkerIsShaftableLevel(CityWorldGenerator generator, int blockY,
		int bottomOfBunker, int topOfBunker) {
		
		return (blockY < bottomOfBunker - bunkerBuffer || blockY > topOfBunker - bunkerSegment - bunkerBuffer);
	}
	
	private static int calcSegmentOrigin(int y) {
		return (y / bunkerSegment) * bunkerSegment;
	}
	
	public static int calcBunkerMinHeight(CityWorldGenerator generator) {
		return calcSegmentOrigin(generator.streetLevel) + bunkerMinHeight - bunkerBelowStreet + bunkerBuffer;
	}
	
	public static int calcBunkerMaxHeight(CityWorldGenerator generator) {
		return calcSegmentOrigin(generator.streetLevel) + bunkerMaxHeight - bunkerBelowStreet + bunkerBuffer;
	}
	
	private int calcBunkerCeiling(CityWorldGenerator generator) {
		return Math.min(calcBunkerMaxHeight(generator), calcSegmentOrigin(blockYs.minHeight) - bunkerBuffer);
	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return bottomOfBunker;
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return topOfBunker;
	}

	@Override
	public RoadLot repaveLot(CityWorldGenerator generator, PlatMap platmap) {
		return new RoadThroughBunkerLot(platmap, chunkX, chunkZ, generator.connectedKeyForPavedRoads, false, this);
	}
	
	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {
		if (buildingType == BunkerType.ENTRY)
			reportLocation(generator, "Bunker", chunk);

		// where is the surface?
//		int surfaceY = getSurfaceAtY(6, 6);
//		generator.reportMessage("SurfaceY = " + surfaceY + " TopOfBunker = " + topOfBunker + " MinHeight = " + minHeight);
//		generator.reportMessage("TopOfBunker = " + topOfBunker + " MinHeight = " + minHeight);
		
		// do it!
		blockYs.lift(generateBunker(generator, platmap, chunk, chunkOdds, context, platX, platZ, blockYs,
									bottomOfBunker, topOfBunker, buildingType));
		
		// add some surface
		generateSurface(generator, chunk, true);
	}
	
	public static boolean materialsDefined = false;
	public static Material supportMaterial = Material.QUARTZ_BLOCK;
	public static Material platformMaterial = Material.QUARTZ_BLOCK;
	public static Material crosswalkMaterial = Material.QUARTZ_BLOCK;
	public static Material railingMaterial = Material.IRON_FENCE;
	public static Material buildingMaterial = Material.STAINED_CLAY;
	public static Material windowMaterial = Material.GLASS;
	public static Material bilgeMaterial = Material.AIR;
	
	protected static int generateBunker(CityWorldGenerator generator, PlatMap platmap, SupportBlocks chunk, Odds odds, 
			DataContext context, int platX, int platZ, CachedYs blockYs,
			int bottomOfBunker, int topOfBunker, BunkerType buildingType) {
		
		// make sure we know what we are making
		if (!materialsDefined) {
			
			platformMaterial = generator.settings.materials.itemsSelectMaterial_BunkerPlatforms.getRandomMaterial(odds, platformMaterial);
			crosswalkMaterial = generator.settings.materials.itemsSelectMaterial_BunkerPlatforms.getRandomMaterial(odds, crosswalkMaterial);
			supportMaterial = generator.settings.materials.itemsSelectMaterial_BunkerPlatforms.getRandomMaterial(odds, supportMaterial);
			buildingMaterial = generator.settings.materials.itemsSelectMaterial_BunkerBuildings.getRandomMaterial(odds, buildingMaterial);
			bilgeMaterial = generator.settings.materials.itemsSelectMaterial_BunkerBilge.getRandomMaterial(odds, bilgeMaterial);
			
			materialsDefined = true;
		}
		
		// precalculate
		int yBottom = bottomOfBunker;//calcSegmentOrigin(generator.sidewalkLevel) - bunkerBelowStreet;
		int yTop4 = topOfBunker;//calcBunkerCeiling(generator);
		int yTop3 = yTop4 - 2;
		int yTop2 = yTop4 - bunkerSegment; 
		int yTop1 = yTop2 - 2;
		int yPlatform = calcSegmentOrigin(yBottom) + 6;
		int yRange = (yTop2 - yPlatform) / 3;
		int yPlatformTop = Math.min(Math.max(yPlatform + bunkerSegment * 2, odds.getRandomInt(yRange) + yRange * 2), yTop1);
		
		// bottom
		chunk.setLayer(yBottom, supportMaterial);
		
		// clear out stuff?
		if (bilgeMaterial != Material.AIR)
			chunk.setLayer(yBottom + 1, bilgeMaterial);
		
		// hold up buildings
		generateSupport(chunk, odds, context, 3, yBottom + 1, 3);
		generateSupport(chunk, odds, context, 3, yBottom + 1, 10);
		generateSupport(chunk, odds, context, 10, yBottom + 1, 3);
		generateSupport(chunk, odds, context, 10, yBottom + 1, 10);
		
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
			return generateBallsyBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case ENTRY:
			return generateEntryBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop, yTop2, blockYs);
		case FLOORED:
			return generateFlooredBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case GROWING:
			return generateGrowingBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case PYRAMID:
			return generatePyramidBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case QUAD:
			return generateQuadBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case RECALL:
			return generateRecallBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case TANK:
			return generateTankBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		case SAUCER:
			return generateSaucerBunker(generator, context, chunk, odds, yPlatform + 1, yPlatformTop, topOfBunker, blockYs);
		case ROAD:
			return generateRoadTunnel(generator, context, chunk, odds, yPlatform + 1, yPlatformTop);
		}
		return 0;
	}
	
	public static int generateEntryBunker(CityWorldGenerator generator, DataContext context, SupportBlocks chunk, Odds odds, 
			int y1, int y2, int topOfBunker, CachedYs blockYs) {
		int surfaceY = blockYs.averageHeight;
		
		// walls
		chunk.setBlocks(5, 11, y1, surfaceY + 4, 5, 11, buildingMaterial);
		
		// do it!
		MineEntranceLot.generateStairWell(generator, chunk, odds, 6, 6, y1, 
				surfaceY, surfaceY, surfaceY + DataContext.FloorHeight + 1,
				Material.QUARTZ_STAIRS, Material.QUARTZ_BLOCK, Material.QUARTZ_BLOCK); // Make the last one air if you want an easy way down
		
		// roof!
		chunk.setBlocks(6, 10, surfaceY + 4, surfaceY + 5, 6, 10, buildingMaterial);
		chunk.setBlocks(7, 9, surfaceY + 5, surfaceY + 6, 7, 9, buildingMaterial);
		
		// camo
		chunk.camoClay(5, 11, surfaceY - 2, surfaceY + 6, 5, 11, odds);
		
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
		for (int y = y1 + 3; y < topOfBunker - 3; y = y + 3) {
			if (odds.flipCoin())
				chunk.setBlocks(7, 8 + odds.getRandomInt(2), y, y + 2, 5, 6, windowMaterial);
			if (odds.flipCoin())
				chunk.setBlocks(8 - odds.getRandomInt(2), 9, y, y + 2, 10, 11, windowMaterial);
			if (odds.flipCoin())
				chunk.setBlocks(5, 6, y, y + 2, 7, 8 + odds.getRandomInt(2), windowMaterial);
			if (odds.flipCoin())
				chunk.setBlocks(10, 11, y, y + 2, 8 - odds.getRandomInt(2), 9, windowMaterial);
		}
		
		// lift the surface?
		return 7;
	}
	
	public static int generateSaucerBunker(CityWorldGenerator generator, DataContext context, SupportBlocks chunk, Odds odds, 
			int y1, int y2, int topOfBunker, CachedYs blockYs) {
		
		// walls
		chunk.setCircle(8, 8, 5, topOfBunker, blockYs.maxHeight + 1, Material.AIR, true);
		chunk.setCircle(8, 8, 6, topOfBunker, blockYs.minHeight + 1, buildingMaterial, false);
		chunk.setCircle(8, 8, 6, blockYs.minHeight, blockYs.averageHeight + 1, Material.STAINED_CLAY, false);
		
//		chunk.clearBlocks(0, 16, y1 + 1, surfaceY + 6, 0, 16);
//		chunk.setWalls(1, 15, y2, surfaceY, 1, 15, buildingMaterial);
//		
//		chunk.setBlocks(2, 14, surfaceY + 20, surfaceY + 22, 2, 14, Material.GLOWSTONE);
		
		// place it then
		generator.structureInAirProvider.generateSaucer(generator, chunk, 8, y1, 8, true);
		
//		chunk.setCircle(7, 7, 3, y1 + 5, surfaceY + 6, buildingMaterial, false);
//		chunk.setCircle(7, 7, 2, y1 + 5, surfaceY + 6, Material.AIR, true);
//		chunk.setCircle(7, 7, 2, surfaceY + 3, surfaceY + 5, Material.IRON_BLOCK, true);
//		
//		// camo the exit
//		chunk.camoClay(3, 12, surfaceY - 2, surfaceY + 6, 3, 12, odds);
//		
//		// do it!
//		MineEntranceLot.generateStairWell(generator, chunk, odds, 6, 6, y1, minHeight, surfaceY,
//				Material.QUARTZ_STAIRS, Material.QUARTZ_BLOCK, Material.QUARTZ_BLOCK); // Make the last one air if you want an easy way down
//		
//		// roof!
//		chunk.setBlocks(6, 10, surfaceY + 4, surfaceY + 5, 6, 10, buildingMaterial);
//		chunk.setBlocks(7, 9, surfaceY + 5, surfaceY + 6, 7, 9, buildingMaterial);
//		
//		// bottom doors
//		chunk.setBlocks(7, 9, y1, y1 + 2, 5, 6, Material.AIR);
//		chunk.setBlocks(7, 9, y1, y1 + 2, 10, 11, Material.AIR);
//		chunk.setBlocks(5, 6, y1, y1 + 2, 7, 9, Material.AIR);
//		chunk.setBlocks(10, 11, y1, y1 + 2, 7, 9, Material.AIR);
//		
//		// top doors
//		chunk.setBlocks(7, 9, surfaceY + 1, surfaceY + 3, 5, 6, railingMaterial);
//		chunk.setBlocks(7, 9, surfaceY + 1, surfaceY + 3, 10, 11, railingMaterial);
//		chunk.setBlocks(5, 6, surfaceY + 1, surfaceY + 3, 7, 9, railingMaterial);
//		chunk.setBlocks(10, 11, surfaceY + 1, surfaceY + 3, 7, 9, railingMaterial);
		
		// lift the surface?
		return 7;
	}
	
	public static int generateGrowingBunker(CityWorldGenerator generator, DataContext context, SupportBlocks chunk, Odds odds, int y1, int y2) {
		int x1 = 4;
		int x2 = x1 + 8;
		int y = y1;
		int z1 = 4;
		int z2 = z1 + 8;
		int Height = FloorHeight;

		Material emptyMaterial = Material.AIR;
		DyeColor coreColor = odds.getRandomColor();
		DyeColor detailColor = odds.getRandomColor();
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
		
		// lift the surface? NOPE
		return 0;
	}

	public static int generateFlooredBunker(CityWorldGenerator generator, DataContext context, SupportBlocks chunk, Odds odds, int y1, int y2) {
		int x1 = 4;
		int x2 = x1 + 8;
		int z1 = 4;
		int z2 = z1 + 8;
		int y3 = y2 - 2;
		
		Material emptyMaterial = Material.AIR;
		DyeColor coreColor = odds.getRandomColor();
		DyeColor detailColor = odds.getRandomColor();
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
		
		// lift the surface? NOPE
		return 0;
	}

	public static int generateRecallBunker(CityWorldGenerator generator, DataContext context, SupportBlocks chunk, Odds odds, int y1, int y2) {
		int buildingWidth = 10;
		int x1 = (chunk.width - buildingWidth) / 2;
		int x2 = x1 + buildingWidth;
		int z1 = x1;
		int z2 = z1 + buildingWidth;
		
		Material emptyMaterial = Material.AIR;
		DyeColor coreColor = odds.getRandomColor();
		DyeColor detailColor = odds.getRandomColor();
		
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
		
		generateTreat(generator, chunk, odds, 5, y1, 5);
		generateTreat(generator, chunk, odds, 10, y1, 10);
		
		generateTrick(generator, chunk, odds, 10, y1, 5);
		generateTrick(generator, chunk, odds, 5, y1, 10);
		
		// lift the surface? NOPE
		return 0;
	}

	public static int generateBallsyBunker(CityWorldGenerator generator, DataContext context, SupportBlocks chunk, Odds odds, int y1, int y2) {
		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		int y3 = y2 - 5;

		DyeColor coreColor = odds.getRandomColor();
		
		// initial pylon
		chunk.setClay(x1 + 4, x2 - 4, y1, y1 + 2, z1 + 4, z2 - 4, coreColor);
		
		// rest of the pylon and balls
		for (int y = y1 + 2; y < y3; y += 6) {
			
			// center pylon
			chunk.setClay(x1 + 4, x2 - 4, y, y + 6, z1 + 4, z2 - 4, coreColor);
			
			// balls baby!
			generateBallsyBuildingBall(chunk, odds, x1, y, z1);
			generateBallsyBuildingBall(chunk, odds, x1, y, z2 - 5);
			generateBallsyBuildingBall(chunk, odds, x2 - 5, y, z1);
			generateBallsyBuildingBall(chunk, odds, x2 - 5, y, z2 - 5);
		}
		
		// lift the surface? NOPE
		return 0;
	}
	
	public static void generateBallsyBuildingBall(SupportBlocks chunk, Odds odds, int x, int y, int z) {
		if (odds.flipCoin()) {
			
			DyeColor ballColor = odds.getRandomColor();
			
			// bottom
			chunk.setClay(x + 1, x + 4, y, y + 1, z + 1, z + 4, ballColor);
			
			// sides
			chunk.setGlassWalls(x, x + 5, y + 1, y + 4, z, z + 5, ballColor);
			
			// top
			chunk.setClay(x + 1, x + 4, y + 4, y + 5, z + 1, z + 4, ballColor);
		}
	}

	public static int generateQuadBunker(CityWorldGenerator generator, DataContext context, SupportBlocks chunk, Odds odds, int y1, int y2) {
		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		int ySegment = Math.max(1, (y2 - y1) / 5);
		int yRange = ySegment * 3;
		int yBase = y1 + ySegment;
		
		DyeColor coreColor = odds.getRandomColor();
		DyeColor detailColor = odds.getRandomColor();
		
		int colY1 = yBase + odds.getRandomInt(yRange);
		int colY2 = yBase + odds.getRandomInt(yRange);
		int colY3 = yBase + odds.getRandomInt(yRange);
		int colY4 = yBase + odds.getRandomInt(yRange);
		
		// four towers
		generateQuadTowers(chunk, odds, x1, x1 + 5, y1, colY1, z1, z1 + 5, coreColor, detailColor);
		generateQuadTowers(chunk, odds, x1, x1 + 5, y1, colY2, z2 - 5, z2, coreColor, detailColor);
		generateQuadTowers(chunk, odds, x2 - 5, x2, y1, colY3, z1, z1 + 5, coreColor, detailColor);
		generateQuadTowers(chunk, odds, x2 - 5, x2, y1, colY4, z2 - 5, z2, coreColor, detailColor);
		
		// now randomly place connectors
		generateQuadConnectors(chunk, odds, x1, x1 + 5, y1 + 3, Math.min(colY1, colY2) - 3, z1 + 5, z1 + 7, true);
		generateQuadConnectors(chunk, odds, x1 + 5, x1 + 7, y1 + 3, Math.min(colY1, colY3) - 3, z1, z1 + 5, false);
		generateQuadConnectors(chunk, odds, x1 + 7, x1 + 12, y1 + 3, Math.min(colY3, colY4) - 3, z1 + 5, z1 + 7, true);
		generateQuadConnectors(chunk, odds, x1 + 5, x1 + 7, y1 + 3, Math.min(colY2, colY4) - 3, z1 + 7, z1 + 12, false);
		
		//TODO make them hollow
		//TODO vertical windows
		//TODO horizontal connections from time to time, place treasures here
		//TODO spiral staircase up the middle
		
		// lift the surface? NOPE
		return 0;
	}
	
	public static void generateQuadTowers(SupportBlocks chunk, Odds odds, int x1, int x2, int y1, int y2, int z1, int z2, 
			DyeColor coreColor, DyeColor detailColor) {
		chunk.setClay(x1 + 2, x2 - 2, y1, y1 + 1, z1 + 2, z2 - 2, detailColor);
		chunk.setClay(x1 + 1, x2 - 1, y1 + 1, y1 + 2, z1 + 1, z2 - 1, detailColor);
		
		chunk.setClayWalls(x1, x2, y1 + 2, y2 - 2, z1, z2, coreColor);
		
		chunk.setClay(x1 + 1, x2 - 1, y2 - 2, y2 - 1, z1 + 1, z2 - 1, detailColor);
		chunk.setClay(x1 + 2, x2 - 2, y2 - 1, y2, z1 + 2, z2 - 2, detailColor);
	}
	
	private static void generateQuadConnectors(SupportBlocks chunk, Odds odds, int x1, int x2, int y1, int y2, int z1, int z2, boolean doX) {
		int px1 = x1;
		int px2 = x2;
		int py1 = y1;
		int pz1 = z1;
		int pz2 = z2;
		
		while (py1 < y2) {
			if (doX) {
				int lx = px1;
				do {
					px1 = x1 + odds.getRandomInt(x2 - x1);
					px2 = px1 + 1;
				} while (px1 == lx);
			} else {
				int lz = pz1;
				do {
					pz1 = z1 + odds.getRandomInt(z2 - z1);
					pz2 = pz1 + 1;
				} while (pz1 == lz);
			}
			
			chunk.setClay(px1, px2, py1, py1 + 1, pz1, pz2, odds.getRandomColor());
			py1 = py1 + 1;
		}
	}
	
	public static int generateTankBunker(CityWorldGenerator generator, DataContext context, SupportBlocks chunk, Odds odds, int y1, int y2) {
		int x1 = 4;
		int x2 = x1 + 8;
		int z1 = 4;
		int z2 = z1 + 8;
		int yBottom = y1 + 4;
		int yTop = y2;
		
		DyeColor coreColor = odds.getRandomColor();
		DyeColor detailColor = odds.getRandomColor();
		
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
		
		// fill it in
		chunk.setBlocks(x1, x2, yBottom + 1, yBottom + ((yTop - yBottom) / 3) * 2, z1, z2, getFillMaterial(odds));
		
		// lift the surface? NOPE
		return 0;
	}
	
	private static Material getFillMaterial(Odds odds) {
		switch (odds.getRandomInt(10)) {
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

	public static int generatePyramidBunker(CityWorldGenerator generator, DataContext context, SupportBlocks chunk, Odds odds, int y1, int y2) {
		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		
		DyeColor coreColor = odds.getRandomColor();
		DyeColor detailColor = odds.getRandomColor();
		
		Material emptyMaterial = Material.AIR;
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

		generateTreat(generator, chunk, odds, 3, y1, 3);
		generateTreat(generator, chunk, odds, 12, y1, 12);
		
		generateTrick(generator, chunk, odds, 12, y1, 3);
		generateTrick(generator, chunk, odds, 3, y1, 12);
		
		// lift the surface? NOPE
		return 0;
	}

	private static double oddsOfWayDownFromTunnel = Odds.oddsVeryLikely;
	
	public static int generateRoadTunnel(CityWorldGenerator generator, DataContext context, SupportBlocks chunk, Odds odds, int y1, int y2) {
		int underStreetY = generator.streetLevel - 3;
		int streetY = generator.streetLevel;
		
		// cross supports
		chunk.setBlocks(0, 16, underStreetY + 1, underStreetY + 2, 0, 2, BunkerLot.supportMaterial);
		chunk.setBlocks(0, 16, underStreetY + 1, underStreetY + 2, 14, 16, BunkerLot.supportMaterial);
		chunk.setBlocks(0, 2, underStreetY + 1, underStreetY + 2, 2, 14, BunkerLot.supportMaterial);
		chunk.setBlocks(14, 16, underStreetY + 1, underStreetY + 2, 2, 14, BunkerLot.supportMaterial);
		chunk.setBlocks(0, 16, underStreetY, underStreetY + 1, 0, 1, BunkerLot.supportMaterial);
		chunk.setBlocks(0, 16, underStreetY, underStreetY + 1, 15, 16, BunkerLot.supportMaterial);
		chunk.setBlocks(0, 1, underStreetY, underStreetY + 1, 2, 14, BunkerLot.supportMaterial);
		chunk.setBlocks(15, 16, underStreetY, underStreetY + 1, 2, 14, BunkerLot.supportMaterial);
		
		// center support
		BlackMagic.setBlocks(chunk, 7, 9, y1, underStreetY + 2, 7, 9, supportMaterial, 2);
		
		//BUKKIT: simply changing the type of these blocks won't reset the metadata associated with the existing blocks, resulting in cracked bricks
		// fix up the tunnel walls
		BlackMagic.setBlocks(chunk, 0, 2, streetY - 1, streetY + 6, 0, 1, RoadThroughBunkerLot.wallMaterial, 0);
		BlackMagic.setBlocks(chunk, 0, streetY - 1, streetY + 6, 1, RoadThroughBunkerLot.wallMaterial, 0);
		BlackMagic.setBlocks(chunk, 0, 2, streetY - 1, streetY + 6, 15, 16, RoadThroughBunkerLot.wallMaterial, 0);
		BlackMagic.setBlocks(chunk, 0, streetY - 1, streetY + 6, 14, RoadThroughBunkerLot.wallMaterial, 0);
		BlackMagic.setBlocks(chunk, 14, 16, streetY - 1, streetY + 6, 0, 1, RoadThroughBunkerLot.wallMaterial, 0);
		BlackMagic.setBlocks(chunk, 15, streetY - 1, streetY + 6, 1, RoadThroughBunkerLot.wallMaterial, 0);
		BlackMagic.setBlocks(chunk, 14, 16, streetY - 1, streetY + 6, 15, 16, RoadThroughBunkerLot.wallMaterial, 0);
		BlackMagic.setBlocks(chunk, 15, streetY - 1, streetY + 6, 14, RoadThroughBunkerLot.wallMaterial, 0);
		
		// put in a way down?
		if (odds.playOdds(oddsOfWayDownFromTunnel)) {
			chunk.setTrapDoor(6, streetY, 7, BadMagic.TrapDoor.TOP_WEST);
			chunk.setLadder(6, y1, streetY, 7, BlockFace.EAST);
		}
		
		// lift the surface? NOPE
		return 0;
	}
	
	private final static Material springMat = Material.QUARTZ_STAIRS;
	private final static Material springBaseMat = Material.QUARTZ_BLOCK;
	private final static Material springCoreMat = Material.GLOWSTONE;
	
	private static void generateSupport(SupportBlocks chunk, Odds odds, DataContext context, int x, int y, int z) {
		BlackMagic.setBlocks(chunk, x, x + 3, y, z, z + 3, springBaseMat, 2);
		
		generateSpringBit(chunk, odds, x    , y + 1, z    , BadMagic.Stair.EAST, BadMagic.Stair.SOUTHFLIP, BadMagic.Stair.EAST);
		generateSpringBit(chunk, odds, x + 1, y + 1, z    , BadMagic.Stair.WESTFLIP, BadMagic.Stair.EAST, BadMagic.Stair.WESTFLIP);
		generateSpringBit(chunk, odds, x + 2, y + 1, z    , BadMagic.Stair.SOUTH, BadMagic.Stair.WESTFLIP, BadMagic.Stair.SOUTH);
		generateSpringBit(chunk, odds, x + 2, y + 1, z + 1, BadMagic.Stair.NORTHFLIP, BadMagic.Stair.SOUTH, BadMagic.Stair.NORTHFLIP);
		generateSpringBit(chunk, odds, x + 2, y + 1, z + 2, BadMagic.Stair.WEST, BadMagic.Stair.NORTHFLIP, BadMagic.Stair.WEST);
		generateSpringBit(chunk, odds, x + 1, y + 1, z + 2, BadMagic.Stair.EASTFLIP, BadMagic.Stair.WEST, BadMagic.Stair.EASTFLIP);
		generateSpringBit(chunk, odds, x    , y + 1, z + 2, BadMagic.Stair.NORTH, BadMagic.Stair.EASTFLIP, BadMagic.Stair.NORTH);
		generateSpringBit(chunk, odds, x    , y + 1, z + 1, BadMagic.Stair.SOUTHFLIP, BadMagic.Stair.NORTH, BadMagic.Stair.SOUTHFLIP);

		chunk.setBlocks(x + 1, y + 1, y + 5, z + 1, springCoreMat);
		
		BlackMagic.setBlocks(chunk, x, x + 3, y + 4, z, z + 3, springBaseMat, 2);
	}
	
	private static void generateSpringBit(SupportBlocks chunk, Odds odds, int x, int y, int z, 
			BadMagic.Stair data1, BadMagic.Stair data2, BadMagic.Stair data3) {
		chunk.setStair(x, y    , z, springMat, data1);
		chunk.setStair(x, y + 1, z, springMat, data2);
		chunk.setStair(x, y + 2, z, springMat, data3);
	}

	private static void generateTreat(CityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x, int y, int z) {
		
		// cool stuff?
		if (generator.settings.treasuresInBunkers && odds.playOdds(generator.settings.oddsOfTreasureInBunkers)) {
			 chunk.setChest(generator, x, y, z, BadMagic.General.NORTH, odds, generator.lootProvider, LootLocation.BUNKER);
		}
	}

	private static void generateTrick(CityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x, int y, int z) {

		// not so cool stuff?
		if (generator.settings.spawnersInBunkers && odds.playOdds(generator.settings.oddsOfSpawnerInBunkers)) {
			chunk.setSpawner(x, y, z, generator.spawnProvider.getEntity(generator, odds, SpawnerLocation.BUNKER));
		}
	}
	
	private static BunkerType getRandomBunkerType(Odds chunkOdds, boolean firstOne) {
		if (firstOne)
			return BunkerType.ENTRY;
		else 
			switch (chunkOdds.getRandomInt(8)) {
			case 1:
				return BunkerType.BALLSY;  
			case 2:
				return BunkerType.FLOORED; 
			case 3:
				return BunkerType.GROWING; 
			case 4:
				return BunkerType.PYRAMID; 
			case 5:
				return BunkerType.QUAD;    
			case 6:
				return BunkerType.RECALL;  
			case 7:
				return BunkerType.SAUCER;  
			default:
				return BunkerType.TANK;    
			}
	}
	
}
