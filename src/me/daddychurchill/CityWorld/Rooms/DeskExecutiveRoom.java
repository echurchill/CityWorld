package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class DeskExecutiveRoom extends DeskRoom {

	public DeskExecutiveRoom() {
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
			chunk.setTable(x, x + 3, y, z, z + 1, tableLeg, tableTop);
			chunk.setBlock(x + 1, y, z + 1, Material.BIRCH_STAIRS, BlockFace.SOUTH);
			break;
		case SOUTH:
			chunk.setTable(x, x + 3, y, z + 2, z + 3, tableLeg, tableTop);
			chunk.setBlock(x + 1, y, z + 1, Material.BIRCH_STAIRS, BlockFace.NORTH);
			break;
		case WEST:
			chunk.setTable(x, x + 1, y, z, z + 3, tableLeg, tableTop);
			chunk.setBlock(x + 1, y, z + 1, Material.BIRCH_STAIRS, BlockFace.EAST);
			break;
		case EAST:
			chunk.setTable(x + 2, x + 3, y, z, z + 3, tableLeg, tableTop);
			chunk.setBlock(x + 1, y, z + 1, Material.BIRCH_STAIRS, BlockFace.WEST);
			break;
		}
	}

}
