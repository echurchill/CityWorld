package me.daddychurchill.CityWorld.Plats;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class OldCastleLot extends ConstructLot {

	public OldCastleLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		//platmap.generator.reportMessage("CASTLE AT " + (chunkX * 16) + ", " + (chunkZ * 16));
	}
	
	private final static byte platformId = (byte) Material.SMOOTH_BRICK.getId();
	private final static byte supportId = (byte) Material.COBBLESTONE.getId();
	private final static byte wallId = platformId;
	
	@Override
	public int getBottomY(WorldGenerator generator) {
		return maxHeight - 1;
	}
	
	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		
		// main bits
		int floorHeight = DataContext.FloorHeight;
		int y1 = minHeight + ((maxHeight - minHeight) / 3 * 2);
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
		chunk.setWalls(2, 14, minHeight, y1 - 2, 2, 14, supportId);
		for (int i = 4; i < 11; i += 3) {
			chunk.setBlocks(i, i + 2, minHeight, y1 - 2, 1, 2, supportId);
			chunk.setBlocks(i, i + 2, minHeight, y1 - 2, 14, 15, supportId);
			chunk.setBlocks(1, 2, minHeight, y1 - 2, i, i + 2, supportId);
			chunk.setBlocks(14, 15, minHeight, y1 - 2, i, i + 2, supportId);
		}
		chunk.setBlocks(1, 15, y1 - 2, 1, 15, supportId);
		
		// clear things out a bit
		chunk.setBlocks(0, 16, y1, maxHeight + 2, 0, 16, airId);
		
		// add the first layer
		chunk.setLayer(y1 - 1, platformId);
		chunk.setWalls(0, 11, y1, y2, 0, 6, wallId);
		chunk.setWalls(10, 16, y1, y2, 0, 11, wallId);
		chunk.setWalls(5, 16, y1, y2, 10, 16, wallId);
		chunk.setWalls(0, 6, y1, y2, 5, 16, wallId);
		chunk.setWalls(0, 16, y2, y2 + 1, 0, 16, wallId);
		chunk.setBlocks(1, 15, y2, 1, 15, platformId);
		chunk.setWalls(0, 16, y2 + 1, y2 + 2, 0, 16, supportId);
		
		// add trim
		for (int i = 0; i < 13; i += 3) {
			chunk.setBlock(i, y2 + 2, 0, supportId);
			chunk.setBlock(15 - i, y2 + 2, 15, supportId);
			chunk.setBlock(15, y2 + 2, i, supportId);
			chunk.setBlock(0, y2 + 2, 15 - i, supportId);
		}
		
		// add retaining walls if needed
		for (int i = 0; i < 15; i++) {
			buildWall(chunk, i, y2 + 2, generator.getFarBlockY(originX + i, originZ - 1), 0);
			buildWall(chunk, i, y2 + 2, generator.getFarBlockY(originX + i, originZ + 16), 15);
			buildWall(chunk, 0, y2 + 2, generator.getFarBlockY(originX - 1, originZ + i), i);
			buildWall(chunk, 15, y2 + 2, generator.getFarBlockY(originX + 16, originZ + i), i);
		}
		
		// punch out the doors
		punchOutNSDoor(chunk, 10, y1, 2);
		punchOutNSDoor(chunk, 5, y1, 7);
		punchOutNSDoor(chunk, 10, y1, 7);
		punchOutNSDoor(chunk, 5, y1, 12);
		punchOutWEDoor(chunk, 2, y1, 5);
		punchOutWEDoor(chunk, 7, y1, 5);
		punchOutWEDoor(chunk, 7, y1, 10);
		punchOutWEDoor(chunk, 12, y1, 10);
		
		// add second level
		buildTower(chunk, secondX1, y2, secondZ1, 9);
		
		// add third level
		buildTower(chunk, thirdX1, y3, thirdZ1, 5);
	}
	
	private void buildWall(ByteChunk chunk, int x, int y1, int y2, int z) {
		if (y2 > y1)
			chunk.setBlocks(x, y1, y2 + 2, z, supportId);
	}
	
	private void buildTower(ByteChunk chunk, int x, int y1, int z, int width) {
		int y2 = y1 + DataContext.FloorHeight;
		chunk.setWalls(x, x + width, y1 + 1, y2 + 1, z, z + width, wallId);
		chunk.setBlocks(x + 1, x + width - 1, y2, z + 1, z + width - 1, platformId);

		// add trim
		chunk.setWalls(x, x + width, y2 + 1, y2 + 2, z, z + width, supportId);
		for (int i = 0; i < width - 1; i += 2) {
			chunk.setBlock(x + i, y2 + 2, z, supportId);
			chunk.setBlock(x + width - 1 - i, y2 + 2, z + width - 1, supportId);
			chunk.setBlock(x, y2 + 2, z + width - 1 - i, supportId);
			chunk.setBlock(x + width - 1, y2 + 2, z + i, supportId);
			
			// windows
			if (i > 0) {
				punchOutWindow(chunk, x + i, y1 + 2, z);
				punchOutWindow(chunk, x + width - 1 - i, y1 + 2, z + width - 1);
				punchOutWindow(chunk, x, y1 + 2, z + width - 1 - i);
				punchOutWindow(chunk, x + width - 1, y1 + 2, z + i);
			}
		}
	}
	
	private void punchOutWindow(ByteChunk chunk, int x, int y, int z) {
		if (chunkOdds.flipCoin())
			chunk.setBlocks(x, y, y + 1 + chunkOdds.getRandomInt(2), z, airId);
	}
	
	private void punchOutNSDoor(ByteChunk chunk, int x, int y, int z) {
		if (chunkOdds.flipCoin())
			chunk.setBlocks(x, x + 1, y, y + 3, z, z + 2, airId);
	}
	
	private void punchOutWEDoor(ByteChunk chunk, int x, int y, int z) {
		if (chunkOdds.flipCoin())
			chunk.setBlocks(x, x + 2, y, y + 3, z, z + 1, airId);
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		
		// main bits
		int floorHeight = DataContext.FloorHeight;
		int y1 = minHeight + ((maxHeight - minHeight) / 3 * 2);
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
		generator.decayBlocks.destroyWithin(originX + 3, originX + 13, y1, y3, originZ + 3, originZ + 13);
//		destroyLot(generator, y1, y3 + floorHeight);
	}
}
