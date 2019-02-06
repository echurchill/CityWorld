package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class StorageDoubleShelvesRoom extends StorageRoom {

	public StorageDoubleShelvesRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		default:
		case NORTH:
			drawNSEmptyShelves(chunk, x, y, z, width, height - 1, depth, 0);
			break;
		case SOUTH:
			drawNSEmptyShelves(chunk, x, y, z, width, height - 1, depth, depth - 1);
			break;
		case EAST:
			drawWEEmptyShelves(chunk, x, y, z, width, height - 1, depth, width - 1);
			break;
		case WEST:
			drawWEEmptyShelves(chunk, x, y, z, width, height - 1, depth, 0);
			break;
		}
	}

	private void drawNSEmptyShelves(RealBlocks chunk, int x, int y, int z, int width, int height, int depth, int i) {
		for (int offset = 0; offset < width; offset += 2) {
			drawNSEmptyShelve(chunk, x + offset, y, z, height, depth);
		}
	}

	private void drawWEEmptyShelves(RealBlocks chunk, int x, int y, int z, int width, int height, int depth, int i) {
		for (int offset = 0; offset < depth; offset += 2) {
			drawWEEmptyShelve(chunk, x, y, z + offset, height, width);
		}
	}
}
