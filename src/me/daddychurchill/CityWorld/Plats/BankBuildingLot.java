package me.daddychurchill.CityWorld.Plats;

import org.bukkit.Material;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SurroundingLots;

public class BankBuildingLot extends FinishedBuildingLot {

	private double oddsOfChests;
	
	public BankBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		height = height < 3 ? height = 3 : height;
		firstFloorHeight = firstFloorHeight * 2;
		oddsOfChests = chunkRandom.nextDouble();
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {
		super.generateActualBlocks(generator, platmap, chunk, context, platX, platZ);
		
		// look around
		SurroundingLots neighbors = new SurroundingLots(platmap, platX, platZ);
		
		// are we in the middle of the building?
		if (neighbors.getCompleteNeighborCount() > 7) {
			int y1 = generator.streetLevel + 1;
			int y2 = generator.streetLevel + firstFloorHeight + 1;
			chunk.setBlocks(2, 14, y1, y2, 2, 14, Material.OBSIDIAN);
			chunk.setBlocks(4, 12, y1 + 1, y2 - 2, 4, 12, Material.AIR);
			
			chunk.setBlock(5, y2 - 2, 5, context.lightMat);
			chunk.setBlock(5, y2 - 2, 10, context.lightMat);
			chunk.setBlock(10, y2 - 2, 5, context.lightMat);
			chunk.setBlock(10, y2 - 2, 10, context.lightMat);

			chunk.setTable(7, 9, y1 + 1, 7, 9, Material.STONE_PLATE);
			
			chunk.setWalls(4, 12, y1 + 2, y1 + 3, 4, 12, Material.STEP.getId(), (byte) 8);
			
			switch (chunkRandom.nextInt(4)) {
			case 1: // north
				chunk.setBlocks(7, 9, y1 + 1, y1 + 3, 2, 5, Material.AIR);
				chunk.setIronDoor(7, y1 + 1, 3, Direction.Door.NORTHBYNORTHEAST);
				chunk.setIronDoor(8, y1 + 1, 3, Direction.Door.NORTHBYNORTHWEST);
				
				createWEChests(generator, chunk, 4, y1 + 1, Direction.General.EAST);
				createWEChests(generator, chunk, 11, y1 + 1, Direction.General.WEST);
				createNSChests(generator, chunk, y1 + 1, 11, Direction.General.NORTH);
				break;
			case 2: // south
				chunk.setBlocks(7, 9, y1 + 1, y1 + 3, 11, 14, Material.AIR);
				chunk.setIronDoor(7, y1 + 1, 12, Direction.Door.SOUTHBYSOUTHEAST);
				chunk.setIronDoor(8, y1 + 1, 12, Direction.Door.SOUTHBYSOUTHWEST);

				createWEChests(generator, chunk, 4, y1 + 1, Direction.General.EAST);
				createWEChests(generator, chunk, 11, y1 + 1, Direction.General.WEST);
				createNSChests(generator, chunk, y1 + 1, 4, Direction.General.SOUTH);
				break;
			case 3: // west
				chunk.setBlocks(2, 5, y1 + 1, y1 + 3, 7, 9, Material.AIR);
				chunk.setIronDoor(3, y1 + 1, 7, Direction.Door.WESTBYSOUTHWEST);
				chunk.setIronDoor(3, y1 + 1, 8, Direction.Door.WESTBYNORTHWEST);
				
				createNSChests(generator, chunk, y1 + 1, 4, Direction.General.SOUTH);
				createNSChests(generator, chunk, y1 + 1, 11, Direction.General.NORTH);
				createWEChests(generator, chunk, 11, y1 + 1, Direction.General.WEST);
				break;
			default:// east
				chunk.setBlocks(11, 14, y1 + 1, y1 + 3, 7, 9, Material.AIR);
				chunk.setIronDoor(12, y1 + 1, 7, Direction.Door.EASTBYSOUTHEAST);
				chunk.setIronDoor(12, y1 + 1, 8, Direction.Door.EASTBYNORTHEAST);
				
				createNSChests(generator, chunk, y1 + 1, 4, Direction.General.SOUTH);
				createNSChests(generator, chunk, y1 + 1, 11, Direction.General.NORTH);
				createWEChests(generator, chunk, 4, y1 + 1, Direction.General.EAST);
				break;
			}
			
			chunk.setBlocks(0, y1, 200, 0, Material.BEDROCK);
		}
	}
	
	private void createNSChests(WorldGenerator generator, RealChunk chunk, int y, int z, Direction.General direction) {
		createNSChests(generator, chunk, 5, 11, y, z, direction);
		createNSChests(generator, chunk, 5, 11, y + 2, z, direction);
	}
	
	private void createNSChests(WorldGenerator generator, RealChunk chunk, int x1, int x2, int y, int z, Direction.General direction) {
		int runlength = 0;
		for (int x = x1; x < x2; x++) {
			
			// if we have done two in a row, reset runlength
			if (runlength == 2) {
				runlength = 0;
			} else if (chunkRandom.nextDouble() < oddsOfChests) {
				chunk.setChest(x, y, z, direction, generator.lootProvider.getItems(generator, chunkRandom, LootLocation.BANKVAULT));
				runlength++;
			} 
		}
	}
	
	private void createWEChests(WorldGenerator generator, RealChunk chunk, int x, int y, Direction.General direction) {
		createWEChests(generator, chunk, x, y, 5, 11, direction);
		createWEChests(generator, chunk, x, y + 2, 5, 11, direction);
	}
	
	private void createWEChests(WorldGenerator generator, RealChunk chunk, int x, int y, int z1, int z2, Direction.General direction) {
		int runlength = 0;
		for (int z = z1; z < z2; z++) {
			
			// if we have done two in a row, reset runlength
			if (runlength == 2) {
				runlength = 0;
			} else if (chunkRandom.nextDouble() < oddsOfChests) {
				chunk.setChest(x, y, z, direction, generator.lootProvider.getItems(generator, chunkRandom, LootLocation.BANKVAULT));
				runlength++;
			} 
		}
	}
	
//	private void setStorage(WorldGenerator generator, RealChunk chunk, int x, int y, int z, Direction.General direction) {
//		chunk.setChest(x, y, z, direction, generator.lootProvider.getItems(generator, chunkRandom, LootLocation.BANKVAULT));
//		setShelf(chunk, x, y, z);
//		chunk.setChest(x, y + 2, z, direction, generator.lootProvider.getItems(generator, chunkRandom, LootLocation.BANKVAULT));
//	}
	
//	private void setShelf(RealChunk chunk, int x, int y, int z) {
//		chunk.setBlock(x, y + 1, z, Material.STEP, (byte) 8);
//	}
}
