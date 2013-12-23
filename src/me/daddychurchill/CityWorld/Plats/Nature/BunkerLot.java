package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Plugins.SpawnProvider.SpawnerLocation;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportChunk;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class BunkerLot extends ConstructLot {

	private final static int FloorHeight = DataContext.FloorHeight;
	
	public BunkerLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		//platmap.generator.reportMessage("BUNKER AT " + (chunkX * 16) + ", " + (chunkZ * 16));
	}
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new BunkerLot(platmap, chunkX, chunkZ);
	}

	// these MUST be given in chunk segment units (currently 16) 
	private final static int bunkerSegment = 16;
	private final static int bunkerBuffer = bunkerSegment;
	private final static int bunkerBelowStreet = bunkerSegment;
	private final static int bunkerMinHeight = bunkerSegment * 2;
	private final static int bunkerMaxHeight = bunkerSegment * 8;

	private int bottomOfBunker;
	private int topOfBunker;
	
	@Override
	protected void initializeContext(WorldGenerator generator, SupportChunk chunk) {
		super.initializeContext(generator, chunk);
		
		bottomOfBunker = calcSegmentOrigin(generator.streetLevel) - bunkerBelowStreet;
		topOfBunker = calcBunkerCeiling(generator);
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
	
	private final static Material supportMaterial = Material.COBBLESTONE;
	private final static Material platformMaterial = Material.SANDSTONE;
	private final static Material crosswalkMaterial = Material.WOOD;
	private final static Material railingMaterial = Material.IRON_FENCE;
	private final static Material buildingMaterial = Material.CLAY;
	private final static Material windowMaterial = Material.GLASS;
	
	//private final static int bilgeEmpty = 0;
	private final static int bilgeWater = 1;
	private final static int bilgeLava = 2;
	private final static int bilgeIce = 3;
	
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

	private int bilgeType;
	private int buildingType;
	
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
		
		// initial rolls
		bilgeType = platmapOdds.getRandomInt(5);
		buildingType = chunkOdds.getRandomInt(7);
		
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
		case bilgeWater:
			chunk.setLayer(yBottom + 1, Material.STATIONARY_WATER);
//			chunk.setBlocks(0, 16, yBottom + 2, yTop3, 0, 16, airId);
			break;
		case bilgeLava:
			chunk.setLayer(yBottom + 1, Material.STATIONARY_LAVA);
//			chunk.setBlocks(0, 16, yBottom + 2, yTop3, 0, 16, airId);
			break;
		case bilgeIce:
			chunk.setLayer(yBottom + 1, Material.ICE);
//			chunk.setBlocks(0, 16, yBottom + 2, yTop3, 0, 16, airId);
			break;
		default: // bilgeEmpty:
//			chunk.setBlocks(0, 16, yBottom + 1, yTop3, 0, 16, airId);
			break;
		}
		
		// vertical beams
		chunk.setBlocks(0, 2, yBottom + 1, yTop3, 0, 1, supportMaterial);
		chunk.setBlocks(0, yBottom + 1, yTop3, 1, supportMaterial);
		chunk.setBlocks(0, 2, yBottom + 1, yTop3, 15, 16, supportMaterial);
		chunk.setBlocks(0, yBottom + 1, yTop3, 14, supportMaterial);
		chunk.setBlocks(14, 16, yBottom + 1, yTop3, 0, 1, supportMaterial);
		chunk.setBlocks(15, yBottom + 1, yTop3, 1, supportMaterial);
		chunk.setBlocks(14, 16, yBottom + 1, yTop3, 15, 16, supportMaterial);
		chunk.setBlocks(15, yBottom + 1, yTop3, 14, supportMaterial);
		
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
		chunk.setBlocks(2, 14, yPlatform, 2, 14, platformMaterial);
		
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
		
		// build a pretend building
		switch (buildingType) {
		case 1:
			generateGrowingBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case 2:
			generateFlooredBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case 3:
			generateBallsyBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case 4:
			generateRecallBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case 5:
			generateQuadBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case 6:
			generateTankBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		default:
			generatePyramidBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		}
		
