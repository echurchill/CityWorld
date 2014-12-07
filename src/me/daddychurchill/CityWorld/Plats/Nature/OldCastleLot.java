package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ShortChunk;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class OldCastleLot extends ConstructLot {

	public OldCastleLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		trulyIsolated = true;

		//platmap.generator.reportMessage("CASTLE AT " + (chunkX * 16) + ", " + (chunkZ * 16));
	}
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new OldCastleLot(platmap, chunkX, chunkZ);
	}
	
	private final static Material platformMaterial = Material.SMOOTH_BRICK;
	private final static Material supportMaterial = Material.COBBLESTONE;
	private final static Material wallMaterial = platformMaterial;
	
	@Override
	public int getBottomY(WorldGenerator generator) {
		return blockYs.maxHeight - 1;
	}
	
	@Override
	public int getTopY(WorldGenerator generator) {
		return getBottomY(generator) + DataContext.FloorHeight * 4 + 1;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ShortChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		
		// main bits
		int floorHeight = DataContext.FloorHeight;
		int y1 = blockYs.minHeight + ((blockYs.maxHeight - blockYs.minHeight) / 3 * 2);
		int y2 = y1 + floorHeight;
		int y3 = y2 + floorHeight;
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();
		
		// random bits
		int secondX1 = 2 + chunkOdds.getRandomInt(3);
		int secondZ1 = 2 + chunkOdds.getRandomInt(3);
		int thirdX1 = chunkOdds.flipCoin() ? secondX1 : secondX1 + 4;
		int thirdZ1 = chunkOdds.flipCoin() ? secondZ1 : secondZ1 + 4;
		
		// legs
//		chunk.setBlocks(2, 4, minHeight, maxHeight - 1, 2, 4, supportId);
//		chunk.setBlocks(2, 4, minHeight, maxHeight - 1, 7, 9, supportId);
//		chunk.setBlocks(2, 4, minHeight, maxHeight - 1, 12, 14, supportId);
//		chunk.setBlocks(7, 9, minHeight, maxHeight - 1, 2, 4, supportId);
//		//chunk.setBlocks(7, 9, minHeight, maxHeight - 1, 7, 9, supportId);
//		chunk.setBlocks(7, 9, minHeight, maxHeight - 1, 12, 14, supportId);
//		chunk.setBlocks(12, 14, minHeight, maxHeight - 1, 2, 4, supportId);
//		chunk.setBlocks(12, 14, minHeight, maxHeight - 1, 7, 9, supportId);
//		chunk.setBlocks(12, 14, minHeight, maxHeight - 1, 12, 14, supportId);

		// platform
		chunk.setWalls(2, 14, blockYs.minHeight, y1 - 2, 2, 14, supportMaterial);
		for (int i = 4; i < 11; i += 3) {
			chunk.setBlocks(i, i + 2, blockYs.minHeight, y1 - 2, 1, 2, supportMaterial);
			chunk.setBlocks(i, i + 2, blockYs.minHeight, y1 - 2, 14, 15, supportMaterial);
			chunk.setBlocks(1, 2, blockYs.minHeight, y1 - 2, i, i + 2, supportMaterial);
			chunk.setBlocks(14, 15, blockYs.minHeight, y1 - 2, i, i + 2, supportMaterial);
		}
		chunk.setBlocks(1, 15, y1 - 2, 1, 15, supportMaterial);
		
		// clear things out a bit
		chunk.setBlocks(0, 16, y1, blockYs.maxHeight + 2, 0, 16, getAirMaterial(generator, y1));
		
		// add the first layer
		chunk.setLayer(y1 - 1, platformMaterial);
		chunk.setWalls(0, 11, y1, y2, 0, 6, wallMaterial);
		chunk.setWalls(10, 16, y1, y2, 0, 11, wallMaterial);
		chunk.setWalls(5, 16, y1, y2, 10, 16, wallMaterial);
		chunk.setWalls(0, 6, y1, y2, 5, 16, wallMaterial);
		chunk.setWalls(0, 16, y2, y2 + 1, 0, 16, wallMaterial);
		chunk.setBlocks(1, 15, y2, 1, 15, platformMaterial);
		chunk.setWalls(0, 16, y2 + 1, y2 + 2, 0, 16, supportMaterial);
		
		// add trim
		for (int i = 0; i < 13; i += 3) {
			chunk.setBlock(i, y2 + 2, 0, supportMaterial);
			chunk.setBlock(15 - i, y2 + 2, 15, supportMaterial);
			chunk.setBlock(15, y2 + 2, i, supportMaterial);
			chunk.setBlock(0, y2 + 2, 15 - i, supportMaterial);
		}
		
		// add retaining walls if needed
		for (int i = 0; i < 15; i++) {
			buildWall(chunk, i, y2 + 2, generator.getFarBlockY(originX + i, originZ - 1), 0);
			buildWall(chunk, i, y2 + 2, generator.getFarBlockY(originX + i, originZ + 16), 15);
			buildWall(chunk, 0, y2 + 2, generator.getFarBlockY(originX - 1, originZ + i), i);
			buildWall(chunk, 15, y2 + 2, generator.getFarBlockY(originX + 16, originZ + i), i);
		}
		
		// punch out the doors
		punchOutNSDoor(generator, chunk, 10, y1, 2);
		punchOutNSDoor(generator, chunk, 5, y1, 7);
		punchOutNSDoor(generator, chunk, 10, y1, 7);
		punchOutNSDoor(generator, chunk, 5, y1, 12);
		punchOutWEDoor(generator, chunk, 2, y1, 5);
		punchOutWEDoor(generator, chunk, 7, y1, 5);
		punchOutWEDoor(generator, chunk, 7, y1, 10);
		punchOutWEDoor(generator, chunk, 12, y1, 10);
		
		// add second level
		buildTower(generator, chunk, secondX1, y2, secondZ1, 9);
		
		// add third level
		buildTower(generator, chunk, thirdX1, y3, thirdZ1, 5);
	}
	
	private void buildWall(ShortChunk chunk, int x, int y1, int y2, int z) {
		if (y2 > y1)
			chunk.setBlocks(x, y1, y2 + 2, z, supportMaterial);
	}
	
	private void buildTower(WorldGenerator generator, ShortChunk chunk, int x, int y1, int z, int width) {
		int y2 = y1 + DataContext.FloorHeight;
		chunk.setWalls(x, x + width, y1 + 1, y2 + 1, z, z + width, wallMaterial);
		chunk.setBlocks(x + 1, x + width - 1, y2, z + 1, z + width - 1, platformMaterial);

		// add trim
		chunk.setWalls(x, x + width, y2 + 1, y2 + 2, z, z + width, supportMaterial);
		for (int i = 0; i < width - 1; i += 2) {
			chunk.setBlock(x + i, y2 + 2, z, supportMaterial);
			chunk.setBlock(x + width - 1 - i, y2 + 2, z + width - 1, supportMaterial);
			chunk.setBlock(x, y2 + 2, z + width - 1 - i, supportMaterial);
			chunk.setBlock(x + width - 1, y2 + 2, z + i, supportMaterial);
			
			// windows
			if (i > 0) {
				punchOutWindow(generator, chunk, x + i, y1 + 2, z);
				punchOutWindow(generator, chunk, x + width - 1 - i, y1 + 2, z + width - 1);
				punchOutWindow(generator, chunk, x, y1 + 2, z + width - 1 - i);
				punchOutWindow(generator, chunk, x + width - 1, y1 + 2, z + i);
			}
		}
	}
	
	private void punchOutWindow(WorldGenerator generator, ShortChunk chunk, int x, int y, int z) {
		if (chunkOdds.flipCoin())
			chunk.setBlocks(x, y, y + 1 + chunkOdds.getRandomInt(2), z, getAirMaterial(generator, y));
	}
	
	private void punchOutNSDoor(WorldGenerator generator, ShortChunk chunk, int x, int y, int z) {
		if (chunkOdds.flipCoin())
			chunk.setBlocks(x, x + 1, y, y + 3, z, z + 2, getAirMaterial(generator, y));
	}
	
	private void punchOutWEDoor(WorldGenerator generator, ShortChunk chunk, int x, int y, int z) {
		if (chunkOdds.flipCoin())
			chunk.setBlocks(x, x + 2, y, y + 3, z, z + 1, getAirMaterial(generator, y));
	}
	
	private static int insetChaos = 3;
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		reportLocation(generator, "Castle", chunk.getOriginX(), chunk.getOriginZ());
		
		// main bits
		int floorHeight = DataContext.FloorHeight;
		int y1 = blockYs.minHeight + ((blockYs.maxHeight - blockYs.minHeight) / 3 * 2);
		int y2 = y1 + floorHeight;
		int y3 = y2 + floorHeight;
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();
		
		// random bits
//		int secondX1 = 2 + chunkRandom.nextInt(3);
//		int secondZ1 = 2 + chunkRandom.nextInt(3);
//		int thirdX1 = chunkRandom.nextBoolean() ? secondX1 : secondX1 + 4;
//		int thirdZ1 = chunkRandom.nextBoolean() ? secondZ1 : secondZ1 + 4;
		
		// ex-castle
		generator.decayBlocks.destroyWithin(originX + insetChaos, originX + 16 - insetChaos, y1, y3, originZ + insetChaos, originZ + 16 - insetChaos);
//		destroyLot(generator, y1, y3 + floorHeight);
	}
}
