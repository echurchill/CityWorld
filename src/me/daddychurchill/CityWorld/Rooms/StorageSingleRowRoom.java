package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

import org.bukkit.Material;

public class StorageSingleRowRoom extends StorageTypeRoom {

	public StorageSingleRowRoom(Material type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		int offset;
		int minheight = odds.getRandomInt(height - 1);
		switch (sideWithWall) {
		case NORTH:
		case SOUTH:
			offset = odds.getRandomInt(width);
			drawNSEmptyShelve(chunk, x + offset, y, z, 1, depth);
			for (int run = 0; run < depth; run++)
				setStorageBlocks(generator, chunk, odds, x + offset, y + 1, y + 1 + Math.max(minheight, odds.getRandomInt(height - 1)), z + run);
			break;
		case WEST:
		case EAST:
			offset = odds.getRandomInt(depth);
			drawWEEmptyShelve(chunk, x, y, z + offset, 1, width);
			for (int run = 0; run < depth; run++)
				setStorageBlocks(generator, chunk, odds, x + run, y + 1, y + 1 + Math.max(minheight, odds.getRandomInt(height - 1)), z + offset);
			break;
		}
	}

}