//		// poke out a flag
//		chunk.setBlocks(7, yTop4, maxHeight + 3, 7, airId);
//		chunk.setBlocks(7, maxHeight + 3, maxHeight + 10, 7, (byte) Material.GLOWSTONE.getId());
	}
	
	private void generateGrowingBuilding(WorldGenerator generator, DataContext context, ByteChunk chunk, int y1, int y2) {
		int x1 = 4;
		int x2 = x1 + 8;
		int y = y1;
		int z1 = 4;
		int z2 = z1 + 8;
		int Height = FloorHeight;
		while (y + Height < y2) {
			
			// walls please
			chunk.setWalls(x1, x2, y, y + Height - 1, z1, z2, buildingMaterial);
			
			// interspace
			chunk.setBlocks(x1 + 1, x2 - 1, y + Height - 1, y + Height, z1 + 1, z2 - 1, buildingMaterial);
			
			// make things bigger
			y += Height;
			Height += FloorHeight;
		}

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.IRON_BLOCK);
	}

	private void generateFlooredBuilding(WorldGenerator generator, DataContext context, ByteChunk chunk, int y1, int y2) {
		int x1 = 4;
		int x2 = x1 + 8;
		int z1 = 4;
		int z2 = z1 + 8;
		int y3 = y2 - 2;
		for (int y = y1; y < y3; y += FloorHeight) {
			
			// walls please
			chunk.setWalls(x1, x2, y, y + FloorHeight - 1, z1, z2, buildingMaterial);
			
			// windows in the wall
			chunk.setBlocks(x1 + 2, x2 - 2, y + 1, y + 2, z1, z1 + 1, windowMaterial);
			chunk.setBlocks(x1 + 2, x2 - 2, y + 1, y + 2, z2 - 1, z2, windowMaterial);
			chunk.setBlocks(x1, x1 + 1, y + 1, y + 2, z1 + 2, z2 - 2, windowMaterial);
			chunk.setBlocks(x2 - 1, x2, y + 1, y + 2, z1 + 2, z2 - 2, windowMaterial);
			
			// interspace
			chunk.setBlocks(x1 + 1, x2 - 1, y + FloorHeight - 1, y + FloorHeight, z1 + 1, z2 - 1, buildingMaterial);
		}

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.BOOKSHELF);
	}

	private void generateRecallBuilding(WorldGenerator generator, DataContext context, ByteChunk chunk, int y1, int y2) {
		int buildingWidth = 10;
		int x1 = (chunk.width - buildingWidth) / 2;
		int x2 = x1 + buildingWidth;
		int z1 = x1;
		int z2 = z1 + buildingWidth;
		Material emptyMaterial = getAirMaterial(generator, y1);
		
		// lower bit
		chunk.setWalls(x1 + 1, x2 - 1, y1, y1 + 1, z1 + 1, z2 - 1, buildingMaterial);
		chunk.setWalls(x1 + 1, x2 - 1, y1 + 1, y1 + 2, z1 + 1, z2 - 1, windowMaterial);
		
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
				chunk.setBlocks(x1 + i, y, yTop, z1, buildingMaterial);
				chunk.setBlocks(x1 + i - 1, y, yTop, z2 - 1, buildingMaterial);
				chunk.setBlocks(x1, y, yTop, z1 + i, buildingMaterial);
				chunk.setBlocks(x2 - 1, y, yTop, z1 + i - 1, buildingMaterial);
			}
			
			// inner wall
			chunk.setWalls(x1 + 1, x2 - 1, y, yTop, z1 + 1, z2 - 1, buildingMaterial);
			
			// cap it off
			chunk.setBlocks(x1 + 1, x2 - 1, yTop, yTop + 1, z1 + 1, z2 - 1, buildingMaterial);
			
			// make things bigger
			y += Height;
			Height += FloorHeight;
		}
		
