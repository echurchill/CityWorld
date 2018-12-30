package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class LibraryStudyRoom extends LibraryRoom {

	public LibraryStudyRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {
		Material tableLeg = getTableLeg(odds);
		Material tableTop = getTableTop(odds);

		switch (sideWithWall) {
		default:
		case NORTH:
			chunk.setBlocks(x, x + width, y, y + height, z, z + 1, Material.BOOKSHELF);
			chunk.setBlock(x, y, z + 2, Material.BIRCH_STAIRS, BlockFace.WEST);
			chunk.setTable(x + 1, y, z + 2, tableLeg, tableTop);
			break;
		case SOUTH:
			chunk.setBlocks(x, x + width, y, y + height, z + depth - 1, z + depth, Material.BOOKSHELF);
			chunk.setBlock(x, y, z, Material.BIRCH_STAIRS, BlockFace.WEST);
			chunk.setTable(x + 1, y, z, tableLeg, tableTop);
			chunk.setBlock(x + 2, y, z, Material.BIRCH_STAIRS, BlockFace.EAST);
			break;
		case WEST:
			chunk.setBlocks(x, x + 1, y, y + height, z, z + depth, Material.BOOKSHELF);
			chunk.setBlock(x + 2, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
			chunk.setTable(x + 2, y, z + 1, tableLeg, tableTop);
			chunk.setBlock(x + 2, y, z + 2, Material.BIRCH_STAIRS, BlockFace.SOUTH);
			break;
		case EAST:
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth, Material.BOOKSHELF);
			chunk.setBlock(x, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
			chunk.setTable(x, y, z + 1, tableLeg, tableTop);
			chunk.setBlock(x, y, z + 2, Material.BIRCH_STAIRS, BlockFace.SOUTH);
			break;
		}
	}

}
