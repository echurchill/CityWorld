package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class LoungeGameRoom extends LoungeChairsRoom {

	public LoungeGameRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {

		super.drawFixture(generator, chunk, odds, floor, x, y, z, width, height, depth, sideWithWall, materialWall,
				materialGlass);

		Material tableLeg = getTableLeg(odds);
		Material tableTop = getTableTop(odds);

		switch (sideWithWall) {
		default:
		case NORTH:
		case SOUTH:
			chunk.setTable(x + 1, y, z, tableLeg, tableTop);
			chunk.setTable(x + 1, y, z + 2, tableLeg, tableTop);
			break;
		case WEST:
		case EAST:
			chunk.setTable(x, y, z + 1, tableLeg, tableTop);
			chunk.setTable(x + 2, y, z + 1, tableLeg, tableTop);
			break;
		}
	}

}