//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.DIAMOND_BLOCK);
	}

	private void generateBallsyBuilding(WorldGenerator generator, DataContext context, ByteChunk chunk, int y1, int y2) {
		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		int y3 = y2 - 5;
		
		// initial pylon
		chunk.setBlocks(x1 + 4, x2 - 4, y1, y1 + 2, z1 + 4, z2 - 4, buildingMaterial);
		
		// rest of the pylon and balls
		for (int y = y1 + 2; y < y3; y += 6) {
			
			// center pylon
			chunk.setBlocks(x1 + 4, x2 - 4, y, y + 6, z1 + 4, z2 - 4, buildingMaterial);
			
			// balls baby!
			generateBallsyBuildingBall(chunk, x1, y, z1);
			generateBallsyBuildingBall(chunk, x1, y, z2 - 5);
			generateBallsyBuildingBall(chunk, x2 - 5, y, z1);
			generateBallsyBuildingBall(chunk, x2 - 5, y, z2 - 5);
		}

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.BEDROCK);
	}
	
	private void generateBallsyBuildingBall(ByteChunk chunk, int x, int y, int z) {
		if (chunkOdds.playOdds(0.25)) {
			
			// bottom
			chunk.setBlocks(x + 1, x + 4, y, y + 1, z + 1, z + 4, buildingMaterial);
			
			// sides
			chunk.setBlocks(x, x + 5, y + 1, y + 4, z, z + 5, buildingMaterial);
			
			// top
			chunk.setBlocks(x + 1, x + 4, y + 4, y + 5, z + 1, z + 4, buildingMaterial);
		}
	}

	private void generateQuadBuilding(WorldGenerator generator, DataContext context, ByteChunk chunk, int y1, int y2) {
		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		int ySegment = Math.max(1, (y2 - y1) / 5);
		int yRange = ySegment * 3;
		int yBase = y1 + ySegment;
		
		// four towers
		chunk.setBlocks(x1, x1 + 5, y1, yBase + chunkOdds.getRandomInt(yRange), z1, z1 + 5, buildingMaterial);
		chunk.setBlocks(x1, x1 + 5, y1, yBase + chunkOdds.getRandomInt(yRange), z2 - 5, z2, buildingMaterial);
		chunk.setBlocks(x2 - 5, x2, y1, yBase + chunkOdds.getRandomInt(yRange), z1, z1 + 5, buildingMaterial);
		chunk.setBlocks(x2 - 5, x2, y1, yBase + chunkOdds.getRandomInt(yRange), z2 - 5, z2, buildingMaterial);
		
		//TODO make them hollow
		//TODO vertical windows
		//TODO horizontal connections from time to time, place treasures here
		//TODO spiral staircase up the middle
		
//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.GOLD_BLOCK);
	}
	
	private void generateTankBuilding(WorldGenerator generator, DataContext context, ByteChunk chunk, int y1, int y2) {
		int x1 = 4;
		int x2 = x1 + 8;
		int z1 = 4;
		int z2 = z1 + 8;
		int yBottom = y1 + 4;
		int yTop = y2;
		
		// supports
		chunk.setBlocks(x1 + 1, x1 + 3, y1, yBottom, z1 + 1, z1 + 3, buildingMaterial);
		chunk.setBlocks(x1 + 1, x1 + 3, y1, yBottom, z2 - 3, z2 - 1, buildingMaterial);
		chunk.setBlocks(x2 - 3, x2 - 1, y1, yBottom, z1 + 1, z1 + 3, buildingMaterial);
		chunk.setBlocks(x2 - 3, x2 - 1, y1, yBottom, z2 - 3, z2 - 1, buildingMaterial);
		
		// bottom bit
		chunk.setBlocks(x1, x2, yBottom, yBottom + 1, z1, z2, buildingMaterial);
		
		// walls
		chunk.setBlocks(x1, x2, yBottom + 1, yTop, z1 - 1, z1, buildingMaterial);
		chunk.setBlocks(x1, x2, yBottom + 1, yTop, z2    , z2 + 1, buildingMaterial);
		chunk.setBlocks(x1 - 1, x1, yBottom + 1, yTop, z1, z2, buildingMaterial);
		chunk.setBlocks(x2    , x2 + 1, yBottom + 1, yTop, z1, z2, buildingMaterial);
		
		// make it so we can see in a bit
		chunk.setBlocks(x1 + 3, x2 - 3, yBottom + 1, yTop, z1 - 1, z1, windowMaterial);
		chunk.setBlocks(x1 + 3, x2 - 3, yBottom + 1, yTop, z2    , z2 + 1, windowMaterial);
		chunk.setBlocks(x1 - 1, x1, yBottom + 1, yTop, z1 + 3, z2 - 3, windowMaterial);
		chunk.setBlocks(x2    , x2 + 1, yBottom + 1, yTop, z1 + 3, z2 - 3, windowMaterial);
		
		// put a top on it
		chunk.setBlocks(x1, x2, yTop, yTop + 1, z1, z2, buildingMaterial);
		
		chunk.setBlocks(x1, x2, yBottom + 1, yBottom + ((yTop - yBottom) / 3) * 2, z1, z2, getFillMaterial());

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.LAPIS_BLOCK);
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

	private void generatePyramidBuilding(WorldGenerator generator, DataContext context, ByteChunk chunk, int y1, int y2) {
		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		Material emptyMaterial = getAirMaterial(generator, y1);
		for (int i = 0; i < 7; i++) {
			int y = y1 + i * 2;
			chunk.setWalls(x1 + i, x2 - i, y, y + 2, z1 + i, z2 - i, buildingMaterial);
		}

		// make it so we can walk through the pyramid
		chunk.setBlocks(x1 + 5, x2 - 5, y1, y1 + 2, z1    , z1 + 1, emptyMaterial);
		chunk.setBlocks(x1 + 5, x2 - 5, y1, y1 + 2, z2 - 1, z2    , emptyMaterial);
		chunk.setBlocks(x1    , x1 + 1, y1, y1 + 2, z1 + 5, z2 - 5, emptyMaterial);
		chunk.setBlocks(x2 - 1, x2    , y1, y1 + 2, z1 + 5, z2 - 5, emptyMaterial);
		
		// top off the entry ways
		chunk.setBlocks(x1 + 4, x2 - 4, y1 + 2, y1 + 3, z1    , z1 + 1, buildingMaterial);
		chunk.setBlocks(x1 + 4, x2 - 4, y1 + 2, y1 + 3, z2 - 1, z2    , buildingMaterial);
		chunk.setBlocks(x1    , x1 + 1, y1 + 2, y1 + 3, z1 + 4, z2 - 4, buildingMaterial);
		chunk.setBlocks(x2 - 1, x2    , y1 + 2, y1 + 3, z1 + 4, z2 - 4, buildingMaterial);

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.STONE);
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		
		// initial rolls
		bilgeType = platmapOdds.getRandomInt(5);
		buildingType = chunkOdds.getRandomInt(7);
			
		int yBottom = bottomOfBunker;
		int yTop4 = topOfBunker;
