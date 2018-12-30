package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class LoungeCouchRoom extends LoungeRoom {

	public LoungeCouchRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		default:
		case NORTH:
			for (int x1 = x; x1 < x + width; x1++)
				chunk.setBlock(x1, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
			break;
		case SOUTH:
			for (int x1 = x; x1 < x + width; x1++)
				chunk.setBlock(x1, y, z + depth - 1, Material.BIRCH_STAIRS, BlockFace.SOUTH);
			break;
		case WEST:
			for (int z1 = z; z1 < z + depth; z1++)
				chunk.setBlock(x, y, z1, Material.BIRCH_STAIRS, BlockFace.WEST);
			break;
		case EAST:
			for (int z1 = z; z1 < z + depth; z1++)
				chunk.setBlock(x + width - 1, y, z1, Material.BIRCH_STAIRS, BlockFace.EAST);
			break;
		}
	}

}
