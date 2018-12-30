package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class MeetingForSixRoom extends MeetingForFourRoom {

	public MeetingForSixRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {

		super.drawFixture(generator, chunk, odds, floor, x, y, z, width, height, depth, sideWithWall, materialWall,
				materialGlass);

		switch (sideWithWall) {
		default:
		case NORTH:
		case SOUTH:
			chunk.setBlock(x, y, z + 1, Material.BIRCH_STAIRS, BlockFace.WEST);
			chunk.setBlock(x + 2, y, z + 1, Material.BIRCH_STAIRS, BlockFace.EAST);
			break;
		case WEST:
		case EAST:
			chunk.setBlock(x + 1, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
			chunk.setBlock(x + 1, y, z + 2, Material.BIRCH_STAIRS, BlockFace.SOUTH);
			break;
		}
	}

}
