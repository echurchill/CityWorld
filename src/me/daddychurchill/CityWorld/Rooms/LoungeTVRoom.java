package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class LoungeTVRoom extends LoungeRoom {

	public LoungeTVRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		default:
		case NORTH:
			chunk.setBlock(x, y, z + depth - 1, Material.BIRCH_STAIRS, BlockFace.SOUTH);
			chunk.setBlock(x + 1, y, z + depth - 1, Material.BIRCH_STAIRS, BlockFace.SOUTH);
			chunk.setBlock(x + 2, y, z + depth - 1, Material.BIRCH_STAIRS, BlockFace.SOUTH);

			chunk.setBlocks(x, x + 3, y, y + height, z - 1, z, materialWall);

			// TODO add picture to wall at x, x + 3, y, y + height, z, z + 1
			break;
		case SOUTH:
			chunk.setBlock(x, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
			chunk.setBlock(x + 1, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
			chunk.setBlock(x + 2, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);

			chunk.setBlocks(x, x + 3, y, y + height, z + depth, z + depth + 1, materialWall);
			break;
		case WEST:
			chunk.setBlock(x + width - 1, y, z, Material.BIRCH_STAIRS, BlockFace.EAST);
			chunk.setBlock(x + width - 1, y, z + 1, Material.BIRCH_STAIRS, BlockFace.EAST);
			chunk.setBlock(x + width - 1, y, z + 2, Material.BIRCH_STAIRS, BlockFace.EAST);

			chunk.setBlocks(x - 1, x, y, y + height, z, z + 3, materialWall);
			break;
		case EAST:
			chunk.setBlock(x, y, z, Material.BIRCH_STAIRS, BlockFace.WEST);
			chunk.setBlock(x, y, z + 1, Material.BIRCH_STAIRS, BlockFace.WEST);
			chunk.setBlock(x, y, z + 2, Material.BIRCH_STAIRS, BlockFace.WEST);

			chunk.setBlocks(x + width, x + width + 1, y, y + height, z, z + 3, materialWall);
			break;
		}
	}

}
