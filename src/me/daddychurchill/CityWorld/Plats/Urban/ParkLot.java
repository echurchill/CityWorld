package me.daddychurchill.CityWorld.Plats.Urban;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConnectedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.LigneousType;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SurroundingLots;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class ParkLot extends ConnectedLot {

	private final static int cisternDepth = DataContext.FloorHeight * 4;
	private final static int groundDepth = 2;
	
	private final static Material cisternMaterial = Material.CLAY;
	private final static Material fenceMaterial = Material.FENCE;
	private final static Material columnMaterial = Material.SMOOTH_BRICK;
	private final static Material pathMaterial = Material.SAND;
	private final static Material stepMaterial = Material.STEP;
	private final static Material ledgeMaterial = Material.CLAY;
	
	//TODO NW/SE quarter partial circle sidewalks
	//TODO pond inside of circle sidewalks instead of tree
	//TODO park benches
	//TODO gazebos
	
	private boolean circleSidewalk;
	private int waterDepth;
	
	public ParkLot(PlatMap platmap, int chunkX, int chunkZ, long globalconnectionkey) {
		super(platmap, chunkX, chunkZ);
		
		// all parks are interconnected
		connectedkey = globalconnectionkey;
		style = LotStyle.STRUCTURE;
		
		// pick a style
		circleSidewalk = chunkOdds.flipCoin();
		waterDepth = 1 + chunkOdds.getRandomInt(DataContext.FloorHeight * 2);
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new ParkLot(platmap, chunkX, chunkZ, connectedkey);
	}

	@Override
	protected boolean isShaftableLevel(WorldGenerator generator, int blockY) {
		return blockY >= 0 && blockY < generator.streetLevel - cisternDepth - 2 - 16;
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
		// other bits
		if (result && relative instanceof ParkLot) {
			ParkLot relativebuilding = (ParkLot) relative;

			// we don't card about circleSidewalk, that is supposed to be different
			waterDepth = relativebuilding.waterDepth;
		}
		return result;
	}
	
	@Override
	public int getBottomY(WorldGenerator generator) {
		return generator.streetLevel - cisternDepth + 1;
	}

	@Override
	public int getTopY(WorldGenerator generator) {
		return generator.streetLevel + DataContext.FloorHeight * 3 + 1;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {

		// look around
		SurroundingLots neighbors = new SurroundingLots(platmap, platX, platZ);
		
		// starting with the bottom
		int lowestY = getBottomY(generator);
		int highestY = generator.streetLevel - groundDepth - 1;
		
		// cistern?
		if (generator.settings.includeCisterns) {
			chunk.setLayer(lowestY, cisternMaterial);
			
			// fill with water
			lowestY++;
			if (generator.settings.includeAbovegroundFluids)
				chunk.setBlocks(0, chunk.width, lowestY, lowestY + waterDepth, 0, chunk.width, generator.oreProvider.fluidSurfaceMaterial);
			
			// clear out the rest
			chunk.setBlocks(0, chunk.width, lowestY + waterDepth, highestY + 1, 0, chunk.width, getAirMaterial(generator, lowestY + waterDepth));
			
			// outer columns and walls as needed
			if (neighbors.toNorth()) {
				chunk.setBlocks(3, 5, lowestY, highestY, 0, 1, cisternMaterial);
				chunk.setBlocks(11, 13, lowestY, highestY, 0, 1, cisternMaterial);
			} else
				chunk.setBlocks(0, 16, lowestY, highestY + 1, 0, 1, cisternMaterial);
			if (neighbors.toSouth()) {
				chunk.setBlocks(3, 5, lowestY, highestY, 15, 16, cisternMaterial);
				chunk.setBlocks(11, 13, lowestY, highestY, 15, 16, cisternMaterial);
			} else
				chunk.setBlocks(0, 16, lowestY, highestY + 1, 15, 16, cisternMaterial);
			if (neighbors.toWest()) {
				chunk.setBlocks(0, 1, lowestY, highestY, 3, 5, cisternMaterial);
				chunk.setBlocks(0, 1, lowestY, highestY, 11, 13, cisternMaterial);
			} else
				chunk.setBlocks(0, 1, lowestY, highestY + 1, 0, 16, cisternMaterial);
			if (neighbors.toEast()) {
				chunk.setBlocks(15, 16, lowestY, highestY, 3, 5, cisternMaterial);
				chunk.setBlocks(15, 16, lowestY, highestY, 11, 13, cisternMaterial);
			} else
				chunk.setBlocks(15, 16, lowestY, highestY + 1, 0, 16, cisternMaterial);
			
			// center columns
			chunk.setBlocks(7, 9, lowestY, highestY, 3, 5, cisternMaterial);
			chunk.setBlocks(7, 9, lowestY, highestY, 11, 13, cisternMaterial);
			chunk.setBlocks(3, 5, lowestY, highestY, 7, 9, cisternMaterial);
			chunk.setBlocks(11, 13, lowestY, highestY, 7, 9, cisternMaterial);
			
			// ceiling supports
			chunk.setBlocks(3, 5, highestY, highestY + 1, 0, 16, cisternMaterial);
			chunk.setBlocks(11, 13, highestY, highestY + 1, 0, 16, cisternMaterial);
			chunk.setBlocks(0, 16, highestY, highestY + 1, 3, 5, cisternMaterial);
			chunk.setBlocks(0, 16, highestY, highestY + 1, 11, 13, cisternMaterial);
	
			// top it off
			chunk.setLayer(highestY + 1, cisternMaterial);
		} else {
			
			// backfill with dirt
			chunk.setLayer(lowestY, highestY + 2 - lowestY, generator.oreProvider.subsurfaceMaterial);
		}
		
		// top it off
		chunk.setLayer(highestY + 2, generator.oreProvider.subsurfaceMaterial);
		chunk.setLayer(highestY + 3, generator.oreProvider.surfaceMaterial);
		
		// surface features
		int surfaceY = generator.streetLevel + 1;
		if (!neighbors.toNorth() && HeightInfo.isBuildableToNorth(generator, chunk)) {
			chunk.setBlocks(0, 6, surfaceY, surfaceY + 1, 0, 1, columnMaterial);
			chunk.setBlocks(0, 6, surfaceY + 1, surfaceY + 2, 0, 1, fenceMaterial);
			chunk.setBlocks(10, 16, surfaceY, surfaceY + 1, 0, 1, columnMaterial);
			chunk.setBlocks(10, 16, surfaceY + 1, surfaceY + 2, 0, 1, fenceMaterial);
			chunk.setBlocks(6, surfaceY, surfaceY + 2, 0, columnMaterial);
			chunk.setBlocks(7, 9, surfaceY, surfaceY + 1, 0, 1, stepMaterial);
			chunk.setBlocks(9, surfaceY, surfaceY + 2, 0, columnMaterial);
			chunk.setBlock(6, surfaceY, 1, columnMaterial);
			chunk.setBlock(9, surfaceY, 1, columnMaterial);
		}
		if (!neighbors.toSouth() && HeightInfo.isBuildableToSouth(generator, chunk)) {
			chunk.setBlocks(0, 6, surfaceY, surfaceY + 1, 15, 16, columnMaterial);
			chunk.setBlocks(0, 6, surfaceY + 1, surfaceY + 2, 15, 16, fenceMaterial);
			chunk.setBlocks(10, 16, surfaceY, surfaceY + 1, 15, 16, columnMaterial);
			chunk.setBlocks(10, 16, surfaceY + 1, surfaceY + 2, 15, 16, fenceMaterial);
			chunk.setBlocks(6, surfaceY, surfaceY + 2, 15, columnMaterial);
			chunk.setBlocks(7, 9, surfaceY, surfaceY + 1, 15, 16, stepMaterial);
			chunk.setBlocks(9, surfaceY, surfaceY + 2, 15, columnMaterial);
			chunk.setBlock(6, surfaceY, 14, columnMaterial);
			chunk.setBlock(9, surfaceY, 14, columnMaterial);
		}
		if (!neighbors.toWest() && HeightInfo.isBuildableToWest(generator, chunk)) {
			chunk.setBlocks(0, 1, surfaceY, surfaceY + 1, 0, 6, columnMaterial);
			chunk.setBlocks(0, 1, surfaceY + 1, surfaceY + 2, 0, 6, fenceMaterial);
			chunk.setBlocks(0, 1, surfaceY, surfaceY + 1, 10, 16, columnMaterial);
			chunk.setBlocks(0, 1, surfaceY + 1, surfaceY + 2, 10, 16, fenceMaterial);
			chunk.setBlocks(0, surfaceY, surfaceY + 2, 6, columnMaterial);
			chunk.setBlocks(0, 1, surfaceY, surfaceY + 1, 7, 9, stepMaterial);
			chunk.setBlocks(0, surfaceY, surfaceY + 2, 9, columnMaterial);
			chunk.setBlock(1, surfaceY, 6, columnMaterial);
			chunk.setBlock(1, surfaceY, 9, columnMaterial);
		}
		if (!neighbors.toEast() && HeightInfo.isBuildableToEast(generator, chunk)) {
			chunk.setBlocks(15, 16, surfaceY, surfaceY + 1, 0, 6, columnMaterial);
			chunk.setBlocks(15, 16, surfaceY + 1, surfaceY + 2, 0, 6, fenceMaterial);
			chunk.setBlocks(15, 16, surfaceY, surfaceY + 1, 10, 16, columnMaterial);
			chunk.setBlocks(15, 16, surfaceY + 1, surfaceY + 2, 10, 16, fenceMaterial);
			chunk.setBlocks(15, surfaceY, surfaceY + 2, 6, columnMaterial);
			chunk.setBlocks(15, 16, surfaceY, surfaceY + 1, 7, 9, stepMaterial);
			chunk.setBlocks(15, surfaceY, surfaceY + 2, 9, columnMaterial);
			chunk.setBlock(14, surfaceY, 6, columnMaterial);
			chunk.setBlock(14, surfaceY, 9, columnMaterial);
		} 
		
		// draw the sidewalks
		if (circleSidewalk) {
			chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 0, 3, pathMaterial);
			chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 13, 16, pathMaterial);
			chunk.setBlocks(0, 3, surfaceY - 1, surfaceY, 7, 9, pathMaterial);
			chunk.setBlocks(13, 16, surfaceY - 1, surfaceY, 7, 9, pathMaterial);
			chunk.setCircle(8, 8, 4, surfaceY - 1, pathMaterial, false);
			chunk.setCircle(8, 8, 3, surfaceY - 1, pathMaterial, false);
		} else {
			chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 0, 8, pathMaterial);
			chunk.setBlocks(7, 9, surfaceY - 1, surfaceY, 8, 16, pathMaterial);
			chunk.setBlocks(0, 8, surfaceY - 1, surfaceY, 7, 9, pathMaterial);
			chunk.setBlocks(8, 16, surfaceY - 1, surfaceY, 7, 9, pathMaterial);
		}
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		int surfaceY = generator.streetLevel + 1;
		
		// way down?
		if (generator.settings.includeCisterns) {
			SurroundingLots neighbors = new SurroundingLots(platmap, platX, platZ);
			if (!neighbors.toNorth() && HeightInfo.isBuildableToNorth(generator, chunk)) {
				int lowestY = generator.streetLevel - cisternDepth + 1 + waterDepth;
				chunk.setBlocks(4, 7, lowestY, lowestY + 1, 1, 2, ledgeMaterial);
				chunk.setLadder(5, lowestY + 1, surfaceY, 1, Direction.General.SOUTH);
				chunk.setTrapDoor(5, surfaceY, 1, Direction.TrapDoor.EAST);
			}
		}
		
		// sprinkle some trees
		if (circleSidewalk) {
			generator.coverProvider.generateTree(generator, chunk, 7, surfaceY, 7, LigneousType.TALL_OAK);
		
		// four smaller trees
		} else {
			LigneousType ligneousType = chunkOdds.flipCoin() ? LigneousType.BIRCH : LigneousType.OAK;
			generator.coverProvider.generateTree(generator, chunk, 3, surfaceY, 3, ligneousType);
			generator.coverProvider.generateTree(generator, chunk, 12, surfaceY, 3, ligneousType);
			generator.coverProvider.generateTree(generator, chunk, 3, surfaceY, 12, ligneousType);
			generator.coverProvider.generateTree(generator, chunk, 12, surfaceY, 12, ligneousType);
		}
	}
}
