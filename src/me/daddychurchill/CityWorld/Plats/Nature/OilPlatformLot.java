package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.Material;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.Tekkit.TekkitMaterial;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class OilPlatformLot extends ConstructLot {

	public OilPlatformLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		trulyIsolated = true;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new OilPlatformLot(platmap, chunkX, chunkZ);
	}

	private final static byte platformId = (byte) Material.DOUBLE_STEP.getId();
	private final static byte slabId = (byte) Material.STEP.getId();
	private final static byte railingId = (byte) Material.IRON_FENCE.getId();
	private final static byte drillId = (byte) Material.NETHER_FENCE.getId();
	private final static byte supportId = (byte) Material.NETHER_BRICK.getId();
	private final static byte topperId = (byte) Material.NETHER_BRICK_STAIRS.getId();
	
	//tekkit materials
	private final static byte oilId = (byte) TekkitMaterial.STATIONARY_OIL;
	
	private final static int aboveSea = 6;

	@Override
	public int getBottomY(WorldGenerator generator) {
		return generator.seaLevel + aboveSea;
	}
	
	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		
		// working levels
		int y0 = generator.seaLevel;
		int y1 = y0 + aboveSea;
		int y2 = y1 + DataContext.FloorHeight;
		int y3 = y2 + DataContext.FloorHeight;
		int y4 = y3 + DataContext.FloorHeight;
		byte emptyId = getAirId(generator, y1);
		
		// access levels
		chunk.setBlocks(2, 6, y0, y0 + 1, 2, 6, platformId);
		chunk.setBlocks(10, 14, y0, y0 + 1, 10, 14, platformId);
		
		// lower level
		chunk.setLayer(y1, platformId);
		chunk.setWalls(0, 16, y1 + 1, y1 + 2, 0, 16, railingId);
		chunk.setBlocks(7, 9, y1, y1 + 1, 7, 9, emptyId);
		chunk.setWalls(6, 10, y1 + 1, y1 + 2, 6, 10, railingId);
		
		// upper level
		chunk.setLayer(y2, platformId);
		chunk.setWalls(0, 16, y2 + 1, y2 + 2, 0, 16, railingId);
		chunk.setBlocks(7, 9, y2, y2 + 1, 7, 9, emptyId);
		chunk.setWalls(6, 10, y2 + 1, y2 + 2, 6, 10, railingId);
		
		// put the balcony on top 
		chunk.setBlocks(2, 14, y3, y3 + 1, 2, 14, platformId);
		chunk.setWalls(2, 14, y3 + 1, y3 + 2, 2, 14, railingId);
		chunk.setBlocks(7, 9, y3, y3 + 1, 7, 9, emptyId);
		chunk.setWalls(6, 10, y3 + 1, y3 + 2, 6, 10, railingId);
		
		// drill head level
		chunk.setBlocks(6, 14, y4, y4 + 1, 6, 14, platformId);
		chunk.setWalls(6, 14, y4 + 1, y4 + 2, 6, 14, railingId);
		chunk.setBlocks(6, 9, y4, y4 + 2, 6, 9, emptyId);
		chunk.setBlocks(9, y3 + 1, y4 + 2, 6, supportId);
		chunk.setBlocks(6, y3 + 1, y4 + 2, 9, supportId);
		
		// drill head itself
		chunk.setBlock(9, y4 + 2, 6, platformId);
		chunk.setBlock(6, y4 + 2, 9, platformId);
		chunk.setBlock(7, y4, 7, slabId);
		chunk.setBlock(7, y4, 8, slabId);
		chunk.setBlock(8, y4, 7, slabId);
		chunk.setBlock(9, y4 + 3, 6, slabId);
		chunk.setBlock(9, y4 + 3, 7, slabId);
		chunk.setBlock(9, y4 + 3, 8, slabId);
		chunk.setBlock(6, y4 + 3, 9, slabId);
		chunk.setBlock(7, y4 + 3, 9, slabId);
		chunk.setBlock(8, y4 + 3, 9, slabId);
		chunk.setBlock(8, y4 + 3, 8, slabId);

		// two big legs to hold up the various levels (a little bit deeper than needed, just to be safe)
		chunk.setBlocks(2, 4, minHeight - 10, y4, 2, 4, supportId);
		chunk.setBlocks(12, 14, minHeight - 10, y4 + 3, 12, 14, supportId);
		
		// two lesser legs to help the other two
		chunk.setBlocks(2, 4, minHeight - 10, y3, 12, 14, supportId);
		chunk.setBlocks(12, 14, minHeight - 10, y3, 2, 4, supportId);
		chunk.setBlocks(13, y3, y3 + 2, 2, supportId);
		chunk.setBlocks(2, y3, y3 + 2, 13, supportId);
		
		// drill down
		if (generator.settings.includeTekkitMaterials && minHeight > 20) { //place a blob of oil if it's a tekkit server (tekkit support by gunre)
			int oilBlobYFloor = chunkOdds.getRandomInt(10) + 2;
			chunk.setBlocks(5, 11, oilBlobYFloor + 1, oilBlobYFloor + 7, 5, 11, oilId);
			chunk.setBlocks(6, 10, oilBlobYFloor, oilBlobYFloor + 8, 6, 10, oilId);
			chunk.setBlocks(6, 10, oilBlobYFloor + 2, oilBlobYFloor + 6, 4, 12, oilId);
			chunk.setBlocks(4, 12, oilBlobYFloor + 2, oilBlobYFloor + 6, 6, 10, oilId);
			chunk.setBlocks(8, oilBlobYFloor, minHeight, 8, oilId);
			chunk.setBlocks(8, minHeight, y4 + 3, 8, drillId);
		} else {
			chunk.setBlocks(8, 1, y4 + 3, 8, drillId); 
		}
		
		// extra drill bits
		chunk.setBlocks(5, y2 + 2, y3 + 2, 1, drillId);
		chunk.setBlocks(7, y2 + 2, y3 + 2, 1, drillId);
		//chunk.setBlocks(9, y2 + 2, y3 + 2, 1, drillId);
		chunk.setBlocks(11, y2 + 2, y3 + 2, 1, drillId);
		chunk.setBlocks(13, y4 + 4, y4 + 8, 2, drillId); // bit hanging from the crane
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {

		// working levels
		int y0 = generator.seaLevel;
		int y1 = y0 + aboveSea;
		int y2 = y1 + DataContext.FloorHeight;
		int y3 = y2 + DataContext.FloorHeight;
		int y4 = y3 + DataContext.FloorHeight;
		
		// ladder from access level to the balcony
		chunk.setLadder(3, y0 + 1, y4 - 2, 4, Direction.General.SOUTH);
		chunk.setLadder(12, y0 + 1, y4 + 2, 11, Direction.General.NORTH);
		
		// now draw the crane
		chunk.setStair(2, y4 - 2, 2, topperId, Direction.Stair.EAST);
		chunk.setStair(2, y4 - 2, 3, topperId, Direction.Stair.EAST);
		chunk.clearBlock(2, y4 - 1, 2);
		chunk.clearBlock(2, y4 - 1, 3);
		chunk.setStair(3, y4 - 1, 3, topperId, Direction.Stair.NORTH);
		chunk.drawCrane(context, chunkOdds, 3, y4, 2);
		
		// bleed off
		chunk.setBlocks(13, y4 + 3, y4 + 8, 13, Material.IRON_FENCE);
		chunk.setBlock(13, y4 + 8, 13, Material.NETHERRACK);
		chunk.setBlock(13, y4 + 9, 13, Material.FIRE);

		// it looked so nice for a moment... but the moment has passed
		if (generator.settings.includeDecayedBuildings) {

			// do we take out a bit of it?
			decayEdge(generator, chunk.getBlockX(7) + chunkOdds.getRandomInt(3) - 1, y1, chunk.getBlockZ(0) + chunkOdds.getRandomInt(2));
			decayEdge(generator, chunk.getBlockX(7) + chunkOdds.getRandomInt(3) - 1, y2, chunk.getBlockZ(0) + chunkOdds.getRandomInt(2));
			decayEdge(generator, chunk.getBlockX(8) + chunkOdds.getRandomInt(3) - 1, y1, chunk.getBlockZ(15) - chunkOdds.getRandomInt(2));
			decayEdge(generator, chunk.getBlockX(8) + chunkOdds.getRandomInt(3) - 1, y2, chunk.getBlockZ(15) - chunkOdds.getRandomInt(2));
			decayEdge(generator, chunk.getBlockX(0) + chunkOdds.getRandomInt(2), y1, chunk.getBlockZ(7) + chunkOdds.getRandomInt(3) - 1);
			decayEdge(generator, chunk.getBlockX(0) + chunkOdds.getRandomInt(2), y2, chunk.getBlockZ(7) + chunkOdds.getRandomInt(3) - 1);
			decayEdge(generator, chunk.getBlockX(15) - chunkOdds.getRandomInt(2), y1, chunk.getBlockZ(8) + chunkOdds.getRandomInt(3) - 1);
			decayEdge(generator, chunk.getBlockX(15) - chunkOdds.getRandomInt(2), y2, chunk.getBlockZ(8) + chunkOdds.getRandomInt(3) - 1);
			decayEdge(generator, chunk.getBlockX(7), y4, chunk.getBlockZ(12));
		}
//TODO destroy it a little bit
//			// world centric view of blocks
//			WorldBlocks blocks = new WorldBlocks(generator);
//			
//			// what is the top floor?
//			int floors = height;
//			if (craned)
//				floors--;
//			
//			// work our way up
//			for (int floor = 1; floor < floors; floor++) {
//				
//				// do only floors that aren't top one or do the top one if there isn't a crane
//				int y = generator.sidewalkLevel + FloorHeight * floor + 1;
//					
//				// do we take out a bit of it?
//				decayEdge(blocks, chunk.getBlockX(7) + chunkRandom.nextInt(3) - 1, y, chunk.getBlockZ(inset));
//				decayEdge(blocks, chunk.getBlockX(8) + chunkRandom.nextInt(3) - 1, y, chunk.getBlockZ(chunk.width - inset - 1));
//				decayEdge(blocks, chunk.getBlockX(inset), y, chunk.getBlockZ(7) + chunkRandom.nextInt(3) - 1);
//				decayEdge(blocks, chunk.getBlockX(chunk.width - inset - 1), y, chunk.getBlockZ(8) + chunkRandom.nextInt(3) - 1);
//			}
	}

	private final static double decayedEdgeOdds = 0.25;
	
	private void decayEdge(WorldGenerator generator, int x, int y, int z) {
		if (chunkOdds.playOdds(decayedEdgeOdds))
			
			// make it go away
			generator.decayBlocks.desperseArea(x, y, z, chunkOdds.getRandomInt(2) + 2);
	}
}
