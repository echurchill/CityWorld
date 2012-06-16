package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatBunker extends PlatNature {

	private final static int FloorHeight = ContextData.FloorHeight;
	
	public PlatBunker(Random random, PlatMap platmap) {
		super(random, platmap);
		// TODO Auto-generated constructor stub
	}
	
	// these MUST be given in chunk segment units (currently 16) 
	public final static int bunkerBelowStreet = 0;
	public final static int bunkerMinHeight = 16 * 2;

	@Override
	protected boolean isShaftableLevel(WorldGenerator generator, ContextData context, int y) {
		return (y < calcSegmentOrigin(generator.sidewalkLevel) - 32 - bunkerBelowStreet || y > calcBunkerCeiling(generator) - 32) &&
				super.isShaftableLevel(generator, context, y);	
//		return (y >= 0 && y < generator.sidewalkLevel - 16) ||
//				   (y >= generator.evergreenLevel + 16 && y < minHeight);	
	}
	
	private final static byte supportId = cobbleId;
	private final static byte platformId = sandstoneId;
	private final static byte crosswalkId = (byte) Material.WOOD.getId();
	private final static byte railingId = (byte) Material.IRON_FENCE.getId();
	private final static byte buildingId = (byte) Material.WOOL.getId();
	
	private int calcSegmentOrigin(int y) {
		return y / 16 * 16;
	}
	
	private int calcBunkerCeiling(WorldGenerator generator) {
		return Math.max(calcSegmentOrigin(generator.sidewalkLevel) + bunkerMinHeight - bunkerBelowStreet, calcSegmentOrigin(minHeight - FloorHeight));
	}

	@Override
	public void generateChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		generateCrust(generator, platmap, chunk, biomes, context, platX, platZ);
//		this.precomputeExtremes(generator, chunk);
		
		// precalculate
		int yBottom = calcSegmentOrigin(generator.sidewalkLevel) - bunkerBelowStreet;
		int yTop4 = calcBunkerCeiling(generator);
		int yTop3 = yTop4 - 2;
		int yTop2 = yTop4 - 16; // must be given in segment units (currently 16)
		int yTop1 = yTop2 - 2;
		int yPlatform = calcSegmentOrigin(yBottom) + 6;
		
		// bottom
		chunk.setBlocks(0, 16, yBottom, yBottom + 1, 0, 16, supportId);
		
		// clear out space
		chunk.setBlocks(0, 16, yBottom + 1, yTop3, 0, 16, airId);
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
		generateBuilding(chunk, 4, yPlatform + 1, yTop2 + 4, 4);
		
		// a mining we go
		generateMines(generator, chunk, context);
	}
	
	public void generateBuilding(ByteChunk chunk, int x, int y1, int y2, int z) {
		int y = y1;
		int Height = FloorHeight;
		while (y + Height < y2) {
			
			// walls please
			chunk.setBlocks(x, x + 8, y, y + Height - 1, z, z + 8, buildingId);
			
			// interspace
			chunk.setBlocks(x + 1, x + 7, y + Height - 1, y + Height, z + 1, z + 7, buildingId);
			
			y += Height;
			Height += FloorHeight;
		}
	}

	@Override
	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
//		super.generateBlocks(generator, platmap, chunk, context, platX, platZ);
		
		// a mining we go
		generateMines(generator, chunk, context);
	}

}
