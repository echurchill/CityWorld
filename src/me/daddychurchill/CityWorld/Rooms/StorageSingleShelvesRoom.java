package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class StorageSingleShelvesRoom extends StorageRoom {

	public StorageSingleShelvesRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {
		int offset;
		switch (sideWithWall) {
		default:
		case NORTH:
		case SOUTH:
			offset = odds.getRandomInt(width);
			drawNSEmptyShelve(chunk, x + offset, y, z, height - 1, depth);
			break;
		case WEST:
		case EAST:
			offset = odds.getRandomInt(depth);
			drawWEEmptyShelve(chunk, x, y, z + offset, height - 1, width);
			break;
		}
	}

}