//		int yTop3 = yTop4 - 2;
		int yTop2 = yTop4 - bunkerSegment; 
//		int yTop1 = yTop2 - 2;
		int yPlatform = calcSegmentOrigin(yBottom) + 6;
		
		// hold up buildings
		generateSupport(chunk, context, 3, yBottom + 1, 3, bilgeType);
		generateSupport(chunk, context, 3, yBottom + 1, 10, bilgeType);
		generateSupport(chunk, context, 10, yBottom + 1, 3, bilgeType);
		generateSupport(chunk, context, 10, yBottom + 1, 10, bilgeType);
		
		// build a pretend building
		switch (buildingType) {
		case 1:
			decorateGrowingBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case 2:
			decorateFlooredBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case 3:
			decorateBallsyBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case 4:
			decorateRecallBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case 5:
			decorateQuadBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		case 6:
			decorateWaterBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		default:
			decoratePyramidBuilding(generator, context, chunk, yPlatform + 1, yTop2);
			break;
		}
		
		// add some surface
		generateSurface(generator, chunk, true);
	}

	private final static Material springMat = Material.SMOOTH_STAIRS;
	private final static Material springBaseMat = Material.STONE;
	private final static Material springCoreMat = Material.GLOWSTONE;
	
	private void generateSupport(RealChunk chunk, DataContext context, int x, int y, int z, int bilgeType) {
		chunk.setBlocks(x, x + 3, y, z, z + 3, springBaseMat);
		
		generateSpringBit(chunk, x    , y + 1, z    , Stair.EAST, Stair.SOUTHFLIP, Stair.EAST);
		generateSpringBit(chunk, x + 1, y + 1, z    , Stair.WESTFLIP, Stair.EAST, Stair.WESTFLIP);
		generateSpringBit(chunk, x + 2, y + 1, z    , Stair.SOUTH, Stair.WESTFLIP, Stair.SOUTH);
		generateSpringBit(chunk, x + 2, y + 1, z + 1, Stair.NORTHFLIP, Stair.SOUTH, Stair.NORTHFLIP);
		generateSpringBit(chunk, x + 2, y + 1, z + 2, Stair.WEST, Stair.NORTHFLIP, Stair.WEST);
		generateSpringBit(chunk, x + 1, y + 1, z + 2, Stair.EASTFLIP, Stair.WEST, Stair.EASTFLIP);
		generateSpringBit(chunk, x    , y + 1, z + 2, Stair.NORTH, Stair.EASTFLIP, Stair.NORTH);
		generateSpringBit(chunk, x    , y + 1, z + 1, Stair.SOUTHFLIP, Stair.NORTH, Stair.SOUTHFLIP);

		chunk.setBlocks(x + 1, y + 1, y + 5, z + 1, springCoreMat);
		
		chunk.setBlocks(x, x + 3, y + 4, z, z + 3, springBaseMat);
	}
	
	private void generateSpringBit(RealChunk chunk, int x, int y, int z, Stair data1, Stair data2, Stair data3) {
		chunk.setStair(x, y    , z, springMat, data1);
		chunk.setStair(x, y + 1, z, springMat, data2);
		chunk.setStair(x, y + 2, z, springMat, data3);
	}

	private void decorateGrowingBuilding(WorldGenerator generator, DataContext context, RealChunk chunk, int y1, int y2) {
//		int x1 = 4;
//		int x2 = x1 + 8;
//		int y = y1;
//		int z1 = 4;
//		int z2 = z1 + 8;
//		int Height = FloorHeight;
//		while (y + Height < y2) {
//			
//			// walls please
//			chunk.setWalls(x1, x2, y, y + Height - 1, z1, z2, buildingMaterial);
//			
//			// interspace
//			chunk.setBlocks(x1 + 1, x2 - 1, y + Height - 1, y + Height, z1 + 1, z2 - 1, buildingMaterial);
//			
//			// make things bigger
//			y += Height;
//			Height += FloorHeight;
//		}

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.IRON_BLOCK);
	}

	private void decorateFlooredBuilding(WorldGenerator generator, DataContext context, RealChunk chunk, int y1, int y2) {
//		int x1 = 4;
//		int x2 = x1 + 8;
//		int z1 = 4;
//		int z2 = z1 + 8;
//		int y3 = y2 - 2;
//		for (int y = y1; y < y3; y += FloorHeight) {
//			
//			// walls please
//			chunk.setWalls(x1, x2, y, y + FloorHeight - 1, z1, z2, buildingMaterial);
//			
//			// windows in the wall
//			chunk.setBlocks(x1 + 2, x2 - 2, y + 1, y + 2, z1, z1 + 1, glassId);
//			chunk.setBlocks(x1 + 2, x2 - 2, y + 1, y + 2, z2 - 1, z2, glassId);
//			chunk.setBlocks(x1, x1 + 1, y + 1, y + 2, z1 + 2, z2 - 2, glassId);
//			chunk.setBlocks(x2 - 1, x2, y + 1, y + 2, z1 + 2, z2 - 2, glassId);
//			
//			//TODO add doors & stairs
//			
//			// interspace
//			chunk.setBlocks(x1 + 1, x2 - 1, y + FloorHeight - 1, y + FloorHeight, z1 + 1, z2 - 1, buildingMaterial);
//		}

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.BOOKSHELF);
	}

	private void decorateRecallBuilding(WorldGenerator generator, DataContext context, RealChunk chunk, int y1, int y2) {
		generateTreat(generator, chunk, 5, y1, 5);
		generateTreat(generator, chunk, 10, y1, 10);
		
		generateTrick(generator, chunk, 10, y1, 5);
		generateTrick(generator, chunk, 5, y1, 10);
	}

	private void decorateBallsyBuilding(WorldGenerator generator, DataContext context, RealChunk chunk, int y1, int y2) {
//		int x1 = 2;
//		int x2 = x1 + 12;
//		int z1 = 2;
//		int z2 = z1 + 12;
//		int y3 = y2 - 5;
//		
//		// initial pylon
//		chunk.setBlocks(x1 + 4, x2 - 4, y1, y1 + 2, z1 + 4, z2 - 4, buildingMaterial);
//		
//		// rest of the pylon and balls
//		for (int y = y1 + 2; y < y3; y += 6) {
//			
//			// center pylon
//			chunk.setBlocks(x1 + 4, x2 - 4, y, y + 6, z1 + 4, z2 - 4, buildingMaterial);
//			
//			// balls baby!
//			decorateBallsyBuildingBall(chunk, random, x1, y, z1);
//			decorateBallsyBuildingBall(chunk, random, x1, y, z2 - 5);
//			decorateBallsyBuildingBall(chunk, random, x2 - 5, y, z1);
//			decorateBallsyBuildingBall(chunk, random, x2 - 5, y, z2 - 5);
//		}

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.BEDROCK);
	}
	
