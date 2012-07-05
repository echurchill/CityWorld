package me.daddychurchill.CityWorld.Plats;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Plugins.SpawnProvider;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatBunker extends PlatIsolated {

	private final static int FloorHeight = ContextData.FloorHeight;
	
	public PlatBunker(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

	}
	
	// these MUST be given in chunk segment units (currently 16) 
	private final static int bunkerSegment = 16;
	private final static int bunkerBuffer = bunkerSegment;
	private final static int bunkerBelowStreet = bunkerSegment;
	private final static int bunkerMinHeight = bunkerSegment * 2;
	private final static int bunkerMaxHeight = bunkerSegment * 8;

	@Override
	protected boolean isValidStrataY(WorldGenerator generator, int blockX, int blockY, int blockZ) {
		return blockY < calcSegmentOrigin(generator.sidewalkLevel) - bunkerBelowStreet || blockY > calcBunkerCeiling(generator);
	}

	@Override
	protected boolean isShaftableLevel(WorldGenerator generator, ContextData context, int y) {
		return (y < calcSegmentOrigin(generator.sidewalkLevel) - bunkerBelowStreet - bunkerBuffer || y > calcBunkerCeiling(generator) - bunkerSegment - bunkerBuffer) &&
				super.isShaftableLevel(generator, context, y);
//		
//		return (y < calcSegmentOrigin(generator.sidewalkLevel) - bunkerMinHeight - bunkerBelowStreet || y > calcBunkerCeiling(generator) - bunkerMinHeight) &&
//				super.isShaftableLevel(generator, context, y);	
	}
	
	private final static byte supportId = cobbleId;
	private final static byte platformId = sandstoneId;
	private final static byte crosswalkId = (byte) Material.WOOD.getId();
	private final static byte railingId = (byte) Material.IRON_FENCE.getId();
	private final static byte buildingId = (byte) Material.CLAY.getId();
	private final static byte glassId = (byte) Material.GLASS.getId();
	private final static byte waterId = (byte) Material.STATIONARY_WATER.getId();
	private final static byte lavaId = (byte) Material.STATIONARY_LAVA.getId();
	private final static byte iceId = (byte) Material.ICE.getId();
	private final static byte spongeId = (byte) Material.SPONGE.getId();
	private final static byte oilId = (byte) 163;
	
	//private final static int bilgeEmpty = 0;
	private final static int bilgeWater = 1;
	private final static int bilgeLava = 2;
	private final static int bilgeIce = 3;
	
	private static int calcSegmentOrigin(int y) {
		return y / bunkerSegment * bunkerSegment;
	}
	
	public static int calcBunkerMinHeight(WorldGenerator generator) {
		return calcSegmentOrigin(generator.sidewalkLevel) + bunkerMinHeight - bunkerBelowStreet + bunkerBuffer;
	}
	
	public static int calcBunkerMaxHeight(WorldGenerator generator) {
		return calcSegmentOrigin(generator.sidewalkLevel) + bunkerMaxHeight - bunkerBelowStreet + bunkerBuffer;
	}
	
	private int calcBunkerCeiling(WorldGenerator generator) {
		return Math.min(calcBunkerMaxHeight(generator), calcSegmentOrigin(minHeight) - bunkerBuffer);
	}

	private int bilgeType;
	private int buildingType;
	
	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		
		// initial rolls
		bilgeType = platmapRandom.nextInt(5);
		buildingType = chunkRandom.nextInt(7);
		
		// precalculate
		int yBottom = calcSegmentOrigin(generator.sidewalkLevel) - bunkerBelowStreet;
		int yTop4 = calcBunkerCeiling(generator);
		int yTop3 = yTop4 - 2;
		int yTop2 = yTop4 - bunkerSegment; 
		int yTop1 = yTop2 - 2;
		int yPlatform = calcSegmentOrigin(yBottom) + 6;
		
		// bottom
		chunk.setLayer(yBottom, supportId);
		
		// clear out stuff?
		switch (bilgeType) {
		case bilgeWater:
			chunk.setLayer(yBottom + 1, waterId);
			chunk.setBlocks(0, 16, yBottom + 2, yTop3, 0, 16, airId);
			break;
		case bilgeLava:
			chunk.setLayer(yBottom + 1, lavaId);
			chunk.setBlocks(0, 16, yBottom + 2, yTop3, 0, 16, airId);
			break;
		case bilgeIce:
			chunk.setLayer(yBottom + 1, iceId);
			chunk.setBlocks(0, 16, yBottom + 2, yTop3, 0, 16, airId);
			break;
		default: // bilgeEmpty:
			chunk.setBlocks(0, 16, yBottom + 1, yTop3, 0, 16, airId);
			break;
		}
		
		// clear out space
		chunk.setBlocks(2, 14, yTop3, yTop3 + 2, 2, 14, airId);
		
		// vertical beams
		chunk.setBlocks(0, 2, yBottom + 1, yTop3, 0, 1, supportId);
		chunk.setBlocks(0, yBottom + 1, yTop3, 1, supportId);
		chunk.setBlocks(0, 2, yBottom + 1, yTop3, 15, 16, supportId);
		chunk.setBlocks(0, yBottom + 1, yTop3, 14, supportId);
		chunk.setBlocks(14, 16, yBottom + 1, yTop3, 0, 1, supportId);
		chunk.setBlocks(15, yBottom + 1, yTop3, 1, supportId);
		chunk.setBlocks(14, 16, yBottom + 1, yTop3, 15, 16, supportId);
		chunk.setBlocks(15, yBottom + 1, yTop3, 14, supportId);
		
		// near top cross beams
		chunk.setBlocks(0, 16, yTop1, yTop2, 0, 2, supportId);
		chunk.setBlocks(0, 16, yTop1, yTop2, 14, 16, supportId);
		chunk.setBlocks(0, 2, yTop1, yTop2, 2, 14, supportId);
		chunk.setBlocks(14, 16, yTop1, yTop2, 2, 14, supportId);
		
		// top cross beams
		chunk.setBlocks(0, 16, yTop3, yTop4, 0, 2, supportId);
		chunk.setBlocks(0, 16, yTop3, yTop4, 14, 16, supportId);
		chunk.setBlocks(0, 2, yTop3, yTop4, 2, 14, supportId);
		chunk.setBlocks(14, 16, yTop3, yTop4, 2, 14, supportId);
		
		// draw platform
		chunk.setBlocks(2, 14, yPlatform, 2, 14, platformId);
		
		// draw crosswalks
		chunk.setBlocks(7, 9, yPlatform, 0, 2, crosswalkId);
		chunk.setBlocks(0, 2, yPlatform, 7, 9, crosswalkId);
		chunk.setBlocks(7, 9, yPlatform, 14, 16, crosswalkId);
		chunk.setBlocks(14, 16, yPlatform, 7, 9, crosswalkId);
		
		// draw railing
		chunk.setBlocks(2, 7, yPlatform + 1, 2, 3, railingId);
		chunk.setBlocks(9, 14, yPlatform + 1, 2, 3, railingId);
		chunk.setBlocks(2, 7, yPlatform + 1, 13, 14, railingId);
		chunk.setBlocks(9, 14, yPlatform + 1, 13, 14, railingId);
		
		chunk.setBlocks(2, 3, yPlatform + 1, 3, 7, railingId);
		chunk.setBlocks(13, 14, yPlatform + 1, 3, 7, railingId);
		chunk.setBlocks(2, 3, yPlatform + 1, 9, 13, railingId);
		chunk.setBlocks(13, 14, yPlatform + 1, 9, 13, railingId);
		
		chunk.setBlocks(6, 7, yPlatform, yPlatform + 2, 0, 2, railingId);
		chunk.setBlocks(9, 10, yPlatform, yPlatform + 2, 0, 2, railingId);
		chunk.setBlocks(6, 7, yPlatform, yPlatform + 2, 14, 16, railingId);
		chunk.setBlocks(9, 10, yPlatform, yPlatform + 2, 14, 16, railingId);
		
		chunk.setBlocks(0, 2, yPlatform, yPlatform + 2, 6, 7, railingId);
		chunk.setBlocks(0, 2, yPlatform, yPlatform + 2, 9, 10, railingId);
		chunk.setBlocks(14, 16, yPlatform, yPlatform + 2, 6, 7, railingId);
		chunk.setBlocks(14, 16, yPlatform, yPlatform + 2, 9, 10, railingId);
		
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
	
	private void generateGrowingBuilding(WorldGenerator generator, ContextData context, ByteChunk chunk, int y1, int y2) {
		int x1 = 4;
		int x2 = x1 + 8;
		int y = y1;
		int z1 = 4;
		int z2 = z1 + 8;
		int Height = FloorHeight;
		while (y + Height < y2) {
			
			// walls please
			chunk.setWalls(x1, x2, y, y + Height - 1, z1, z2, buildingId);
			
			// interspace
			chunk.setBlocks(x1 + 1, x2 - 1, y + Height - 1, y + Height, z1 + 1, z2 - 1, buildingId);
			
			// make things bigger
			y += Height;
			Height += FloorHeight;
		}

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.IRON_BLOCK);
	}

	private void generateFlooredBuilding(WorldGenerator generator, ContextData context, ByteChunk chunk, int y1, int y2) {
		int x1 = 4;
		int x2 = x1 + 8;
		int z1 = 4;
		int z2 = z1 + 8;
		int y3 = y2 - 2;
		for (int y = y1; y < y3; y += FloorHeight) {
			
			// walls please
			chunk.setWalls(x1, x2, y, y + FloorHeight - 1, z1, z2, buildingId);
			
			// windows in the wall
			chunk.setBlocks(x1 + 2, x2 - 2, y + 1, y + 2, z1, z1 + 1, glassId);
			chunk.setBlocks(x1 + 2, x2 - 2, y + 1, y + 2, z2 - 1, z2, glassId);
			chunk.setBlocks(x1, x1 + 1, y + 1, y + 2, z1 + 2, z2 - 2, glassId);
			chunk.setBlocks(x2 - 1, x2, y + 1, y + 2, z1 + 2, z2 - 2, glassId);
			
			// interspace
			chunk.setBlocks(x1 + 1, x2 - 1, y + FloorHeight - 1, y + FloorHeight, z1 + 1, z2 - 1, buildingId);
		}

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.BOOKSHELF);
	}

	private void generateRecallBuilding(WorldGenerator generator, ContextData context, ByteChunk chunk, int y1, int y2) {
		int buildingWidth = 10;
		int x1 = (chunk.width - buildingWidth) / 2;
		int x2 = x1 + buildingWidth;
		int z1 = x1;
		int z2 = z1 + buildingWidth;
		
		// lower bit
		chunk.setWalls(x1 + 1, x2 - 1, y1, y1 + 1, z1 + 1, z2 - 1, buildingId);
		chunk.setWalls(x1 + 1, x2 - 1, y1 + 1, y1 + 2, z1 + 1, z2 - 1, glassId);
		
		// make it so we can walk into the 
		chunk.setBlocks(x1 + 4, x2 - 4, y1, y1 + 2, z1 + 1, z1 + 2, airId);
		chunk.setBlocks(x1 + 4, x2 - 4, y1, y1 + 2, z2 - 2, z2 - 1, airId);
		chunk.setBlocks(x1 + 1, x1 + 2, y1, y1 + 2, z1 + 4, z2 - 4, airId);
		chunk.setBlocks(x2 - 2, x2 - 1, y1, y1 + 2, z1 + 4, z2 - 4, airId);
		
		int y = y1 + 2;
		int Height = FloorHeight;
		while (y + Height < y2) {
			int yTop = y + Height - 1;
			
			// texture
			for (int i = 1; i < buildingWidth; i += 2) {
				chunk.setBlocks(x1 + i, y, yTop, z1, buildingId);
				chunk.setBlocks(x1 + i - 1, y, yTop, z2 - 1, buildingId);
				chunk.setBlocks(x1, y, yTop, z1 + i, buildingId);
				chunk.setBlocks(x2 - 1, y, yTop, z1 + i - 1, buildingId);
			}
			
			// inner wall
			chunk.setWalls(x1 + 1, x2 - 1, y, yTop, z1 + 1, z2 - 1, buildingId);
			
			// cap it off
			chunk.setBlocks(x1 + 1, x2 - 1, yTop, yTop + 1, z1 + 1, z2 - 1, buildingId);
			
			// make things bigger
			y += Height;
			Height += FloorHeight;
		}
		
//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.DIAMOND_BLOCK);
	}

	private void generateBallsyBuilding(WorldGenerator generator, ContextData context, ByteChunk chunk, int y1, int y2) {
		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		int y3 = y2 - 5;
		
		// initial pylon
		chunk.setBlocks(x1 + 4, x2 - 4, y1, y1 + 2, z1 + 4, z2 - 4, buildingId);
		
		// rest of the pylon and balls
		for (int y = y1 + 2; y < y3; y += 6) {
			
			// center pylon
			chunk.setBlocks(x1 + 4, x2 - 4, y, y + 6, z1 + 4, z2 - 4, buildingId);
			
			// balls baby!
			generateBallsyBuildingBall(chunk, x1, y, z1);
			generateBallsyBuildingBall(chunk, x1, y, z2 - 5);
			generateBallsyBuildingBall(chunk, x2 - 5, y, z1);
			generateBallsyBuildingBall(chunk, x2 - 5, y, z2 - 5);
		}

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.BEDROCK);
	}
	
	private void generateBallsyBuildingBall(ByteChunk chunk, int x, int y, int z) {
		if (chunkRandom.nextDouble() > 0.25) {
			
			// bottom
			chunk.setBlocks(x + 1, x + 4, y, y + 1, z + 1, z + 4, buildingId);
			
			// sides
			chunk.setBlocks(x, x + 5, y + 1, y + 4, z, z + 5, buildingId);
			
			// top
			chunk.setBlocks(x + 1, x + 4, y + 4, y + 5, z + 1, z + 4, buildingId);
		}
	}

	private void generateQuadBuilding(WorldGenerator generator, ContextData context, ByteChunk chunk, int y1, int y2) {
		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		int ySegment = Math.max(1, (y2 - y1) / 5);
		int yRange = ySegment * 3;
		int yBase = y1 + ySegment;
		
		// four towers
		chunk.setBlocks(x1, x1 + 5, y1, yBase + chunkRandom.nextInt(yRange), z1, z1 + 5, buildingId);
		chunk.setBlocks(x1, x1 + 5, y1, yBase + chunkRandom.nextInt(yRange), z2 - 5, z2, buildingId);
		chunk.setBlocks(x2 - 5, x2, y1, yBase + chunkRandom.nextInt(yRange), z1, z1 + 5, buildingId);
		chunk.setBlocks(x2 - 5, x2, y1, yBase + chunkRandom.nextInt(yRange), z2 - 5, z2, buildingId);
		
		//TODO make them hollow
		//TODO vertical windows
		//TODO horizontal connections from time to time, place treasures here
		//TODO spiral staircase up the middle
		
//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.GOLD_BLOCK);
	}
	
	private void generateTankBuilding(WorldGenerator generator, ContextData context, ByteChunk chunk, int y1, int y2) {
		int x1 = 4;
		int x2 = x1 + 8;
		int z1 = 4;
		int z2 = z1 + 8;
		int yBottom = y1 + 4;
		int yTop = y2;
		
		// supports
		chunk.setBlocks(x1 + 1, x1 + 3, y1, yBottom, z1 + 1, z1 + 3, buildingId);
		chunk.setBlocks(x1 + 1, x1 + 3, y1, yBottom, z2 - 3, z2 - 1, buildingId);
		chunk.setBlocks(x2 - 3, x2 - 1, y1, yBottom, z1 + 1, z1 + 3, buildingId);
		chunk.setBlocks(x2 - 3, x2 - 1, y1, yBottom, z2 - 3, z2 - 1, buildingId);
		
		// bottom bit
		chunk.setBlocks(x1, x2, yBottom, yBottom + 1, z1, z2, buildingId);
		
		// walls
		chunk.setBlocks(x1, x2, yBottom + 1, yTop, z1 - 1, z1, buildingId);
		chunk.setBlocks(x1, x2, yBottom + 1, yTop, z2    , z2 + 1, buildingId);
		chunk.setBlocks(x1 - 1, x1, yBottom + 1, yTop, z1, z2, buildingId);
		chunk.setBlocks(x2    , x2 + 1, yBottom + 1, yTop, z1, z2, buildingId);
		
		// make it so we can see in a bit
		chunk.setBlocks(x1 + 3, x2 - 3, yBottom + 1, yTop, z1 - 1, z1, glassId);
		chunk.setBlocks(x1 + 3, x2 - 3, yBottom + 1, yTop, z2    , z2 + 1, glassId);
		chunk.setBlocks(x1 - 1, x1, yBottom + 1, yTop, z1 + 3, z2 - 3, glassId);
		chunk.setBlocks(x2    , x2 + 1, yBottom + 1, yTop, z1 + 3, z2 - 3, glassId);
		
		// put a top on it
		chunk.setBlocks(x1, x2, yTop, yTop + 1, z1, z2, buildingId);
		
		// fill it
		byte fillId;
		switch (chunkRandom.nextInt(4)) {
		case 1:
			fillId = lavaId;
			break;
		case 2:
			fillId = iceId;
			break;
		case 3:
			fillId = snowId;
			break;
		case 4:
			if (generator.settings.tekkitServer) {
				fillId = oilId;
			} else {
				fillId = spongeId;
			}
			break;
		default:
			fillId = waterId;
			break;
		}
		chunk.setBlocks(x1, x2, yBottom + 1, yBottom + ((yTop - yBottom) / 3) * 2, z1, z2, fillId);

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.LAPIS_BLOCK);
	}

	private void generatePyramidBuilding(WorldGenerator generator, ContextData context, ByteChunk chunk, int y1, int y2) {
		int x1 = 2;
		int x2 = x1 + 12;
		int z1 = 2;
		int z2 = z1 + 12;
		for (int i = 0; i < 7; i++) {
			int y = y1 + i * 2;
			chunk.setWalls(x1 + i, x2 - i, y, y + 2, z1 + i, z2 - i, buildingId);
		}

		// make it so we can walk through the pyramid
		chunk.setBlocks(x1 + 5, x2 - 5, y1, y1 + 2, z1    , z1 + 1, airId);
		chunk.setBlocks(x1 + 5, x2 - 5, y1, y1 + 2, z2 - 1, z2    , airId);
		chunk.setBlocks(x1    , x1 + 1, y1, y1 + 2, z1 + 5, z2 - 5, airId);
		chunk.setBlocks(x2 - 1, x2    , y1, y1 + 2, z1 + 5, z2 - 5, airId);
		
		// top off the entry ways
		chunk.setBlocks(x1 + 4, x2 - 4, y1 + 2, y1 + 3, z1    , z1 + 1, buildingId);
		chunk.setBlocks(x1 + 4, x2 - 4, y1 + 2, y1 + 3, z2 - 1, z2    , buildingId);
		chunk.setBlocks(x1    , x1 + 1, y1 + 2, y1 + 3, z1 + 4, z2 - 4, buildingId);
		chunk.setBlocks(x2 - 1, x2    , y1 + 2, y1 + 3, z1 + 4, z2 - 4, buildingId);

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.STONE);
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		
		// initial rolls
		bilgeType = platmapRandom.nextInt(5);
		buildingType = chunkRandom.nextInt(7);
			
		int yBottom = calcSegmentOrigin(generator.sidewalkLevel) - bunkerBelowStreet;
		int yTop4 = calcBunkerCeiling(generator);
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
		generateSurface(generator, platmap, chunk, context, platX, platZ, true);
	}

	private final static Material springMat = Material.SMOOTH_STAIRS;
	private final static Material springBaseMat = Material.STONE;
	private final static Material springCoreMat = Material.GLOWSTONE;
	
	private void generateSupport(RealChunk chunk, ContextData context, int x, int y, int z, int bilgeType) {
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

	private void decorateGrowingBuilding(WorldGenerator generator, ContextData context, RealChunk chunk, int y1, int y2) {
//		int x1 = 4;
//		int x2 = x1 + 8;
//		int y = y1;
//		int z1 = 4;
//		int z2 = z1 + 8;
//		int Height = FloorHeight;
//		while (y + Height < y2) {
//			
//			// walls please
//			chunk.setWalls(x1, x2, y, y + Height - 1, z1, z2, buildingId);
//			
//			// interspace
//			chunk.setBlocks(x1 + 1, x2 - 1, y + Height - 1, y + Height, z1 + 1, z2 - 1, buildingId);
//			
//			// make things bigger
//			y += Height;
//			Height += FloorHeight;
//		}

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.IRON_BLOCK);
	}

	private void decorateFlooredBuilding(WorldGenerator generator, ContextData context, RealChunk chunk, int y1, int y2) {
//		int x1 = 4;
//		int x2 = x1 + 8;
//		int z1 = 4;
//		int z2 = z1 + 8;
//		int y3 = y2 - 2;
//		for (int y = y1; y < y3; y += FloorHeight) {
//			
//			// walls please
//			chunk.setWalls(x1, x2, y, y + FloorHeight - 1, z1, z2, buildingId);
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
//			chunk.setBlocks(x1 + 1, x2 - 1, y + FloorHeight - 1, y + FloorHeight, z1 + 1, z2 - 1, buildingId);
//		}

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.BOOKSHELF);
	}

	private void decorateRecallBuilding(WorldGenerator generator, ContextData context, RealChunk chunk, int y1, int y2) {
		generateTreat(generator, context, chunk, 5, y1, 5);
		generateTreat(generator, context, chunk, 10, y1, 10);
		
		generateTrick(generator, context, chunk, 10, y1, 5);
		generateTrick(generator, context, chunk, 5, y1, 10);
	}

	private void decorateBallsyBuilding(WorldGenerator generator, ContextData context, RealChunk chunk, int y1, int y2) {
//		int x1 = 2;
//		int x2 = x1 + 12;
//		int z1 = 2;
//		int z2 = z1 + 12;
//		int y3 = y2 - 5;
//		
//		// initial pylon
//		chunk.setBlocks(x1 + 4, x2 - 4, y1, y1 + 2, z1 + 4, z2 - 4, buildingId);
//		
//		// rest of the pylon and balls
//		for (int y = y1 + 2; y < y3; y += 6) {
//			
//			// center pylon
//			chunk.setBlocks(x1 + 4, x2 - 4, y, y + 6, z1 + 4, z2 - 4, buildingId);
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
//			chunk.setBlocks(x + 1, x + 4, y, y + 1, z + 1, z + 4, buildingId);
//			
//			// sides
//			chunk.setBlocks(x, x + 5, y + 1, y + 4, z, z + 5, buildingId);
//			
//			// top
//			chunk.setBlocks(x + 1, x + 4, y + 4, y + 5, z + 1, z + 4, buildingId);
//		}
//	}

	private void decorateQuadBuilding(WorldGenerator generator, ContextData context, RealChunk chunk, int y1, int y2) {
//		int x1 = 2;
//		int x2 = x1 + 12;
//		int z1 = 2;
//		int z2 = z1 + 12;
//		int ySegment = (y2 - y1) / 5;
//		int yRange = ySegment * 3;
//		int yBase = y1 + ySegment;
//		
//		// four towers
//		chunk.setBlocks(x1, x1 + 5, y1, yBase + random.nextInt(yRange), z1, z1 + 5, buildingId);
//		chunk.setBlocks(x1, x1 + 5, y1, yBase + random.nextInt(yRange), z2 - 5, z2, buildingId);
//		chunk.setBlocks(x2 - 5, x2, y1, yBase + random.nextInt(yRange), z1, z1 + 5, buildingId);
//		chunk.setBlocks(x2 - 5, x2, y1, yBase + random.nextInt(yRange), z2 - 5, z2, buildingId);
		
//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.GOLD_BLOCK);
	}
	
	private void decorateWaterBuilding(WorldGenerator generator, ContextData context, RealChunk chunk, int y1, int y2) {
//		int x1 = 4;
//		int x2 = x1 + 8;
//		int z1 = 4;
//		int z2 = z1 + 8;
//		int yBottom = y1 + 4;
//		int yTop = y2;
//		
//		// supports
//		chunk.setBlocks(x1 + 1, x1 + 3, y1, yBottom, z1 + 1, z1 + 3, buildingId);
//		chunk.setBlocks(x1 + 1, x1 + 3, y1, yBottom, z2 - 3, z2 - 1, buildingId);
//		chunk.setBlocks(x2 - 3, x2 - 1, y1, yBottom, z1 + 1, z1 + 3, buildingId);
//		chunk.setBlocks(x2 - 3, x2 - 1, y1, yBottom, z2 - 3, z2 - 1, buildingId);
//		
//		// bottom bit
//		chunk.setBlocks(x1, x2, yBottom, yBottom + 1, z1, z2, buildingId);
//		
//		// walls
//		chunk.setBlocks(x1, x2, yBottom + 1, yTop, z1 - 1, z1, buildingId);
//		chunk.setBlocks(x1, x2, yBottom + 1, yTop, z2    , z2 + 1, buildingId);
//		chunk.setBlocks(x1 - 1, x1, yBottom + 1, yTop, z1, z2, buildingId);
//		chunk.setBlocks(x2    , x2 + 1, yBottom + 1, yTop, z1, z2, buildingId);
//		
//		// make it so we can see in a bit
//		chunk.setBlocks(x1 + 3, x2 - 3, yBottom + 1, yTop, z1 - 1, z1, glassId);
//		chunk.setBlocks(x1 + 3, x2 - 3, yBottom + 1, yTop, z2    , z2 + 1, glassId);
//		chunk.setBlocks(x1 - 1, x1, yBottom + 1, yTop, z1 + 3, z2 - 3, glassId);
//		chunk.setBlocks(x2    , x2 + 1, yBottom + 1, yTop, z1 + 3, z2 - 3, glassId);
//		
//		// put a top on it
//		chunk.setBlocks(x1, x2, yTop, yTop + 1, z1, z2, buildingId);
//		
//		// fill it
//		chunk.setBlocks(x1, x2, yBottom + 1, yBottom + ((yTop - yBottom) / 3) * 2, z1, z2, waterId);

//		chunk.setBlocks(x1, y1, y1 + 10, z1, Material.LAPIS_BLOCK);
	}

	private void decoratePyramidBuilding(WorldGenerator generator, ContextData context, RealChunk chunk, int y1, int y2) {
		generateTreat(generator, context, chunk, 3, y1, 3);
		generateTreat(generator, context, chunk, 12, y1, 12);
		
		generateTrick(generator, context, chunk, 12, y1, 3);
		generateTrick(generator, context, chunk, 3, y1, 12);
	}
	
	private void generateTreat(WorldGenerator generator, ContextData context, RealChunk chunk, int x, int y, int z) {
		
		// cool stuff?
		if (generator.settings.treasuresInBunkers && chunkRandom.nextDouble() <= context.oddsOfTreasureInBunkers) {
			 chunk.setChest(x, y, z, Direction.Chest.NORTH, generator.getLootProvider().getItems(generator, LootProvider.chestInBunkers));
		}
	}

	private void generateTrick(WorldGenerator generator, ContextData context, RealChunk chunk, int x, int y, int z) {

		// not so cool stuff?
		if (generator.settings.spawnersInBunkers && chunkRandom.nextDouble() <= context.oddsOfSpawnerInBunkers) {
			chunk.setSpawner(x, y, z, generator.getSpawnProvider().getEntity(generator, SpawnProvider.spawnerInBunkers));
		}
	}
}
