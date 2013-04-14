package me.daddychurchill.CityWorld.Buildings.Rooms;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;

public class StorageDoubleBooksRoom extends StorageSingleBooksRoom {

	public StorageDoubleBooksRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(WorldGenerator generator, RealChunk chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		case SOUTH:
			drawNSBookShelves(chunk, odds, x, y, z, width, height, depth, depth - 1);
			break;
		case NORTH:
			drawNSBookShelves(chunk, odds, x, y, z, width, height, depth, 0);
			break;
		case EAST:
			drawWEBookShelves(chunk, odds, x, y, z, width, height, depth, width - 1);
			break;
		case WEST:
			drawWEBookShelves(chunk, odds, x, y, z, width, height, depth, 0);
			break;
		}
	}
	
	public void drawNSBookShelves(RealChunk chunk, Odds odds, int x, int y, int z, int width, int height, int depth, int i) {
		int minheight = odds.getRandomInt(height - 1);
		for (int offset = 0; offset < width; offset += 2) {
			drawNSEmptyShelve(chunk, x + offset, y, z, 1, depth);
			for (int run = 0; run < depth; run++)
				chunk.setBlocks(x + offset, y + 1, y + 1 + Math.max(minheight, odds.getRandomInt(height - 1)), z + run, Material.BOOKSHELF);
		}
	}
	
	public void drawWEBookShelves(RealChunk chunk, Odds odds, int x, int y, int z, int width, int height, int depth, int i) {
		int minheight = odds.getRandomInt(height - 1);
		for (int offset = 0; offset < depth; offset += 2) {
			drawWEEmptyShelve(chunk, x, y, z + offset, 1, width);
			for (int run = 0; run < depth; run++)
				chunk.setBlocks(x + run, y + 1, y + 1 + Math.max(minheight, odds.getRandomInt(height - 1)), z + offset, Material.BOOKSHELF);
		}
	}

}
