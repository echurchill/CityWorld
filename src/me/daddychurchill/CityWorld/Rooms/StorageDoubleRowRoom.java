package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;

public class StorageDoubleRowRoom extends StorageSingleRowRoom {

	public StorageDoubleRowRoom(Material type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(WorldGenerator generator, RealChunk chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		case SOUTH:
			drawNSMaterialShelves(chunk, odds, x, y, z, width, height, depth, depth - 1);
			break;
		case NORTH:
			drawNSMaterialShelves(chunk, odds, x, y, z, width, height, depth, 0);
			break;
		case EAST:
			drawWEMaterialShelves(chunk, odds, x, y, z, width, height, depth, width - 1);
			break;
		case WEST:
			drawWEMaterialShelves(chunk, odds, x, y, z, width, height, depth, 0);
			break;
		}
	}
	
	public void drawNSMaterialShelves(RealChunk chunk, Odds odds, int x, int y, int z, int width, int height, int depth, int i) {
		int minheight = odds.getRandomInt(height - 1);
		for (int offset = 0; offset < width; offset += 2) {
			drawNSEmptyShelve(chunk, x + offset, y, z, 1, depth);
			for (int run = 0; run < depth; run++)
				chunk.setBlocks(x + offset, y + 1, y + 1 + Math.max(minheight, odds.getRandomInt(height - 1)), z + run, materialType);
		}
	}
	
	public void drawWEMaterialShelves(RealChunk chunk, Odds odds, int x, int y, int z, int width, int height, int depth, int i) {
		int minheight = odds.getRandomInt(height - 1);
		for (int offset = 0; offset < depth; offset += 2) {
			drawWEEmptyShelve(chunk, x, y, z + offset, 1, width);
			for (int run = 0; run < depth; run++)
				chunk.setBlocks(x + run, y + 1, y + 1 + Math.max(minheight, odds.getRandomInt(height - 1)), z + offset, materialType);
		}
	}

}
