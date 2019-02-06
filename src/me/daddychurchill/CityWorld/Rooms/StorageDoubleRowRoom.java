package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class StorageDoubleRowRoom extends StorageSingleRowRoom {

	public StorageDoubleRowRoom(Material type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		default:
		case NORTH:
			drawNSMaterialShelves(generator, chunk, odds, x, y, z, width, height, depth, 0);
			break;
		case SOUTH:
			drawNSMaterialShelves(generator, chunk, odds, x, y, z, width, height, depth, depth - 1);
			break;
		case EAST:
			drawWEMaterialShelves(generator, chunk, odds, x, y, z, width, height, depth, width - 1);
			break;
		case WEST:
			drawWEMaterialShelves(generator, chunk, odds, x, y, z, width, height, depth, 0);
			break;
		}
	}

	private void drawNSMaterialShelves(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int x, int y, int z,
			int width, int height, int depth, int i) {
		int minheight = odds.getRandomInt(height - 1);
		for (int offset = 0; offset < width; offset += 2) {
			drawNSEmptyShelve(chunk, x + offset, y, z, 1, depth);
			for (int run = 0; run < depth; run++)
				setStorageBlocks(generator, chunk, odds, x + offset, y + 1,
						y + 1 + Math.max(minheight, odds.getRandomInt(height - 1)), z + run);
		}
	}

	private void drawWEMaterialShelves(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int x, int y, int z,
			int width, int height, int depth, int i) {
		int minheight = odds.getRandomInt(height - 1);
		for (int offset = 0; offset < depth; offset += 2) {
			drawWEEmptyShelve(chunk, x, y, z + offset, 1, width);
			for (int run = 0; run < depth; run++)
				setStorageBlocks(generator, chunk, odds, x + run, y + 1,
						y + 1 + Math.max(minheight, odds.getRandomInt(height - 1)), z + offset);
		}
	}

}
