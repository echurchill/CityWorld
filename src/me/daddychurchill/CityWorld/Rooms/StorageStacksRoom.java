package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class StorageStacksRoom extends StorageTypeRoom {

	public StorageStacksRoom(Material type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {

		int minheight = odds.getRandomInt(height / 2);
		for (int x1 = x; x1 < x + width; x1++) {
			for (int z1 = z; z1 < z + depth; z1++) {
				setStorageBlocks(generator, chunk, odds, x1, y, y + Math.max(minheight, odds.getRandomInt(height - 1)),
						z1);
			}
		}
	}

}