//	private void decorateBallsyBuildingBall(RealChunk chunk, int x, int y, int z) {
//		if (random.nextDouble() > 0.25) {
//			
//			// bottom
//			chunk.setBlocks(x + 1, x + 4, y, y + 1, z + 1, z + 4, buildingMaterial);
//			
//			// sides
//			chunk.setBlocks(x, x + 5, y + 1, y + 4, z, z + 5, buildingMaterial);
//			
//			// top
//			chunk.setBlocks(x + 1, x + 4, y + 4, y + 5, z + 1, z + 4, buildingMaterial);
//		}
//	}

	private void decorateQuadBuilding(WorldGenerator generator, DataContext context, RealChunk chunk, int y1, int y2) {
//		int x1 = 2;
//		int x2 = x1 + 12;
//		int z1 = 2;
//		int z2 = z1 + 12;
//		int ySegment = (y2 - y1) / 5;
//		int yRange = ySegment * 3;
//		int yBase = y1 + ySegment;
//		
//		// four towers
//		chunk.setBlocks(x1, x1 + 5, y1, yBase + random.nextInt(yRange), z1, z1 + 5, buildingMaterial);
//		chunk.setBlocks(x1, x1 + 5, y1, yBase + random.nextInt(yRange), z2 - 5, z2, buildingMaterial);
//		chunk.setBlocks(x2 - 5, x2, y1, yBase + random.nextInt(yRange), z1, z1 + 5, buildingMaterial);
//		chunk.setBlocks(x2 - 5, x2, y1, yBase + random.nextInt(yRange), z2 - 5, z2, buildingMaterial);
		
//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.GOLD_BLOCK);
	}
	
	private void decorateWaterBuilding(WorldGenerator generator, DataContext context, RealChunk chunk, int y1, int y2) {
//		int x1 = 4;
//		int x2 = x1 + 8;
//		int z1 = 4;
//		int z2 = z1 + 8;
//		int yBottom = y1 + 4;
//		int yTop = y2;
//		
//		// supports
//		chunk.setBlocks(x1 + 1, x1 + 3, y1, yBottom, z1 + 1, z1 + 3, buildingMaterial);
//		chunk.setBlocks(x1 + 1, x1 + 3, y1, yBottom, z2 - 3, z2 - 1, buildingMaterial);
//		chunk.setBlocks(x2 - 3, x2 - 1, y1, yBottom, z1 + 1, z1 + 3, buildingMaterial);
//		chunk.setBlocks(x2 - 3, x2 - 1, y1, yBottom, z2 - 3, z2 - 1, buildingMaterial);
//		
//		// bottom bit
//		chunk.setBlocks(x1, x2, yBottom, yBottom + 1, z1, z2, buildingMaterial);
//		
//		// walls
//		chunk.setBlocks(x1, x2, yBottom + 1, yTop, z1 - 1, z1, buildingMaterial);
//		chunk.setBlocks(x1, x2, yBottom + 1, yTop, z2    , z2 + 1, buildingMaterial);
//		chunk.setBlocks(x1 - 1, x1, yBottom + 1, yTop, z1, z2, buildingMaterial);
//		chunk.setBlocks(x2    , x2 + 1, yBottom + 1, yTop, z1, z2, buildingMaterial);
//		
//		// make it so we can see in a bit
//		chunk.setBlocks(x1 + 3, x2 - 3, yBottom + 1, yTop, z1 - 1, z1, glassId);
//		chunk.setBlocks(x1 + 3, x2 - 3, yBottom + 1, yTop, z2    , z2 + 1, glassId);
//		chunk.setBlocks(x1 - 1, x1, yBottom + 1, yTop, z1 + 3, z2 - 3, glassId);
//		chunk.setBlocks(x2    , x2 + 1, yBottom + 1, yTop, z1 + 3, z2 - 3, glassId);
//		
//		// put a top on it
//		chunk.setBlocks(x1, x2, yTop, yTop + 1, z1, z2, buildingMaterial);
//		
//		// fill it
//		chunk.setBlocks(x1, x2, yBottom + 1, yBottom + ((yTop - yBottom) / 3) * 2, z1, z2, waterMaterial);

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.LAPIS_BLOCK);
	}

	private void decoratePyramidBuilding(WorldGenerator generator, DataContext context, RealChunk chunk, int y1, int y2) {
		generateTreat(generator, chunk, 3, y1, 3);
		generateTreat(generator, chunk, 12, y1, 12);
		
		generateTrick(generator, chunk, 12, y1, 3);
		generateTrick(generator, chunk, 3, y1, 12);
	}
	
	private void generateTreat(WorldGenerator generator, RealChunk chunk, int x, int y, int z) {
		
		// cool stuff?
		if (generator.settings.treasuresInBunkers && chunkOdds.playOdds(generator.settings.oddsOfTreasureInBunkers)) {
			 chunk.setChest(x, y, z, Direction.General.NORTH, chunkOdds, generator.lootProvider, LootLocation.BUNKER);
		}
	}

	private void generateTrick(WorldGenerator generator, RealChunk chunk, int x, int y, int z) {

		// not so cool stuff?
		if (generator.settings.spawnersInBunkers && chunkOdds.playOdds(generator.settings.oddsOfSpawnerInBunkers)) {
			chunk.setSpawner(x, y, z, generator.spawnProvider.getEntity(generator, chunkOdds, SpawnerLocation.BUNKER));
		}
	}
}
