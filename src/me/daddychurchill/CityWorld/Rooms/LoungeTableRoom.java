package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class LoungeTableRoom extends LoungeRoom {

	public LoungeTableRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {
		Material tableLeg = getTableLeg(odds);
		Material tableTop = getTableTop(odds);

		int offset;
		switch (sideWithWall) {
		default:
		case NORTH:
		case SOUTH:
			offset = odds.getRandomInt(width);
			chunk.setTable(x + offset, x + 1 + offset, y, z, z + depth, tableLeg, tableTop);
			break;
		case WEST:
		case EAST:
			offset = odds.getRandomInt(depth);
			chunk.setTable(x, x + width, y, z + offset, z + 1 + offset, tableLeg, tableTop);
			break;
		}
	}

}
