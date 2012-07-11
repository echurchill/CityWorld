package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction.Ladder;
import me.daddychurchill.CityWorld.Support.Direction.TrapDoor;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.SurroundingParks;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class PlatPark extends PlatConnected {

	protected final static int cisternDepth = ContextData.FloorHeight * 4;
	protected final static int groundDepth = 2;
	
	protected final static byte cisternId = (byte) Material.CLAY.getId();
	protected final static byte fenceId = (byte) Material.FENCE.getId();
	protected final static byte columnId = (byte) Material.SMOOTH_BRICK.getId();
	protected final static byte pathId = (byte) Material.SAND.getId();
	protected final static byte stepId = (byte) Material.STEP.getId();
	
//	protected final static Material ledgeMaterial = Material.IRON_BLOCK;
	protected final static Material ledgeMaterial = Material.CLAY;
	
	//TODO NW/SE quarter partial circle sidewalks
	//TODO pond inside of circle sidewalks instead of tree
	//TODO park benches
	//TODO gazebos
	
	private boolean circleSidewalk;
	private int waterDepth;
	
	public PlatPark(PlatMap platmap, int chunkX, int chunkZ, long globalconnectionkey) {
		super(platmap, chunkX, chunkZ);
		
		// all parks are interconnected
		connectedkey = globalconnectionkey;
		style = LotStyle.STRUCTURE;
		
		// pick a style
		circleSidewalk = chunkRandom.nextBoolean();
		waterDepth = chunkRandom.nextInt(ContextData.FloorHeight * 2) + 1;
	}

	@Override
	protected int getTopStrataY(WorldGenerator generator, int blockX, int blockZ) {
		return generator.sidewalkLevel - cisternDepth - 2 - 16;
	}

	@Override
	protected boolean isShaftableLevel(WorldGenerator generator, ContextData context, int y) {
		return y >= 0 && y < generator.sidewalkLevel - cisternDepth - 2 - 16;	
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
		// other bits
		if (result && relative instanceof PlatPark) {
			PlatPark relativebuilding = (PlatPark) relative;

			// we don't card about circleSidewalk, that is supposed to be different
			waterDepth = relativebuilding.waterDepth;
		}
		return result;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {

		// look around
		SurroundingParks neighbors = new SurroundingParks(platmap, platX, platZ);
		
		// starting with the bottom
		int lowestY = generator.sidewalkLevel - cisternDepth + 1;
		int highestY = generator.sidewalkLevel - groundDepth - 1;
		
		// cistern?
		if (generator.settings.includeCisterns) {
			chunk.setLayer(lowestY, cisternId);
			
			// fill with water
			lowestY++;
			chunk.setBlocks(0, chunk.width, lowestY, lowestY + waterDepth, 0, chunk.width, stillWaterId);
			
			// clear out the rest
			chunk.setBlocks(0, chunk.width, lowestY + waterDepth, highestY + 1, 0, chunk.width, airId);
			
			// outer columns and walls as needed
			if (neighbors.toNorth()) {
				chunk.setBlocks(3, 5, lowestY, highestY, 0, 1, cisternId);
				chunk.setBlocks(11, 13, lowestY, highestY, 0, 1, cisternId);
			} else
				chunk.setBlocks(0, 16, lowestY, highestY + 1, 0, 1, cisternId);
			if (neighbors.toSouth()) {
				chunk.setBlocks(3, 5, lowestY, highestY, 15, 16, cisternId);
				chunk.setBlocks(11, 13, lowestY, highestY, 15, 16, cisternId);
			} else
				chunk.setBlocks(0, 16, lowestY, highestY + 1, 15, 16, cisternId);
			if (neighbors.toWest()) {
				chunk.setBlocks(0, 1, lowestY, highestY, 3, 5, cisternId);
				chunk.setBlocks(0, 1, lowestY, highestY, 11, 13, cisternId);
			} else
				chunk.setBlocks(0, 1, lowestY, highestY + 1, 0, 16, cisternId);
			if (neighbors.toEast()) {
				chunk.setBlocks(15, 16, lowestY, highestY, 3, 5, cisternId);
				chunk.setBlocks(15, 16, lowestY, highestY, 11, 13, cisternId);
			} else
				chunk.setBlocks(15, 16, lowestY, highestY + 1, 0, 16, cisternId);
			
			// center columns
			chunk.setBlocks(7, 9, lowestY, highestY, 3, 5, cisternId);
			chunk.setBlocks(7, 9, lowestY, highestY, 11, 13, cisternId);
			chunk.setBlocks(3, 5, lowestY, highestY, 7, 9, cisternId);
			chunk.setBlocks(11, 13, lowestY, highestY, 7, 9, cisternId);
			
			// ceiling supports
			chunk.setBlocks(3, 5, highestY, highestY + 1, 0, 16, cisternId);
			chunk.setBlocks(11, 13, highestY, highestY + 1, 0, 16, cisternId);
			chunk.setBlocks(0, 16, highestY, highestY + 1, 3, 5, cisternId);
			chunk.setBlocks(0, 16, highestY, highestY + 1, 11, 13, cisternId);
	
			// top it off
			chunk.setLayer(highestY + 1, cisternId);
		} else {
			
			// backfill with dirt
			chunk.setLayer(lowestY, highestY + 2 - lowestY, dirtId);
		}
		
		// top it off
		chunk.setLayer(highestY + 2, dirtId);
		chunk.setLayer(highestY + 3, grassId);
		
		// surface features
		int surfaceY = generator.sidewalkLevel + 1;
		if (!neighbors.toNorth() && HeightInfo.isBuildableToNorth(generator, chunk)) {
			chunk.setBlocks(0, 6, surfaceY, surfaceY + 1, 0, 1, columnId);
			chunk.setBlocks(0, 6, surfaceY + 1, surfaceY + 2, 0, 1, fenceId);
			chunk.setBlocks(10, 16, surfaceY, surfaceY + 1, 0, 1, columnId);
			chunk.setBlocks(10, 16, surfaceY + 1, surfaceY + 2, 0, 1, fenceId);
			chunk.setBlocks(6, surfaceY, surfaceY + 2, 0, columnId);
			chunk.setBlocks(7, 9, surfaceY, surfaceY + 1, 0, 1, stepId);
			chunk.setBlocks(9, surfaceY, surfaceY + 2, 0, columnId);
			chunk.setBlock(6, surfaceY, 1, columnId);
			chunk.setBlock(9, surfaceY, 1, columnId);
		}
		if (!neighbors.toSouth() && HeightInfo.isBuildableToSouth(generator, chunk)) {
			chunk.setBlocks(0, 6, surfaceY, surfaceY + 1, 15, 16, columnId);
			chunk.setBlocks(0, 6, surfaceY + 1, surfaceY + 2, 15, 16, fenceId);
			chunk.setBlocks(10, 16, surfaceY, surfaceY + 1, 15, 16, columnId);
			chunk.setBlocks(10, 16, surfaceY + 1, surfaceY + 2, 15, 16, fenceId);
			chunk.setBlocks(6, surfaceY, surfaceY + 2, 15, columnId);
			chunk.setBlocks(7, 9, surfaceY, surfaceY + 1, 15, 16, stepId);
			chunk.setBlocks(9, surfaceY, surfaceY + 2, 15, columnId);
			chunk.setBlock(6, surfaceY, 14, columnId);
			chunk.setBlock(9, surfaceY, 14, columnId);
		}
		if (!neighbors.toWest() && HeightInfo.isBuildableToWest(generator, chunk)) {
			chunk.setBlocks(0, 1, surfaceY, surfaceY + 1, 0, 6, columnId);
			chunk.setBlocks(0, 1, surfaceY + 1, surfaceY + 2, 0, 6, fenceId);
			chunk.setBlocks(0, 1, surfaceY, surfaceY + 1, 10, 16, columnId);
			chunk.setBlocks(0, 1, surfaceY + 1, surfaceY + 2, 10, 16, fenceId);
			chunk.setBlocks(0, surfaceY, surfaceY + 2, 6, columnId);
			chunk.setBlocks(0, 1, surfaceY, surfaceY + 1, 7, 9, stepId);
			chunk.setBlocks(0, surfaceY, surfaceY + 2, 9, columnId);
			chunk.setBlock(1, surfaceY, 6, columnId);
			chunk.setBlock(1, surfaceY, 9, columnId);
		}
		if (!neighbors.toEast() && HeightInfo.isBuildableToEast(generator, chunk)) {
			chunk.setBlocks(15, 16, surfaceY, surfaceY + 1, 0, 6, columnId);
			chunk.setBlocks(15, 16, surfaceY + 1, surfaceY + 2, 0, 6, fenceId);
			chunk.setBlocks(15, 16, surfaceY, surfaceY + 1, 10, 16, columnId);
			chunk.setBlocks(15, 16, surfaceY + 1, surfaceY + 2, 10, 16, fenceId);
			chunk.setBlocks(15, surfaceY, surfaceY + 2, 6, columnId);
			chunk.setBlocks(15, 16, surfaceY, surfaceY + 1, 7, 9, stepId);
			chunk.setBlocks(15, surfaceY, surfaceY + 2, 9, columnId);
			chunk.setBlock(14, surfaceY, 6, columnId);
			chunk.setBlock(14, surfaceY, 9, columnId);
		} 
		
		// draw the sidewalks
		if (circleSidewalk) {
			chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 0, 3, pathId);
			chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 13, 16, pathId);
			chunk.setBlocks(0, 3, surfaceY - 1, surfaceY, 7, 9, pathId);
			chunk.setBlocks(13, 16, surfaceY - 1, surfaceY, 7, 9, pathId);
			chunk.setCircle(8, 8, 4, surfaceY - 1, pathId, false);
			chunk.setCircle(8, 8, 3, surfaceY - 1, pathId, false);
		} else {
			chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 0, 8, pathId);
			chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 8, 16, pathId);
			chunk.setBlocks(0, 8, surfaceY - 1, surfaceY, 7, 9, pathId);
			chunk.setBlocks(8, 16, surfaceY - 1, surfaceY, 7, 9, pathId);
		}
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		int surfaceY = generator.sidewalkLevel + 1;
		
		// way down?
		if (generator.settings.includeCisterns) {
			SurroundingParks neighbors = new SurroundingParks(platmap, platX, platZ);
			if (!neighbors.toNorth() && HeightInfo.isBuildableToNorth(generator, chunk)) {
				int lowestY = generator.sidewalkLevel - cisternDepth + 1 + waterDepth;
				chunk.setBlocks(4, 7, lowestY, lowestY + 1, 1, 2, ledgeMaterial);
				chunk.setLadder(5, lowestY + 1, surfaceY, 1, Ladder.SOUTH);
				chunk.setTrapDoor(5, surfaceY, 1, TrapDoor.EAST);
			}
		}
		
		// sprinkle some trees
		if (circleSidewalk) {
			generateTree(generator, chunk, 7, surfaceY, 7, TreeType.BIG_TREE);
		
		// four smaller trees
		} else {
			TreeType tree = chunkRandom.nextBoolean() ? TreeType.BIRCH : TreeType.TREE;
			generateTree(generator, chunk, 3, surfaceY, 3, tree);
			generateTree(generator, chunk, 12, surfaceY, 3, tree);
			generateTree(generator, chunk, 3, surfaceY, 12, tree);
			generateTree(generator, chunk, 12, surfaceY, 12, tree);
		}
	}
}
