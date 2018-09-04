package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.CityWorldGenerator;


import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class LoungeTrioRoom extends LoungeRoom {

	public LoungeTrioRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			BlockFace sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		default:
		case NORTH:
			chunk.setBlock(x + 1, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
			chunk.setBlock(x, y, z + 1, Material.BIRCH_STAIRS, BlockFace.WEST);
			chunk.setBlock(x + 2, y, z + 1, Material.BIRCH_STAIRS, BlockFace.EAST);

			chunk.setTable(x, y, z);
			chunk.setTable(x + 2, y, z);
			break;
		case SOUTH:
			chunk.setBlock(x + 1, y, z + 2, Material.BIRCH_STAIRS, BlockFace.SOUTH);
			chunk.setBlock(x, y, z + 1, Material.BIRCH_STAIRS, BlockFace.WEST);
			chunk.setBlock(x + 2, y, z + 1, Material.BIRCH_STAIRS, BlockFace.EAST);

			chunk.setTable(x, y, z + 2);
			chunk.setTable(x + 2, y, z + 2);
			break;
		case WEST:
			chunk.setBlock(x + 1, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
			chunk.setBlock(x + 1, y, z + 2, Material.BIRCH_STAIRS, BlockFace.SOUTH);
			chunk.setBlock(x, y, z + 1, Material.BIRCH_STAIRS, BlockFace.WEST);

			chunk.setTable(x, y, z);
			chunk.setTable(x, y, z + 2);
			break;
		case EAST:
			chunk.setBlock(x + 1, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
			chunk.setBlock(x + 1, y, z + 2, Material.BIRCH_STAIRS, BlockFace.SOUTH);
			chunk.setBlock(x + 2, y, z + 1, Material.BIRCH_STAIRS, BlockFace.EAST);
			
			chunk.setTable(x + 2, y, z);
			chunk.setTable(x + 2, y, z + 2);
			break;
		}

	}

}
